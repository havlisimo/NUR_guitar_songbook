package cz.cvut.fit.nurguitarsongbook.main.song.songlist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cz.cvut.fit.nurguitarsongbook.App
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup

import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        setSupportActionBar(toolbar_song_list)
        val bundle = Bundle()
        val indicesList = ArrayList<Int>()
        for( s in DataMockup.songs )
        {
            indicesList.add( DataMockup.songs.indexOf( s ) )
        }
        bundle.putIntegerArrayList( SongListActivityFragment.INDEX_LIST, indicesList )
        App.instance.fragmentManager.changeFragment(SongListActivityFragment::class.java, SongListActivityFragment::class.java.getSimpleName(), bundle)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, SongListActivity::class.java)
            return intent
        }
    }

}
