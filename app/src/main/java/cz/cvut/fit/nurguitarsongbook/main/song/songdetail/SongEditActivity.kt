package cz.cvut.fit.nurguitarsongbook.main.song.songdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import cz.cvut.fit.nurguitarsongbook.model.entity.Song

import kotlinx.android.synthetic.main.activity_song_edit.*
import kotlinx.android.synthetic.main.content_song_edit.*

class SongEditActivity : AppCompatActivity() {

    lateinit var song: Song
    var songId: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_edit)
        setSupportActionBar(toolbar_edit_song)
        songId = intent.getIntExtra(SongEditActivity.INTENT_SONG_ID, 0);
        song = DataMockup.songs[songId]
        this.et_song_name.setText( song.name )
        this.et_song_artist.setText( song.artist )
        this.et_song_comment.setText( song.comment )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_done, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_done) {
            song.name = et_song_name.text.toString()
            song.artist = et_song_artist.text.toString()
            song.comment = et_song_comment.text.toString()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private val INTENT_SONG_ID = "song_id"

        fun newIntent(context: Context, songId: Int): Intent {
            val intent = Intent(context, SongEditActivity::class.java)
            intent.putExtra(INTENT_SONG_ID, songId)
            return intent
        }
    }

}
