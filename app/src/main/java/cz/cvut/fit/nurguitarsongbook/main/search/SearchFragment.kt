package cz.cvut.fit.nurguitarsongbook.main.search

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.CardView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import cz.cvut.fit.nurguitarsongbook.App
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.base.BaseAdapter
import cz.cvut.fit.nurguitarsongbook.base.BaseListFragment
import cz.cvut.fit.nurguitarsongbook.main.song.songdetail.SongDetailFragment
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import cz.cvut.fit.nurguitarsongbook.model.entity.Song
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.item_search_header.view.*
import kotlinx.android.synthetic.main.item_search_song_offline.view.*
import kotlinx.android.synthetic.main.item_search_song_online.view.*
import java.util.Collections
import java.util.concurrent.TimeUnit

/**
 * Created by tomas on 27.11.2017.
 */
class SearchFragment : BaseListFragment<SongSearchWrapper>() {
    companion object {
        const val OFFLINE_HEADER = 1
        const val ONLINE_HEADER = 2
        const val OFFLINE_SONG = 3
        const val ONLINE_SONG = 4
    }

    private var disposable: Disposable? = null

    private var searchString: String = ""
    private var searchSongs: MutableList<SongSearchWrapper> = Collections.emptyList()
    private var onlineHidden: Boolean = false
        set(value) {
            field = value
            reloadItems()
        }

    private var offlineHidden: Boolean = false
        set(value) {
            field = value
            reloadItems()
        }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.instance.activity?.setDisplayHomeAsUpEnabled(false)
        App.instance.activity?.supportActionBar?.setTitle(R.string.menu_search)
        reloadItems()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search, menu);
        val searchView = menu?.findItem(R.id.option_search)?.actionView as SearchView
        searchView.setIconifiedByDefault(false)
        searchView.setQueryHint(getString(R.string.search_hint))
        searchView.setQuery(searchString, false)
        disposable = Observable.create<String>({ e ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    e.onNext(p0 ?: "")
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    e.onNext(p0 ?: "")
                    return true
                }
            })
        }).debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                searchString = it
                reloadItems()
            }, { Log.d("SearchFragment", "error", it) })

    }

    private fun reloadItems() {
        Observable.fromCallable { getSearchItems(searchString) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                searchSongs = it
                (recyclerView.adapter as BaseAdapter<SongSearchWrapper>).data = searchSongs
                recyclerView.adapter.notifyDataSetChanged()
            })
    }

    private fun getSearchItems(searchString: String): MutableList<SongSearchWrapper> {
        val list = ArrayList<SongSearchWrapper>()
        list.add(SongSearchWrapper(OFFLINE_HEADER))
        if (!offlineHidden) list.addAll(DataMockup.songs.filter { applyFilter(searchString, it) }.map { SongSearchWrapper(OFFLINE_SONG, it) })
        list.add(SongSearchWrapper(ONLINE_HEADER))
        if (!onlineHidden) list.addAll(DataMockup.onlineSongs.filter { applyFilter(searchString, it) }.map { SongSearchWrapper(ONLINE_SONG, it) })
        return list
    }

    private fun applyFilter(searchString: String, song: Song): Boolean {
        val searchStringLC = searchString.toLowerCase()
        return (searchString.isBlank()
            || song.name.toLowerCase().contains(searchStringLC)
            || song.artist.toLowerCase().contains(searchStringLC)
            || song.comment.toLowerCase().contains(searchStringLC))
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
            setArrow(holder?.view?.arrow, offlineHidden)
            holder?.view?.container?.setOnClickListener({ offlineHidden = !offlineHidden })
        }
        if (item.type.equals(ONLINE_HEADER)) {
            holder?.view?.text?.setText(R.string.search_online_header)
            setArrow(holder?.view?.arrow, onlineHidden)
            holder?.view?.container?.setOnClickListener({ onlineHidden = !onlineHidden })
        }
        if (item.type.equals(OFFLINE_SONG)) {
            holder?.view?.songNameOffline?.text = item.song?.name
            holder?.view?.songMetaOffline?.text = item.song?.artist ?: ""
            holder?.view?.findViewById<CardView>(R.id.card_view)?.setOnClickListener({
                App.instance.fragmentManager.changeFragment(SongDetailFragment::class.java, SongDetailFragment::class.java.name,
                    SongDetailFragment.newDataBundle(item.song!!.id, SongDetailFragment.OFFLINE))
            })
            holder?.view?.songbookLabels?.removeAllViews()
            if (item.song?.songbooks?.size ?: 0 > 0) {
                val containerWidth = App.convertDpToPixel(80f)
                var width = containerWidth / (item.song?.songbooks?.size ?: 1)
                if (item.song?.songbooks?.size!! < 5) {
                    width = containerWidth / 5
                }
                item.song.songbooks.forEach {
                    val tv = View(activity)
                    tv.layoutParams = ViewGroup.LayoutParams(width.toInt(), ViewGroup.LayoutParams.MATCH_PARENT)
                    tv.setBackgroundColor(it.color.toArgb())
                    holder?.view?.songbookLabels?.addView(tv)
                }
            }
        }
        if (item.type.equals(ONLINE_SONG)) {
            holder?.view?.songNameOnline?.text = item.song?.name
            holder?.view?.songMetaOnline?.text = item.song?.artist ?: ""
            holder?.view?.findViewById<CardView>(R.id.card_view)?.setOnClickListener({
                App.instance.fragmentManager.changeFragment(SongDetailFragment::class.java, SongDetailFragment::class.java.name,
                    SongDetailFragment.newDataBundle(item.song!!.id, SongDetailFragment.ONLINE))
            })
            holder?.view?.downloadButton?.setOnClickListener {
                val song = item.song
                if (!DataMockup.songs.contains(song)) {
                    DataMockup.songs.add(song!!)
                    Snackbar.make(it, R.string.song_downloaded, Snackbar.LENGTH_SHORT).show()
                    reloadItems()
                } else {
                    Snackbar.make(it, R.string.song_already_downloaded, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setArrow(arrow: ImageView?, hidden: Boolean) {
        if (hidden) {
            arrow?.setImageResource(R.drawable.arrow_down)
        } else {
            arrow?.setImageResource(R.drawable.arrow_up)
        }

    }

}