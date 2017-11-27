package cz.cvut.fit.nurguitarsongbook.base

import com.bignerdranch.android.multiselector.SwappingHolder

/**
 * Created by tomas on 01.11.2017.
 */
interface SelectableListFragment<T> {

    fun getData(): MutableList<T>
    fun getListItemView(viewType: Int): Int
    fun initListItem(holder: MultiselectAdapter.SelectableViewHolder?, item: T)
    fun getListItemViewType(): Int
}