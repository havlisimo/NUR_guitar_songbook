package cz.cvut.fit.nurguitarsongbook.main.search

import cz.cvut.fit.nurguitarsongbook.base.BaseAdapter
import cz.cvut.fit.nurguitarsongbook.base.BaseListFragment
import java.util.Collections

/**
 * Created by tomas on 27.11.2017.
 */
class SearchFragment: BaseListFragment<SearchItem>() {
    override fun getListItemView(viewType: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getData(): MutableList<SearchItem> {
        return Collections.emptyList()
    }

    fun getListItemView(): Int {
        return 0
    }

    override fun initListItem(holder: BaseAdapter.ViewHolder?, item: SearchItem) {

    }
}