package com.aprendiendo.themovie.views.activities.search

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.aprendiendo.themovie.R
import com.aprendiendo.themovie.data.Person
import com.aprendiendo.themovie.listeners.Search
import com.aprendiendo.themovie.presenters.search.PersonSearchPresenter
import com.aprendiendo.themovie.util.QUERY
import com.aprendiendo.themovie.views.activities.detail.PersonDetailActivity
import com.aprendiendo.themovie.views.adapters.BaseAdapter
import com.aprendiendo.themovie.views.adapters.PersonAdapter
import kotlinx.android.synthetic.main.app_bar_search.*
import kotlinx.android.synthetic.main.fragment_base.*

class PersonSearchActivity : SearchActivity(), Search.View<Person> {
    companion object {
        const val TAG = "PersonSearch"
    }

    private val presenter: Search.Presenter<Person> = PersonSearchPresenter(this)
    private val adapter: BaseAdapter<Person> = PersonAdapter(this)
    override val spanCount: Int = 2
    override val detail: Class<*> = PersonDetailActivity::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val layout = getLayoutManager()
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

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        val grid = GridLayoutManager(this, spanCount)
        grid.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    BaseAdapter.LOADING -> spanCount
                    else -> 1
                }
            }
        }
        return grid
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
        recycler.visibility = View.INVISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.INVISIBLE
        recycler.visibility = View.VISIBLE
    }

    override fun showDataFirst(items: ArrayList<Person>, isLastPage: Boolean) {
        isLast = isLastPage
        adapter.clear()
        adapter.addAll(items)
        if (!isLast) adapter.addLoadingFooter()

    }

    override fun showDataNext(items: ArrayList<Person>, isLastPage: Boolean) {
        isLast = isLastPage
        isLoadingItems = false
        adapter.removeLoadingFooter()
        adapter.addAll(items)
        if (!isLast) adapter.addLoadingFooter()
    }
}
