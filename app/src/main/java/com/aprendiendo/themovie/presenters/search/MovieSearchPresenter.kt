package com.aprendiendo.themovie.presenters.search

import com.aprendiendo.themovie.data.Movie
import com.aprendiendo.themovie.listeners.Search
import com.aprendiendo.themovie.models.search.MovieSearchModel

class MovieSearchPresenter(private val view : Search.View<Movie>) : Search.Presenter<Movie>, Search.Listener<Movie> {
    private val model : Search.Model<Movie> = MovieSearchModel()

    override fun onLoadSearched(query: String) {
        view.showProgress()
        model.onLoadedSearched(query, this)
    }

    override fun onLoadSearchedNext() {
        model.onLoadedSearchedNext(this)
    }

    override fun onFinishedFirst(items: ArrayList<Movie>, isLastPage: Boolean) {
        view.hideProgress()
        view.showDataFirst(items, isLastPage)
    }

    override fun onFinishedNext(items: ArrayList<Movie>, isLastPage: Boolean) {
        view.showDataNext(items, isLastPage)
    }

    override fun onError(error: String) {
        view.putError(error)
    }

    override fun onMessage(message: String) {
        view.showMessage(message)
    }
}