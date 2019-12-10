package com.rz.qrary.maintainer.detailpeminjam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.rz.qrary.R
import com.rz.qrary.repository.Repository
import com.rz.qrary.repository.model.Book
import com.rz.qrary.repository.model.Mahasiswa
import kotlinx.android.synthetic.main.activity_detail_peminjam.*

class DetailPeminjamActivity : AppCompatActivity(), DetailPeminjamContract.View {

    lateinit var npm: String
    lateinit var mPresenter: DetailPeminjamContract.Presenter
    lateinit var mAdapter: DetailPeminjamRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_peminjam)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        npm = intent.getStringExtra("npm")!!
        mPresenter = DetailPeminjamPresenter(this)

        mPresenter.getUserProfile(npm)

        val layoutManager = LinearLayoutManager(this)
                layoutManager.reverseLayout = true
                layoutManager.stackFromEnd = true
                rv_detail_peminjam.layoutManager = layoutManager
                rv_detail_peminjam.itemAnimator = DefaultItemAnimator()
                val option = FirebaseRecyclerOptions.Builder<Book>()
                    .setQuery(
                        Repository.firebase().child("pinjam_history").child(npm),
                        Book::class.java)
                    .build()
                mAdapter = DetailPeminjamRVAdapter(npm, option)
                rv_detail_peminjam.adapter = mAdapter
                mAdapter.startListening()

        button_selesai_meminjam.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("npm",npm)

            val dialog = SelesaiFragment()
            dialog.arguments = bundle
            dialog.show(supportFragmentManager, SelesaiFragment().tag)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setProfileView(mhs: Mahasiswa) {
        detail_nama.text = mhs.nama
        detail_npm.text = mhs.npm

        Glide.with(this)
            .load(mhs.url_foto)
            .centerCrop()
            .into(detail_foto)
    }
}
