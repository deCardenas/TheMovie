package com.aprendiendo.themovie.views.activities.search

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.aprendiendo.themovie.R
import com.aprendiendo.themovie.data.Movie
import com.aprendiendo.themovie.listeners.Search
import com.aprendiendo.themovie.presenters.search.MovieSearchPresenter
import com.aprendiendo.themovie.util.QUERY
import com.aprendiendo.themovie.views.activities.detail.MovieDetailActivity
import com.aprendiendo.themovie.views.adapters.MovieAdapter
import kotlinx.android.synthetic.main.app_bar_search.*
import kotlinx.android.synthetic.main.fragment_base.*

class MovieSearchActivity : SearchActivity(), Search.View<Movie> {

    private val presenter: Search.Presenter<Movie> = MovieSearchPresenter(this)
    private val adapter = MovieAdapter(this)
    override val detail: Class<*>
        get() = MovieDetailActivity::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val layout = LinearLayoutManager(this)
        recycler.layoutManager = layout
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.adapter = adapter
        recycler.addOnScrollListener(onScrollListener(layout))
        if (intent == null)
            finish()
        val query = intent.getStringExtra(QUERY)
        presenter.onLoadSearched(query)
        edtSearch.setText(query)
        edtSearch.setOnKeyListener(onKeyListener())
    }

    override fun onScrollLoadMore() {
        presenter.onLoadSearchedNext()
    }

    override fun onLoadSearched(query: String) {
        presenter.onLoadSearched(query)
    }

    override fun showDataFirst(items: ArrayList<Movie>, isLastPage: Boolean) {
        isLast = isLastPage
        adapter.clear()
        adapter.addAll(items)
        if (!isLast) adapter.addLoadingFooter()
    }

    override fun showDataNext(items: ArrayList<Movie>, isLastPage: Boolean) {
        isLast = isLastPage
        isLoadingItems = false
        adapter.removeLoadingFooter()
        adapter.addAll(items)
        if (!isLast) adapter.addLoadingFooter()
    }

    override fun putError(error: String) {
        Log.e(TAG, error)
    }

    override fun showMessage(message: String) {

    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
        recycler.visibility = View.INVISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.INVISIBLE
        recycler.visibility = View.VISIBLE
    }
}
