package healthcare.app.fastingtracker.android.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import healthcare.app.fastingtracker.android.R

@Composable
fun SelectionPill(
    option: ToggleButtonOption,
    selected: Boolean,
    onClick: (option: ToggleButtonOption) -> Unit = {}
) {
    Button(
        onClick = { onClick(option) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background,
        ),
        shape = RoundedCornerShape(0),
        elevation = ButtonDefaults.elevation(0.dp, 0.dp),
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.padding(14.dp, 0.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(0.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = option.text,
                color = if (selected) {
                    colorResource(id = R.color.colorButtonBackGroundEnable)
                } else {
                    colorResource(id = R.color.colorButtonBackGroundDisable)
                },
                modifier = Modifier
                    .padding(0.dp)
                    .align(Alignment.CenterVertically),
            )
            option.iconVector?.let { icon ->
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (selected) {
                        colorResource(id = R.color.colorButtonBackGroundEnable)
                    } else {
                        colorResource(id = R.color.colorButtonBackGroundDisable)
                    },
                    modifier = Modifier.padding(4.dp, 2.dp, 2.dp, 2.dp),
                )
            }
        }
    }
}

enum class SelectionType {
    NONE,
    SINGLE,
    MULTIPLE,
}

data class ToggleButtonOption(
    val text: String,
    val iconVector: ImageVector? = null,
)

@Composable
fun ToggleButton(
    options: Array<ToggleButtonOption>,
    modifier: Modifier = Modifier,
    type: SelectionType = SelectionType.SINGLE,
    onClick: (selectedOptions: Array<ToggleButtonOption>) -> Unit = {},
) {
    val state = remember { mutableStateMapOf<String, ToggleButtonOption>() }

    OutlinedButton(
        onClick = { },
        border = BorderStroke(1.dp, colorResource(id = R.color.colorButtonBackGroundDisable)),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = colorResource(id = R.color.colorButtonBackGroundDisable)),
        contentPadding = PaddingValues(0.dp, 0.dp),
        modifier = modifier
            .padding(0.dp)
            .height(52.dp),
    ) {
        if (options.isEmpty()) {
            return@OutlinedButton
        }
        val onItemClick: (option: ToggleButtonOption) -> Unit = { option ->
            if (type == SelectionType.SINGLE) {
                options.forEach {
                    val key = it.text
                    if (key == option.text) {
                        state[key] = option
                    } else {
                        state.remove(key)
                    }
                }
            } else {
                val key = option.text
                if (!state.contains(key)) {
                    state[key] = option
                } else {
                    state.remove(key)
                }
            }
            onClick(state.values.toTypedArray())
        }
        if (options.size == 1) {
            val option = options.first()
            SelectionPill(
                option = option,
                selected = state.contains(option.text),
                onClick = onItemClick,
            )
            return@OutlinedButton
        }
        val first = options.first()
        val last = options.last()
        val middle = options.slice(1..options.size - 2)
        SelectionPill(
            option = first,
            selected = state.contains(first.text),
            onClick = onItemClick,
        )
        VerticalDivider()
        middle.map { option ->
            SelectionPill(
                option = option,
                selected = state.contains(option.text),
                onClick = onItemClick,
            )
            VerticalDivider()
        }
        SelectionPill(
            option = last,
            selected = state.contains(last.text),
            onClick = onItemClick,
        )
    }
}

@Composable
fun VerticalDivider() {
    Divider(
        modifier = Modifier
            .fillMaxHeight()
            .width(2.dp),
        color = colorResource(id = R.color.colorButtonBackGroundDisable)
    )
}