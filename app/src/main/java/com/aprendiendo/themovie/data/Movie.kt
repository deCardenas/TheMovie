package com.aprendiendo.themovie.data

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

class Movie {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("original_title")
    var originalTitle: String? = null
    @SerializedName("overview")
    var overview: String? = null
    @SerializedName("poster_path")
    var posterPath: String? = null
    @SerializedName("backdrop_path")
    var backdropPath: String? = null
    @SerializedName("release_date")
    var releaseDate: String? = null
    @SerializedName("homepage")
    var homepage: String? = null
    @SerializedName("runtime")
    var runtime: Int? = null
    @SerializedName("budget")
    var budget: Int? = null
    @SerializedName("revenue")
    var revenue: Int? = null
    @SerializedName("popularity")
    var popularity: Double? = null
    var posterBit: Bitmap? = null
    var backdropBit: Bitmap? = null
    fun getRuntime(): String {
        val hours = runtime!! / 60
        val minutes = runtime!! % 60
        if (hours != 0) return "$hours horas $minutes minutos" else return "$minutes minutos"
    }
}