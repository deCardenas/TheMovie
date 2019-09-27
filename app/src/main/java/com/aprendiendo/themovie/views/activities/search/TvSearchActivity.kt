package com.aprendiendo.themovie.views.activities.search

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.aprendiendo.themovie.R
import com.aprendiendo.themovie.data.TvShow
import com.aprendiendo.themovie.listeners.Search
import com.aprendiendo.themovie.presenters.search.TvSearchPresenter
import com.aprendiendo.themovie.util.QUERY
import com.aprendiendo.themovie.views.activities.detail.TvDetailActivity
import com.aprendiendo.themovie.views.adapters.TvAdapter
import kotlinx.android.synthetic.main.app_bar_search.*
import kotlinx.android.synthetic.main.fragment_base.*

class TvSearchActivity : SearchActivity(), Search.View<TvShow> {

    private val presenter: Search.Presenter<TvShow> = TvSearchPresenter(this)
    private val adapter = TvAdapter(this)
    override val detail: Class<*> = TvDetailActivity::class.java

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
        if (intent==null)
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

    override fun showDataFirst(items: ArrayList<TvShow>, isLastPage: Boolean) {
        isLast = isLastPage
        adapter.clear()
        adapter.addAll(items)
        if (!isLastPage) adapter.addLoadingFooter()
    }

    override fun showDataNext(items: ArrayList<TvShow>, isLastPage: Boolean) {
        isLast = isLastPage
        isLoadingItems = false
        adapter.removeLoadingFooter()
        adapter.addAll(items)
        if (!isLastPage) adapter.addLoadingFooter()
    }
}
