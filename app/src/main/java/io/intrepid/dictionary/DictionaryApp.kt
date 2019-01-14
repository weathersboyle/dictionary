package io.intrepid.dictionary

import android.app.Application
import android.util.Log
import timber.log.Timber

private const val DEFAULT_TAG = "Timber"

class DictionaryApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber(Log::println)
    }

    private fun initTimber(logger: (Int, String, String) -> Int) {
        val tree = if (BuildConfig.DEBUG) {
            Timber.DebugTree()
        } else {
            object : Timber.Tree() {
                override fun isLoggable(tag: String?, priority: Int): Boolean = (priority >= Log.INFO)

                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    logger(priority, tag ?: DEFAULT_TAG, message)
                    if (t != null) {
                        logger(priority, tag ?: DEFAULT_TAG, Log.getStackTraceString(t))
                    }
                }
            }
        }
        Timber.plant(tree)
    }
}
