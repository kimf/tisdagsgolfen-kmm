package se.fransman.tgapp

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import se.fransman.tg.shared.viewmodel.DKMPViewModel
import se.fransman.tg.shared.viewmodel.getAndroidInstance
import androidx.lifecycle.ProcessLifecycleOwner

class App : Application() {

  lateinit var model: DKMPViewModel

  override fun onCreate() {
    super.onCreate()
    model = DKMPViewModel.Factory.getAndroidInstance(this)

    val appLifecycleObserver = AppLifecycleObserver(model)
    ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)
  }

}

class AppLifecycleObserver (private val model: DKMPViewModel) : LifecycleObserver {

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  fun onEnterForeground() {
    if (model.stateFlow.value.recompositionIndex > 0) { // not calling at app startup
      model.navigation.onReEnterForeground()
    }
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  fun onEnterBackground() {
    model.navigation.onEnterBackground()
  }

}