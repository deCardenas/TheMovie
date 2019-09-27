package com.aprendiendo.themovie.models.detail

import com.aprendiendo.themovie.BuildConfig
import com.aprendiendo.themovie.api.Client
import com.aprendiendo.themovie.api.Service
import com.aprendiendo.themovie.data.ListItem
import com.aprendiendo.themovie.data.Movie
import com.aprendiendo.themovie.listeners.Detail
import com.aprendiendo.themovie.util.ERROR401
import com.aprendiendo.themovie.util.ERROR404
import com.aprendiendo.themovie.util.getLanguage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailModel : Detail.Model<Movie> {
    private val lang = getLanguage()

    override fun loadingData(id: Int, listener: Detail.Listener<Movie>) {
        val client = Client.retrofit!!.create(Service.Movies::class.java)
        val service = client.movieById(id, BuildConfig.THE_MOVIE_DB_API_TOKEN, lang)
        service.enqueue(object : Callback<Movie>{
            override fun onFailure(call: Call<Movie>, t: Throwable) =
                    listener.onError(t.localizedMessage)

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                when(response.code()){
                    200->listener.onLoadedData(response.body()!!)
                    401->listener.onError(ERROR401)
                    404->listener.onError(ERROR404)
                }
            }
        })
    }

    override fun loadingSimilarities(id: Int, listener: Detail.Listener<Movie>) {
        val client = Client.retrofit!!.create(Service.Movies::class.java)
        val service = client.similarMovies(id, BuildConfig.THE_MOVIE_DB_API_TOKEN, lang, 1)
        service.enqueue(object : Callback<ListItem<Movie>>{
            override fun onFailure(call: Call<ListItem<Movie>>, t: Throwable) =
                    listener.onError(t.localizedMessage)

            override fun onResponse(call: Call<ListItem<Movie>>, response: Response<ListItem<Movie>>) {
                when(response.code()){
                    200->listener.onLoadedSimilarities(response.body()?.results!!)
                    401->listener.onError(ERROR401)
                    404->listener.onError(ERROR404)
                }
            }
        })
    }
}