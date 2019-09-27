package com.aprendiendo.themovie.listeners

interface Detail {
    interface View<T> {
        fun showProgress()
        fun hideProgress()
        fun setError(error: String)
        fun showMessage(message: String)
        fun showData(item: T)
        fun showSimilarities(items: ArrayList<T>)
    }

    interface Presenter {
        fun onLoadData(id: Int)
        fun onLoadSimilarities(id: Int)
    }

    interface Model<T> {
        fun loadingData(id: Int, listener: Listener<T>)
        fun loadingSimilarities(id: Int, listener: Listener<T>)
    }

    interface Listener<T> {
        fun onMessage(message: String)
        fun onError(error: String)
        fun onLoadedData(item: T)
        fun onLoadedSimilarities(items: ArrayList<T>)
    }
}