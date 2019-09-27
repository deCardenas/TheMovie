package com.aprendiendo.themovie.models.detail

import com.aprendiendo.themovie.BuildConfig
import com.aprendiendo.themovie.api.Client
import com.aprendiendo.themovie.api.Service
import com.aprendiendo.themovie.data.Person
import com.aprendiendo.themovie.listeners.Detail
import com.aprendiendo.themovie.util.ERROR401
import com.aprendiendo.themovie.util.ERROR404
import com.aprendiendo.themovie.util.getLanguage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonDetailModel : Detail.Model<Person> {
    private val lang = getLanguage()

    override fun loadingData(id: Int, listener: Detail.Listener<Person>) {
        val client = Client.retrofit!!.create(Service.People::class.java)
        val service = client.personById(id, BuildConfig.THE_MOVIE_DB_API_TOKEN, lang)
        service.enqueue(object : Callback<Person>{
            override fun onFailure(call: Call<Person>, t: Throwable) =
                    listener.onError(t.localizedMessage)

            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                when(response.code()){
                    200->listener.onLoadedData(response.body()!!)
                    401->listener.onError(ERROR401)
                    404->listener.onError(ERROR404)
                }
            }
        })
    }

    override fun loadingSimilarities(id: Int, listener: Detail.Listener<Person>) {
        //do not implement
    }
}