package com.rz.qrary.maintainer.admin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.rz.qrary.R
import com.rz.qrary.login.LoginActivity
import com.rz.qrary.maintainer.addbuku.TambahBukuActivity
import com.rz.qrary.maintainer.daftar.DaftarActivity
import com.rz.qrary.maintainer.konfirmasi.KonfirmasiActivity
import com.rz.qrary.repository.model.Mahasiswa
import com.rz.qrary.repository.Repository
import kotlinx.android.synthetic.main.activity_maintainer.*
import me.ydcool.lib.qrmodule.activity.QrScannerActivity

class MaintainerActivity : AppCompatActivity() {

    lateinit private var pengunjungAdapter: PengunjungRVAdapter
    lateinit private var peminjamAdapter: PeminjamRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintainer)
        // Button Pengunjung
        button_pengunjung.setOnClickListener{
            val intent = Intent(this, QrScannerActivity::class.java)
            startActivityForResult(intent, QrScannerActivity.QR_REQUEST_CODE)
        }
        // Button Konfirmasi Pinjam
        button_pinjam.setOnClickListener{
            startActivity(Intent(this, KonfirmasiActivity::class.java))
        }

        // Pengunjung RecyclerView
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        rv_pengunjung.layoutManager = layoutManager
        rv_pengunjung.itemAnimator = DefaultItemAnimator()
        val option = FirebaseRecyclerOptions.Builder<Mahasiswa>()
            .setQuery(Repository.getPengunjung(), Mahasiswa::class.java)
            .build()
        pengunjungAdapter =
            PengunjungRVAdapter(this, option)
        rv_pengunjung.adapter = pengunjungAdapter
        pengunjungAdapter.startListening()

        // Peminjam RecyclerView
        val layoutManager2 = LinearLayoutManager(this)
        layoutManager2.reverseLayout = true
        layoutManager2.stackFromEnd = true
        rv_peminjam.layoutManager = layoutManager2
        rv_peminjam.itemAnimator = DefaultItemAnimator()
        val option2 = FirebaseRecyclerOptions.Builder<Mahasiswa>()
            .setQuery(Repository.firebase().child("pinjam_history"), Mahasiswa::class.java)
            .build()
        peminjamAdapter = PeminjamRVAdapter(this, option2)
        rv_peminjam.adapter = peminjamAdapter
        peminjamAdapter.startListening()

        // Button Logout
        logout_admin_button.setOnClickListener {
            startActivity(Intent(this , LoginActivity::class.java))
            finish()
        }
        // Button Daftar
        cek_npm_button.setOnClickListener {
            startActivity(Intent(this, DaftarActivity::class.java))
        }
        // Button addbuku
        add_buku.setOnClickListener {
            startActivity(Intent(this, TambahBukuActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == QrScannerActivity.QR_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                val npm = data!!.extras!!.getString(QrScannerActivity.QR_RESULT_STR)!!
                Log.d("data" , npm.substring(5))
                Log.d("data" , npm.substring(0,5))
                if(npm.substring(0,5) == "qrary"){
                    Repository.addPengunjung(npm.substring(5))
                    Toast.makeText(this, "Pengunjung ditambahkan", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "QR Code tidak terdaftar!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Scan dibatalkan", Toast.LENGTH_SHORT).show()
            }
        } else {

        }
    }
}
