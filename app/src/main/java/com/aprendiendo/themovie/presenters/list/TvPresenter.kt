package com.aprendiendo.themovie.presenters.list

import com.aprendiendo.themovie.data.TvShow
import com.aprendiendo.themovie.models.list.TvModel

class TvPresenter : BasePresenter<TvShow>() {
    init {
        model = TvModel()
    }
}