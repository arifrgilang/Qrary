package com.rz.qrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rz.qrary.maintainer.MaintainerActivity
import com.rz.qrary.user.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn.setOnClickListener {
            if(username.text.isNullOrEmpty() || password.text.isNullOrEmpty()){
                Toast.makeText(this,
                    "Isi form login!",
                    Toast.LENGTH_SHORT)
                    .show()
            } else {
                if(username.text!!.toString().equals("arif17003")
                    && password.text!!.toString().equals("argil")){
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else if(username.text!!.toString().equals("admin")
                    && password.text!!.toString().equals("nimda")){
                    startActivity(Intent(this, MaintainerActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this,
                        "PAuS ID atau Password salah!",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}
