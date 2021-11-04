package com.medhdj.lejournal

import android.app.Application
import timber.log.Timber

class LeJournalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
