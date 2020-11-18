package com.example.os_proj

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DEBUG_PROPERTY_NAME
import kotlinx.coroutines.DEBUG_PROPERTY_VALUE_ON

@HiltAndroidApp
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        System.setProperty(DEBUG_PROPERTY_NAME, DEBUG_PROPERTY_VALUE_ON)
        //AndroidNetworking.initialize(applicationContext)
        //AndroidNetworking.enableLogging()

        /*val imageLoader = ImageLoader.Builder(this)
            .componentRegistry {
                add(SvgDecoder(this@Reshuege))
            }
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(this))
                    .build()
            }
            .build()
        Coil.setImageLoader(imageLoader)*/
    }
}