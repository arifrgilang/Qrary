package com.rz.qrary.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Repository {
    companion object{
        private val firebase = FirebaseDatabase.getInstance()
        private lateinit var dbRef: DatabaseReference
        private fun firebase(): DatabaseReference = firebase.reference

        fun getBookDb() = firebase().child("db_buku")

    }
}