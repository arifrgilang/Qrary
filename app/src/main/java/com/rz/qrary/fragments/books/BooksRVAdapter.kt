package com.rz.qrary.fragments.books

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prof.rssparser.Article
import com.rz.qrary.R
import com.rz.qrary.fragments.BookDetailActivity
import kotlinx.android.synthetic.main.books_viewholder.view.*

class BooksRVAdapter(val articles: MutableList<Article>, val ctx: Context): RecyclerView.Adapter<BooksRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.books_viewholder, parent, false))

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(articles[position])
        holder.itemView.cardview_holder.setOnClickListener{
            val intent = Intent(ctx, BookDetailActivity::class.java)
            intent.putExtra("title", articles[position].title)
            intent.putExtra("link", articles[position].link)
            intent.putExtra("pubDate", articles[position].pubDate)
            intent.putExtra("description", articles[position].description)
            intent.putExtra("category", articles[position].categories.toString())
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            ctx.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(article: Article){
            itemView.title_text.text = article.title.toString()
            itemView.type_text.text = article.pubDate.toString()
        }
    }
}