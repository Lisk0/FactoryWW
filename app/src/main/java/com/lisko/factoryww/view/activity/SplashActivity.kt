package com.lisko.factoryww.view.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle

import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.lisko.factoryww.R
import com.lisko.factoryww.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity(){
    private lateinit var splashBinding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        val splashAnimation= AnimationUtils.loadAnimation(this, R.anim.anim_intro)
        splashBinding.appSplashText.animation= splashAnimation
        splashBinding.appSplashTextRow2.animation= splashAnimation
        splashBinding.appSplashTextRow3.animation= splashAnimation


        splashAnimation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
                //
            }

            override fun onAnimationEnd(p0: Animation?) {
                //go to next screen after animation ends
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }, 1000)
            }

            override fun onAnimationRepeat(p0: Animation?) {
                //
            }

        })


    }
}