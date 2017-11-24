package cz.cvut.fit.nurguitarsongbook.main.songdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import cz.cvut.fit.nurguitarsongbook.R

import kotlinx.android.synthetic.main.activity_play_song.*

class PlaySongActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_song)
        setSupportActionBar(toolbar_play_song)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, PlaySongActivity::class.java)
            return intent
        }
    }

}
