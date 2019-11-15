package com.rz.qrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val fadeAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        logo_splash.startAnimation((fadeAnim))

        Handler().postDelayed({
            val intent =  Intent(this, LoginActivity::class.java)
//            intent.putExtra(LoginActivity.EXT)
            val options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, logo_splash, "qrary_logo")

            startActivity(intent, options.toBundle())
            ActivityCompat.finishAfterTransition(this)
//            finish()
        }, 2000)
    }
}
