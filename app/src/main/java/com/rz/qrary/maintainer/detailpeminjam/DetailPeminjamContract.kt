package com.rz.qrary.maintainer.detailpeminjam

import com.rz.qrary.repository.model.Mahasiswa

interface DetailPeminjamContract{
    interface View{
        fun setProfileView(mhs: Mahasiswa)
    }
    interface Presenter{
        fun getUserProfile(npm: String)
    }
}