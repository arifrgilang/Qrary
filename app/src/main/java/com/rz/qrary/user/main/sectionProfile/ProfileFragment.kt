package com.rz.qrary.user.main.sectionProfile

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.marginBottom
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel

import com.rz.qrary.R
import com.rz.qrary.login.LoginActivity
import com.rz.qrary.repository.Mahasiswa
import com.rz.qrary.repository.Repository
import kotlinx.android.synthetic.main.fragment_profile.*
import me.ydcool.lib.qrmodule.encoding.QrGenerator

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val npm = Repository.localDb(activity!!).getString(Repository.NPM, "")
        Repository.firebase().child("data_mhs").child(npm!!)
            .addValueEventListener(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.d("Get Mahasiswa", p0.message)
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val mhs = p0.getValue(Mahasiswa::class.java)!!
                    Glide.with(activity!!)
                        .load(mhs.url_foto)
                        .centerCrop()
                        .into(foto_mhs)
                    nama_mhs.text = mhs.nama
                    npm_mhs.text = mhs.npm
                }
            })


//        val alertDialog = AlertDialog.Builder(activity)
        val factory = LayoutInflater.from(activity!!.applicationContext)
//        val qrView = factory.inflate(R.layout.qr_zoomed, null)

        // QR Code Generator
        val qrCode: Bitmap = QrGenerator.Builder()
            .content(Repository.localDb(activity!!).getString(Repository.NPM,""))
            .qrSize(800)
            .color(Color.BLACK)
            .ecc(ErrorCorrectionLevel.H)
            .encode()

        qr_code.setImageBitmap(qrCode)
        // Alert Dialog QR
//        card_qr.setOnClickListener{
//            if(qrView.parent != null){
//                val a = qrView.parent as ViewGroup
//                a.removeView(qrView)
//            }
//            val img = qrView.findViewById<ImageView>(R.id.qr_zoomed)
//            img.setImageBitmap(qrCode)
//            alertDialog
//                .setTitle("*digunakan saat akan meminjam buku")
//                .setView(qrView)
//                .create().show()
//        }

        logout_button.setOnClickListener {
            startActivity(Intent(activity!! ,LoginActivity::class.java))
            activity!!.finish()
        }
    }
}
