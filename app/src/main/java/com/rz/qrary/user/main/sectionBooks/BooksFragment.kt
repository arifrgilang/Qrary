package com.rz.qrary.user.main.sectionBooks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions

import com.rz.qrary.R
import com.rz.qrary.repository.model.Book
import com.rz.qrary.repository.Repository
import kotlinx.android.synthetic.main.fragment_books.*

class BooksFragment : Fragment() {
    private lateinit var rvAdapter: BooksRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_books, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        books_rv.layoutManager = LinearLayoutManager(activity)
        books_rv.itemAnimator = DefaultItemAnimator()

        val option = FirebaseRecyclerOptions.Builder<Book>()
            .setQuery(Repository.getBookDb(), Book::class.java)
            .build()
        rvAdapter = BooksRVAdapter(option)
        books_rv.adapter = rvAdapter
        rvAdapter.startListening()
    }
}
