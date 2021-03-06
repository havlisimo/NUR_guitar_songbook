package cz.cvut.fit.nurguitarsongbook.base

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bignerdranch.android.multiselector.MultiSelector
import cz.cvut.fit.nurguitarsongbook.R
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * Created by tomas on 01.11.2017.
 */
abstract class BaseSelectableListFragment<T>: BaseFragment(), SelectableListFragment<T> {

    protected val selector = MultiSelector()
    protected val adapter: MultiselectAdapter<T> by lazy { MultiselectAdapter<T>(this, selector) }

    protected fun setEmptyLabelText(text:String) {
        empty_label.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        empty_label.text = text
    }

    protected fun hideLabel() {
        empty_label.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater?.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    override fun getListItemViewType(): Int {
        return 0
    }

    override fun onResume() {
        super.onResume()
        adapter.refreshData()
    }

    abstract fun onItemClick(view: View, item: T)

}