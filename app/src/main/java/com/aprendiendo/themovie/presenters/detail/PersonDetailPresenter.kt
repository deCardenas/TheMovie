package com.aprendiendo.themovie.presenters.detail

import com.aprendiendo.themovie.data.Person
import com.aprendiendo.themovie.listeners.Detail
import com.aprendiendo.themovie.models.detail.PersonDetailModel

class PersonDetailPresenter(view : Detail.View<Person>) : DetailPresenter<Person>(view) {
    init {
        model = PersonDetailModel()
    }
}