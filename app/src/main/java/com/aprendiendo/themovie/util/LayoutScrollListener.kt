package com.aprendiendo.themovie.util

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

abstract class LayoutScrollListener(private var layoutManager: RecyclerView.LayoutManager) : RecyclerView.OnScrollListener(){
    abstract val totalPageCount : Int
    abstract val isLastPage : Boolean
    abstract val isLoading : Boolean
    //only use with @GridLayoutManager
    abstract val visibleThreshold : Int

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (layoutManager is LinearLayoutManager)
            onScrolledLinear(layoutManager as LinearLayoutManager)
        if (layoutManager is GridLayoutManager)
            onScrolledGrid(dy,layoutManager as GridLayoutManager)
    }

    private fun onScrolledLinear(layoutManager: LinearLayoutManager){
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading && !isLastPage) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= totalPageCount) {
                loadMoreItems()
            }
        }
    }
    private fun onScrolledGrid(dy : Int,layoutManager: GridLayoutManager){
        if (dy>0){
            val lastItem =layoutManager.findLastCompletelyVisibleItemPosition()
            val currentCount = layoutManager.itemCount
            if (!isLoading && !isLastPage){
                if (currentCount<=lastItem + visibleThreshold){
                    loadMoreItems()
                }
            }
        }
    }

    protected abstract fun loadMoreItems()
}