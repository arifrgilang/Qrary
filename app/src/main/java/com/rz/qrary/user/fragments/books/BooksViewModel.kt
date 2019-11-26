package com.rz.qrary.user.fragments.books

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prof.rssparser.Article
import com.prof.rssparser.Parser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BooksViewModel: ViewModel() {
    private var url = "http://repository.unpad.ac.id/rss"
    private var url2019 = "http://repository.unpad.ac.id/rss/index/index/searchtype/simple/query/%2A%3A%2A/yearfq/2019"

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private lateinit var articleList: MutableLiveData<MutableList<Article>>

    fun getArticle(): MutableLiveData<MutableList<Article>>{
        if(!::articleList.isInitialized){
            articleList = MutableLiveData()
        }
        return articleList
    }

    private fun setArticle(list: MutableList<Article>)= articleList.postValue(list)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun fetchArticle(){
        coroutineScope.launch(Dispatchers.Main) {
            try {
                val parser = Parser()
                val articleList = parser.getArticles(url2019)
                setArticle(articleList)
            } catch (e: Exception){
                e.printStackTrace()
                Log.d("BooksViewModel", e.message.toString())
                setArticle(mutableListOf())
            }
        }
    }
}