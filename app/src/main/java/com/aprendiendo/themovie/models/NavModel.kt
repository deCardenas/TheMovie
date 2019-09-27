package com.aprendiendo.themovie.models

import android.support.v4.app.Fragment
import android.view.MenuItem
import com.aprendiendo.themovie.R
import com.aprendiendo.themovie.listeners.Nav
import com.aprendiendo.themovie.views.fragments.MovieFragment
import com.aprendiendo.themovie.views.fragments.PersonFragment
import com.aprendiendo.themovie.views.fragments.TvFragment

class NavModel : Nav.Model {
    companion object {
        private val fragments: List<Fragment> = listOf(
                MovieFragment(),
                TvFragment(),
                PersonFragment())
    }

    override fun onNavigate(listener: Nav.Listener, item: MenuItem) {
        when (item.itemId) {
            R.id.menu_movies -> {
                item.isChecked = true
                listener.replaceFragment(fragments[0])
            }
            R.id.menu_tvs -> {
                item.isChecked = true
                listener.replaceFragment(fragments[1])
            }
            R.id.menu_people -> {
                item.isChecked = true
                listener.replaceFragment(fragments[2])
            }
            R.id.menu_contact_me -> listener.actionWhatsApp()
            R.id.menu_about_me -> listener.actionAboutMe()
        }
    }
}