package com.rz.qrary.login

interface LoginContract {
    interface View{
        fun showToast(text: String)
        fun navigateToUser()
        fun navigateToMaintainer()
    }
    interface Presenter{
        fun checkLogin(id: String, pw: String)
    }
}