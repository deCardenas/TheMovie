package com.aprendiendo.themovie.views.activities.detail

import android.os.Bundle
import android.util.Log
import com.aprendiendo.themovie.BuildConfig.BASE_IMAGE
import com.aprendiendo.themovie.R
import com.aprendiendo.themovie.data.Person
import com.aprendiendo.themovie.listeners.Detail
import com.aprendiendo.themovie.presenters.detail.PersonDetailPresenter
import com.aprendiendo.themovie.util.ID
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_person_detail.*
import kotlinx.android.synthetic.main.app_bar.*

class PersonDetailActivity : DetailActivity(), Detail.View<Person> {

    override var presenter: Detail.Presenter = PersonDetailPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_detail)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        title = ""
        if (intent == null)
            finish()
        val id = intent.getIntExtra(ID, 0)
        presenter.onLoadData(id)
    }

    override fun showProgress() {}

    override fun hideProgress() {}

    override fun setError(error: String) {
        Log.e(TAG, error)
    }

    override fun showMessage(message: String) {}

    override fun showData(item: Person) {
        title = item.name
        homepage = if (!item.homepage.isNullOrEmpty()) "" else item.homepage!!
        if (item.profilePath.isNullOrEmpty()) {
            imvProfile.setImageResource(R.drawable.ic_launcher_foreground)
        } else
            Picasso.with(this)
                    .load(BASE_IMAGE.plus(item.profilePath))
                    .placeholder(R.drawable.ic_launcher_background)
                    .fit().into(imvProfile)
        txtAdult.text = if (item.adult) getString(android.R.string.yes) else getString(android.R.string.no)
        txtGender.text = when (item.gender){
            1->"female"
            2->"male"
            else->""
        }
        txtPlaceBirth.text = if (item.placeOfBirth.isNullOrEmpty()) "" else item.placeOfBirth
        txtBirthday.text = if (item.birthday.isNullOrEmpty()) "" else item.birthday
        txtDeathday.text = if (item.deathday.isNullOrEmpty()) "" else item.deathday
        txtBiography.text = if (item.biography.isNullOrEmpty()) "" else item.biography
    }

    override fun showSimilarities(items: ArrayList<Person>) {
        //do not implement
    }
}
