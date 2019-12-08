package com.rz.qrary.login

import android.content.Context
import com.rz.qrary.repository.Repository

class LoginPresenter(val ctx: Context, val mView: LoginContract.View): LoginContract.Presenter {
    override fun checkLogin(id: String, pw: String) {
        if(id.isEmpty() || pw.isEmpty()){
            mView.showToast("Isi form login!")
        } else {
            if((id == "admin") && pw == ("nimda")) {
                mView.navigateToMaintainer()
            } else {
                Repository.checkLogin(ctx, mView, id,pw)
            }
        }
    }
}