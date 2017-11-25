package cz.cvut.fit.nurguitarsongbook.main.songdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cz.cvut.fit.nurguitarsongbook.App
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.model.entity.Song

import kotlinx.android.synthetic.main.activity_song_detail.*
import cz.cvut.fit.nurguitarsongbook.R.string.app_name
import cz.cvut.fit.nurguitarsongbook.main.chord.ChordListFragment
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import kotlinx.android.synthetic.main.content_song_detail.*
import kotlinx.android.synthetic.main.fragment_song_detail.*


class SongDetailActivity : AppCompatActivity() {

    var song: Song = Song( "", "", "" )
    var songId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        songId = intent.getIntExtra(INTENT_SONG_ID, 0);
        song = DataMockup.songs[songId]

        setContentView(R.layout.activity_song_detail)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle(song.name)
        val f = fragment_song_detail
        f.tv_song_name.setText( getString( R.string.name ) + ": " + song.name )
        f.tv_song_artist.setText( getString(R.string.artist ) + ": " + song.artist )
        f.tv_song_comment.setText( getString(R.string.comment ) + ": " + song.comment )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_song_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_edit_song) {
            startActivityForResult( SongEditActivity.newIntent( this, songId ), 0 );
        }
        if (item?.itemId == R.id.action_add_song_to_list) {
            //TODO: Add song to list
        }
        if (item?.itemId == R.id.action_play_song) {
            startActivity( PlaySongActivity.newIntent( this ) )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        song = DataMockup.songs[songId]
        val f = fragment_song_detail
        f.tv_song_name.setText( getString( R.string.name ) + ": " + song.name )
        f.tv_song_artist.setText( getString(R.string.artist ) + ": " + song.artist )
        f.tv_song_comment.setText( getString(R.string.comment ) + ": " + song.comment )
    }

    companion object {

        private val INTENT_SONG_ID = "song_id"

        fun newIntent(context: Context, songId: Int): Intent {
            val intent = Intent(context, SongDetailActivity::class.java)
            intent.putExtra(INTENT_SONG_ID, songId)
            return intent
        }
    }

}
