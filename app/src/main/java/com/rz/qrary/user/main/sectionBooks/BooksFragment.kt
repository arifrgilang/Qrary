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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.rz.qrary.R

class BooksFragment : Fragment() {
    private lateinit var rvAdapter: BooksRVAdapter
    private lateinit var viewModel: BooksViewModel
    private lateinit var rv: RecyclerView
    private lateinit var swipeLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_books, container, false)
        // Inflate the layout for this fragment
        rv = view.findViewById(R.id.books_rv)
        swipeLayout = view.findViewById(R.id.swipe_layout)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run{
            ViewModelProviders.of(this)[BooksViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv.layoutManager = LinearLayoutManager(activity)
        rv.itemAnimator = DefaultItemAnimator()
        rv.setHasFixedSize(true)

        viewModel.getArticle().observe(this, Observer { articles ->
            if(articles != null){
                rvAdapter = BooksRVAdapter(
                    articles,
                    activity!!.applicationContext
                )
                Log.d("BooksFragment", articles.toString())
                rv.adapter = rvAdapter
                rvAdapter.notifyDataSetChanged()
                swipeLayout.isRefreshing = false
            }
        })

        swipeLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark)
        swipeLayout.canChildScrollUp()
        swipeLayout.setOnRefreshListener {
            rvAdapter.articles.clear()
            rvAdapter.notifyDataSetChanged()
            swipeLayout.isRefreshing = true
            viewModel.fetchArticle()
        }

        viewModel.fetchArticle()
    }
}
