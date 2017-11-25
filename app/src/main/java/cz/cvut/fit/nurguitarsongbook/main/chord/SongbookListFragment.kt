package cz.cvut.fit.nurguitarsongbook.main.chord

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.base.BaseAdapter
import cz.cvut.fit.nurguitarsongbook.base.BaseListFragment
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import cz.cvut.fit.nurguitarsongbook.model.entity.Songbook
import kotlinx.android.synthetic.main.item_chord.view.*


/**
 * Created by vasek on 11/13/2017.
 */
class SongbookListFragment : BaseListFragment<Songbook>() {

    override fun getData(): MutableList<cz.cvut.fit.nurguitarsongbook.model.entity.Songbook> {
        return DataMockup.songbooks
    }

    override fun getListItemView(): Int = R.layout.item_chord

    override fun initListItem(holder: BaseAdapter.ViewHolder?, item: cz.cvut.fit.nurguitarsongbook.model.entity.Songbook) {
        val view = holder!!.view
        view.text.text = item.name
        val circle = activity.resources.getDrawable(R.drawable.circle_drawable) as GradientDrawable
        circle.setColor(item.color.toArgb())
        view.avatar.background = circle
//        view.setOnClickListene
//            val b = Bundle()
//            b.putSerializable(BaseFragment.EXTRA_CONTENT, item)
//            App.instance.fragmentManager.changeFragment(ChordDetailFragment::class.java, ChordDetailFragment::class.java.simpleName, b)
//        }
    }

}