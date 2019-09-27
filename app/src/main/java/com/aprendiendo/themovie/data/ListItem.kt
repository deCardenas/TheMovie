package com.aprendiendo.themovie.data

import com.google.gson.annotations.SerializedName

class ListItem<T> {
    @SerializedName("page")
    val page: Int? = null
    @SerializedName("total_pages")
    val totalPages: Int? = null
    @SerializedName("results")
    val results: ArrayList<T>? = null
}