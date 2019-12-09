package com.rz.qrary.maintainer.konfirmasi

import com.rz.qrary.repository.model.Mahasiswa

interface KonfirmasiContract {
    interface View{
        fun setUserData(mhs: Mahasiswa)
        fun finishActivity()
    }
    interface Presenter{
        fun getUserData(npm: String)
        fun setListener(npm: String)
    }
}