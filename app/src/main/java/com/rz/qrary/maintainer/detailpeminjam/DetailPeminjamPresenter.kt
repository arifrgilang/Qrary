package com.rz.qrary.maintainer.detailpeminjam

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.rz.qrary.repository.Repository
import com.rz.qrary.repository.model.Mahasiswa

class DetailPeminjamPresenter(val mView: DetailPeminjamContract.View): DetailPeminjamContract.Presenter {
    override fun getUserProfile(npm: String) {
        val ref = Repository.firebase().child("data_mhs").child(npm)
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                var mhs: Mahasiswa? = null
                mhs = p0.getValue(Mahasiswa::class.java)
                if(mhs!=null){
                    mView.setProfileView(mhs)
                    ref.removeEventListener(this)
                }
            }
        })
    }
}