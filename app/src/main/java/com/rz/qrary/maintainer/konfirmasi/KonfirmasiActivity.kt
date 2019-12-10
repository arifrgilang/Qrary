package com.rz.qrary.maintainer.konfirmasi

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.rz.qrary.R
import com.rz.qrary.repository.Repository
import com.rz.qrary.repository.model.Book
import com.rz.qrary.repository.model.Mahasiswa
import kotlinx.android.synthetic.main.activity_konfirmasi.*
import me.ydcool.lib.qrmodule.activity.QrScannerActivity

class KonfirmasiActivity : AppCompatActivity(), KonfirmasiContract.View {

    private var count = 0
    private var peminjam: Mahasiswa? = null
    lateinit private var mPresenter: KonfirmasiContract.Presenter
    lateinit private var npmMhs: String
    private var mAdapter: QueueRvAdapter? = null
    private val ADD_BOOK = 300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi)
        mPresenter = KonfirmasiPresenter(this)
        if (peminjam == null){
            val intent = Intent(this, QrScannerActivity::class.java)
            startActivityForResult(intent, QrScannerActivity.QR_REQUEST_CODE)
        }

        tambah_konfirmasi.setOnClickListener {
            val intent = Intent(this, QrScannerActivity::class.java)
            startActivityForResult(intent, ADD_BOOK)
        }

        selesai_konfirmasi.setOnClickListener {
            if(count>0){
                val dialog = ConfirmFragment()
                dialog.show(supportFragmentManager, ConfirmFragment().tag)
            } else {
                Toast.makeText(this, "Belum ada buku yang ditambahkan!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRV(){
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        rv_konfirmasi.layoutManager = layoutManager
        rv_konfirmasi.itemAnimator = DefaultItemAnimator()
        val option = FirebaseRecyclerOptions.Builder<Book>()
            .setQuery(Repository.firebase().child("list_konfirm").child(npmMhs),
                Book::class.java)
            .build()
        mAdapter = QueueRvAdapter(npmMhs, option)
        rv_konfirmasi.adapter = mAdapter
        mAdapter!!.startListening()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == QrScannerActivity.QR_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK) {
                val npm = data!!.extras!!.getString(QrScannerActivity.QR_RESULT_STR)!!
                Log.d("data" , npm.substring(5))
                Log.d("data" , npm.substring(0,5))
                if(npm.substring(0,5) == "qrary"){
                    // Get user data and set mode_pinjam
                    npmMhs = npm.substring(5)
                    mPresenter.getUserData(npmMhs)
                    initRV()
                    Toast.makeText(this, "Peminjam ditambahkan", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "QR Code tidak terdaftar!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Scan dibatalkan", Toast.LENGTH_SHORT).show()
                npmMhs = ""
                finish()
            }
        } else if(requestCode == ADD_BOOK){
            if(resultCode == Activity.RESULT_OK) {
                val issn = data!!.extras!!.getString(QrScannerActivity.QR_RESULT_STR)
                Log.d("ADDBOOK", issn!!)
                Repository.addBookList(this, issn, npmMhs)
                count++
            } else {
                Toast.makeText(this, "Scan dibatalkan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(npmMhs!= ""){
            mAdapter = null
            mPresenter.setModePinjamValue(npmMhs, "0")
            mPresenter.killListener()
            Repository.removeTempList(npmMhs)
        }
    }

    override fun setUserData(mhs: Mahasiswa) {
        peminjam = mhs
        Glide.with(this)
            .load(mhs.url_foto)
            .centerCrop()
            .into(image_peminjam)

        nama_peminjam.text = mhs.nama
        npm_peminjam.text = mhs.npm
        mPresenter.setListener(npmMhs)
    }

    override fun finishActivity() = finish()

}
