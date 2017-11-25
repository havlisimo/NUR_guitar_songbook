package cz.cvut.fit.nurguitarsongbook.main.chord

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.flask.colorpicker.ColorPickerView
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.base.BaseAdapter
import cz.cvut.fit.nurguitarsongbook.base.BaseListFragment
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import cz.cvut.fit.nurguitarsongbook.model.entity.Songbook
import cz.cvut.fit.nurguitarsongbook.model.entity.SongbookColor
import kotlinx.android.synthetic.main.dialog_songbook.*
import kotlinx.android.synthetic.main.item_chord.view.*
import org.jetbrains.anko.*
import kotlinx.android.synthetic.main.fragment_songbook_list.view.*
import kotlinx.android.synthetic.main.dialog_songbook.view.*
import org.w3c.dom.Text


/**
 * Created by vasek on 11/13/2017.
 */
class SongbookListFragment : BaseListFragment<Songbook>() {

    override fun getData(): MutableList<cz.cvut.fit.nurguitarsongbook.model.entity.Songbook> {
        return DataMockup.songbooks
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.fragment_songbook_list, container, false)
        v!!.songbook_add.setOnClickListener({ _ -> showCreateSongbookDialog() })
        return v
    }


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

    private fun showCreateSongbookDialog() {
        val builder = getDialogBuilder(activity)
        builder.setPositiveButton(R.string.songbook_create,  null)
        builder.setTitle(R.string.songbook_title)
        val diag = builder.create()
        diag.show()
        diag.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener({v -> run {
            if (diag.wrapper.error != null) {
                toast(activity.getString(R.string.songbook_errors));
            } else {
                val name = diag.name.text.toString()
                val colorPicker = diag.findViewById<View>(R.id.color_picker_view) as ColorPickerView
                DataMockup.songbooks.add(Songbook(1, name, SongbookColor(colorPicker.selectedColor)))
                diag.dismiss()
                toast(activity.getString(R.string.songbook_success))
                adapter.notifyItemInserted(adapter.itemCount - 1)
            }

        }})
    }

    private fun getDialogBuilder(context: Context) : AlertDialog.Builder {
        val builder = AlertDialog.Builder(context)
        val view = activity.layoutInflater.inflate(R.layout.dialog_songbook, null);
        view.wrapper.error = context.getString(R.string.songbook_name_error)
        view.name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length < 3) view.wrapper.error = context.getString(R.string.songbook_name_error)
                else view.wrapper.error = null
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)  {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
        builder.setView(view)
        builder.setNegativeButton(R.string.songbook_cancel, {dialog, _ -> dialog.dismiss() })
        return builder
    }





}