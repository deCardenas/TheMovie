package com.aprendiendo.themovie.data

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

class TvShow {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("original_name")
    var originalName: String? = null
    @SerializedName("overview")
    var overview: String? = null
    @SerializedName("poster_path")
    var posterPath: String? = null
    @SerializedName("backdrop_path")
    var backdropPath: String? = null
    @SerializedName("homepage")
    var homepage: String? = null
    @SerializedName("first_air_date")
    var firstAirDate: String? = null
    @SerializedName("episode_run_time")
    var runtime: IntArray? = null
    @SerializedName("popularity")
    var popularity: Double? = null
    var posterBit : Bitmap? = null
    var backdropBit : Bitmap? = null
}