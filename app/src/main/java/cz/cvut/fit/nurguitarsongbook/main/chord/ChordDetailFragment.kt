package cz.cvut.fit.nurguitarsongbook.main.chord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.base.BaseFragment
import cz.cvut.fit.nurguitarsongbook.model.entity.Chord
import kotlinx.android.synthetic.main.fragment_chord_detail.*

/**
 * Created by tomas on 01.11.2017.
 */
class ChordDetailFragment: BaseFragment() {

    var chord: Chord? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater?.inflate(R.layout.fragment_chord_detail, container, false)


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chord = data?.get(EXTRA_CONTENT) as Chord?
        chord?.imageRes?.let {
            image.setImageResource(it)
        }
    }


}