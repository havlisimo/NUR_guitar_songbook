package cz.cvut.fit.nurguitarsongbook

import android.app.Fragment
import android.app.FragmentManager
import android.os.Bundle
import android.util.Log
import cz.cvut.fit.nurguitarsongbook.base.BaseFragment

class FragmentManager
{
    var mFragmentManager: FragmentManager? = null

    private var mCurrentFragment: BaseFragment? = null

    val fragmentManager: FragmentManager
        get()
        {
            if (mFragmentManager==null) {
                mFragmentManager = App.instance.activity?.fragmentManager
            }
            return mFragmentManager ?: throw RuntimeException("Could not get Fragment Manager")
        }

    fun <FragmentType : BaseFragment> changeFragment(fragmentClass: Class<FragmentType>, key: String, rFragContainer: Int, fragmentManager: FragmentManager): FragmentType {
        return changeFragment(fragmentClass, key, rFragContainer, fragmentManager, null)
    }

    fun <FragmentType : BaseFragment> changeFragment(fragmentClass: Class<FragmentType>, key: String, rFragContainer: Int, fragmentManager: FragmentManager, extras: Bundle?): FragmentType {
        return changeFragment(fragmentClass, key, rFragContainer, true, fragmentManager, extras)
    }

    fun <FragmentType : BaseFragment> changeFragment(fragmentClass: Class<FragmentType>, key: String): FragmentType {
        return changeFragment(fragmentClass, key, MAIN_CONTAINER_ID, true, null, null)
    }

    fun <FragmentType : BaseFragment> changeFragment(fragmentClass: Class<FragmentType>, key: String, extras: Bundle): FragmentType {
        return changeFragment(fragmentClass, key, MAIN_CONTAINER_ID, true, null, extras)
    }

    /**
     * Fragment change method - used for fragments on first level
     * @param fragmentClass Fragment to change
     * *
     * @param rFragContainer Resource of fragment container
     * *
     * @param addToBackStack Add to backstack; usually true (combinig true/false on one level breaks back button functionality)
     * *
     * @param fragmentManager Internal fragment manager of enclosing object
     * *
     * @param extras Custom data to update fragment with
     * *
     * @param <FragmentType> Class type of the fragment
     * *
     * @param <BundleType> Class type of the bundle
     * *
     * @return Fragment instance
    </BundleType></FragmentType> */
    protected fun <FragmentType : BaseFragment> changeFragment(fragmentClass: Class<FragmentType>, key: String, rFragContainer: Int, addToBackStack: Boolean, frManager: FragmentManager?, extras: Bundle?): FragmentType {
        // Sets specific fragmentManager (from parent fragment) or use globally defined fragment manager (topmost)
        val fm = frManager ?: fragmentManager

        try {
            val fragment = getFragment<FragmentType>(fragmentClass, key, fm, extras)

            // Begin transaction
            val transaction = fm.beginTransaction()

            // Replace whatever is in the fragment_container view with this fragment
            transaction.replace(rFragContainer, fragment as Fragment, key)

            // Add the transaction to the back stack so the user can navigate back
            if (addToBackStack) {
                transaction.addToBackStack(key)
            }

            // Commit the transaction
            transaction.commitAllowingStateLoss()

            if (rFragContainer == MAIN_CONTAINER_ID) {
                mCurrentFragment = fragment
            }

            // @INFO wrong backstack count here; commit is not yet finished so count shows count-1
            Log.d(LOG_TAG, "Fragment: " + key + " (backstack count " + fm.getBackStackEntryCount() + ")")

            return fragmentClass.cast(fragment)
        } catch (ex: Exception) {
            Log.e(LOG_TAG, "Change fragment failed!")
            ex.printStackTrace()
            throw ex
        }

    }

