package com.aprendiendo.themovie.listeners

import com.aprendiendo.themovie.data.ListItem

interface Base {
    interface View<T>{
        fun showProgress()
        fun hideProgress()
        fun putError(error: String)
        fun showItemsFromDb(items : ArrayList<T>)
        fun showItemsFirst(items : ArrayList<T>, isLast : Boolean)
        fun showItemsNext(items : ArrayList<T>, isLast: Boolean)
    }
    interface Presenter<T>{
        fun onAttach(view : Base.View<T>)
        fun onDetach()
        fun onLoadItemsFromDb()
        fun onLoadItemsFirst()
        fun onLoadItemsNext()
    }
    interface Model<T> {
        fun loadingItemsFromDb(listener: Listener<T>)
        fun loadingItemsFirst(listener: Listener<T>)
        fun loadingItemsNext(listener: Listener<T>)
    }
    interface Listener<T> {
        fun onError(error : String)
        fun onItemsLoadedFromDb(items: ArrayList<T>)
        fun onItemsLoadedFirst(items: ArrayList<T>, isLast: Boolean)
        fun onItemsLoadedNext(items: ArrayList<T>, isLast: Boolean)
    }
}