package com.aprendiendo.themovie.presenters.detail

import com.aprendiendo.themovie.data.TvShow
import com.aprendiendo.themovie.listeners.Detail
import com.aprendiendo.themovie.models.detail.TvDetailModel

class TvDetailPresenter(view : Detail.View<TvShow>) : DetailPresenter<TvShow>(view) {
    init {
        model = TvDetailModel()
    }
}