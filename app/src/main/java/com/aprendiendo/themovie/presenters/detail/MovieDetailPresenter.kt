package com.aprendiendo.themovie.presenters.detail

import com.aprendiendo.themovie.data.Movie
import com.aprendiendo.themovie.listeners.Detail
import com.aprendiendo.themovie.models.detail.MovieDetailModel

class MovieDetailPresenter(view : Detail.View<Movie>) : DetailPresenter<Movie>(view) {
    init {
        model = MovieDetailModel()
    }
}