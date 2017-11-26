package cz.cvut.fit.nurguitarsongbook.main.song.songlist

import android.os.Bundle
import android.view.View
import cz.cvut.fit.nurguitarsongbook.App
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.base.BaseAdapter
import cz.cvut.fit.nurguitarsongbook.base.BaseListFragment
import cz.cvut.fit.nurguitarsongbook.main.song.songdetail.SongDetailFragment
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import cz.cvut.fit.nurguitarsongbook.model.entity.Song
import kotlinx.android.synthetic.main.item_song.view.*

/**
 * A placeholder fragment containing a simple view.
 */
class SongListFragment : BaseListFragment<Song>() {

    val mySongs: ArrayList<Song> = ArrayList<Song>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val indices = data?.getIntegerArrayList( INDEX_LIST )
        if (indices==null) {
            mySongs.addAll(DataMockup.songs)
        }
        else {
            indices.forEach { mySongs.add( DataMockup.songs[it] ) }
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.instance.activity?.setDisplayHomeAsUpEnabled(false)
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
            App.instance.fragmentManager.changeFragment(SongDetailFragment::class.java,
                SongDetailFragment::class.java.getSimpleName(),
                SongDetailFragment.newDataBundle(DataMockup.songs.indexOf(item)))
        }
    }

    companion object {

        val INDEX_LIST: String = "indices"
    }
}
