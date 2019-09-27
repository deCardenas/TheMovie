package com.aprendiendo.themovie.views.activities.detail

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.aprendiendo.themovie.R
import com.aprendiendo.themovie.listeners.Detail
import com.aprendiendo.themovie.util.ID

abstract class DetailActivity : AppCompatActivity() {
    companion object {
        const val TAG = "DetailActivity"
    }

    abstract var presenter: Detail.Presenter
    var id = 0
    protected var homepage : String? = ""

    override fun onStart() {
        super.onStart()
        if (intent == null)
            finish()
        else {
            id = intent.getIntExtra(ID, 0)
            if (id == 0) finish() else presenter.onLoadData(id)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.share -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, homepage)
                startActivity(Intent.createChooser(intent, getString(R.string.share)))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}