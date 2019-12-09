package com.rz.qrary.user.main.container

interface MainContract {
    interface View{
        fun navigateToConfirmView()
        fun finishActivity()
    }
    interface Presenter{
        fun setListener(npm: String)
    }
}