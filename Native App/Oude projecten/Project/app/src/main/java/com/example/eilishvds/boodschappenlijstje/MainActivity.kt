package com.example.eilishvds.boodschappenlijstje

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley.newRequestQueue
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.login.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import androidx.navigation.Navigation
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, main_fragment.OnFragmentInteractionListener, settings_fragment.OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.id.layout.login)

        val queue = newRequestQueue(this);
        val url = "https://bramvandenabbeele.000webhostapp.com/mi3/project/api.php?";
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_boodschappenlijstje -> {
                Navigation.findNavController(findViewById(R.id.home_fragment)).navigate(R.id.main_fragment)
            }
            R.id.nav_instellingen -> {
                Navigation.findNavController(findViewById(R.id.home_fragment)).navigate(R.id.settings_fragment)
            }
            R.id.nav_afmelden -> {
                setContentView(R.layout.login)
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

     fun activityMain() {
         Navigation.findNavController(findViewById(R.id.home_fragment)).navigate(R.id.main_fragment)
/**
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        */
    }
}
