package com.dmioto.desafio_android_daniel_mioto

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class AppInit: Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }

}