package com.rz.qrary.maintainer.addbuku

import android.app.Activity

interface TambahBukuContract {
    interface View{

    }
    interface Presenter{
        fun cekIssn(ctx: Activity, issn: String)
        fun submit(
            issn: String,
            judul: String,
            bahasa: String,
            jmlHal: String,
            penerbit: String,
            penulis: String
        )
    }
}