package cz.cvut.fit.nurguitarsongbook.main.search

import android.support.v7.widget.CardView
import cz.cvut.fit.nurguitarsongbook.App
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.base.BaseAdapter
import cz.cvut.fit.nurguitarsongbook.base.BaseListFragment
import cz.cvut.fit.nurguitarsongbook.main.song.songdetail.SongDetailFragment
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import cz.cvut.fit.nurguitarsongbook.model.entity.Song
import kotlinx.android.synthetic.main.item_search_header.view.*
import kotlinx.android.synthetic.main.item_search_song_offline.view.*
import kotlinx.android.synthetic.main.item_search_song_online.view.*

/**
 * Created by tomas on 27.11.2017.
 */
class SearchFragment: BaseListFragment<SongSearchWrapper>() {
    companion object {
        const val OFFLINE_HEADER = 1
        const val ONLINE_HEADER = 2
        const val OFFLINE_SONG = 3
        const val ONLINE_SONG = 4
    }

    private var searchSongs: MutableList<SongSearchWrapper>

    init {
        searchSongs = getSearchItems("")
    }

    private fun getSearchItems(searchString: String): MutableList<SongSearchWrapper> {
        val list = ArrayList<SongSearchWrapper>()
        list.add(SongSearchWrapper(OFFLINE_HEADER))
        list.addAll(DataMockup.songs.filter { applyFilter(searchString, it) }.map { SongSearchWrapper(OFFLINE_SONG, it) })
        list.add(SongSearchWrapper(ONLINE_HEADER))
        list.addAll(DataMockup.onlineSongs.filter { applyFilter(searchString, it) }.map { SongSearchWrapper(ONLINE_SONG, it) })
        return list
    }

    private fun applyFilter(searchString: String, song: Song): Boolean {
        return (searchString.isBlank()
            || song.name.contains(searchString)
            || song.artist.contains(searchString)
            || song.comment.contains(searchString))
    }

    override fun getListItemView(viewType: Int): Int {
        when (viewType) {
            OFFLINE_HEADER, ONLINE_HEADER -> return R.layout.item_search_header
            OFFLINE_SONG -> return R.layout.item_search_song_offline
            ONLINE_SONG -> return R.layout.item_search_song_online
        }
        throw RuntimeException("Incorrect search list item type")
    }

    override fun getData(): MutableList<SongSearchWrapper> {
        return searchSongs
    }

    override fun getListItemViewType(position: Int): Int {
        return searchSongs[position].type
    }

    override fun initListItem(holder: BaseAdapter.ViewHolder?, item: SongSearchWrapper) {
        if (item.type.equals(OFFLINE_HEADER)) {
            holder?.view?.text?.setText(R.string.search_offline_header)
        }
        if (item.type.equals(ONLINE_HEADER)) {
            holder?.view?.text?.setText(R.string.search_online_header)
        }
        if (item.type.equals(OFFLINE_SONG)) {
            holder?.view?.songNameOffline?.text = item.song?.name
            holder?.view?.songMetaOffline?.text = item.song?.artist ?: ""
            holder?.view?.findViewById<CardView>(R.id.card_view)?.setOnClickListener({
                App.instance.fragmentManager.changeFragment(SongDetailFragment::class.java, SongDetailFragment::class.java.name,
                    SongDetailFragment.newDataBundle(DataMockup.songs.indexOf(item.song), SongDetailFragment.OFFLINE))
            })
        }
        if (item.type.equals(ONLINE_SONG)) {
            holder?.view?.songNameOnline?.text = item.song?.name
            holder?.view?.songMetaOnline?.text = item.song?.artist ?: ""
            holder?.view?.findViewById<CardView>(R.id.card_view)?.setOnClickListener({
                App.instance.fragmentManager.changeFragment(SongDetailFragment::class.java, SongDetailFragment::class.java.name,
                    SongDetailFragment.newDataBundle(DataMockup.onlineSongs.indexOf(item.song), SongDetailFragment.ONLINE))
            })
        }
    }
}