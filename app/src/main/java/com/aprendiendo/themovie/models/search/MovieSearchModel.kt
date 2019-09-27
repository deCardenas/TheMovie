package com.aprendiendo.themovie.models.search

import com.aprendiendo.themovie.BuildConfig
import com.aprendiendo.themovie.api.Client
import com.aprendiendo.themovie.api.Service
import com.aprendiendo.themovie.data.ListItem
import com.aprendiendo.themovie.data.Movie
import com.aprendiendo.themovie.listeners.Search
import com.aprendiendo.themovie.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieSearchModel : Search.Model<Movie> {
    private val lang = getLanguage()
    private var current = 0
    private var last = 0
    private var query = ""
    override fun onLoadedSearched(query: String, listener: Search.Listener<Movie>) {
        current = 1
        this.query = query
        val client = Client.retrofit!!.create(Service.Movies::class.java)
        val service = client.searchMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN, query, lang, current)
        service.enqueue(object : Callback<ListItem<Movie>>{
            override fun onFailure(call: Call<ListItem<Movie>>, t: Throwable) =
                    listener.onError(t.localizedMessage)

            override fun onResponse(call: Call<ListItem<Movie>>, response: Response<ListItem<Movie>>) {
                when(response.code()){
                    200->{
                        val movies = response.body()
                        if (movies == null)
                            listener.onError(ERROR_PARSE)
                        else{
                            last = movies.totalPages!!
                            listener.onFinishedFirst(movies.results!!, current == last)
                        }
                    }
                    401->listener.onError(ERROR401)
                    404->listener.onError(ERROR404)
                }
            }
        })
    }

    override fun onLoadedSearchedNext(listener: Search.Listener<Movie>) {
        current++
        val client = Client.retrofit!!.create(Service.Movies::class.java)
        val service = client.searchMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN, query, lang, current)
        service.enqueue(object : Callback<ListItem<Movie>>{
            override fun onFailure(call: Call<ListItem<Movie>>, t: Throwable) =
                    listener.onError(t.localizedMessage)

            override fun onResponse(call: Call<ListItem<Movie>>, response: Response<ListItem<Movie>>) {
                when(response.code()){
                    200->listener.onFinishedNext(response.body()!!.results!!, current == last)
                    401->listener.onError(ERROR401)
                    404->listener.onError(ERROR404)
                }
            }
        })
    }
}