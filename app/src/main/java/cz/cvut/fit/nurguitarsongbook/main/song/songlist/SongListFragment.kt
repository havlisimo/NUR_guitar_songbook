package cz.cvut.fit.nurguitarsongbook.main.song.songlist

import android.app.FragmentManager
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.view.*
import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback
import cz.cvut.fit.nurguitarsongbook.App
import cz.cvut.fit.nurguitarsongbook.MainActivity
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.base.BaseSelectableListFragment
import cz.cvut.fit.nurguitarsongbook.base.MultiselectAdapter
import cz.cvut.fit.nurguitarsongbook.main.song.songdetail.SongDetailFragment
import cz.cvut.fit.nurguitarsongbook.main.songbook.SongBookEditActivity
import cz.cvut.fit.nurguitarsongbook.main.songbook.SongbookListFragment
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import cz.cvut.fit.nurguitarsongbook.model.entity.Song
import kotlinx.android.synthetic.main.item_search_song_offline.view.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

/**
 * A placeholder fragment containing a simple view.
 */
open class SongListFragment : BaseSelectableListFragment<Song>() {

//    init {
//        selector.isSelectable = true
//    }

    override fun onItemClick(view: View, item: Song) {
        App.instance.fragmentManager.changeFragment(SongDetailFragment::class.java,
            SongDetailFragment::class.java.getSimpleName(),
            SongDetailFragment.newDataBundle(DataMockup.songs.indexOf(item)))
    }

    var mySongs: ArrayList<Song> = ArrayList<Song>()

