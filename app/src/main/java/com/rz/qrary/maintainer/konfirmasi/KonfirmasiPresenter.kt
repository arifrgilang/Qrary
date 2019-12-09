package com.rz.qrary.maintainer.konfirmasi

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.rz.qrary.repository.model.Mahasiswa
import com.rz.qrary.repository.Repository

class KonfirmasiPresenter(val mView: KonfirmasiContract.View): KonfirmasiContract.Presenter {
    override fun getUserData(npm: String) {
        Repository.firebase().child("data_mhs").child(npm)
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    val mhs = p0.getValue(Mahasiswa::class.java)
                    if(mhs!=null){
                        mView.setUserData(mhs)
                        Repository.setPinjamMode(mhs.npm, "1")
                    }
                }
            })
    }

    override fun setListener(npm: String) {
        Repository.firebase().child("data_mhs").child(npm)
            .addValueEventListener(object: ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    val mhs = p0.getValue(Mahasiswa::class.java)
                    if(mhs!!.mode_pinjam == "0"){
                        mView.finishActivity()
                    }
                }
            })
    }


}