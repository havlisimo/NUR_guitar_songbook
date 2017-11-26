package cz.cvut.fit.nurguitarsongbook.main.chord

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.cvut.fit.nurguitarsongbook.App
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.base.BaseAdapter
import cz.cvut.fit.nurguitarsongbook.base.BaseListFragment
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import cz.cvut.fit.nurguitarsongbook.model.entity.Chord
import kotlinx.android.synthetic.main.item_chord.view.*

/**
 * Created by tomas on 01.11.2017.
 */
class ChordListFragment: BaseListFragment<Chord>() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        App.instance.activity?.setDisplayHomeAsUpEnabled(false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun getData(): MutableList<Chord> {
        return DataMockup.chords
    }

    override fun getListItemView(): Int = R.layout.item_chord

    override fun initListItem(holder: BaseAdapter.ViewHolder?, item: Chord) {
        val view = holder!!.view
        view.text.text = "${getText(R.string.chord)} ${item.name}"
        view.setOnClickListener {
            val b = Bundle()
            b.putSerializable(EXTRA_CONTENT, item)
            App.instance.fragmentManager.changeFragment(ChordDetailFragment::class.java, ChordDetailFragment::class.java.simpleName, b)
        }
    }

}