package com.aprendiendo.themovie.presenters

import android.support.v4.app.Fragment
import android.util.Log
import android.view.MenuItem
import com.aprendiendo.themovie.listeners.Nav
import com.aprendiendo.themovie.models.NavModel

class NavPresenter : Nav.Presenter, Nav.Listener {
    private val model: Nav.Model = NavModel()
    private var view: Nav.View? = null

    override fun onAttach(view: Nav.View) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    override fun onNavigateItemSelected(item: MenuItem) {
        model.onNavigate(this, item)
    }

    override fun replaceFragment(fragment: Fragment) {
        if (view != null)
            view!!.navigateTo(fragment)
        else
            Log.e("TAG","something is wrong with the view")
    }

    override fun actionWhatsApp() {
        if (view != null) view!!.onWhatsAppPushed()
    }

    override fun actionAboutMe() {
        if (view != null) view!!.onAboutMePushed()
    }
}