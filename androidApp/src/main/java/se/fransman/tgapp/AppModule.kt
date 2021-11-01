package se.fransman.tgapp

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
  viewModel { AppViewModel(get()) }
}