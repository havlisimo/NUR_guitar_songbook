package cz.cvut.fit.nurguitarsongbook.main.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.cvut.fit.nurguitarsongbook.R
import cz.cvut.fit.nurguitarsongbook.base.BaseFragment

/**
 * Created by vasek on 11/28/2017.
 */
class OptionsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val v = inflater!!.inflate(R.layout.fragment_options, container, false)

        return v
    }
}