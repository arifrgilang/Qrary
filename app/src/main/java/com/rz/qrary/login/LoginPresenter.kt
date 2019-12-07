package com.rz.qrary.login

class LoginPresenter(val mView: LoginContract.View): LoginContract.Presenter {
    override fun checkLogin(id: String, pw: String) {
        if(id.isEmpty() || pw.isEmpty()){
            mView.showToast("Isi form login!")
        } else {
            if(id == ("arif17003") && pw == ("argil")){
                mView.navigateToUser()
            } else if((id == "admin") && pw == ("nimda")){
                mView.navigateToMaintainer()
            } else {
                mView.showToast("ID atau PW salah!")
            }
        }
    }
}