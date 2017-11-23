package cz.cvut.fit.nurguitarsongbook.main.songdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.model.entity.Song

import kotlinx.android.synthetic.main.activity_song_detail.*
import cz.cvut.fit.nurguitarsongbook.R.string.app_name
import kotlinx.android.synthetic.main.content_song_detail.*
import kotlinx.android.synthetic.main.fragment_song_detail.*


class SongDetailActivity : AppCompatActivity() {

    var song: Song = Song( 0, "", "", "" )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val songId = intent.getIntExtra(INTENT_SONG_ID, 0);
        val songName = intent.getStringExtra(INTENT_SONG_NAME)
        val songArtist = intent.getStringExtra(INTENT_SONG_ARTIST)
        val songComment = intent.getStringExtra(INTENT_SONG_COMMENT)
        song = Song(songId, songName,songArtist,songComment)

        setContentView(R.layout.activity_song_detail)
        setSupportActionBar(toolbar)
        supportActionBar!!.setTitle(song.name)
        val f = fragment_song_detail
        f.tv_song_name.setText( songName )
        f.tv_song_artist.setText( songArtist )
        f.tv_song_comment.setText( songComment )
    }

    companion object {

        private val INTENT_SONG_ID = "song_id"
        private val INTENT_SONG_NAME = "song_name"
        private val INTENT_SONG_ARTIST = "song_artist"
        private val INTENT_SONG_COMMENT = "song_comment"

        fun newIntent(context: Context, song: Song): Intent {
            val intent = Intent(context, SongDetailActivity::class.java)
            intent.putExtra(INTENT_SONG_ID, song.id)
            intent.putExtra(INTENT_SONG_NAME, song.name)
            intent.putExtra(INTENT_SONG_ARTIST, song.artist)
            intent.putExtra(INTENT_SONG_COMMENT, song.comment)
            return intent
        }
    }

}
