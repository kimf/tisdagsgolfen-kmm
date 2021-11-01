package se.fransman.tgapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.getViewModel

class AppActivity: ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      MaterialTheme() {
        MainLayout()
      }
    }
  }
}

@Composable
fun MainLayout() {
  val viewModel = getViewModel<AppViewModel>()
  MainScreen(viewModel = viewModel)
}