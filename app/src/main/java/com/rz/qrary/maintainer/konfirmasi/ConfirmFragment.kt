package com.rz.qrary.maintainer.konfirmasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rz.qrary.R
import com.rz.qrary.repository.Repository
import kotlinx.android.synthetic.main.fragment_confirm.*

class ConfirmFragment : BottomSheetDialogFragment() {

    private lateinit var npm: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_confirm, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        npm = arguments!!.getString("npm")!!
        selesai_button_final.setOnClickListener {
            Repository.confirmPeminjaman(activity!!, npm)
        }
    }
}