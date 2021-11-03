package se.fransman.tgapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import se.fransman.tgapp.composables.MainComposable

class AppActivity: ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val model = (application as App).model

    setContent {
      MaterialTheme() {
        MainComposable(model)
      }
    }
  }
}