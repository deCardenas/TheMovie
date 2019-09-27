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
import com.aprendiendo.themovie.R
import com.aprendiendo.themovie.data.Movie
import com.aprendiendo.themovie.listeners.AdapterListener
import com.aprendiendo.themovie.listeners.Detail
import com.aprendiendo.themovie.presenters.detail.MovieDetailPresenter
import com.aprendiendo.themovie.util.ID
import com.aprendiendo.themovie.views.adapters.MovieAdapter
import com.aprendiendo.themovie.views.adapters.SectionPageAdapter
import com.aprendiendo.themovie.views.fragments.OverviewFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.fragment_similar.*
import org.jetbrains.anko.support.v4.act
import java.text.NumberFormat
import java.util.*

class MovieDetailActivity : DetailActivity(), Detail.View<Movie> {
    override var presenter: Detail.Presenter = MovieDetailPresenter(this)
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

    override fun showData(item: Movie) {
        title = item.title!!
        homepage = item.homepage
        Picasso.with(this).load(BuildConfig.BASE_IMAGE.plus(item.backdropPath))
                .placeholder(R.drawable.ic_launcher_background)
                .fit().into(backdrop)
        val overFragment = adapter!!.fragments[0] as OverviewFragment
        if (!overFragment.isLoaded)
            overFragment.overview = item.overview!!
        val detailFragment = adapter!!.fragments[1] as DetailFragment
        if (!detailFragment.isLoaded)
            detailFragment.setData(item)

    }

    override fun showSimilarities(items: ArrayList<Movie>) {
        val similarFragment = adapter!!.fragments[2] as SimilarFragment
        if (!similarFragment.isLoaded) {
            similarFragment.isLoaded = true
            similarFragment.adapter.addAll(items)
        }
    }

    class DetailFragment : Fragment() {
        var isLoaded = false

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater.inflate(R.layout.fragment_movie_detail, container, false)
        }

        fun setData(movie: Movie) {
            isLoaded = true
            if (movie.posterBit == null) {
                if (movie.posterPath == null)
                    imvPoster.setImageResource(R.drawable.ic_launcher_foreground)
                else
                    Picasso.with(context)
                            .load(BuildConfig.BASE_IMAGE.plus(movie.posterPath))
                            .placeholder(R.drawable.ic_launcher_background)
                            .fit().into(imvPoster)
            } else
                imvPoster.setImageBitmap(movie.posterBit)
            txtOriginalTitle.text = if (movie.originalTitle != null) movie.originalTitle else ""
            txtHomepage.text = if (movie.homepage != null) movie.homepage else ""
            txtRuntime.text = if (movie.runtime != null) movie.getRuntime() else ""
            txtBudget.text =
                    if (movie.budget != null)
                        NumberFormat.getCurrencyInstance(Locale("en", "US"))
                                .format(movie.budget!!) else ""
            txtRevenue.text =
                    if (movie.revenue != null)
                        NumberFormat.getCurrencyInstance(Locale("en", "US"))
                                .format(movie.revenue!!) else ""
        }
    }

    class SimilarFragment : Fragment(), AdapterListener {
        var isLoaded = false
        val adapter: MovieAdapter = MovieAdapter(this)
        private val detail = MovieDetailActivity::class.java

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
                val activity = act as MovieDetailActivity
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
