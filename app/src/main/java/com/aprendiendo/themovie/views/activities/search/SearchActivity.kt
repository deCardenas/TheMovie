package com.aprendiendo.themovie.views.activities.search

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.aprendiendo.themovie.R
import com.aprendiendo.themovie.listeners.AdapterListener
import com.aprendiendo.themovie.util.ID
import com.aprendiendo.themovie.util.LayoutScrollListener
import kotlinx.android.synthetic.main.app_bar_search.*

abstract class SearchActivity : AppCompatActivity(), AdapterListener {
    companion object {
        const val TAG = "SearchActivity"
    }

    protected var isLast = true
    protected var isLoadingItems = false
    protected open val spanCount = 0
    protected abstract val detail: Class<*>

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.nav, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search_button -> {
                if (edtSearch.text.isNullOrEmpty())
                    Toast.makeText(this, R.string.search_in_blank, Toast.LENGTH_SHORT).show()
                else {
                    val query = edtSearch.text.toString()
                    onLoadSearched(query)
                }
            }
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    protected open fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this)
    }

    protected fun onScrollListener(layout: RecyclerView.LayoutManager)
            : RecyclerView.OnScrollListener {
        return object : LayoutScrollListener(layout) {
            override val totalPageCount: Int
                get() = 20
            override val isLastPage: Boolean
                get() = isLast
            override val isLoading: Boolean
                get() = isLoadingItems
            override val visibleThreshold: Int
                get() = spanCount

            override fun loadMoreItems() {
                isLoadingItems = true
                onScrollLoadMore()
            }
        }
    }

    protected fun onKeyListener(): View.OnKeyListener {
        return View.OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                if (edtSearch.text.isNullOrEmpty())
                    Toast.makeText(baseContext, R.string.search_in_blank, Toast.LENGTH_SHORT).show()
                else
                    onLoadSearched(edtSearch.text.toString())
                return@OnKeyListener true
            } else
                return@OnKeyListener false
        }
    }

    override fun onClick(id: Int) {
        val intent = Intent(this, detail)
        intent.putExtra(ID, id)
        startActivity(intent)
    }

    abstract fun onScrollLoadMore()
    abstract fun onLoadSearched(query: String)

}