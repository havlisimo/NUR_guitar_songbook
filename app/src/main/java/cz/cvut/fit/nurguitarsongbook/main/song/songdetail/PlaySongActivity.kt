package cz.cvut.fit.nurguitarsongbook.main.song.songdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.model.data.OptionsMockup

import kotlinx.android.synthetic.main.activity_play_song.*
import kotlinx.android.synthetic.main.content_play_song.*
import kotlinx.android.synthetic.main.content_song_edit.*
import java.util.*

class PlaySongActivity : AppCompatActivity() {

    var text: String = ""
    var speed: Int = 1
    var paused: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_song)
        setSupportActionBar(toolbar_play_song)
        text = intent.getStringExtra( INTENT_SONG_TEXT )
        song_text.text = text
        song_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, OptionsMockup.songTextSize.toFloat())
        updateTextSpeed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_play_song, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_despacito) {
            if( speed > 1 ) {
                speed = speed - 1
                updateTextSpeed()
            }
        }
        if (item?.itemId == R.id.action_play_pause) {
            if( paused == false )
            {
                item.setIcon( R.drawable.ic_play_circle_outline_black_24dp )
                paused = true
                speed_label.visibility = View.GONE
                line.visibility = View.GONE
            }
            else
            {
                item.setIcon( R.drawable.ic_pause_circle_outline_black_24dp )
                paused = false
                speed_label.visibility = View.VISIBLE
                line.visibility = View.VISIBLE
            }
        }
        if (item?.itemId == R.id.action_faster) {
            speed = speed + 1
            updateTextSpeed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun updateTextSpeed() {
        speed_label.text = "${getString(R.string.text_speed)}${speed}"
    }

    public override fun onResume() {
        super.onResume()
        val autoUpdate = Timer()
        autoUpdate.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread { updateScroll() }
            }
        }, 0, 35)
    }

    private fun updateScroll() {
        if( !paused )
            textAreaScroller.smoothScrollBy( 0, speed )
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
