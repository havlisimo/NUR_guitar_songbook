package cz.cvut.fit.nurguitarsongbook.base

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bignerdranch.android.multiselector.MultiSelector
import com.bignerdranch.android.multiselector.MultiSelectorBindingHolder
import com.bignerdranch.android.multiselector.SwappingHolder
import kotlinx.android.synthetic.main.item_songbok.view.*
import org.jetbrains.anko.*

/**
 * Created by tomas on 01.11.2017.
 */
class MultiselectAdapter<T>(val listFragment: SelectableListFragment<T>, val selector: MultiSelector) : RecyclerView.Adapter<MultiselectAdapter.SelectableViewHolder>() {

    var data: MutableList<T>? = listFragment.getData()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SelectableViewHolder {
        val v = LayoutInflater.from(parent?.getContext()).inflate(listFragment.getListItemView(0), parent, false);
        val vh = SelectableViewHolder(v, selector);

        return vh;
    }

    override fun onBindViewHolder(holder: SelectableViewHolder?, position: Int) {
        holder!!.view.setOnClickListener { v-> run {
            if (selector.tapSelection(holder))
            else (listFragment as BaseSelectableListFragment).onItemClick(v, data!![position])
        }}
        listFragment.initListItem(holder, data!![position])
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    class SelectableViewHolder(val view: View, val selector: MultiSelector) : MultiSelectorBindingHolder(view, selector) {

        private var selectable = false
        private var selected = false

        override fun isSelectable(): Boolean {
            return selectable
        }

        override fun isActivated(): Boolean {
            return selected
        }

        override fun setSelectable(p0: Boolean) {
            selectable = p0
            if (p0) view.checkbox.visibility = View.VISIBLE
            else view.checkbox.visibility = View.INVISIBLE

        }

        override fun setActivated(p0: Boolean) {
            selected = p0
            view.checkbox.isChecked = p0
        }

    }

}