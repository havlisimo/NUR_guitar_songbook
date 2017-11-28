package cz.cvut.fit.nurguitarsongbook.main.song.songlist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback
import cz.cvut.fit.nurguitarsongbook.App
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.base.BaseSelectableListFragment
import cz.cvut.fit.nurguitarsongbook.base.MultiselectAdapter
import cz.cvut.fit.nurguitarsongbook.main.song.songdetail.SongDetailFragment
import cz.cvut.fit.nurguitarsongbook.main.songbook.SongbookListFragment
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import cz.cvut.fit.nurguitarsongbook.model.entity.Song
import kotlinx.android.synthetic.main.item_search_song_offline.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.noButton
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

    val mySongs: ArrayList<Song> = ArrayList<Song>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val indices = data?.getIntegerArrayList(INDEX_LIST)
        if (indices == null) {
            mySongs.addAll(DataMockup.songs)
        } else {
            indices.forEach { mySongs.add(DataMockup.songs[it]) }
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

    val mDeleteMode = object : ModalMultiSelectorCallback(selector) {
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.itemId) {
                R.id.action_delete -> deleteSongs(mode)
                R.id.action_add_song_to_list -> addToList(mode)
            }
            return true
        }

        override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
            super.onCreateActionMode(actionMode, menu)
            activity.menuInflater.inflate(R.menu.menu_song_list, menu)
            return true
        }
    }

    private fun deleteSongs(mode: ActionMode?) {
        alert(R.string.songbooks_delete_dialog) {
            yesButton { adapter.deleteSelectedData()
                longSnackbar(view, R.string.undo_songbook_deletion, R.string.undo, {adapter.undoDelete()})
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
    }
}
