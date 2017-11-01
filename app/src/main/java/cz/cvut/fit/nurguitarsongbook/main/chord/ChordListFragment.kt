package cz.cvut.fit.nurguitarsongbook.main.chord

import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.main.BaseAdapter
import cz.cvut.fit.nurguitarsongbook.main.BaseListFragment
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import cz.cvut.fit.nurguitarsongbook.model.entity.Chord
import kotlinx.android.synthetic.main.item_chord.view.*

/**
 * Created by tomas on 01.11.2017.
 */
class ChordListFragment: BaseListFragment<Chord>() {

    override fun getData(): MutableList<Chord> {
        return DataMockup.chords
    }

    override fun getListItemView(): Int = R.layout.item_chord

    override fun initListItem(holder: BaseAdapter.ViewHolder?, get: Chord) {
        val view = holder!!.view
        view.text.text = get.name
    }

}