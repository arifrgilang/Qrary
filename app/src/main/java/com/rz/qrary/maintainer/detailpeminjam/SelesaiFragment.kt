package com.rz.qrary.maintainer.detailpeminjam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rz.qrary.R
import com.rz.qrary.repository.Repository
import kotlinx.android.synthetic.main.fragment_selesai.*

class SelesaiFragment : BottomSheetDialogFragment() {

    private lateinit var npm: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_selesai, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        npm = arguments!!.getString("npm")!!
        selesai_button_final.setOnClickListener {
            Repository.moveToSelesai(activity!!, npm)
//            Repository.confirmPeminjaman(activity!!, npm)
            Toast.makeText(activity!!, "Buku dikembalikan", Toast.LENGTH_SHORT).show()
            activity!!.finish()
        }
    }
}