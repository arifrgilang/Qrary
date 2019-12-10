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
import com.rz.qrary.repository.Repository
import com.rz.qrary.repository.model.Mahasiswa
import kotlinx.android.synthetic.main.mhs_viewholder.view.*

class PeminjamRVAdapter (val ctx: Context, option: FirebaseRecyclerOptions<Mahasiswa>)
    : FirebaseRecyclerAdapter<Mahasiswa, PeminjamRVAdapter.ViewHolder>(option) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.mhs_viewholder, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int, mhs: Mahasiswa) {
        val npm = getRef(position).key.toString()
        var childrenCount = 0
        val ref = Repository.firebase().child("pinjam_history").child(npm)
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) { Log.d("OnCancelled", p0.message) }
            override fun onDataChange(p0: DataSnapshot) {
//                holder.bind(ctx, mhs, tanggalParsed)
                childrenCount = p0.childrenCount.toInt()
                if (childrenCount!=0){
                    val ref = Repository.firebase().child("data_mhs").child(npm)
                    ref.addListenerForSingleValueEvent(object: ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {}
                        override fun onDataChange(p0: DataSnapshot) {
                            var mhs: Mahasiswa? = null
                            mhs = p0.getValue(Mahasiswa::class.java)
                            if(mhs!=null){
                                holder.bind(ctx, mhs, childrenCount)
                            }
                            ref.removeEventListener(this)
                        }
                    })
                }
                // possible error
                ref.removeEventListener(this)
            }
        })
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(ctx: Context, mhs: Mahasiswa, count: Int){
            itemView.vh_nama_mhs.text = mhs.nama
            itemView.vh_npm_mhs.text = mhs.npm
            itemView.vh_tanggal.text = "Meminjam " + count + " buku"

            Glide.with(ctx)
                .load(mhs.url_foto)
                .centerCrop()
                .into(itemView.vh_foto_mhs)

            itemView.mhs_viewholder.setOnClickListener{
                val intent = Intent(ctx, DetailPeminjamActivity::class.java)
                intent.putExtra("npm", mhs.npm)
                ctx.startActivity(intent)
            }
        }
    }
}