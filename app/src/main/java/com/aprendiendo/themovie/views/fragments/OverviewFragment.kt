package com.aprendiendo.themovie.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aprendiendo.themovie.R
import kotlinx.android.synthetic.main.fragment_overview.*

class OverviewFragment : Fragment() {
    var isLoaded = false
    var overview = ""
        set(value) {
            field = value
            isLoaded = true
            txtOverview.text = overview
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_overview, container, false)
    }

    override fun onStart() {
        super.onStart()
        txtOverview.text = overview
    }

}