package com.aprendiendo.themovie.views.fragments

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.util.Log
import android.view.View
import com.aprendiendo.themovie.data.TvShow
import com.aprendiendo.themovie.listeners.Base
import com.aprendiendo.themovie.presenters.list.TvPresenter
import com.aprendiendo.themovie.util.isConnected
import com.aprendiendo.themovie.views.activities.detail.TvDetailActivity
import com.aprendiendo.themovie.views.activities.search.TvSearchActivity
import com.aprendiendo.themovie.views.adapters.BaseAdapter
import com.aprendiendo.themovie.views.adapters.TvAdapter
import kotlinx.android.synthetic.main.app_bar_search.*
import kotlinx.android.synthetic.main.fragment_base.*
import org.jetbrains.anko.support.v4.act

class TvFragment : BaseFragment(), Base.View<TvShow> {
    private val adapter : BaseAdapter<TvShow> = TvAdapter(this)
    private val presenter : Base.Presenter<TvShow> = TvPresenter()

    override val detail: Class<*> = TvDetailActivity::class.java
    override val search: Class<*> = TvSearchActivity::class.java

    override fun onScrollLoadMore() {
        presenter.onLoadItemsNext()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val layout = getLayoutManager()
        presenter.onAttach(this)
        recycler.layoutManager = layout
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.adapter = adapter
        recycler.addOnScrollListener(onScrollListener(layout))
        act.edtSearch.setOnKeyListener(onKeyListener())
        if (isConnected(context!!))
            presenter.onLoadItemsFirst()
        else
            presenter.onLoadItemsFromDb()

    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
        recycler.visibility = View.INVISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.INVISIBLE
        recycler.visibility = View.VISIBLE
    }

    override fun putError(error: String) {
        Log.e(TAG, error)
    }

    override fun showItemsFromDb(items: ArrayList<TvShow>) {
        isLast = true
        adapter.clear()
        adapter.addAll(items)
    }

    override fun showItemsFirst(items: ArrayList<TvShow>, isLast: Boolean) {
        this.isLast = isLast
        adapter.clear()
        adapter.addAll(items)
        if (!isLast) adapter.addLoadingFooter()
    }

    override fun showItemsNext(items: ArrayList<TvShow>, isLast: Boolean) {
        isLoadingItems = false
        this.isLast = isLast
        adapter.removeLoadingFooter()
        adapter.addAll(items)
        if (!isLast) adapter.addLoadingFooter()
    }
}