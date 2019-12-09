package com.rz.qrary.user.main.container

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.rz.qrary.R
import com.rz.qrary.login.LoginActivity
import com.rz.qrary.repository.Repository
import com.rz.qrary.user.main.preview.PreviewActivity

class MainActivity : AppCompatActivity(), MainContract.View {

    lateinit var mPresenter: MainContract.Presenter
    var npmUser: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenter(this)
        val mainPagerAdapter =
            MainPagerAdapter(
                this,
                supportFragmentManager
            )
//        navigateToConfirmView()
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = mainPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        npmUser = Repository.localDb(this).getString(Repository.NPM,"")
        mPresenter.setListener(npmUser!!)
    }

    override fun navigateToConfirmView() {
        Log.d("navigate", "berhasil")
        startActivity(Intent(this, PreviewActivity::class.java))
    }

    override fun finishActivity() = finish()
}