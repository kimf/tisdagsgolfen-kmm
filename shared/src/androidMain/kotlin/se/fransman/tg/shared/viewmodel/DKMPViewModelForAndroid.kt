package se.fransman.tg.shared.viewmodel

import android.content.Context
import se.fransman.tg.shared.datalayer.Repository


fun DKMPViewModel.Factory.getAndroidInstance(context : Context) : DKMPViewModel {
  val repository = Repository()
  return DKMPViewModel(repository)
}