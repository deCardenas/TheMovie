package com.aprendiendo.themovie.models.list

import com.aprendiendo.themovie.BuildConfig
import com.aprendiendo.themovie.api.Client
import com.aprendiendo.themovie.api.Service
import com.aprendiendo.themovie.data.ListItem
import com.aprendiendo.themovie.data.Person
import com.aprendiendo.themovie.listeners.Base
import com.aprendiendo.themovie.util.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonModel : Base.Model<Person> {
    private val lang = getLanguage()
    private var current = 0
    private var last = 0
    override fun loadingItemsFromDb(listener: Base.Listener<Person>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadingItemsFirst(listener: Base.Listener<Person>) {
        current = 1
        val client = Client.retrofit!!.create(Service.People::class.java)
        val service = client.popularPeople(BuildConfig.THE_MOVIE_DB_API_TOKEN, lang, current)
        service.enqueue(object : Callback<ListItem<Person>>{
            override fun onFailure(call: Call<ListItem<Person>>, t: Throwable) =
                    listener.onError(t.localizedMessage)

            override fun onResponse(call: Call<ListItem<Person>>, response: Response<ListItem<Person>>) {
                when(response.code()){
                    200->{
                        val people = response.body()
                        if (people == null)
                            listener.onError(ERROR_PARSE)
                        else{
                            last = people.totalPages!!
                            listener.onItemsLoadedFirst(people.results!!, current == last)
                        }
                    }
                    401->listener.onError(ERROR401)
                    404->listener.onError(ERROR404)
                }
            }
        })
    }

    override fun loadingItemsNext(listener: Base.Listener<Person>) {
        current++
        val client = Client.retrofit!!.create(Service.People::class.java)
        val service = client.popularPeople(BuildConfig.THE_MOVIE_DB_API_TOKEN, lang, current)
        service.enqueue(object : Callback<ListItem<Person>>{
            override fun onFailure(call: Call<ListItem<Person>>, t: Throwable) =
                    listener.onError(t.localizedMessage)

            override fun onResponse(call: Call<ListItem<Person>>, response: Response<ListItem<Person>>) {
                when(response.code()){
                    200-> listener.onItemsLoadedNext(response.body()!!.results!!, current == last)
                    401->listener.onError(ERROR401)
                    404->listener.onError(ERROR404)
                }
            }
        })
    }
}