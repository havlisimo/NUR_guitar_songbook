package cz.cvut.fit.nurguitarsongbook.main.songdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.model.entity.Song

import kotlinx.android.synthetic.main.activity_song_detail.*

class SongDetailActivity : AppCompatActivity() {

    var songId : Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        songId = intent.getIntExtra(INTENT_SONG_ID, 0);

        setContentView(R.layout.activity_song_detail)
        setSupportActionBar(toolbar)

    }

    companion object {

        private val INTENT_SONG_ID = "song_id"

        fun newIntent(context: Context, song: Song): Intent {
            val intent = Intent(context, SongDetailActivity::class.java)
            intent.putExtra(INTENT_SONG_ID, song.id)
            return intent
        }
    }

}
