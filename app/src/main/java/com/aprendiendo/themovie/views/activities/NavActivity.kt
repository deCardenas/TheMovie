package com.aprendiendo.themovie.views.activities

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import com.aprendiendo.themovie.R
import com.aprendiendo.themovie.listeners.Nav
import com.aprendiendo.themovie.presenters.NavPresenter
import com.aprendiendo.themovie.util.CheckPermissions
import kotlinx.android.synthetic.main.activity_nav.*
import kotlinx.android.synthetic.main.app_bar_search.*
import org.jetbrains.anko.act

class NavActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, Nav.View {
    private var presenter: Nav.Presenter = NavPresenter()

    companion object {
        private val permissions = listOf(Manifest.permission.READ_CONTACTS)
        private const val REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)
        setSupportActionBar(toolbar)
        val toogle = ActionBarDrawerToggle(act, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toogle)
        toogle.syncState()
        presenter.onAttach(this)
        navView.setNavigationItemSelectedListener(this)
        onNavigationItemSelected(navView.menu.getItem(0))
        val verifyPermissions = object : CheckPermissions(this, permissions, REQUEST_CODE) {
            override fun onPermissionsGranted() {}
        }
        verifyPermissions.onVerifying()
    }

    override fun onResume() {
        super.onResume()
        presenter.onAttach(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.onDetach()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.START))
            drawer.closeDrawer(Gravity.START)
        else
            super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        presenter.onNavigateItemSelected(item)
        return true
    }

    override fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
        drawer.closeDrawer(Gravity.START)
        edtSearch.text.clear()
    }

    override fun onWhatsAppPushed() {
        val uri = Uri.parse("smsto:" + "+51945589188")
        val ws = Intent(Intent.ACTION_SENDTO, uri)
        ws.putExtra("sms_body", "Hello Diego,")
        startActivity(Intent.createChooser(ws, getString(R.string.share_with)))
    }

    override fun onAboutMePushed() {
        val builder = AlertDialog.Builder(act)
        builder.setTitle(R.string.about_me)
                .setMessage("About me.....")
                .setNegativeButton(R.string.ok) { dialog, _ -> dialog.cancel() }
        val dialog = builder.create()
        dialog.show()
    }
}
