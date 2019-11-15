package com.rz.qrary.fragments.books

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prof.rssparser.Article
import com.rz.qrary.R
import kotlinx.android.synthetic.main.books_viewholder.view.*

class BooksRVAdapter(val articles: MutableList<Article>): RecyclerView.Adapter<BooksRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.books_viewholder, parent, false))

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(articles[position])

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(article: Article){
            itemView.title_text.text = article.title.toString()
            itemView.type_text.text = article.pubDate.toString()
        }
    }
}