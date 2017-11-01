package cz.cvut.fit.nurguitarsongbook.main

/**
 * Created by tomas on 01.11.2017.
 */
interface ListFragment<T> {

    fun getData(): MutableList<T>
    fun getListItemView(): Int
    fun initListItem(holder: BaseAdapter.ViewHolder?, item: T)
}