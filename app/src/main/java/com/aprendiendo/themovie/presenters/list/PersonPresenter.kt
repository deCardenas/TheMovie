package com.aprendiendo.themovie.presenters.list

import com.aprendiendo.themovie.data.Person
import com.aprendiendo.themovie.models.list.PersonModel

class PersonPresenter : BasePresenter<Person>() {
    init {
        model = PersonModel()
    }
}