    var mDeleteMode = object : ModalMultiSelectorCallback(selector) {
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.itemId) {
                R.id.action_delete -> {
                    if (fr_mode == MODE_NORMAL) deleteSongs(mode)
                    else removeSongsFromSongbook(mode!!)
                }
                R.id.action_add_song_to_list -> addToList(mode)
            }
            return true
        }

        override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
            super.onCreateActionMode(actionMode, menu)
            if (fr_mode == MODE_NORMAL)
                activity.menuInflater.inflate(R.menu.menu_song_list, menu)
            if (fr_mode == MODE_SONGBOOK)
                activity.menuInflater.inflate(R.menu.menu_songbook_detail_context, menu)

            return true
        }
    }

    private fun removeSongsFromSongbook(mode: ActionMode) {
        alert(R.string.remove_songs) {
            yesButton {
                adapter.deleteSelectedData()
                DataMockup.getSongbookById(data?.getInt(SONGBOOK_ID)!!).songIds = ArrayList(adapter.data!!.map { song -> song.id })
                longSnackbar(view, R.string.undo_song_removal, R.string.undo, {
                    adapter.undoDelete()
                    DataMockup.getSongbookById(data?.getInt(SONGBOOK_ID)!!).songIds = ArrayList(adapter.data!!.map { song -> song.id })
                })
                mode!!.finish()
            }
            noButton {  }
        }.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fr_mode = data?.getInt(EXTRA_MODE) ?: MODE_NORMAL
        setHasOptionsMenu(fr_mode != MODE_NORMAL)

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        if (fr_mode == MODE_SONGBOOK) {
            inflater!!.inflate(R.menu.menu_songbook_detail, menu)
        }
        if (fr_mode == MODE_ADD_SONGS)
            inflater!!.inflate(R.menu.menu_add_songs_to_songbook, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_done -> run {addSelectedSongsToSongbook() }
            R.id.action_edit_songbook -> run {startEditSongookActivity() }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startEditSongookActivity() {
        val i = SongBookEditActivity.newIntent(activity, data?.getInt(SONGBOOK_ID)!!)
        startActivityForResult(i, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        adapter.refreshData()
    }

    private fun addSelectedSongsToSongbook() {
        val sb = DataMockup.getSongbookById(data?.getInt(SONGBOOK_ID)!!)
        val items = adapter.getSelectedItems().map { song -> song.id }
        sb.songIds.addAll(items)
        fragmentManager.popBackStackImmediate()

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.instance.activity?.setDisplayHomeAsUpEnabled(false)
        App.instance.activity?.supportActionBar?.setTitle(R.string.menu_songs)
        if (fr_mode == MODE_SONGBOOK) {
            view!!.fab.visibility = View.VISIBLE
            view.fab.setOnClickListener {_-> run {startMissingSongsActivity()}}
        }
    }

    private fun startMissingSongsActivity() {
        val bundle = Bundle()
        bundle.putInt(SongListFragment.EXTRA_MODE, SongListFragment.MODE_ADD_SONGS)
        bundle.putInt(SongListFragment.SONGBOOK_ID, data?.getInt(SONGBOOK_ID)!!)
        App.instance.fragmentManager.changeFragment(SongListFragment::class.java, "add_songs_to_songbook", bundle)
    }

    override fun getData(): MutableList<Song> {
        mySongs.clear()
        if (fr_mode == MODE_NORMAL) {
            return DataMockup.songs
        }
        if (fr_mode == MODE_SONGBOOK) {
            val songbook = DataMockup.getSongbookById(data?.getInt(SONGBOOK_ID)!!)
            App.instance.activity?.supportActionBar?.title = songbook.name
            (activity as MainActivity).setDisplayHomeAsUpEnabled(true)
            mySongs.addAll(DataMockup.getSongsForSongbookId(songbook.id))
        }
        if (fr_mode == MODE_ADD_SONGS) {
            val sbId = data?.getInt(SONGBOOK_ID)
            (activity as MainActivity).setDisplayHomeAsUpEnabled(true)
            mySongs.addAll(DataMockup.getSongsNotInSongbook(sbId!!))
            App.instance.activity?.supportActionBar?.title = "Add songs to songbook"
            selector.isSelectable = true
        }

        return mySongs
    }

    override fun getListItemView(viewType: Int): Int = R.layout.item_search_song_offline

    override fun initListItem(holder: MultiselectAdapter.SelectableViewHolder?, item: Song) {
        val view = holder!!.view
        view.songNameOffline.text = item.name
        view.songMetaOffline.text = item.artist

        view.setOnLongClickListener( object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                toggleSelection()
                selector.setSelected(holder, true)
                return true
            }

        })

        view.songbookLabels?.removeAllViews()
        if (item.songbooks.size ?: 0 > 0) {
            val containerWidth = App.convertDpToPixel(80f)
            var width = containerWidth / (item.songbooks.size ?: 1)
            if (item.songbooks.size < 5) {
                width = containerWidth / 5
            }
            item.songbooks.forEach {
                val tv = View(activity)
                tv.layoutParams = ViewGroup.LayoutParams(width.toInt(), ViewGroup.LayoutParams.MATCH_PARENT)
                tv.setBackgroundColor(it.color.toArgb())
                view.songbookLabels?.addView(tv)
            }
        }
    }

    fun toggleSelection() {
        //return if already selecting
        if (selector.isSelectable) return
        val a = activity as AppCompatActivity
        a.startSupportActionMode(mDeleteMode)
    }

    private fun deleteSongs(mode: ActionMode?) {
        alert(R.string.songs_delete_dialog) {
            yesButton { adapter.deleteSelectedData()
                longSnackbar(view, R.string.undo_song_deletion, R.string.undo, {adapter.undoDelete()})
                mode!!.finish()
            }
            noButton {  }
        }.show()
    }

    private fun addToList(mode: ActionMode?) {
        mode!!.finish()
        val bundle = Bundle()
        bundle.putInt(SongbookListFragment.EXTRA_MODE, SongbookListFragment.MODE_SELECT_SINGLE)
        bundle.putIntegerArrayList(SongbookListFragment.EXTRA_SELECTED_IDS, ArrayList(adapter.getSelectedItems().map { it.id }))
        App.instance.fragmentManager.changeFragment(SongbookListFragment::class.java, SongbookListFragment::class.java.getSimpleName(), bundle)
    }

    companion object {

        val INDEX_LIST: String = "indices"
        val SONGBOOK_NAME: String = "sbname"
        val SONGBOOK_ID: String = "sbid"
        const val MODE_NORMAL = 0
        const val MODE_SONGBOOK = 1
        const val MODE_ADD_SONGS = 2
        const val EXTRA_MODE = "fr_mode"
    }

    var fr_mode: Int = MODE_NORMAL
}
