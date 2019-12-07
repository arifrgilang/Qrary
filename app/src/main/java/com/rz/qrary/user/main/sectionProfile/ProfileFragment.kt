package com.rz.qrary.user.main.sectionProfile

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.marginBottom

import com.rz.qrary.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val cardQR = view.findViewById<CardView>(R.id.card_qr)

        val imageView = ImageView(activity)
        imageView.setImageResource(R.drawable.qr)

        val alertDialog = AlertDialog.Builder(activity)
        val factory = LayoutInflater.from(activity!!.applicationContext)
        val qrView = factory.inflate(R.layout.qr_zoomed, null)

        cardQR.setOnClickListener{
            // to remove error showing
            if(qrView.parent != null){
                val a = qrView.parent as ViewGroup
                a.removeView(qrView)
            }
            alertDialog
                .setTitle("*digunakan saat akan meminjam buku")
                .setView(qrView)
                .create().show()
        }
        return view
    }

}
