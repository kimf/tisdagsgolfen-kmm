package se.fransman.tisdagsgolfen

import se.fransman.tisdagsgolfen.AppViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
  viewModel { AppViewModel(get()) }
}