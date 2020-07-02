package com.dmioto.desafio_android_daniel_mioto

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.dmioto.desafio_android_daniel_mioto.util.BaseActivity
import com.dmioto.desafio_android_daniel_mioto.util.ModuleHelper

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        animateSplash()
    }

    private fun openHeroesList() {
        openModule(ModuleHelper.HEROES_LIST)
        finish()
    }


    private fun animateSplash() {

        AnimationUtils.loadAnimation(this, R.anim.bounce_animation).run {
            findViewById<ImageView>(R.id.image_to_anim).startAnimation(this)

            this.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {

                }
                override fun onAnimationEnd(animation: Animation) {
                    openHeroesList()
                }
                override fun onAnimationRepeat(animation: Animation) {

                }
            })
        }
    }
}