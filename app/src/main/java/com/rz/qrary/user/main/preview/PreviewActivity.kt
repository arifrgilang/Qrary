package com.rz.qrary.user.main.preview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.rz.qrary.R
import com.rz.qrary.maintainer.konfirmasi.QueueRvAdapter
import com.rz.qrary.repository.Repository
import com.rz.qrary.repository.model.Book
import kotlinx.android.synthetic.main.activity_preview.*

class PreviewActivity : AppCompatActivity(), PreviewContract.View {

    private var mAdapter: QueueRvAdapter? = null

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
        initRV()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("onDestroy", npm)
        mPresenter.setModePinjamValue(npm!!,"0")
        Repository.removeTempList(npm!!)
    }
    override fun finishActivity() = finish()

    private fun initRV(){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        rv_preview.layoutManager = layoutManager
        rv_preview.itemAnimator = DefaultItemAnimator()
        val option = FirebaseRecyclerOptions.Builder<Book>()
            .setQuery(Repository.firebase().child("list_konfirm").child(npm!!),
                Book::class.java)
            .build()
        mAdapter = QueueRvAdapter(npm!!, option)
        rv_preview.adapter = mAdapter
        mAdapter!!.startListening()
    }
}
