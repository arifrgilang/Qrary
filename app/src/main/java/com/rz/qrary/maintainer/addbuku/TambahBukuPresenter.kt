package com.rz.qrary.maintainer.addbuku

import android.app.Activity
import com.rz.qrary.repository.Repository
import com.rz.qrary.repository.model.Book

class TambahBukuPresenter(val mView: TambahBukuContract.View): TambahBukuContract.Presenter {
    override fun cekIssn(ctx: Activity, issn: String) {
        Repository.cekIssn(ctx, issn)
    }

    override fun submit(
        issn: String,
        judul: String,
        bahasa: String,
        jmlHal: String,
        penerbit: String,
        penulis: String
    ) {
        val ref = Repository.firebase().child("db_buku").child(issn)
        ref.setValue(Book(bahasa, jmlHal, judul, penerbit, penulis, issn))
    }
}