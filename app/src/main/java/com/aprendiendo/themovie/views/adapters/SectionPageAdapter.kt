package com.aprendiendo.themovie.views.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.ArrayList

class SectionPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    val fragments: ArrayList<Fragment> = arrayListOf()
    private val titles: ArrayList<String> = arrayListOf()

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        titles.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence = titles[position]
}