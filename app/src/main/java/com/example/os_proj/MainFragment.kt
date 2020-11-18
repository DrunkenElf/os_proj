package com.example.os_proj

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.os_proj.databinding.FragmentMainBinding
import com.example.os_proj.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import tellh.com.recyclertreeview_lib.TreeNode
import tellh.com.recyclertreeview_lib.TreeViewAdapter

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class MainFragment : Fragment() {
    val sharedModel: MainViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null

    private lateinit var groupLayoutManager: LinearLayoutManager
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private  var listId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listId = arguments?.getString("list_id")
        Log.d("LIST_ID: ", listId.toString())

        listId?.let{ sharedModel.getNodes(listId) }

        _binding = FragmentMainBinding.inflate(inflater, container, false)


        groupLayoutManager = LinearLayoutManager(context)

        binding.recycler.apply {
            layoutManager = groupLayoutManager
        }


        binding.recycler.adapter = TreeViewAdapter(listOf(ChildBinder(), HeadBinder()))
            .apply{
            setOnTreeNodeListener(object : TreeViewAdapter.OnTreeNodeListener {
                override fun onClick(p0: TreeNode<*>?, p1: RecyclerView.ViewHolder?): Boolean {
                    Log.i("on click", "click")
                    if (!p0!!.isLeaf) {
                        onToggle(!p0.isExpand, p1)
                        if (!p0.isExpand) this@apply.collapseBrotherNode(p0)
                    }
                    return false
                }

                override fun onToggle(p0: Boolean, p1: RecyclerView.ViewHolder?) {
                    TODO("Not yet implemented")
                }
            })
        }
        _binding?.recycler?.apply { setAdapter(adapter) }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedModel.nodes.observe(viewLifecycleOwner, { updated ->
            Log.d("observe Main", "")
            _binding?.recycler?.adapter = TreeViewAdapter(updated, listOf(ChildBinder(), HeadBinder())).also {
                it.notifyDataSetChanged()
            }
            _binding?.recycler?.adapter?.notifyDataSetChanged()
        })

        /*binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}