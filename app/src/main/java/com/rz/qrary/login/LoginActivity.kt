package com.rz.qrary.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rz.qrary.R
import com.rz.qrary.maintainer.admin.MaintainerActivity
import com.rz.qrary.user.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var mPresenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mPresenter = LoginPresenter(this, this)

        login_btn.setOnClickListener {
            mPresenter.checkLogin(username.text.toString(), password.text.toString())
        }
    }

    override fun showToast(text: String) =
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

    override fun navigateToUser() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun navigateToMaintainer() {
        startActivity(Intent(this, MaintainerActivity::class.java))
        finish()
    }

    override fun showLoading(condition: Boolean) {
        login_btn.text = if (condition) "Please Wait. . ." else "Login"
        login_btn.isEnabled = !condition
    }
}
