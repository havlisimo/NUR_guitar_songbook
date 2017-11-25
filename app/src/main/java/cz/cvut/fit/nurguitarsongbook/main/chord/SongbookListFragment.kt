package cz.cvut.fit.nurguitarsongbook.main.chord

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.base.BaseAdapter
import cz.cvut.fit.nurguitarsongbook.base.BaseListFragment
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import cz.cvut.fit.nurguitarsongbook.model.entity.Songbook
import kotlinx.android.synthetic.main.item_chord.view.*
import org.jetbrains.anko.*


/**
 * Created by vasek on 11/13/2017.
 */
class SongbookListFragment : BaseListFragment<Songbook>() {

    override fun getData(): MutableList<cz.cvut.fit.nurguitarsongbook.model.entity.Songbook> {
        return DataMockup.songbooks
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_songbook_list, container, false)

    override fun getListItemView(): Int = R.layout.item_chord

    override fun initListItem(holder: BaseAdapter.ViewHolder?, item: cz.cvut.fit.nurguitarsongbook.model.entity.Songbook) {
        val view = holder!!.view
        view.text.text = item.name
        val circle = activity.resources.getDrawable(R.drawable.circle_drawable) as GradientDrawable
        circle.setColor(item.color.toArgb())
        view.avatar.background = circle
        view.setOnClickListener { }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showCreateSongbookDialog() {
        val builder = getDialogBuilder(activity)
        builder.setPositiveButton(R.string.songbook_create, null)
        builder.setTitle(R.string.songbook_title)
        builder.show()
    }

    private fun getDialogBuilder(context: Context) : AlertDialog.Builder {
        val builder = AlertDialog.Builder(context)
        builder.setView(R.layout.dialog_songbook)
        builder.setNegativeButton(R.string.songbook_cancel, {dialog, _ -> dialog.dismiss() })
        return builder
    }





}