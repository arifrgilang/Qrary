package com.rz.qrary.maintainer.konfirmasi

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.rz.qrary.R
import com.rz.qrary.repository.Repository
import com.rz.qrary.repository.model.Book
import kotlinx.android.synthetic.main.books_viewholder.view.*

class QueueRvAdapter(val npm: String, option: FirebaseRecyclerOptions<Book>)
    : FirebaseRecyclerAdapter<Book, QueueRvAdapter.ViewHolder>(option) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.books_viewholder, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int, buku: Book) {
        val issn = getRef(position).key.toString()
        val ref = Repository.firebase()
            .child("list_konfirm")
            .child(npm)
            .child(issn)
        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                holder.bind(buku)
                Log.d("bind buku", buku.judul)
                ref.removeEventListener(this)
            }
        })
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(buku: Book){
            itemView.title_text.text = buku.judul
            itemView.type_text.text = buku.penulis
        }
    }
}