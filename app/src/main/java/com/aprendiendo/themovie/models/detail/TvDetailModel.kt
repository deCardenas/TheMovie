package com.aprendiendo.themovie.models.detail

import com.aprendiendo.themovie.BuildConfig
import com.aprendiendo.themovie.api.Client
import com.aprendiendo.themovie.api.Service
import com.aprendiendo.themovie.data.ListItem
import com.aprendiendo.themovie.data.TvShow
import com.aprendiendo.themovie.listeners.Detail
import com.aprendiendo.themovie.util.ERROR401
import com.aprendiendo.themovie.util.ERROR404
import com.aprendiendo.themovie.util.ERROR_PARSE
import com.aprendiendo.themovie.util.getLanguage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvDetailModel : Detail.Model<TvShow>{
    private val lang = getLanguage()
    override fun loadingData(id: Int, listener: Detail.Listener<TvShow>) {
        val client = Client.retrofit!!.create(Service.Tvs::class.java)
        val service = client.tvById(id, BuildConfig.THE_MOVIE_DB_API_TOKEN, lang)
        service.enqueue(object : Callback<TvShow>{
            override fun onFailure(call: Call<TvShow>, t: Throwable)=
                    listener.onError(t.localizedMessage)

            override fun onResponse(call: Call<TvShow>, response: Response<TvShow>) {
                when(response.code()){
                    200->{
                        val tv = response.body()
                        if (tv == null)
                            listener.onError(ERROR_PARSE)
                        else
                            listener.onLoadedData(tv)
                    }
                    401->listener.onError(ERROR401)
                    404->listener.onError(ERROR404)
                }
            }
        })
    }

    override fun loadingSimilarities(id: Int, listener: Detail.Listener<TvShow>) {
        val client = Client.retrofit!!.create(Service.Tvs::class.java)
        val service = client.similarTvShows(id, BuildConfig.THE_MOVIE_DB_API_TOKEN, lang,1)
        service.enqueue(object : Callback<ListItem<TvShow>>{
            override fun onFailure(call: Call<ListItem<TvShow>>, t: Throwable) =
                    listener.onError(t.localizedMessage)

            override fun onResponse(call: Call<ListItem<TvShow>>, response: Response<ListItem<TvShow>>) {
                when(response.code()){
                    200->{
                        val tvShows = response.body()
                        if (tvShows == null)
                            listener.onError(ERROR_PARSE)
                        else
                            listener.onLoadedSimilarities(tvShows.results!!)
                    }
                    401->listener.onError(ERROR401)
                    404->listener.onError(ERROR404)
                }
            }
        })
    }
}