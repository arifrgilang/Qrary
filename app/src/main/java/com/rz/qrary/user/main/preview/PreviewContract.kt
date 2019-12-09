package com.rz.qrary.user.main.preview

interface PreviewContract {
    interface View{
        fun finishActivity()
    }
    interface Presenter{
        fun getKonfirmasiData()
        fun setListener(npm: String)
        fun setModePinjamValue(npm: String, value: String)
    }
}