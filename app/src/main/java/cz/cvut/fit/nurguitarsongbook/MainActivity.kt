package cz.cvut.fit.nurguitarsongbook

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import cz.cvut.fit.nurguitarsongbook.main.chord.ChordListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_layout.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.instance.activity = this
        App.instance.fragmentManager.mFragmentManager = fragmentManager
        // Set initial fragment
        if (savedInstanceState == null) {
            App.instance.fragmentManager.changeFragment(ChordListFragment::class.java, ChordListFragment::class.java.simpleName)
        }

        // Main layout setup
        setSupportActionBar(toolbar)

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }


    /**
     * Main menu onclick
     * @param item
     * *
     * @return
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.menu_item_chords) {
            App.instance.fragmentManager.changeFragment(ChordListFragment::class.java, ChordListFragment::class.java.getSimpleName())
        }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

}
