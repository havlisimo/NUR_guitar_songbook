package cz.cvut.fit.nurguitarsongbook.main.song.songlist

import android.os.Bundle
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.base.BaseAdapter
import cz.cvut.fit.nurguitarsongbook.base.BaseListFragment
import cz.cvut.fit.nurguitarsongbook.main.song.songdetail.SongDetailActivity
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import cz.cvut.fit.nurguitarsongbook.model.entity.Song
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
