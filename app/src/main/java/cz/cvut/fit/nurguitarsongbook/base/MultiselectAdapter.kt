package cz.cvut.fit.nurguitarsongbook.base

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bignerdranch.android.multiselector.MultiSelector
import com.bignerdranch.android.multiselector.SwappingHolder

/**
 * Created by tomas on 01.11.2017.
 */
class MultiselectAdapter<T>(val listFragment: ListFragment<T>, val selector: MultiSelector) : RecyclerView.Adapter<MultiselectAdapter.SelectableViewHolder>() {

    var data: MutableList<T>? = listFragment.getData()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SelectableViewHolder {
        val v = LayoutInflater.from(parent?.getContext()).inflate(listFragment.getListItemView(), parent, false);
        val vh = SelectableViewHolder(v, selector);
        return vh;
    }

    override fun onBindViewHolder(holder: SelectableViewHolder?, position: Int) {

    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    class SelectableViewHolder(val view: View, val selector: MultiSelector) : SwappingHolder(view, selector), View.OnClickListener{
        override fun onClick(v: View?) {
            if (selector.tapSelection(this)) {

            }
            else {

            }
        }

    }

}