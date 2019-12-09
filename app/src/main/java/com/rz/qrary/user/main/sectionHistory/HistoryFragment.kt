package com.rz.qrary.user.main.sectionHistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions

import com.rz.qrary.R
import com.rz.qrary.repository.model.Book
import com.rz.qrary.repository.Repository
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {
    private lateinit var mDipinjamAdapter: DipinjamRVAdapter
    private lateinit var mTerpinjamAdapter: TerpinjamRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_history, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val npm = Repository.localDb(activity!!).getString(Repository.NPM,"")!!
        // Sedang Dipinjam
        rv_sedang_dipinjam.layoutManager = GridLayoutManager(activity, 2)
        val optionSedang = FirebaseRecyclerOptions.Builder<Book>()
            .setQuery(Repository.getDipinjamDb(npm), Book::class.java)
            .build()
        mDipinjamAdapter = DipinjamRVAdapter(npm, optionSedang)
        rv_sedang_dipinjam.adapter = mDipinjamAdapter
        mDipinjamAdapter.startListening()
        // Telah Dipinjam
        rv_telah_dipinjam.layoutManager = GridLayoutManager(activity, 2)
        val optionTelah = FirebaseRecyclerOptions.Builder<Book>()
            .setQuery(Repository.getTerpinjamDb(npm), Book::class.java)
            .build()
        mTerpinjamAdapter = TerpinjamRVAdapter(npm, optionTelah)
        rv_telah_dipinjam.adapter = mTerpinjamAdapter
        mTerpinjamAdapter.startListening()
    }
}
