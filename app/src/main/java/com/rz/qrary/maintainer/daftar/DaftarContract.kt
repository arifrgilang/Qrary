package com.rz.qrary.maintainer.daftar

interface DaftarContract {
    interface View{
        fun showLoading(condition: Boolean)
        fun showLoadingTwo(condition: Boolean)
        fun showToast(text: String)
        fun showDaftarForm(condition: Boolean, url: String)
        fun setTersedia()
    }
    interface Presenter{
        fun checkavailability(npm: String)
        fun daftar(nama: String, npm: String, pw: String)
    }
}