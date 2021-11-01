package se.fransman.tgapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import se.fransman.tg.initKoin

// import android.material.color.DynamicColors;

class App : Application() {
  override fun onCreate() {
    super.onCreate()

    // DynamicColors.applyToActivitiesIfAvailable(this);

    initKoin() {
      androidLogger()
      androidContext(this@App)
      modules(appModule)
    }
  }

}