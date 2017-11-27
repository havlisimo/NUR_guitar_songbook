package cz.cvut.fit.nurguitarsongbook.base

import com.bignerdranch.android.multiselector.SwappingHolder

/**
 * Created by tomas on 01.11.2017.
 */
interface ListFragment<T> {

    fun getData(): MutableList<T>
    fun getListItemView(viewType: Int): Int
    fun initListItem(holder: BaseAdapter.ViewHolder?, item: T)
    fun getListItemViewType(position: Int): Int
}