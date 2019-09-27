package com.aprendiendo.themovie.models.list

import android.content.Context
import com.aprendiendo.themovie.BuildConfig
import com.aprendiendo.themovie.api.Client
import com.aprendiendo.themovie.api.Service
import com.aprendiendo.themovie.data.ListItem
import com.aprendiendo.themovie.data.TvShow
import com.aprendiendo.themovie.listeners.Base
import com.aprendiendo.themovie.util.ERROR401
import com.aprendiendo.themovie.util.ERROR404
import com.aprendiendo.themovie.util.ERROR_PARSE
import com.aprendiendo.themovie.util.getLanguage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvModel : Base.Model<TvShow> {
    private val lang = getLanguage()
    private var current = 0
    private var last = 0
    private lateinit var context: Context

    override fun loadingItemsFromDb(listener: Base.Listener<TvShow>) {

    }

    override fun loadingItemsFirst(listener: Base.Listener<TvShow>) {
        current = 1
        val client = Client.retrofit!!.create(Service.Tvs::class.java)
        val service = client.popularTvs(BuildConfig.THE_MOVIE_DB_API_TOKEN, lang, current)
        service.enqueue(object : Callback<ListItem<TvShow>> {
            override fun onFailure(call: Call<ListItem<TvShow>>, t: Throwable) =
                    listener.onError(t.localizedMessage)

            override fun onResponse(call: Call<ListItem<TvShow>>, response: Response<ListItem<TvShow>>) {
                when (response.code()) {
                    200 -> {
                        val tvs = response.body()
                        if (tvs == null)
                            listener.onError(ERROR_PARSE)
                        else {
                            last = tvs.totalPages!!
                            listener.onItemsLoadedFirst(tvs.results!!, current == last)
                        }
                    }
                    401 -> listener.onError(ERROR401)
                    404 -> listener.onError(ERROR404)
                }
            }
        })
    }

    override fun loadingItemsNext(listener: Base.Listener<TvShow>) {
        current++
        val client = Client.retrofit!!.create(Service.Tvs::class.java)
        val service = client.popularTvs(BuildConfig.THE_MOVIE_DB_API_TOKEN, lang, current)
        service.enqueue(object : Callback<ListItem<TvShow>> {
            override fun onFailure(call: Call<ListItem<TvShow>>, t: Throwable) =
                    listener.onError(t.localizedMessage)

            override fun onResponse(call: Call<ListItem<TvShow>>, response: Response<ListItem<TvShow>>) {
                when (response.code()) {
                    200 -> listener.onItemsLoadedNext(response.body()!!.results!!, current == last)
                    401 -> listener.onError(ERROR401)
                    404 -> listener.onError(ERROR404)
                }
            }
        })

    }
/*
    private fun insert(tvShow: TvShow, dir : String?) {
        context.database.use {
            try {
                beginTransaction()
                val insertId = insert(TvShowTable.TABLE_NAME,
                        TvShowTable.ID to tvShow.id,
                        TvShowTable.NAME to tvShow.name,
                        TvShowTable.OVERVIEW to tvShow.overview,
                        TvShowTable.POSTER_PATH to dir)
                if (insertId != -1L){
                    setTransactionSuccessful()
                } else
                    throw RuntimeException("Fail to insert")
            } finally {
                endTransaction()
            }
        }
    }

    private fun clear(){
        context.database.use {
            delete(TvShowTable.TABLE_NAME)
        }
    }

    private fun select() : Observable<List<TvShow>>{
        var tvShows : List<TvShow> = listOf()
        context.database.use {
            tvShows = select(TvShowTable.TABLE_NAME).parseList(classParser<TvShow>())
        }
        return Observable.just(tvShows)
    }

    object TvObservable{
        fun load(){
            Observable.create(object : ObservableOnSubscribe<Int>{
                override fun subscribe(emitter: ObservableEmitter<Int>) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }
    }*/
}