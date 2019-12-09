package com.rz.qrary.user.main.preview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.rz.qrary.R
import com.rz.qrary.repository.Repository

class PreviewActivity : AppCompatActivity(), PreviewContract.View {

    lateinit var mPresenter: PreviewContract.Presenter
    var npm: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        npm = Repository.localDb(this).getString(Repository.NPM,"")
        mPresenter = PreviewPresenter(this)
        if(npm!=null){
            mPresenter.setListener(npm!!)
        }
        Log.d("npm Preview", npm)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy", npm)
        mPresenter.setModePinjamValue(npm!!,"0")
    }
    override fun finishActivity() = finish()
}
