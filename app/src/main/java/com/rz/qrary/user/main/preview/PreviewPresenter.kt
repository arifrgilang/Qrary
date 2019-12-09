package com.rz.qrary.user.main.preview

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.rz.qrary.repository.Repository
import com.rz.qrary.repository.model.Mahasiswa

class PreviewPresenter(val mView: PreviewContract.View): PreviewContract.Presenter {
    override fun getKonfirmasiData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setListener(npm: String) {
        Repository.firebase().child("data_mhs").child(npm)
            .addValueEventListener(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    var mhs: Mahasiswa? = null
                    mhs = p0.getValue(Mahasiswa::class.java)
                    if(mhs!=null && mhs.mode_pinjam == "0"){
                        Log.d("setlistener", mhs.mode_pinjam)
                        mView.finishActivity()
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
}