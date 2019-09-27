package com.aprendiendo.themovie.views.fragments

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.aprendiendo.themovie.data.Person
import com.aprendiendo.themovie.listeners.Base
import com.aprendiendo.themovie.presenters.list.PersonPresenter
import com.aprendiendo.themovie.util.isConnected
import com.aprendiendo.themovie.views.activities.detail.PersonDetailActivity
import com.aprendiendo.themovie.views.activities.search.PersonSearchActivity
import com.aprendiendo.themovie.views.adapters.BaseAdapter
import com.aprendiendo.themovie.views.adapters.PersonAdapter
import kotlinx.android.synthetic.main.app_bar_search.*
import kotlinx.android.synthetic.main.fragment_base.*
import org.jetbrains.anko.support.v4.act

class PersonFragment : BaseFragment(), Base.View<Person> {
    private val adapter: BaseAdapter<Person> = PersonAdapter(this)
    private val presenter: Base.Presenter<Person> = PersonPresenter()
    override val detail: Class<*> = PersonDetailActivity::class.java
    override val search: Class<*> = PersonSearchActivity::class.java
    override val spanCount: Int = 2

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

    override fun onScrollLoadMore() {
        presenter.onLoadItemsNext()
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

    override fun showItemsFromDb(items: ArrayList<Person>) {
        isLast = true
        adapter.clear()
        adapter.addAll(items)
    }

    override fun showItemsFirst(items: ArrayList<Person>, isLast: Boolean) {
        this.isLast = isLast
        adapter.clear()
        adapter.addAll(items)
        if (!isLast) adapter.addLoadingFooter()
    }

    override fun showItemsNext(items: ArrayList<Person>, isLast: Boolean) {
        isLoadingItems = false
        this.isLast = isLast
        adapter.removeLoadingFooter()
        adapter.addAll(items)
        if (!isLast) adapter.addLoadingFooter()
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        val grid = GridLayoutManager(context, spanCount)
        grid.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when(adapter.getItemViewType(position)){
                    BaseAdapter.LOADING->spanCount
                    else->1
                }
            }
        }
        return grid
    }
}