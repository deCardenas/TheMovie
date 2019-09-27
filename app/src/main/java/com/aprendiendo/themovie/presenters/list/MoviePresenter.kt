package com.aprendiendo.themovie.presenters.list

import com.aprendiendo.themovie.data.Movie
import com.aprendiendo.themovie.models.list.MovieModel

class MoviePresenter : BasePresenter<Movie>(){
    init {
        model = MovieModel()
    }
}