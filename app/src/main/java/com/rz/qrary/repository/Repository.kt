package com.rz.qrary.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.database.*
import com.rz.qrary.login.LoginContract

class Repository {
    companion object{
        private val firebase = FirebaseDatabase.getInstance()
        fun firebase(): DatabaseReference = firebase.reference
        private var PRIVATE_MODE = 0
        private var PREF_NAME = "qrary"
        var NPM = "npm"

        // Offline
        fun localDb(ctx: Context): SharedPreferences =
            ctx.getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        fun writeStringToDB(db: SharedPreferences, key: String, value: String){
            val editor = db.edit()
            editor.putString(key, value)
            editor.apply()
        }

        // Online
        fun getBookDb() = firebase()
            .child("db_buku")

        fun getDipinjamDb(npm: String) = firebase()
            .child("pinjam_history")
            .child(npm)

        fun getTerpinjamDb(npm: String) = firebase()
            .child("terpinjam_history")
            .child(npm)

        fun checkLogin(ctx: Context, mView: LoginContract.View, npm: String, pw: String){
            Log.d("check login", npm.substring(0,3))
            if(npm.substring(0,3) == "140") {
                firebase().child("data_mhs").child(npm)
                    .addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            Log.d("Check Login", p0.message)
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            val mhs = p0.getValue(Mahasiswa::class.java)
                            Log.d("mhs", mhs.toString())
                            Log.d("mhspw", (mhs!!.pw == pw).toString())
                            if (mhs.pw == pw) {
                                writeStringToDB(localDb(ctx), NPM, npm)
                                mView.navigateToUser()
                            } else {
                                mView.showToast("ID atau PW salah!")
                            }
                        }
                    })
            } else {
                mView.showToast("NPM Tidak Ada!")
            }
        }

//        fun getMahasiswa(npm: String): Mahasiswa{
//            var result = Mahasiswa()
//            firebase().child("data_mhs").child(npm)
//                .addValueEventListener(object: ValueEventListener{
//                    override fun onCancelled(p0: DatabaseError) {
//                        Log.d("Get Mahasiswa", p0.message)
//                    }
//
//                    override fun onDataChange(p0: DataSnapshot) {
//                        result = p0.getValue(Mahasiswa::class.java)!!
//                    }
//                })
//        }
    }
}