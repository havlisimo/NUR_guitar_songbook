package cz.cvut.fit.nurguitarsongbook.main.song.songdetail

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import cz.cvut.fit.nurguitarsongbook.App
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.base.BaseAdapter
import cz.cvut.fit.nurguitarsongbook.base.BaseFragment
import cz.cvut.fit.nurguitarsongbook.base.ListFragment
import cz.cvut.fit.nurguitarsongbook.main.songbook.SongbookListFragment
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import cz.cvut.fit.nurguitarsongbook.model.data.OptionsMockup
import cz.cvut.fit.nurguitarsongbook.model.entity.Song
import kotlinx.android.synthetic.main.fragment_song_detail.*
import kotlinx.android.synthetic.main.item_search_song_offline.view.*
import kotlinx.android.synthetic.main.item_song_line.view.*

class SongDetailFragment : BaseFragment(), ListFragment<String> {
    override fun getListItemViewType(position: Int): Int = 0

    override fun getData(): MutableList<String> {
        return song.text.lines().toMutableList()
    }

    override fun getListItemView(viewType: Int): Int {
        return R.layout.item_song_line
    }

    override fun initListItem(holder: BaseAdapter.ViewHolder?, item: String) {
        holder?.view?.line?.setTextSize(TypedValue.COMPLEX_UNIT_SP, OptionsMockup.songTextSize.toFloat())
        holder?.view?.line?.text = item
    }

    lateinit var song: Song
    var songId: Int = 0
    var songType: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        songId = data!!.getInt(EXTRA_SONG_ID, 0);
        songType = data!!.getInt(EXTRA_SONG_TYPE, 0);
        if (songType == ONLINE) {
            song = DataMockup.getOnlineSongById(songId)
        }
        if (songType == OFFLINE) {
            song = DataMockup.getSongById(songId)
        }
        setHasOptionsMenu(true)
        App.instance.activity?.setDisplayHomeAsUpEnabled(true)
        return inflater!!.inflate(R.layout.fragment_song_detail, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.instance.activity?.supportActionBar?.setTitle(song.name)
        tv_song_name.setText( getString( R.string.name ) + ": " + song.name )
        tv_song_artist.setText( getString(R.string.artist ) + ": " + song.artist )
        tv_song_comment.setText( getString(R.string.comment ) + ": " + song.comment )
        song_text.layoutManager = LinearLayoutManager(activity)
        song_text.setHasFixedSize(true)
        song_text.adapter = BaseAdapter<String>(this)

        songbookLabelsDetail?.removeAllViews()
        if (song.songbooks.size ?: 0 > 0) {
            val containerWidth = App.convertDpToPixel(80f)
            var width = containerWidth / (song.songbooks.size ?: 1)
            if (song.songbooks.size < 5) {
                width = containerWidth / 5
            }
            song.songbooks.forEach {
                val tv = View(activity)
                tv.layoutParams = ViewGroup.LayoutParams(width.toInt(), ViewGroup.LayoutParams.MATCH_PARENT)
                tv.setBackgroundColor(it.color.toArgb())
                songbookLabelsDetail?.addView(tv)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_song_detail, menu)
        menu?.findItem(R.id.action_edit_song)?.setVisible(songType.equals(OFFLINE))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_edit_song) {
            startActivityForResult( SongEditActivity.newIntent( activity, songId ), 0 );
        }
        if (item?.itemId == R.id.action_add_song_to_list) {
                val bundle = Bundle()
                bundle.putInt(SongbookListFragment.EXTRA_MODE, SongbookListFragment.MODE_SELECT_SINGLE)
                bundle.putIntegerArrayList(SongbookListFragment.EXTRA_SELECTED_IDS, arrayListOf(songId))
                App.instance.fragmentManager.changeFragment(SongbookListFragment::class.java, SongbookListFragment::class.java.getSimpleName(), bundle)
        }
        if (item?.itemId == R.id.action_play_song) {
            startActivity( PlaySongActivity.newIntent( activity, song.text ) )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        song = DataMockup.getSongById(songId)
        tv_song_name.setText( getString( R.string.name ) + ": " + song.name )
        tv_song_artist.setText( getString(R.string.artist ) + ": " + song.artist )
        tv_song_comment.setText( getString(R.string.comment ) + ": " + song.comment )
    }

    companion object {

        const val OFFLINE = 0
        const val ONLINE = 1

        private val EXTRA_SONG_TYPE = "song_type"
        private val EXTRA_SONG_ID = "song_id"

        fun newDataBundle(songId: Int, songType: Int = OFFLINE): Bundle {
            val bundle = Bundle()
            bundle.putInt(EXTRA_SONG_ID, songId)
            bundle.putInt(EXTRA_SONG_TYPE, songType)
            return bundle
        }
    }

}
