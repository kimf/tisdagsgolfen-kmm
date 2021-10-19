package se.fransman.tisdagsgolfen

import android.graphics.Color.*
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsHeight
import androidx.compose.foundation.background
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlin.math.log

@Composable
fun SideItem(
  side: CubeQuery.Side
) {
  Row(
    Modifier.padding(16.dp)
  ) {
    Text(text = side.id?.uppercase() ?: "-")
    Spacer(modifier = Modifier.size(16.dp))
    Row {
      Text(
        style = MaterialTheme.typography.body1,
        text = side.red.toString()
      )
      Spacer(modifier = Modifier.size(8.dp))
      Text(
        style = MaterialTheme.typography.body1,
        text = side.green.toString()
      )
      Spacer(modifier = Modifier.size(8.dp))
      Text(
        style = MaterialTheme.typography.body1,
        text = side.blue.toString()
      )
      Spacer(modifier = Modifier.size(16.dp))
      Box(
        modifier = Modifier
          .size(40.dp)
          .fillMaxSize()
          .background(
            Color(rgb((side.red ?: 0), (side.green ?: 0), (side.blue ?: 0)))
          )
      )
    }
  }
}

@Composable
fun SideItemList(
  sides: List<CubeQuery.Side>
) {
  LazyColumn {
    itemsIndexed(sides) { i, side ->
      if (i == 0) Spacer(modifier = Modifier.statusBarsHeight())
      SideItem(side)
    }
  }
}

sealed class UiState {
  object Loading : UiState()
  object Error : UiState()
  class Success(val sides: List<CubeQuery.Side>) : UiState()
}

@Composable
fun MainScreen(viewModel: AppViewModel, cubeId: String) {
  val (cube, setCube) = remember { mutableStateOf<CubeQuery.Cube?>(null) }

  LaunchedEffect(cubeId) {
    setCube(viewModel.getCube())
  }

  cube?.let {
    val sides = cube.sides?.filterIsInstance<CubeQuery.Side>()
    sides?.let {
      SideItemList(sides = sides)
    }
  }
}

@Preview
@Composable
fun MainScreenPreview() {
  AppTheme {
    SideItemList(sides = listOf(PreviewData.side, PreviewData.side))
  }
}