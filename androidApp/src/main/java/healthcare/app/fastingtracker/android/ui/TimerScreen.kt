package healthcare.app.fastingtracker.android.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import healthcare.app.fastingtracker.android.MainViewModel
import healthcare.app.fastingtracker.android.R
import healthcare.app.fastingtracker.android.ui.state.MainUiState
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun TimerScreen(viewModel: MainViewModel) {
    val options = listOf(
        stringResource(R.string.timeOption1),
        stringResource(R.string.timeOption2),
    )

    val state = viewModel.uiState.collectAsState()
    val record = viewModel.record.collectAsState()

    TimerTaskComposable(
        state = state.value,
        viewModel = viewModel
    )

    if (state.value == MainUiState.Loading) {
        CircularProgressIndicator()
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(
            state = rememberScrollState()
        )
    ) {
        // タイトル表示
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(
                    top = 10.dp,
                    bottom = 10.dp
                ),
            text = stringResource(id = R.string.app_name),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 28.sp,
            textAlign = TextAlign.Center
        )

        // 12:12 と 16:8 の選択
        SegmentedControl(
            items = options,
            useFixedWidth = true,
            cornerRadius = 20,
            itemWidth = 200.dp,
            onItemSelection = {}
        )

        // 経過時間
        InformationDisplay(
            title = stringResource(R.string.trackerTitleElapsedTime),
            text = record.value?.elapsedTime ?: stringResource(R.string.trackerPlaceholderTime1)
        )

        // 残り時間
        InformationDisplay(
            title = stringResource(R.string.trackerTitleRemainingTime),
            text = record.value?.remainingTime ?: stringResource(R.string.trackerPlaceholderTime1)
        )

        // 開始時刻
        InformationDisplay(
            title = stringResource(R.string.trackerTitleStartTime),
            text = record.value?.startTime ?: stringResource(R.string.trackerPlaceholderTime2)
        )

        // 終了時刻
        InformationDisplay(
            title = stringResource(R.string.trackerTitleEndTime),
            text = record.value?.endTime ?: stringResource(R.string.trackerPlaceholderTime2)
        )

        OutlinedButton(
            onClick = {
                when (state.value) {
                    MainUiState.Fasting -> viewModel.stopFasting()
                    else -> viewModel.startFasting()
                }
            },
            border = BorderStroke(
                1.dp,
                colorResource(id = R.color.colorButtonBackGroundDisable)
            ),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = colorResource(id = R.color.colorButtonBackGroundDisable)),
            contentPadding = PaddingValues(0.dp, 0.dp),
            modifier = Modifier
                .padding(
                    vertical = 10.dp
                )
                .fillMaxWidth(0.6f)
                .height(52.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Text(
                text = getButtonText(state.value),
                color = colorResource(id = R.color.colorButtonBackGroundEnable),
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun TimerTaskComposable(state: MainUiState, viewModel: MainViewModel) {
    if (state is MainUiState.Fasting) {
        LaunchedEffect(Unit) {
            while (true) {
                delay(1.seconds)
                viewModel.updateFasting()
            }
        }
    }
}

@Composable
fun InformationDisplay(title: String, text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = 6.dp
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 10.dp,
                    bottom = 10.dp
                )
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                text = title,
                color = Color.DarkGray,
                fontSize = 24.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                text = text,
                color = Color.Black,
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun getButtonText(state: MainUiState) = when (state) {
    is MainUiState.Start -> stringResource(R.string.trackerStart)
    is MainUiState.Fasting -> stringResource(R.string.trackerEnd)
    else -> stringResource(R.string.defaultEmptyMessage)
}

