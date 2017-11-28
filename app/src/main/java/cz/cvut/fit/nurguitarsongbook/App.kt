package cz.cvut.fit.nurguitarsongbook

import android.app.Application
import android.content.res.Resources


class App : Application() {

    var fragmentManager: FragmentManager = FragmentManager()
        private set

    var activity: MainActivity? = null

    override fun onCreate() {
        super.onCreate()
        _instance = this
    }

    override fun onTerminate() {
        super.onTerminate()
        _instance = null
    }

    companion object {
        private var _instance: App? = null
        val instance: App
            get() = _instance ?: throw RuntimeException("App instance terminated or not yet created")

        fun convertDpToPixel(dp: Float): Float {
            val metrics = Resources.getSystem().getDisplayMetrics()
            val px = dp * (metrics.densityDpi / 160f)
            return Math.round(px).toFloat()
        }
    }
}
