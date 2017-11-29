package cz.cvut.fit.nurguitarsongbook.main.songbook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.R.drawable.d
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import cz.cvut.fit.nurguitarsongbook.model.entity.Song
import cz.cvut.fit.nurguitarsongbook.model.entity.Songbook
import cz.cvut.fit.nurguitarsongbook.model.entity.SongbookColor
import kotlinx.android.synthetic.main.activity_songbook_edit.*
import kotlinx.android.synthetic.main.dialog_songbook.*
import kotlinx.android.synthetic.main.dialog_songbook.view.*
import org.jetbrains.anko.toast

class SongBookEditActivity : AppCompatActivity() {

    lateinit var songook: Songbook
    var songId: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songbook_edit)
        setSupportActionBar(toolbar_edit_song)
        supportActionBar!!.setTitle("Edit Songbook")
        songId = intent.getIntExtra(SongBookEditActivity.INTENT_SONG_ID, 0);
        songook = DataMockup.getSongbookById(songId)
        name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length < 3) wrapper.error = getString(R.string.songbook_name_error)
                else wrapper.error = null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)  {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
        name.setText(songook.name)
        color_picker_view.setSelectedColor(songook.color.toArgb())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_done, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_done) {
            if (wrapper.error == null) {
                songook.name = name.text.toString()
                songook.color = SongbookColor(color_picker_view.color)
                finish()
            }
            else toast(R.string.songbook_errors)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private val INTENT_SONG_ID = "song_id"

        fun newIntent(context: Context, songId: Int): Intent {
            val intent = Intent(context, SongBookEditActivity::class.java)
            intent.putExtra(INTENT_SONG_ID, songId)
            return intent
        }
    }

}