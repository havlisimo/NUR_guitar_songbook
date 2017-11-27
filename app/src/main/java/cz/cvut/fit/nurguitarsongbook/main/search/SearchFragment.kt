package cz.cvut.fit.nurguitarsongbook.main.search

import cz.cvut.fit.nurguitarsongbook.base.BaseAdapter
import cz.cvut.fit.nurguitarsongbook.base.BaseListFragment
import java.util.Collections

/**
 * Created by tomas on 27.11.2017.
 */
class SearchFragment: BaseListFragment<SearchItem>() {
    companion object {
        const val OFFLINE_HEADER = 1
        const val ONLINE_HEADER = 2
        const val OFFLINE_SONG = 3
        const val ONLINE_SONG = 4
    }

    override fun getListItemView(viewType: Int): Int {
        return 0
    }

    override fun getData(): MutableList<SearchItem> {
        return Collections.emptyList()
    }

    override fun getListItemViewType(position: Int): Int {
        return super.getListItemViewType(position)
    }

    override fun initListItem(holder: BaseAdapter.ViewHolder?, item: SearchItem) {

    }
}