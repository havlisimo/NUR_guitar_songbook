package cz.cvut.fit.nurguitarsongbook.main.songdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.model.entity.Song
import kotlinx.android.synthetic.main.activity_song_detail.*

import kotlinx.android.synthetic.main.activity_song_edit.*
import kotlinx.android.synthetic.main.content_song_detail.*
import kotlinx.android.synthetic.main.content_song_edit.*
import kotlinx.android.synthetic.main.fragment_song_detail.*

class SongEditActivity : AppCompatActivity() {

    var song: Song = Song( 0, "", "", "" )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_edit)
        setSupportActionBar(toolbar_edit_song)
        val songId = intent.getIntExtra(SongEditActivity.INTENT_SONG_ID, 0);
        val songName = intent.getStringExtra(SongEditActivity.INTENT_SONG_NAME)
        val songArtist = intent.getStringExtra(SongEditActivity.INTENT_SONG_ARTIST)
        val songComment = intent.getStringExtra(SongEditActivity.INTENT_SONG_COMMENT)
        song = Song(songId, songName,songArtist,songComment)
        this.et_song_name.setText( songName )
        this.et_song_artist.setText( songArtist )
        this.et_song_comment.setText( songComment )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_done, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_done) {
            return true;
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private val INTENT_SONG_ID = "song_id"
        private val INTENT_SONG_NAME = "song_name"
        private val INTENT_SONG_ARTIST = "song_artist"
        private val INTENT_SONG_COMMENT = "song_comment"

        fun newIntent(context: Context, song: Song): Intent {
            val intent = Intent(context, SongEditActivity::class.java)
            intent.putExtra(INTENT_SONG_ID, song.id)
            intent.putExtra(INTENT_SONG_NAME, song.name)
            intent.putExtra(INTENT_SONG_ARTIST, song.artist)
            intent.putExtra(INTENT_SONG_COMMENT, song.comment)
            return intent
        }
    }

}
