package com.rz.qrary.user.main.sectionHistory

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
import com.rz.qrary.repository.model.Book
import com.rz.qrary.repository.Repository
import kotlinx.android.synthetic.main.books_viewholder.view.*

class DipinjamRVAdapter (val npm: String,  option: FirebaseRecyclerOptions<Book>)
    : FirebaseRecyclerAdapter<Book, DipinjamRVAdapter.ViewHolder>(option) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.books_viewholder, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int, book: Book) {
        val issn = getRef(position).key.toString()
//        Log.d("OnBindViewHolder", issn)
        Repository.getDipinjamDb(npm).child(issn)
            .addValueEventListener(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError) { Log.d("OnCancelled", p0.message) }
                override fun onDataChange(p0: DataSnapshot) { holder.bind(book) }
            })
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(book: Book){
            itemView.title_text.text = book.judul
            itemView.type_text.text = book.penulis
        }
    }
}