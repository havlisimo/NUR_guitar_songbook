package cz.cvut.fit.nurguitarsongbook.main.song.songdetail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import cz.cvut.fit.nurguitarsongbook.App
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.base.BaseFragment
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import cz.cvut.fit.nurguitarsongbook.model.entity.Song
import kotlinx.android.synthetic.main.fragment_song_detail.*


class SongDetailFragment : BaseFragment() {

    lateinit var song: Song
    var songId: Int = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        songId = data!!.getInt(INTENT_SONG_ID, 0);
        song = DataMockup.songs[songId]
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
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_song_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_edit_song) {
            startActivityForResult( SongEditActivity.newIntent( activity, songId ), 0 );
        }
        if (item?.itemId == R.id.action_add_song_to_list) {
            //TODO: Add song to list
        }
        if (item?.itemId == R.id.action_play_song) {
            startActivity( PlaySongActivity.newIntent( activity ) )
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        song = DataMockup.songs[songId]
        tv_song_name.setText( getString( R.string.name ) + ": " + song.name )
        tv_song_artist.setText( getString(R.string.artist ) + ": " + song.artist )
        tv_song_comment.setText( getString(R.string.comment ) + ": " + song.comment )
    }

    companion object {

        private val INTENT_SONG_ID = "song_id"

        fun newDataBundle(songId: Int): Bundle {
            val bundle = Bundle()
            bundle.putInt(INTENT_SONG_ID, songId)
            return bundle
        }
    }

}
