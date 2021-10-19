package se.fransman.tisdagsgolfen

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class App : Application() {
  override fun onCreate() {
    super.onCreate()

    initKoin {
      androidLogger()
      androidContext(this@App)
      modules(appModule)
    }
  }

}