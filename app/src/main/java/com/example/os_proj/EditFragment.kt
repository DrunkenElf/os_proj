package com.example.os_proj

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.os_proj.R
import com.example.os_proj.databinding.FragmentEditBinding
import com.example.os_proj.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import tellh.com.recyclertreeview_lib.TreeNode
import tellh.com.recyclertreeview_lib.TreeViewAdapter

@AndroidEntryPoint
class EditFragment : Fragment(){

    val sharedModel: MainViewModel by activityViewModels()

    private var _binding: FragmentEditBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val tmp  = arguments?.getString("list_id")
        Log.d("LIST_ID: ", tmp.toString())

        tmp?.let{ sharedModel.getNodes(tmp) }
        _binding = FragmentEditBinding.inflate(inflater, container, false)

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
        }

        //sharedModel.openEdit()

        setEditor()

        sharedModel.taskList.observe(viewLifecycleOwner, {
            setEditor()
            binding.name.setText(it.name)
            binding.description.setText(it.description)
        })
        binding.brnInsert.setOnClickListener {
            sharedModel.update(binding.name.text.toString(), binding.description.text.toString())
        }


        binding.recycler.adapter = MenuRecAdapter(requireContext(), emptyList(), sharedModel)
        //_binding?.recycler?.apply { setAdapter(adapter) }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedModel.tasks.observe(viewLifecycleOwner, { updated ->
            Log.d("observe Main", "")
            _binding?.recycler?.adapter = MenuRecAdapter(requireContext(),
            updated, sharedModel)
            _binding?.recycler?.adapter?.notifyDataSetChanged()
        })
        sharedModel.openEdit()
       /* binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }*/
    }
    fun initHead(){

    }

    fun setEditor(){
        with(binding.includ){
            editDesc.setText("")
            editDesc.setText("")
            btnAdd.setOnClickListener {  }
            btnReset.setOnClickListener {  }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sharedModel.fetchTaskLists()
        _binding = null
    }
}