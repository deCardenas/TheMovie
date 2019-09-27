package com.aprendiendo.themovie.models.search

import com.aprendiendo.themovie.BuildConfig
import com.aprendiendo.themovie.api.Client
import com.aprendiendo.themovie.api.Service
import com.aprendiendo.themovie.data.ListItem
import com.aprendiendo.themovie.data.Person
import com.aprendiendo.themovie.listeners.Search
import com.aprendiendo.themovie.util.ERROR401
import com.aprendiendo.themovie.util.ERROR404
import com.aprendiendo.themovie.util.getLanguage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonSearchModel : Search.Model<Person> {
    private val lang = getLanguage()
    private var current = 0
    private var last = 0
    private var query = ""

    override fun onLoadedSearched(query: String, listener: Search.Listener<Person>) {
        current = 1
        this.query = query
        val client = Client.retrofit!!.create(Service.People::class.java)
        val service = client.searchPeople(BuildConfig.THE_MOVIE_DB_API_TOKEN, query, lang, current)
        service.enqueue(object : Callback<ListItem<Person>>{
            override fun onFailure(call: Call<ListItem<Person>>, t: Throwable) =
                    listener.onError(t.localizedMessage)

            override fun onResponse(call: Call<ListItem<Person>>, response: Response<ListItem<Person>>) {
                when(response.code()){
                    200->{
                        val people = response.body()
                        last = people?.totalPages!!
                        listener.onFinishedFirst(people.results!!, current == last)
                    }
                    401->listener.onError(ERROR401)
                    404->listener.onError(ERROR404)
                }
            }
        })
    }

    override fun onLoadedSearchedNext(listener: Search.Listener<Person>) {
        current++
        val client = Client.retrofit!!.create(Service.People::class.java)
        val service = client.searchPeople(BuildConfig.THE_MOVIE_DB_API_TOKEN, query, lang, current)
        service.enqueue(object : Callback<ListItem<Person>>{
            override fun onFailure(call: Call<ListItem<Person>>, t: Throwable) =
                    listener.onError(t.localizedMessage)

            override fun onResponse(call: Call<ListItem<Person>>, response: Response<ListItem<Person>>) {
                when(response.code()){
                    200->listener.onFinishedNext(response.body()?.results!!, current == last)
                    401->listener.onError(ERROR401)
                    404->listener.onError(ERROR404)
                }
            }
        })

    }
}