package com.aprendiendo.themovie.data

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

class Person {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("adult")
    var adult: Boolean = false
    @SerializedName("biography")
    var biography: String? = null
    @SerializedName("profile_path")
    var profilePath: String? = null
    @SerializedName("homepage")
    var homepage: String? = null
    @SerializedName("gender")
    var gender: Int = 0
    @SerializedName("place_of_birth")
    var placeOfBirth: String? = null
    @SerializedName("birthday")
    var birthday: String? = null
    @SerializedName("deathday")
    var deathday: String? = null
    @SerializedName("popularity")
    var popularity: Double? = null
    var profileBit : Bitmap? = null
}