package com.rz.qrary.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView

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
            .setMessage("QR Code")
            .setView(imageView)
            .create()
        val factory = LayoutInflater.from(activity!!.applicationContext)
        val qrView = factory.inflate(R.layout.qr_zoomed, null)
//        alertDialog.setView(qrView)
        cardQR.setOnClickListener{
            alertDialog.show()
        }
        return view
    }

}
