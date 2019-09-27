package com.aprendiendo.themovie.presenters.search

import com.aprendiendo.themovie.data.Person
import com.aprendiendo.themovie.listeners.Search
import com.aprendiendo.themovie.models.search.PersonSearchModel

class PersonSearchPresenter(private val view: Search.View<Person>) :
        Search.Presenter<Person>, Search.Listener<Person> {

    private val model : Search.Model<Person> = PersonSearchModel()

    override fun onLoadSearched(query: String) {
        view.showProgress()
        model.onLoadedSearched(query, this)
    }

    override fun onLoadSearchedNext() {
        model.onLoadedSearchedNext(this)
    }

    override fun onFinishedFirst(items: ArrayList<Person>, isLastPage: Boolean) {
        view.hideProgress()
        view.showDataFirst(items, isLastPage)
    }

    override fun onFinishedNext(items: ArrayList<Person>, isLastPage: Boolean) {
        view.showDataNext(items, isLastPage)
    }

    override fun onError(error: String) {
        view.putError(error)
    }

    override fun onMessage(message: String) {
        view.showMessage(message)
    }
}