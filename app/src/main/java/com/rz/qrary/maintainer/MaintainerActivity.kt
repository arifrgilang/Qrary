package com.rz.qrary.maintainer

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.rz.qrary.R
import com.rz.qrary.repository.Mahasiswa
import com.rz.qrary.repository.Repository
import kotlinx.android.synthetic.main.activity_maintainer.*
import me.ydcool.lib.qrmodule.activity.QrScannerActivity

class MaintainerActivity : AppCompatActivity() {

    lateinit private var pengunjungAdapter: PengunjungRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintainer)
        button_pengunjung.setOnClickListener{
            val intent = Intent(this, QrScannerActivity::class.java)
            startActivityForResult(intent, QrScannerActivity.QR_REQUEST_CODE)
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
        pengunjungAdapter = PengunjungRVAdapter(this, option)
        rv_pengunjung.adapter = pengunjungAdapter

        pengunjungAdapter.startListening()

        // Peminjam RecyclerView

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == QrScannerActivity.QR_REQUEST_CODE){
            val npm = data!!.extras!!.getString(QrScannerActivity.QR_RESULT_STR)!!
            Log.d("data" , npm.substring(5))
            Log.d("data" , npm.substring(0,5))
            if(npm.substring(0,5) == "qrary"){
                Repository.addPengunjung(npm.substring(5))
            } else {
                Toast.makeText(this, "QR Code tidak terdaftar!", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
