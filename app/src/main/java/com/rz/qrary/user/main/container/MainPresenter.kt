package com.rz.qrary.user.main.container

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.rz.qrary.repository.Repository
import com.rz.qrary.repository.model.Mahasiswa

class MainPresenter(val mView: MainContract.View): MainContract.Presenter {
    lateinit var ref: DatabaseReference
    lateinit var listener: ValueEventListener
    override fun setListener(npm: String) {
        ref = Repository.firebase().child("data_mhs").child(npm)
        listener = object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                var mhs: Mahasiswa? = null
                mhs = p0.getValue(Mahasiswa::class.java)

                if(mhs!=null && mhs.mode_pinjam == "1"){
                    Log.d("mode", mhs.mode_pinjam)
                    mView.navigateToConfirmView()
//                        Log.d("setlistener", mhs.mode_pinjam)
//                        mView.finishActivity()
                }
            }
        }
        ref.addValueEventListener(listener)
    }

    override fun destroyListener() {
        ref.removeEventListener(listener)
    }
}