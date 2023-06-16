package healthcare.app.fastingtracker.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.daysOfWeek
import healthcare.app.fastingtracker.android.ui.Day
import healthcare.app.fastingtracker.android.ui.DaysOfWeekTitle
import healthcare.app.fastingtracker.android.ui.SegmentedControl
import healthcare.app.fastingtracker.android.ui.state.MainUiState
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import java.time.YearMonth
import kotlin.time.Duration.Companion.seconds

class MainActivity : ComponentActivity(), KoinComponent {
    companion object {
        private val TAB_HEIGHT = 72.dp
        private val INDICATOR_HEIGHT = 4.dp
    }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TabLayout()
                }
            }
        }

        initData()
    }

    private fun initData() {
        // TODO: Fetch Data from datasource to see if it is started or stopped
        viewModel.fetchData()
    }

    @Composable
    fun TabLayout() {
        var selectedTabIndex by remember { mutableStateOf(0) }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxHeight()) {
                when (selectedTabIndex) {
                    0 -> TimerScreen()
                    1 -> RecordScreen()
                    2 -> CalendarScreen()
                    3 -> SettingScreen()
                }
            }

            // Reuse the default offset animation modifier, but use our own indicator
            val indicator = @Composable { tabPositions: List<TabPosition> ->
                TopIndicator(Color.White, Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]))
            }

            TabRow(
                modifier = Modifier
                    .height(TAB_HEIGHT)
                    .align(Alignment.BottomCenter),
                selectedTabIndex = selectedTabIndex,
                backgroundColor = MaterialTheme.colors.surface,
                indicator = indicator
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = {
                        selectedTabIndex = 0
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.trackerTabTitle),
                            fontWeight = FontWeight.Bold,
                            color = if (selectedTabIndex == 0) MaterialTheme.colors.primary else Color.Gray
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = null
                        )
                    }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = {
                        selectedTabIndex = 1
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.recordTabTitle),
                            fontWeight = FontWeight.Bold,
                            color = if (selectedTabIndex == 1) MaterialTheme.colors.primary else Color.Gray
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null
                        )
                    }
                )
                Tab(
                    selected = selectedTabIndex == 2,
                    onClick = {
                        selectedTabIndex = 2
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.calendarTabTitle),
                            fontWeight = FontWeight.Bold,
                            color = if (selectedTabIndex == 2) MaterialTheme.colors.primary else Color.Gray
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = null
                        )
                    }
                )
                Tab(
                    selected = selectedTabIndex == 3,
                    onClick = {
                        selectedTabIndex = 3
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.settingTabTitle),
                            fontWeight = FontWeight.Bold,
                            color = if (selectedTabIndex == 3) MaterialTheme.colors.primary else Color.Gray
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }

    @Composable
    fun TopIndicator(color: Color, modifier: Modifier = Modifier) {
        Box(
            modifier
                .fillMaxWidth()
                .offset(y = INDICATOR_HEIGHT - TAB_HEIGHT)  //SmallTabHeight=48.dp - height indicator=2.dp
                .height(INDICATOR_HEIGHT)
                .background(color = color)
        )
    }

    @Composable
    fun TimerTaskComposable(state: MainUiState) {
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
    fun TimerScreen() {
        val options = listOf(
            getString(R.string.timeOption1),
            getString(R.string.timeOption2),
        )

        val state = viewModel.uiState.collectAsState()
        val record = viewModel.record.collectAsState()

        TimerTaskComposable(state = state.value)

        if (state.value == MainUiState.Loading) {
            CircularProgressIndicator()
        }

        Column(modifier = Modifier
            .fillMaxWidth()
        ) {
            SegmentedControl(
                items = options,
                useFixedWidth = true,
                cornerRadius = 20,
                itemWidth = 200.dp,
                onItemSelection = {}
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(Alignment.Start),
                text = getString(R.string.trackerTitleElapsedTime),
                color = MaterialTheme.colors.primary,
                fontSize = 30.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
            )

            Text(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.End),
                text = record.value?.elapsedTime ?: getString(R.string.trackerPlaceholderTime1),
                color = MaterialTheme.colors.primary,
                fontSize = 28.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Medium,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(Alignment.Start),
                text = getString(R.string.trackerTitleRemainingTime),
                color = MaterialTheme.colors.primary,
                fontSize = 30.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
            )

            Text(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.End),
                text = record.value?.remainingTime ?: getString(R.string.trackerPlaceholderTime1),
                color = MaterialTheme.colors.primary,
                fontSize = 28.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Medium,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(Alignment.Start),
                text = getString(R.string.trackerTitleStartTime),
                color = MaterialTheme.colors.primary,
                fontSize = 24.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Normal,
            )

            Text(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.End),
                text = record.value?.startTime ?: getString(R.string.trackerPlaceholderTime2),
                color = MaterialTheme.colors.primary,
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Light,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(Alignment.Start),
                text = getString(R.string.trackerTitleEndTime),
                color = MaterialTheme.colors.primary,
                fontSize = 24.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Normal,
            )

            Text(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.End),
                text = record.value?.endTime ?: getString(R.string.trackerPlaceholderTime2),
                color = MaterialTheme.colors.primary,
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Light,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
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
                    .padding(0.dp)
                    .height(52.dp)
                    .fillMaxWidth(0.5f)
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

    private fun getButtonText(state: MainUiState) = when (state) {
        is MainUiState.Start -> getString(R.string.trackerStart)
        is MainUiState.Fasting -> getString(R.string.trackerEnd)
        else -> getString(R.string.defaultEmptyMessage)
    }

    /**
     * Record Screen Session
     */
    @Composable
    fun RecordScreen() {

    }

    @Composable
    fun CalendarScreen() {
        val currentMonth = remember { YearMonth.now() }
        val startMonth = remember { currentMonth.minusMonths(100) }
        val endMonth = remember { currentMonth.plusMonths(100) }
        val daysOfWeek = remember { daysOfWeek() }

        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first(),
            outDateStyle = OutDateStyle.EndOfGrid
        )

        Column(
            modifier = Modifier.fillMaxSize()
                .background(color = Color.White)
                .padding(top = 10.dp)
        ) {
            HorizontalCalendar(
                state = state,
                dayContent = { day ->
                    Day(day)
                             },
                monthHeader = {
                    DaysOfWeekTitle(
                        daysOfWeek = daysOfWeek,
                        recentDisplay = state.firstVisibleMonth
                    )
                }
            )
        }
    }

    @Composable
    fun SettingScreen() {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.my_lottie_file))
        LottieAnimation(
            modifier = Modifier.fillMaxSize(),
            composition = composition,
            iterations = LottieConstants.IterateForever,
        )
    }
}