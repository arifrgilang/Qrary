package com.rz.qrary.maintainer.daftar

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.rz.qrary.maintainer.admin.MaintainerActivity
import com.rz.qrary.repository.Mahasiswa
import com.rz.qrary.repository.Repository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class DaftarPresenter(var ctx: DaftarActivity, var mView: DaftarContract.View): DaftarContract.Presenter {
    override fun checkavailability(npm: String) {
        mView.showLoading(true)
        if(npm.isEmpty() || npm.length != 12){
            mView.showToast("Isi form NPM dengan benar!")
            mView.showLoading(false)
        } else {
            checkUrl(npm)
        }
    }

    override fun daftar(nama: String, npm: String, pw: String) {
        val fakultas = npm.substring(0,6)
        val angkatan = "20"+npm.substring(6,8)
        val urlFoto = "https://media.unpad.ac.id/photo/mahasiswa/" +
                fakultas + "/" + angkatan+ "/" + npm +".JPG"
        val mhs = Mahasiswa(nama,npm,pw,urlFoto,"0")
        Repository.firebase()
            .child("data_mhs")
            .child(npm)
            .setValue(mhs)
        mView.showToast("Berhasil mendaftarkan user!")
        ctx.finish()
    }

    private fun checkUrl(npm: String) {

        var mhs: Mahasiswa?
        Repository.firebase().child("data_mhs").child(npm)
            .addValueEventListener(object: ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {
                    mhs = p0.getValue(Mahasiswa::class.java)
                    if (mhs != null){
                        mView.showToast("NPM sudah terdaftar")
                    } else {
                        urlCheck(npm)
                    }
                }
            })

    }

    private fun urlCheck(npm: String){
        val fakultas = npm.substring(0,6)
        val angkatan = "20"+npm.substring(6,8)
        var response = ""
        val url = URL("https://media.unpad.ac.id/photo/mahasiswa/"
                + fakultas +"/" + angkatan+ "/" + npm +".JPG")

        Thread(Runnable{
            val huc: HttpURLConnection = url.openConnection() as HttpURLConnection
            huc.requestMethod = "GET"
            huc.connect()
            response = huc.responseCode.toString()
            Log.d("response code", response)

            ctx.runOnUiThread {
                if(response == "404"){
                    mView.showLoading(false)
                    mView.showToast("NPM Tidak ada!")
                } else if (response == "200"){
                    mView.showLoading(false)
                    mView.setTersedia()
                    mView.showDaftarForm(true, url.toString())
                }
            }
        }).start()
    }

}