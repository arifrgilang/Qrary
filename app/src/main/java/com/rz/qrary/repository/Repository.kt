package com.rz.qrary.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.rz.qrary.login.LoginContract
import com.rz.qrary.repository.model.Book
import com.rz.qrary.repository.model.Mahasiswa
import com.rz.qrary.util.Util

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
            val ref = firebase().child("data_mhs").child(npm)
            ref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.d("Check Login", p0.message)
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val mhs = p0.getValue(Mahasiswa::class.java)
                    if(mhs == null){
                        mView.showToast("NPM Tidak Terdaftar!")
                        ref.removeEventListener(this)
                    } else {
                        if (mhs.pw == pw) {
                            writeStringToDB(localDb(ctx), NPM, npm)
                            mView.navigateToUser()
                            ref.removeEventListener(this)
                        } else {
                            mView.showToast("ID atau PW salah!")
                            ref.removeEventListener(this)
                        }
                    }
                    mView.showLoading(false)
                }
            })
        }

        fun getPengunjung() = firebase()
            .child("pengunjung")

        fun addPengunjung(npm: String){
            val ref = firebase().child("data_mhs").child(npm)
            ref.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    val mhs = p0.getValue(Mahasiswa::class.java)
                    getPengunjung()
                        .child(Util.getLocalDate()+"X"+ Util.getLocalTime())
                        .setValue(mhs)
                    ref.removeEventListener(this)
                }
            })
        }

        fun setPinjamMode(npm: String, value: String){
            val ref = firebase().child("data_mhs").child(npm)
            ref.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    var mhs: Mahasiswa? = null
                    mhs = p0.getValue(Mahasiswa::class.java)
                    if(mhs!=null){
                        mhs!!.mode_pinjam = value
                        val ref2 = firebase()
                            .child("data_mhs")
                            .child(mhs.npm)
                            .setValue(mhs)
                        ref.removeEventListener(this)
                    }
                }
            })
        }

        fun addBookList(ctx: Context, issn: String, npm: String){
            val ref = firebase().child("db_buku").child(issn)
            ref.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    val buku = p0.getValue(Book::class.java)
                    if(buku!=null){
                        Log.d("isListed", buku.issn)
                        addBooktoConfirm(npm, issn)
                        Toast.makeText(ctx, "Berhasil menambah Buku", Toast.LENGTH_SHORT).show()
                        ref.removeEventListener(this)
                    } else {
                        Toast.makeText(ctx, "ISSN buku tidak tercatat di Database!", Toast.LENGTH_SHORT).show()
                        ref.removeEventListener(this)
                    }
                }
            })
        }

        private fun addBooktoConfirm(npm: String, issn: String): Boolean{
            var condition = false
            val ref = firebase().child("db_buku").child(issn)
            ref.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    var buku: Book? = null
                    buku = p0.getValue(Book::class.java)
                    if(buku!=null){
                        Log.d("addbuku", buku.issn)
                        val ref2 = firebase()
                            .child("list_konfirm")
                            .child(npm)
                            .child(buku.issn)
                            .setValue(buku)
                        condition = true
                        ref.removeEventListener(this)
                    } else{
                        condition = false
                    }
                }
            })
            return condition
        }
    }
}