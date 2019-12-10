package com.rz.qrary.maintainer.admin

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.rz.qrary.R
import com.rz.qrary.maintainer.detailpeminjam.DetailPeminjamActivity
import com.rz.qrary.repository.model.Mahasiswa
import com.rz.qrary.repository.Repository
import kotlinx.android.synthetic.main.mhs_viewholder.view.*

class PengunjungRVAdapter (val ctx: Context, option: FirebaseRecyclerOptions<Mahasiswa>)
    : FirebaseRecyclerAdapter<Mahasiswa, PengunjungRVAdapter.ViewHolder>(option) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.mhs_viewholder, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int, mhs: Mahasiswa) {
        val tanggal = getRef(position).key.toString()
        var tglfix = tanggal.split("X")
        var tanggalParsed = "Tanggal " + tglfix[0]
        var temp = tglfix[1].split("-")
        tanggalParsed+= ", Pukul "
        tanggalParsed+= temp.joinToString(":")
        Log.d("TanggalParsed", tanggalParsed)

        val ref = Repository.getPengunjung().child(tanggal)
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) { Log.d("OnCancelled", p0.message) }
            override fun onDataChange(p0: DataSnapshot) { holder.bind(ctx, mhs, tanggalParsed)
                // possible error
            ref.removeEventListener(this)
            }
        })
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(ctx: Context, mhs: Mahasiswa, tanggal: String){
            itemView.vh_tanggal.text = tanggal
            itemView.vh_nama_mhs.text = mhs.nama
            itemView.vh_npm_mhs.text = mhs.npm

            Glide.with(ctx)
                .load(mhs.url_foto)
                .centerCrop()
                .into(itemView.vh_foto_mhs)
        }
    }
}