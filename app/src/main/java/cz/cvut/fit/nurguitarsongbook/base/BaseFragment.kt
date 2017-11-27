package cz.cvut.fit.nurguitarsongbook.base

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by tomas on 01.11.2017.
 */

abstract class BaseFragment : Fragment() {

    var data: Bundle? = null

    /**
     * Save/Restore data
     */
    override fun onSaveInstanceState(outState: Bundle?) {
        //Save the fragment's state here
        outState?.putBundle(BUNDLE_KEY, data)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            //Restore the fragment's state here
            data = savedInstanceState.getBundle(BUNDLE_KEY)
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            //Restore the fragment's state here
            data = savedInstanceState.getBundle(BUNDLE_KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (savedInstanceState != null) {
            //Restore the fragment's state here
            data = savedInstanceState.getBundle(BUNDLE_KEY)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            //Restore the fragment's state here
            data = savedInstanceState.getBundle(BUNDLE_KEY)
        }
    }


    companion object {
        protected val BUNDLE_KEY = "fragment_data"
        val EXTRA_CONTENT: String = "EXTRA_CONTENT";
    }
}
