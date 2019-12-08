package com.rz.qrary.login

import android.content.Context
import com.rz.qrary.repository.Repository

class LoginPresenter(val ctx: Context, val mView: LoginContract.View): LoginContract.Presenter {
    override fun checkLogin(id: String, pw: String) {
        mView.showLoading(true)
        if(id.isEmpty() || pw.isEmpty()){
            mView.showToast("Isi form login!")
            mView.showLoading(false)
        } else {
            if((id == "admin") && pw == ("nimda")) {
                mView.navigateToMaintainer()
                mView.showLoading(false)
            } else {
                Repository.checkLogin(ctx, mView, id,pw)
            }
        }
    }
}