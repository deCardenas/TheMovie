package com.aprendiendo.themovie.presenters.list

import com.aprendiendo.themovie.listeners.Base

abstract class BasePresenter<T> : Base.Presenter<T>, Base.Listener<T> {
    private var view: Base.View<T>? = null
    protected lateinit var model: Base.Model<T>

    override fun onAttach(view: Base.View<T>) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    override fun onLoadItemsFromDb() {
        if (view != null){
        view!!.showProgress()
        model.loadingItemsFromDb(this)}
    }

    override fun onLoadItemsFirst() {
        if (view != null) {
            view!!.showProgress()
            model.loadingItemsFirst(this)
        }
    }

    override fun onLoadItemsNext() {
        model.loadingItemsNext(this)
    }

    override fun onError(error: String) {
        view!!.putError(error)
    }

    override fun onItemsLoadedFromDb(items: ArrayList<T>) {
        view!!.hideProgress()
        view!!.showItemsFromDb(items)
    }

    override fun onItemsLoadedFirst(items: ArrayList<T>, isLast: Boolean) {
        if (view != null) {
            view!!.hideProgress()
            view!!.showItemsFirst(items, isLast)
        }
    }

    override fun onItemsLoadedNext(items: ArrayList<T>, isLast: Boolean) {
        if (view != null)
        view!!.showItemsNext(items, isLast)
    }
}