package com.aprendiendo.themovie.models.list

import com.aprendiendo.themovie.BuildConfig
import com.aprendiendo.themovie.api.Client
import com.aprendiendo.themovie.api.Service
import com.aprendiendo.themovie.data.ListItem
import com.aprendiendo.themovie.data.Movie
import com.aprendiendo.themovie.listeners.Base
import com.aprendiendo.themovie.util.ERROR401
import com.aprendiendo.themovie.util.ERROR404
import com.aprendiendo.themovie.util.ERROR_PARSE
import com.aprendiendo.themovie.util.getLanguage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieModel : Base.Model<Movie> {
    private val lang = getLanguage()
    private var current = 0
    private var last = 0

    override fun loadingItemsFromDb(listener: Base.Listener<Movie>) {
        current = 1

    }

    override fun loadingItemsFirst(listener: Base.Listener<Movie>) {
        current = 1
        val client = Client.retrofit!!.create(Service.Movies::class.java)
        val service = client.popularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN, lang, current)
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
                            //do something to save in database
                            listener.onItemsLoadedFirst(movies.results!!, current == last)
                        }
                    }
                    401->listener.onError(ERROR401)
                    404->listener.onError(ERROR404)
                }
            }
        })
    }

    override fun loadingItemsNext(listener: Base.Listener<Movie>) {
        current++
        val client = Client.retrofit!!.create(Service.Movies::class.java)
        val service = client.popularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN, lang, current)
        service.enqueue(object : Callback<ListItem<Movie>>{
            override fun onFailure(call: Call<ListItem<Movie>>, t: Throwable) =
                    listener.onError(t.localizedMessage)

            override fun onResponse(call: Call<ListItem<Movie>>, response: Response<ListItem<Movie>>) {
                when(response.code()){
                    200->listener.onItemsLoadedNext(response.body()!!.results!!,current == last)
                    401->listener.onError(ERROR401)
                    404->listener.onError(ERROR404)
                }
            }
        })
    }
}