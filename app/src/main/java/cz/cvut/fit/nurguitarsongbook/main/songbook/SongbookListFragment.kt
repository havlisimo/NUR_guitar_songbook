package cz.cvut.fit.nurguitarsongbook.main.songbook

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback
import com.flask.colorpicker.ColorPickerView
import cz.cvut.fit.nurguitarsongbook.App
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.base.BaseSelectableListFragment
import cz.cvut.fit.nurguitarsongbook.base.MultiselectAdapter
import cz.cvut.fit.nurguitarsongbook.main.song.songlist.SongListFragment
import cz.cvut.fit.nurguitarsongbook.model.data.DataMockup
import cz.cvut.fit.nurguitarsongbook.model.entity.Songbook
import cz.cvut.fit.nurguitarsongbook.model.entity.SongbookColor
import kotlinx.android.synthetic.main.dialog_songbook.*
import kotlinx.android.synthetic.main.dialog_songbook.view.*
import kotlinx.android.synthetic.main.fragment_songbook_list.view.*
import kotlinx.android.synthetic.main.item_chord.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton


/**
 * Created by vasek on 11/13/2017.
 */
class SongbookListFragment : BaseSelectableListFragment<Songbook>() {

    companion object {
        const val MODE_NORMAL = 0
        const val MODE_SELECT_SINGLE = 1
        const val EXTRA_SELECTED_IDS = "selectedIds"
        const val EXTRA_MODE = "fr_mode"
    }

    var mode: Int = MODE_NORMAL

    override fun onItemClick(view: View, item: Songbook) {
        val bundle = Bundle()
        if (mode == MODE_SELECT_SINGLE) {
            val list = data?.getIntegerArrayList(EXTRA_SELECTED_IDS)
            list?.forEach {
                if (!item.songIds.contains(it)) {
                    item.songIds.add(it)
                }
            }
            longSnackbar(view, R.string.added_to_songbook)
            activity.onBackPressed()
        }
        else {
            bundle.putString(SongListFragment.SONGBOOK_NAME, item.name)
            bundle.putInt(SongListFragment.SONGBOOK_ID, item.id)
            bundle.putInt(SongListFragment.EXTRA_MODE, SongListFragment.MODE_SONGBOOK)
            App.instance.fragmentManager.changeFragment(SongListFragment::class.java,
                "Songbook detail", bundle
            )
        }
    }

    override fun getData(): MutableList<cz.cvut.fit.nurguitarsongbook.model.entity.Songbook> {
        return DataMockup.songbooks
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.fragment_songbook_list, container, false)
        v!!.songbook_add.setOnClickListener({ _ -> showCreateSongbookDialog() })
        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.instance.activity?.setDisplayHomeAsUpEnabled(false)
        mode = data?.getInt(EXTRA_MODE) ?: MODE_NORMAL
        if (mode== MODE_SELECT_SINGLE) {
            App.instance.activity?.supportActionBar?.setTitle(R.string.song_add_to)
            App.instance.activity?.setDisplayHomeAsUpEnabled(true)
        }
        else {
            App.instance.activity?.supportActionBar?.setTitle(R.string.menu_songbooks)
        }
    }


    override fun getListItemView(type: Int): Int = R.layout.item_songbok

    override fun initListItem(holder: MultiselectAdapter.SelectableViewHolder?, item: Songbook) {
        val view = holder!!.view
        view.text.text = item.name
        val circle = activity.resources.getDrawable(R.drawable.circle_drawable) as GradientDrawable
        circle.setColor(item.color.toArgb())
        view.avatar.background = circle
        if (mode== MODE_NORMAL) {
            view.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    toggleSelection()
                    selector.setSelected(holder, true)
                    return true
                }
            })
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater?.inflate(R.menu.menu_songbook_list, menu)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
    }

    fun toggleSelection() {
        //return if already selecting
        if (selector.isSelectable) return
        val a = activity as AppCompatActivity
        a.startSupportActionMode(mDeleteMode)
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
                DataMockup.songbooks.add(Songbook(DataMockup.getSongbookId(), name, SongbookColor(diag.color_picker_view.color), ArrayList()))
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
        view.color_picker_view.setSelectedColor(resources.getColor(R.color.magenta))
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

    val mDeleteMode = object : ModalMultiSelectorCallback(selector) {
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.itemId) {
                R.id.action_delete -> deleteSongs(mode)
            }
            return true
        }

        override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
            super.onCreateActionMode(actionMode, menu)
            activity.menuInflater.inflate(R.menu.menu_songbook_list, menu)
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


}