    /**
     * Remove fragment
     * @param fragmentClass Fragment to remove
     * *
     * @param fragmentManager Internal fragment manager of enclosing object
     * *
     * @param <FragmentType> Class type of the fragment
    </FragmentType> */
    fun <FragmentType : Fragment> removeFragment(fragmentClass: Class<FragmentType>, key: String, frManager: FragmentManager?) {
        val fm = frManager ?: fragmentManager

        val transaction = fm.beginTransaction()
        val fragment = fm.findFragmentByTag(key)

        transaction.detach(fragment)
        transaction.commit()
    }

    /**
     * Create new fragment instance or get existing
     * @param fragmentClass Fragment to get
     * *
     * @param fm Internal fragment manager of enclosing object
     * *
     * @param <FragmentType> Class type of the fragment
     * *
     * @return Fragment instance
    </FragmentType> */
    fun <FragmentType : BaseFragment> getFragment(fragmentClass: Class<FragmentType>, key: String, fm: FragmentManager, extras: Bundle?): FragmentType? {
        try {
            // Check if fragment exists
            val existFragment = fm.findFragmentByTag(key)
            val isReusing: Boolean

            val fragment: FragmentType

            if (existFragment != null) {
                // Use existing fragment
                fragment = existFragment as FragmentType
                isReusing = true
            } else {
                // Create fragment - call static method newInstance and return its object return value
                fragment = fragmentClass.newInstance()
                isReusing = false
            }
            fragment.data = extras

            Log.d(LOG_TAG, (if (isReusing) "ReUsing" else "Creating") + " Fragment: " + key)

            return fragmentClass.cast(fragment)
        } catch (ex: Exception) {
            Log.e(LOG_TAG, "Get fragment failed!")
            ex.printStackTrace()

            return null
        }

    }

    fun removeFragmentAtBackStackIndex(index: Int) {
        removeFragmentAtBackStackIndex(null, index)
    }

    fun removeFragmentAtBackStackIndex(frManager: FragmentManager?, index: Int) {
        val fm = frManager ?: fragmentManager
        val name = fm.getBackStackEntryAt(index).name
        val fragment = fm.findFragmentByTag(name)

        val trans = fm.beginTransaction()
        trans.remove(fragment)
        trans.commit()
        fm.popBackStack()
    }

    /**
     * Clears top item for given fragment manager (or use mFragmentManager)
     * @param fragmentManager
     */
    @JvmOverloads fun popBackstackTop(frManager: FragmentManager? = null) {
        // Sets specific fragmentManager (from parent fragment) or use globally defined fragment manager (topmost)
        val fm = frManager ?: fragmentManager

        try {
            if (fm.getBackStackEntryCount() > 1) {
                fm.executePendingTransactions()
                removeFragmentAtBackStackIndex(fm, fm.getBackStackEntryCount() - 1)
                removeFragmentAtBackStackIndex(fm, fm.getBackStackEntryCount() - 1)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    /**
     * Clears back stact for given fragment manager (or use mFragmentManager)
     * @param fragmentManager
     */
    @JvmOverloads fun clearBackstack(frManager: FragmentManager? = null) {
        // Sets specific fragmentManager (from parent fragment) or use globally defined fragment manager (topmost)
        val fm = frManager ?: fragmentManager

        try {
            fm.executePendingTransactions()
            for (i in 0..fm.getBackStackEntryCount() - 1) {
                fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    /**
     * @return Fragment to IFragment casted instance
     */
    val currentIFragment: Fragment
        get() = currentFragment as Fragment

    /**
     * Get current fragment
     * @return Fragment or null if fragment doesn't exists
     */
    val currentFragment: Fragment
        get() {
            val fragmentTag = currentFragmentTag
            return fragmentManager.findFragmentByTag(fragmentTag)
        }

    /**
     * Get current fragment tag
     * @return Fragment tag
     */
    val currentFragmentTag: String
        get() {
            val backStackCount = fragmentManager.backStackEntryCount

            var fragmentTag = ""

            if (backStackCount > 0) {
                fragmentTag = fragmentManager.getBackStackEntryAt(backStackCount - 1).name
            }

            return fragmentTag
        }

    companion object {
        private val MAIN_CONTAINER_ID = R.id.fragment_container

        private val LOG_TAG = "FragmentManager"
    }
}