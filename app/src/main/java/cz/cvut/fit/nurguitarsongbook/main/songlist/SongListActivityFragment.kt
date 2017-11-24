package cz.cvut.fit.nurguitarsongbook.main.songlist

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.cvut.fit.nurguitarsongbook.App
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.base.BaseAdapter
import cz.cvut.fit.nurguitarsongbook.base.BaseFragment
import cz.cvut.fit.nurguitarsongbook.base.BaseListFragment
import cz.cvut.fit.nurguitarsongbook.main.chord.ChordDetailFragment
import cz.cvut.fit.nurguitarsongbook.main.songdetail.SongDetailActivity
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import cz.cvut.fit.nurguitarsongbook.model.entity.Chord
import cz.cvut.fit.nurguitarsongbook.model.entity.Song
import kotlinx.android.synthetic.main.item_chord.view.*
import kotlinx.android.synthetic.main.item_song.view.*

/**
 * A placeholder fragment containing a simple view.
 */
class SongListActivityFragment : BaseListFragment<Song>() {

    val mySongs: ArrayList<Song> = ArrayList<Song>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val indices = data?.getIntegerArrayList( INDEX_LIST )
        for( i in indices!! )
        {
            mySongs.add( DataMockup.songs[i] )
        }
    }

    override fun getData(): MutableList<Song> {
        return mySongs
    }

    override fun getListItemView(): Int = R.layout.item_song

    override fun initListItem(holder: BaseAdapter.ViewHolder?, item: Song) {
        val view = holder!!.view
        view.song_name.text = item.name
        view.song_artist.text = item.artist
        view.setOnClickListener {
            startActivity( SongDetailActivity.newIntent( this.activity, DataMockup.songs.indexOf(item) ) )
        }
    }

    companion object {

        val INDEX_LIST: String = "indices"
    }
}
