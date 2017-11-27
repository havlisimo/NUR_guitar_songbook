package cz.cvut.fit.nurguitarsongbook.main.song.songlist

import android.os.Bundle
import android.view.View
import cz.cvut.fit.nurguitarsongbook.App
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.base.BaseAdapter
import cz.cvut.fit.nurguitarsongbook.base.BaseListFragment
import cz.cvut.fit.nurguitarsongbook.base.BaseSelectableListFragment
import cz.cvut.fit.nurguitarsongbook.base.MultiselectAdapter
import cz.cvut.fit.nurguitarsongbook.main.song.songdetail.SongDetailFragment
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import cz.cvut.fit.nurguitarsongbook.model.entity.Song
import kotlinx.android.synthetic.main.item_search_song_offline.view.*

/**
 * A placeholder fragment containing a simple view.
 */
class SongListFragment : BaseSelectableListFragment<Song>() {

//    init {
//        selector.isSelectable = true
//    }

    override fun onItemClick(view: View, item: Song) {
        App.instance.fragmentManager.changeFragment(SongDetailFragment::class.java,
                SongDetailFragment::class.java.getSimpleName(),
                SongDetailFragment.newDataBundle(DataMockup.songs.indexOf(item)))
    }

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
        App.instance.activity?.supportActionBar?.setTitle(R.string.menu_songs)
    }

    override fun getData(): MutableList<Song> {
        return mySongs
    }

    override fun getListItemView(viewType: Int): Int = R.layout.item_search_song_offline

    override fun initListItem(holder: MultiselectAdapter.SelectableViewHolder?, item: Song) {
        val view = holder!!.view
        view.songNameOffline.text = item.name
        view.songMetaOffline.text = item.artist
    }

    companion object {

        val INDEX_LIST: String = "indices"
    }
}
