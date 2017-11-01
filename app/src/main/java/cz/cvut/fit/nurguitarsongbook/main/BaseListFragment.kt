package cz.cvut.fit.nurguitarsongbook.main

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.cvut.fit.nurguitarsongbook.R
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * Created by tomas on 01.11.2017.
 */
abstract class BaseListFragment<T>: Fragment(), ListFragment<T> {

    private val adapter: BaseAdapter<T> by lazy { BaseAdapter<T>(this) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater?.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

}