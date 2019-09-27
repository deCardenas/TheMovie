package com.aprendiendo.themovie.presenters.search

import com.aprendiendo.themovie.data.TvShow
import com.aprendiendo.themovie.listeners.Search
import com.aprendiendo.themovie.models.search.TvSearchModel

class TvSearchPresenter(private val view: Search.View<TvShow>) :
        Search.Presenter<TvShow>, Search.Listener<TvShow> {
    private val model: Search.Model<TvShow> = TvSearchModel()

    override fun onLoadSearched(query: String) {
        view.showProgress()
        model.onLoadedSearched(query, this)
    }

    override fun onLoadSearchedNext() {
        model.onLoadedSearchedNext(this)
    }

    override fun onFinishedFirst(items: ArrayList<TvShow>, isLastPage: Boolean) {
        view.hideProgress()
        view.showDataFirst(items, isLastPage)
    }

    override fun onFinishedNext(items: ArrayList<TvShow>, isLastPage: Boolean) {
        view.showDataNext(items, isLastPage)
    }

    override fun onError(error: String) {
        view.putError(error)
    }

    override fun onMessage(message: String) {
        view.showMessage(message)
    }
}