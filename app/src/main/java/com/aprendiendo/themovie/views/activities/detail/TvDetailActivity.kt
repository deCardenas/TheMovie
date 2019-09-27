package com.aprendiendo.themovie.views.activities.detail

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aprendiendo.themovie.BuildConfig
import com.aprendiendo.themovie.BuildConfig.BASE_IMAGE
import com.aprendiendo.themovie.R
import com.aprendiendo.themovie.data.TvShow
import com.aprendiendo.themovie.listeners.AdapterListener
import com.aprendiendo.themovie.listeners.Detail
import com.aprendiendo.themovie.presenters.detail.TvDetailPresenter
import com.aprendiendo.themovie.util.ID
import com.aprendiendo.themovie.views.adapters.SectionPageAdapter
import com.aprendiendo.themovie.views.adapters.TvAdapter
import com.aprendiendo.themovie.views.fragments.OverviewFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_similar.*
import kotlinx.android.synthetic.main.fragment_tv_detail.*
import org.jetbrains.anko.support.v4.act

class TvDetailActivity : DetailActivity(), Detail.View<TvShow> {
    override var presenter: Detail.Presenter = TvDetailPresenter(this)
    private var adapter: SectionPageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        title = ""
        setUpViewPager(viewpager)
        tabs.setupWithViewPager(viewpager)
        tabs.tabMode = TabLayout.MODE_FIXED
    }

    private fun setUpViewPager(viewPager: ViewPager) {
        adapter = SectionPageAdapter(supportFragmentManager)
        adapter!!.addFragment(OverviewFragment(), getString(R.string.overview))
        adapter!!.addFragment(DetailFragment(), getString(R.string.detail))
        adapter!!.addFragment(SimilarFragment(), getString(R.string.similarities))
        viewPager.adapter = adapter!!
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
        content.visibility = View.INVISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.INVISIBLE
        content.visibility = View.VISIBLE
    }

    override fun setError(error: String) {
        Log.e(TAG, error)
    }

    override fun showMessage(message: String) {
        txtMessage.text = message
    }

    override fun showData(item: TvShow) {
        title = item.name!!
        homepage = item.homepage!!
        Picasso.with(this).load(BuildConfig.BASE_IMAGE.plus(item.backdropPath))
                .placeholder(R.drawable.ic_launcher_background)
                .fit().into(backdrop)
        val overFragment = adapter!!.fragments[0] as OverviewFragment
        if (!overFragment.isLoaded)
            overFragment.overview = item.overview!!
        val detailFragment = adapter!!.fragments[1] as DetailFragment
        if (!detailFragment.isLoaded)
            detailFragment.setDate(item)
    }

    override fun showSimilarities(items: ArrayList<TvShow>) {
        val similarFragment = adapter!!.fragments[2] as SimilarFragment
        if (!similarFragment.isLoaded) {
            similarFragment.isLoaded = true
            similarFragment.adapter.addAll(items)
        }
    }

    class DetailFragment : Fragment() {
        var isLoaded = false

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_tv_detail, container, false)
        }

        fun setDate(tv: TvShow) {
            if (tv.posterBit == null) {
                if (tv.posterPath.isNullOrEmpty())
                    imvPoster.setImageResource(R.drawable.ic_launcher_foreground)
                else
                    Picasso.with(context!!)
                            .load(BASE_IMAGE.plus(tv.posterPath))
                            .placeholder(R.drawable.ic_launcher_background)
                            .fit().into(imvPoster)
            } else
                imvPoster.setImageBitmap(tv.posterBit)
            txtOriginalTitle.text = if (tv.originalName.isNullOrEmpty()) "" else tv.originalName!!
            txtFirstAirDate.text = if (tv.firstAirDate.isNullOrEmpty()) "" else tv.firstAirDate!!
            txtHomepage.text = if (tv.homepage.isNullOrEmpty()) "" else tv.homepage!!
        }
    }

    class SimilarFragment : Fragment(), AdapterListener {
        var isLoaded = false
        private val detail = TvDetailActivity::class.java
        val adapter = TvAdapter(this)

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_similar, container, false)
        }

        override fun onStart() {
            super.onStart()
            if (!isLoaded) {
                val layout = LinearLayoutManager(context)
                recycler.layoutManager = layout
                recycler.itemAnimator = DefaultItemAnimator()
                recycler.adapter = adapter
                val activity = act as TvDetailActivity
                activity.presenter.onLoadSimilarities(activity.id)
            }
        }

        override fun onClick(id: Int) {
            val intent = Intent(act, detail)
            intent.putExtra(ID, id)
            startActivity(intent)
        }
    }
}
