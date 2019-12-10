package com.rz.qrary.maintainer.addbuku

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rz.qrary.R
import com.rz.qrary.repository.Repository
import kotlinx.android.synthetic.main.activity_tambah_buku.*
import me.ydcool.lib.qrmodule.activity.QrScannerActivity

class TambahBukuActivity : AppCompatActivity(), TambahBukuContract.View {

    private var issn: String? = null
    private lateinit var mPresenter: TambahBukuContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_buku)
        mPresenter = TambahBukuPresenter(this)
        val intent = Intent(this, QrScannerActivity::class.java)
        startActivityForResult(intent, QrScannerActivity.QR_REQUEST_CODE)
        addbuku_button.setOnClickListener {
            if (addbuku_judul.text.isNullOrEmpty() ||
                    addbuku_bahasa.text.isNullOrEmpty() ||
                    addbuku_jumlah_halaman.text.isNullOrEmpty() ||
                    addbuku_penerbit.text.isNullOrEmpty() ||
                    addbuku_penulis.text.isNullOrEmpty()){
                Toast.makeText(this, "Isi form dengan benar!", Toast.LENGTH_SHORT).show()
            } else {
                mPresenter.submit(issn.toString(), addbuku_judul.text.toString(),
                    addbuku_bahasa.text.toString(),
                    addbuku_jumlah_halaman.text.toString(),
                    addbuku_penerbit.text.toString(),
                    addbuku_penulis.text.toString())
                Toast.makeText(this, "Buku telah ditambahkan ke database", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == QrScannerActivity.QR_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK) {
                issn = data!!.extras!!.getString(QrScannerActivity.QR_RESULT_STR)!!
                mPresenter.cekIssn(this, issn!!)
            } else {
                Toast.makeText(this, "Scan dibatalkan", Toast.LENGTH_SHORT).show()
                issn = ""
                finish()
            }
        }
    }

}
