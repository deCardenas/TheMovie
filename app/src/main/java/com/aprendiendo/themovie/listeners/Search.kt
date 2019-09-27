package com.aprendiendo.themovie.listeners

interface Search {
    interface View<T>{
        fun putError(error : String)
        fun showMessage(message : String)
        fun showProgress()
        fun hideProgress()
        fun showDataFirst(items : ArrayList<T>, isLastPage: Boolean)
        fun showDataNext(items: ArrayList<T>, isLastPage: Boolean)
    }
    interface Presenter<T>{
        fun onLoadSearched(query : String)
        fun onLoadSearchedNext()
    }
    interface Model<T>{
        fun onLoadedSearched(query: String, listener : Listener<T>)
        fun onLoadedSearchedNext(listener : Listener<T>)
    }
    interface Listener<T>{
        fun onFinishedFirst(items : ArrayList<T>, isLastPage : Boolean)
        fun onFinishedNext(items : ArrayList<T>, isLastPage: Boolean)
        fun onError(error: String)
        fun onMessage(message: String)
    }
}