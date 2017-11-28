package cz.cvut.fit.nurguitarsongbook.main.song.songdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cz.cvut.fit.nurguitarsongbook.R

import kotlinx.android.synthetic.main.activity_play_song.*
import kotlinx.android.synthetic.main.content_play_song.*

class PlaySongActivity : AppCompatActivity() {

    var text: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_song)
        setSupportActionBar(toolbar_play_song)
        text = intent.getStringExtra( INTENT_SONG_TEXT )
        song_text.text = text
    }

    companion object {

        private val INTENT_SONG_TEXT = "song_text"

        fun newIntent(context: Context, text: String): Intent {
            val intent = Intent(context, PlaySongActivity::class.java)
            intent.putExtra( INTENT_SONG_TEXT, text )
            return intent
        }
    }

}
