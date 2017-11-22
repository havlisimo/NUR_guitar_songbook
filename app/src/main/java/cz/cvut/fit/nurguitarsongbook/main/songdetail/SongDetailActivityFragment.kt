package cz.cvut.fit.nurguitarsongbook.main.songdetail

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.cvut.fit.nurguitarsongbook.R

/**
 * A placeholder fragment containing a simple view.
 */
class SongDetailActivityFragment : Fragment() {

    companion object {

        fun newInstance(): SongDetailActivityFragment {
            return SongDetailActivityFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_song_detail, container, false)
    }
}
