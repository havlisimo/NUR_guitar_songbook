package cz.cvut.fit.nurguitarsongbook.base

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by tomas on 01.11.2017.
 */
class BaseAdapter<T>(val listFragment: ListFragment<T>) : RecyclerView.Adapter<BaseAdapter.ViewHolder>() {

    var data: MutableList<T>? = listFragment.getData()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.getContext()).inflate(listFragment.getListItemView(viewType), parent, false);
        val vh = ViewHolder(v);
        return vh;
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        listFragment.initListItem(holder, data!!.get(position))
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return listFragment.getListItemViewType(position)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun refreshData() {
        data = listFragment.getData()
        notifyDataSetChanged()
    }

}