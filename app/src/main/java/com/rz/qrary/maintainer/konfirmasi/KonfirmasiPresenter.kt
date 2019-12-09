package com.rz.qrary.maintainer.konfirmasi

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.rz.qrary.repository.model.Mahasiswa
import com.rz.qrary.repository.Repository

class KonfirmasiPresenter(val mView: KonfirmasiContract.View): KonfirmasiContract.Presenter {
    lateinit var query: DatabaseReference
    lateinit var listener: ValueEventListener

    override fun getUserData(npm: String) {
        query = Repository.firebase().child("data_mhs").child(npm)
        listener = object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                var mhs: Mahasiswa? = null
                mhs = p0.getValue(Mahasiswa::class.java)
                if (mhs != null) {
                    Repository.setPinjamMode(mhs.npm, "1")
                    if (mhs.mode_pinjam == "1") {
                        Log.d("setKonfirmasiPresenter", mhs.mode_pinjam)
                        mView.setUserData(mhs)
                    }
                }
            }
        }
        query.addValueEventListener(listener)
    }

    override fun setListener(npm: String) {
        Repository.firebase().child("data_mhs").child(npm)
            .addValueEventListener(object: ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    var mhs: Mahasiswa? = null
                    mhs = p0.getValue(Mahasiswa::class.java)
                    if(mhs!=null){
                        Log.d("kofirm 0", mhs.mode_pinjam)
                        if(mhs.mode_pinjam == "0"){
                            mView.finishActivity()
                        }
                    }
                }
            })
    }

    override fun setModePinjamValue(npm: String, value: String) {
        Repository.firebase().child("data_mhs").child(npm)
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    var mhs: Mahasiswa? = null
                    mhs = p0.getValue(Mahasiswa::class.java)
                    if(mhs!=null){
                        mhs.mode_pinjam = value
                        Log.d("setmodepinjam", mhs.mode_pinjam)
                        Repository.setPinjamMode(mhs.npm,value)
                    }
                }
            })
    }

    override fun killListener() {
        query.removeEventListener(listener)
    }


}