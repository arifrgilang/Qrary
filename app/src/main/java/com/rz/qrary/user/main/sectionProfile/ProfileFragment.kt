package com.rz.qrary.user.main.sectionProfile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        initProfile(npm)
        generateQR(npm)
        logout_button.setOnClickListener {
            val intent = Intent(activity!! ,LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            activity!!.finish()
        }

        getDipinjamCount(npm)
        getTerpinjamCount(npm)
    }

    private fun generateQR(npm: String?) {
        val contentString = "qrary"+npm
        val qrCode: Bitmap = QrGenerator.Builder()
            .content(contentString)
            .qrSize(800)
            .color(Color.BLACK)
            .ecc(ErrorCorrectionLevel.H)
            .encode()

        qr_code.setImageBitmap(qrCode)
    }

    private fun initProfile(npm: String?) {
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
    }

    private fun getDipinjamCount(npm: String?) {
        Repository.getDipinjamDb(npm!!).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                profile_dipinjam.text = "" + p0.childrenCount + " Buku"
            }
        })
    }
    private fun getTerpinjamCount(npm: String?) {
        Repository.getTerpinjamDb(npm!!).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                profile_terpinjam.text = "" + p0.childrenCount + " Buku"
            }
        })
    }
}
