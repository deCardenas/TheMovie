package com.aprendiendo.themovie.views.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast
import com.aprendiendo.themovie.R
import com.aprendiendo.themovie.listeners.AdapterListener
import com.aprendiendo.themovie.util.ID
import com.aprendiendo.themovie.util.LayoutScrollListener
import com.aprendiendo.themovie.util.QUERY
import kotlinx.android.synthetic.main.app_bar_search.*
import org.jetbrains.anko.support.v4.act

abstract class BaseFragment : Fragment(), AdapterListener {
    companion object {
        const val TAG = "BaseFragment"
    }

    protected var isLast = true
    protected var isLoadingItems = false
    protected var isFirstTime = true
    protected abstract val detail: Class<*>
    protected abstract val search: Class<*>
    protected open val spanCount = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search_button) {
            val query = act.edtSearch.text.toString()
            if (query.isEmpty() || query.isBlank())
                Toast.makeText(act, R.string.search_in_blank, Toast.LENGTH_SHORT).show()
            else {
                val intent = Intent(act, search)
                intent.putExtra(QUERY, query)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun onKeyListener(): View.OnKeyListener {
        return View.OnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                if (edtSearch.text.isNullOrEmpty())
                    Toast.makeText(context, R.string.search_in_blank, Toast.LENGTH_SHORT).show()
                else {
                    val intent = Intent(act, search)
                    intent.putExtra(QUERY, edtSearch.text.toString())
                    startActivity(intent)
                }
                return@OnKeyListener true
            } else
                return@OnKeyListener false
        }
    }

    open fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }

    protected fun onScrollListener(layout: RecyclerView.LayoutManager): RecyclerView.OnScrollListener {
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

    override fun onClick(id: Int) {
        val intent = Intent(act, detail)
        intent.putExtra(ID, id)
        startActivity(intent)
    }

    abstract fun onScrollLoadMore()

}