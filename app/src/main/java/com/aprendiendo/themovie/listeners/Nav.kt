package com.aprendiendo.themovie.listeners

import android.support.v4.app.Fragment
import android.view.MenuItem

interface Nav {
    interface View{
        fun navigateTo(fragment: Fragment)
        fun onWhatsAppPushed()
        fun onAboutMePushed()
    }
    interface Presenter{
        fun onAttach(view: View)
        fun onDetach()
        fun onNavigateItemSelected(item : MenuItem)
    }
    interface Model{
        fun onNavigate(listener : Listener, item : MenuItem)
    }
    interface Listener{
        fun replaceFragment(fragment: Fragment)
        fun actionWhatsApp()
        fun actionAboutMe()
    }
}