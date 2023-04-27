package healthcare.app.fastingtracker.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import healthcare.app.fastingtracker.Greeting
import healthcare.app.fastingtracker.android.ui.SelectionType
import healthcare.app.fastingtracker.android.ui.ToggleButton
import healthcare.app.fastingtracker.android.ui.ToggleButtonOption
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : ComponentActivity(), KoinComponent {
    // TODO: Move to presenter
    private val greeting: Greeting by inject()

    companion object {
        private val TAB_HEIGHT = 72.dp
        private val INDICATOR_HEIGHT = 4.dp
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    GreetingView(greeting.greet())
                }
            }
        }
    }

    @Composable
    fun GreetingView(text: String) {
        TabLayout(text = text)
    }

    @Composable
    fun TabLayout(text: String) {
        var selectedTabIndex by remember { mutableStateOf(0) }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxHeight()) {
                when (selectedTabIndex) {
                    0 -> TimerScreen(text = text)
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
    fun TimerScreen(text: String) {
        val options = arrayOf(
            ToggleButtonOption(
                getString(R.string.timeOption1),
            ),
            ToggleButtonOption(
                getString(R.string.timeOption2),
            ),
        )

        ToggleButton(
            options = options,
            type = SelectionType.SINGLE,
            modifier = Modifier
                .padding(40.dp)
                .fillMaxWidth(),
            onClick = {

            }
        )

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
                text = getString(R.string.trackerPlaceholderTime1),
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
                text = getString(R.string.trackerPlaceholderTime1),
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
                text = getString(R.string.trackerPlaceholderTime2),
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
                text = getString(R.string.trackerPlaceholderTime2),
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
                onClick = { },
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
                    text = getString(R.string.trackerStart),
                    color = colorResource(id = R.color.colorButtonBackGroundEnable),
                    fontSize = 20.sp
                )
            }
        }
    }

    @Composable
    fun RecordScreen() {

    }

    @Composable
    fun CalendarScreen() {

    }

    @Composable
    fun SettingScreen() {

    }

    @Preview
    @Composable
    fun DefaultPreview() {
        MyApplicationTheme {
            GreetingView("Hello, Android!")
        }
    }
}