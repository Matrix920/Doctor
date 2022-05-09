package com.matrix.doctor

import android.app.ActivityOptions
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.transition.Slide
import android.view.Gravity
import android.view.View

class MainActivity : AppCompatActivity() {

    val hander=Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hander.postDelayed(Runnable {
            try {
                explodeTransitionByCode()
            }catch (e:Exception){
                e.printStackTrace()
            }
        },1000)

        setupWindowAnimation()
    }



    fun explodeTransitionByCode(){
        val options= ActivityOptions.makeSceneTransitionAnimation(this)
        val intent=Intent(applicationContext,MenuActivity::class.java)
        startActivity(intent,options.toBundle())
       // finish()
    }

    public fun nextActivity(v:View){
        explodeTransitionByCode()
    }

    fun setupWindowAnimation(){
        val slideTransition= Slide()
        slideTransition.slideEdge= Gravity.RIGHT
        slideTransition.duration=1000L

        window.reenterTransition=slideTransition
        window.exitTransition=slideTransition

        window.allowReturnTransitionOverlap=false
    }

    override fun onDestroy() {
        super.onDestroy()

        hander.removeCallbacksAndMessages(null)
    }
}
