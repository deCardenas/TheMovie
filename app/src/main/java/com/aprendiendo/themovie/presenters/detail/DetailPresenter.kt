package com.aprendiendo.themovie.presenters.detail

import com.aprendiendo.themovie.listeners.Detail

abstract class DetailPresenter<T>(val view : Detail.View<T>) : Detail.Presenter, Detail.Listener<T> {
    lateinit var model : Detail.Model<T>

    override fun onLoadData(id: Int) {
        view.showProgress()
        model.loadingData(id, this)
    }

    override fun onLoadSimilarities(id: Int) {
        model.loadingSimilarities(id, this)
    }

    override fun onMessage(message: String) {
        view.showMessage(message)
    }

    override fun onError(error: String) {
        view.setError(error)
    }

    override fun onLoadedData(item: T) {
        view.hideProgress()
        view.showData(item)
    }

    override fun onLoadedSimilarities(items: ArrayList<T>) {
        view.showSimilarities(items)
    }
}