package com.aprendiendo.themovie.api

import com.aprendiendo.themovie.data.ListItem
import com.aprendiendo.themovie.data.Movie
import com.aprendiendo.themovie.data.Person
import com.aprendiendo.themovie.data.TvShow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {
    interface Movies {
        @GET("movie/popular")
        fun popularMovies(@Query("api_key") apiKey: String,
                          @Query("language") language: String,
                          @Query("page") page: Int): Call<ListItem<Movie>>

        @GET("movie/{movie_id}")
        fun movieById(@Path("movie_id") id: Int,
                      @Query("api_key") apiKey: String,
                      @Query("language") language: String): Call<Movie>

        @GET("movie/{movie_id}/images")
        fun movieImages(@Path("movie_id") id: Int,
                        @Query("api_key") apiKey: String): Call<Movie>

        @GET("movie/{movie_id}/similar")
        fun similarMovies(@Path("movie_id") id: Int,
                          @Query("api_key") apiKey: String,
                          @Query("language") language: String,
                          @Query("page") page: Int): Call<ListItem<Movie>>

        @GET("search/movie")
        fun searchMovies(@Query("api_key") apiKey: String,
                         @Query("query") query: String,
                         @Query("language") language: String,
                         @Query("page") page: Int): Call<ListItem<Movie>>
    }

    interface Tvs {
        @GET("tv/popular")
        fun popularTvs(@Query("api_key") apiKey: String,
                       @Query("language") language: String,
                       @Query("page") page: Int): Call<ListItem<TvShow>>

        @GET("tv/{tv_id}")
        fun tvById(@Path("tv_id") id: Int,
                   @Query("api_key") apiKey: String,
                   @Query("language") language: String): Call<TvShow>

        @GET("tv/{tv_id}/similar")
        fun similarTvShows(@Path("tv_id") id: Int,
                           @Query("api_key") apiKey: String,
                           @Query("language") language: String,
                           @Query("page") page: Int): Call<ListItem<TvShow>>

        @GET("search/tv")
        fun searchTvs(@Query("api_key") apiKey: String,
                      @Query("query") query: String,
                      @Query("language") language: String,
                      @Query("page") page: Int): Call<ListItem<TvShow>>
    }

    interface People {
        @GET("person/popular")
        fun popularPeople(@Query("api_key") apiKey: String,
                          @Query("language") language: String,
                          @Query("page") page: Int): Call<ListItem<Person>>

        @GET("person/{person_id}")
        fun personById(@Path("person_id") id: Int,
                       @Query("api_key") apiKey: String,
                       @Query("language") language: String): Call<Person>

        @GET("search/person")
        fun searchPeople(@Query("api_key") apiKey: String,
                         @Query("query") query: String,
                         @Query("language") language: String,
                         @Query("page") page: Int): Call<ListItem<Person>>
    }
}