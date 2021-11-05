package se.fransman.tgapp

import android.app.Application
import se.fransman.tg.app.LeaderboardStore
import se.fransman.tg.core.Tisdagsgolfen
import se.fransman.tg.core.create
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    initKoin()
    launchBackgroundSync()
  }

  private val appModule = module {
    single { Tisdagsgolfen.create(get(), BuildConfig.DEBUG) }
    single { LeaderboardStore(get()) }
  }

  private fun initKoin() {
    startKoin {
      if (BuildConfig.DEBUG) androidLogger(Level.ERROR)

      androidContext(this@App)
      modules(appModule)
    }
  }

  private fun launchBackgroundSync() {
    RefreshWorker.enqueue(this)
  }
}