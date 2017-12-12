package cz.cvut.fit.nurguitarsongbook.main.song.songdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.ScaleGestureDetector
import cz.cvut.fit.nurguitarsongbook.App
import android.view.View
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.model.data.OptionsMockup
import kotlinx.android.synthetic.main.activity_play_song.*
import kotlinx.android.synthetic.main.content_play_song.*
import java.util.Timer
import java.util.TimerTask

class PlaySongActivity : AppCompatActivity() {

    var text: String = ""
    var speed: Int = 1
    var paused: Boolean = false

    lateinit var scaleGestureDetector: ScaleGestureDetector
    var textSizeFactor: Float = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_song)
        setSupportActionBar(toolbar_play_song)
        text = intent.getStringExtra( INTENT_SONG_TEXT )
        textSizeFactor = intent.getFloatExtra( INTENT_SONG_SIZE_FACTOR , 1f)
        song_text.text = text
        val ts = App.convertSpToPixels(OptionsMockup.songTextSize.toFloat())*textSizeFactor
        song_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, ts)
        scaleGestureDetector = ScaleGestureDetector(this, simpleOnScaleGestureListener())
        song_text.setOnTouchListener({
            _, touchEvent -> scaleGestureDetector.onTouchEvent(touchEvent)
        })
        updateTextSpeed()
    }

    inner class simpleOnScaleGestureListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            var size = textSizeFactor
            val factor = detector.scaleFactor
            textSizeFactor = (size * factor)
            val ts = App.convertSpToPixels(OptionsMockup.songTextSize.toFloat()*textSizeFactor).toFloat()
            song_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, ts)
            return true
        }
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
        private val INTENT_SONG_SIZE_FACTOR = "song_size"

        fun newIntent(context: Context, text: String, textSizeFactor: Float): Intent {
            val intent = Intent(context, PlaySongActivity::class.java)
            intent.putExtra( INTENT_SONG_TEXT, text )
            intent.putExtra( INTENT_SONG_SIZE_FACTOR, textSizeFactor )
            return intent
        }
    }

}
