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
import cz.cvut.fit.nurguitarsongbook.main.options.OptionsFragment
import cz.cvut.fit.nurguitarsongbook.main.search.SearchFragment
import cz.cvut.fit.nurguitarsongbook.main.song.songlist.SongListFragment
import cz.cvut.fit.nurguitarsongbook.main.songbook.SongbookListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.main_layout.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        private const val HOME_AS_UP = "HOME_AS_UP"
    }

    private var homeAsUpEnabled: Boolean = false

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.instance.activity = this
        App.instance.fragmentManager.mFragmentManager = fragmentManager
        // Set initial fragment
        if (savedInstanceState == null) {
            App.instance.fragmentManager.changeFragment(SongListFragment::class.java, SongListFragment::class.java.simpleName)
        }
        else {
            homeAsUpEnabled = savedInstanceState.getBoolean(HOME_AS_UP)
        }

        // Main layout setup
        setSupportActionBar(toolbar)

        val drawer = findViewById<View>(R.id.drawerLayout) as DrawerLayout
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        toggle.setToolbarNavigationClickListener {
            if (homeAsUpEnabled) {
                onBackPressed()
            }
        }
        drawer.addDrawerListener(toggle)
        drawer.nav_view.setCheckedItem(R.id.menu_item_songs)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        setDisplayHomeAsUpEnabled(homeAsUpEnabled)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(HOME_AS_UP, homeAsUpEnabled)
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
        if (id == R.id.menu_item_songbooks) {
            App.instance.fragmentManager.changeFragment(SongbookListFragment::class.java, SongbookListFragment::class.java.getSimpleName())
        }

        if ( id == R.id.menu_item_songs )
        {
            App.instance.fragmentManager.changeFragment(SongListFragment::class.java, SongListFragment::class.java.getSimpleName())
        }
        if ( id == R.id.menu_item_search )
        {
            App.instance.fragmentManager.changeFragment(SearchFragment::class.java, SearchFragment::class.java.getSimpleName())
        }
        if ( id == R.id.menu_item_setting )
        {
            App.instance.fragmentManager.changeFragment(OptionsFragment::class.java, OptionsFragment::class.java.getSimpleName())
        }

        val drawer = findViewById<View>(R.id.drawerLayout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers()
            return
        }
        if (App.instance.fragmentManager.fragmentManager.backStackEntryCount==1) {
            finish()
        }
        else {
            super.onBackPressed()
            when (App.instance.fragmentManager.currentFragment.javaClass) {
                SearchFragment::class.java -> drawerLayout.nav_view.setCheckedItem(R.id.menu_item_search)
                SongListFragment::class.java -> drawerLayout.nav_view.setCheckedItem(R.id.menu_item_songs)
                ChordListFragment::class.java -> drawerLayout.nav_view.setCheckedItem(R.id.menu_item_chords)
                SongbookListFragment::class.java -> drawerLayout.nav_view.setCheckedItem(R.id.menu_item_songbooks)
                OptionsFragment::class.java -> drawerLayout.nav_view.setCheckedItem(R.id.menu_item_setting)
            }
        }
    }

    fun setDisplayHomeAsUpEnabled(enabled: Boolean) {
        if ( !enabled ) {
            supportActionBar?.setDisplayHomeAsUpEnabled(enabled)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.syncState();

        }
        else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.setDrawerIndicatorEnabled(false);
            toggle.syncState();
            supportActionBar?.setDisplayHomeAsUpEnabled(enabled)
        }
        homeAsUpEnabled = enabled
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (App.instance.fragmentManager.currentFragment is OptionsFragment)
        {
            val fr = App.instance.fragmentManager.currentFragment as OptionsFragment
            fr.permissionsResult(requestCode, permissions, grantResults)
        }

    }
}
