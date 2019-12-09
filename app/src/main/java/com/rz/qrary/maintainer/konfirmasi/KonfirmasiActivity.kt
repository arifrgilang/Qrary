package com.rz.qrary.maintainer.konfirmasi

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.rz.qrary.R
import com.rz.qrary.repository.model.Mahasiswa
import kotlinx.android.synthetic.main.activity_konfirmasi.*
import me.ydcool.lib.qrmodule.activity.QrScannerActivity

class KonfirmasiActivity : AppCompatActivity(), KonfirmasiContract.View {

    private var peminjam: Mahasiswa? = null
    lateinit private var mPresenter: KonfirmasiContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konfirmasi)
        mPresenter = KonfirmasiPresenter(this)
        if (peminjam == null){
            val intent = Intent(this, QrScannerActivity::class.java)
            startActivityForResult(intent, QrScannerActivity.QR_REQUEST_CODE)
        }
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
                    mPresenter.getUserData(npm.substring(5))
                    Toast.makeText(this, "Pengunjung ditambahkan", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "QR Code tidak terdaftar!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Scan dibatalkan", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun setUserData(mhs: Mahasiswa) {
        Glide.with(this)
            .load(mhs.url_foto)
            .centerCrop()
            .into(image_peminjam)

        nama_peminjam.text = mhs.nama
        npm_peminjam.text = mhs.npm
    }

    override fun finishActivity() = finish()

}
