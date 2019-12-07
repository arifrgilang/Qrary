package com.rz.qrary.user.main.sectionBooks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.firebase.ui.database.FirebaseRecyclerOptions

import com.rz.qrary.R
import com.rz.qrary.repository.Book
import com.rz.qrary.repository.Repository
import kotlinx.android.synthetic.main.fragment_books.*

class BooksFragment : Fragment() {
    private lateinit var rvAdapter: BooksRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_books, container, false)
        // Inflate the layout for this fragment
//        rv = view.findViewById(R.id.books_rv)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        rv.layoutManager = LinearLayoutManager(activity)
        books_rv.layoutManager = GridLayoutManager(activity, 2)
        books_rv.itemAnimator = DefaultItemAnimator()

        val option = FirebaseRecyclerOptions.Builder<Book>()
            .setQuery(Repository.getBookDb(), Book::class.java)
            .build()
        rvAdapter = BooksRVAdapter(option)
        books_rv.adapter = rvAdapter
        rvAdapter.startListening()
    }
}
