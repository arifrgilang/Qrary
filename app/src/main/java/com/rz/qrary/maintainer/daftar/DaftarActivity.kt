package com.rz.qrary.maintainer.daftar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.collection.LLRBNode
import com.rz.qrary.R
import kotlinx.android.synthetic.main.activity_daftar.*

class DaftarActivity : AppCompatActivity(), DaftarContract.View {

    private lateinit var mPresenter: DaftarContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar)
        mPresenter = DaftarPresenter(this, this)
        cek_daftar_button.setOnClickListener {
            mPresenter.checkavailability(username_daftar.text.toString())
        }

        daftar_button.setOnClickListener {
            mPresenter.daftar(
                nama_daftar.text.toString(),
                username_daftar.text.toString(),
                password_daftar.text.toString())
        }

    }

    override fun showLoading(condition: Boolean) {
        cek_daftar_button.text = if (condition) "Please Wait. . ." else "Daftarkan User"
        cek_daftar_button.isEnabled = !condition

    }

    override fun showLoadingTwo(condition: Boolean) {
        daftar_button.text = if (condition) "Please Wait. . ." else "Daftar"
        daftar_button.isEnabled = !condition
    }

    override fun showToast(text: String) =
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

    override fun showDaftarForm(condition: Boolean, url: String) {
        Glide.with(this)
            .load(url)
            .centerCrop()
            .into(daftar_image)

        form_daftar.visibility = if (condition) View.VISIBLE else View.INVISIBLE
        daftar_button.visibility = if (condition) View.VISIBLE else View.INVISIBLE
    }

    override fun setTersedia() {
        cek_daftar_button.text = "NPM Tersedia!"
        cek_daftar_button.isEnabled = false
        username_daftar.isEnabled = false
    }
}
