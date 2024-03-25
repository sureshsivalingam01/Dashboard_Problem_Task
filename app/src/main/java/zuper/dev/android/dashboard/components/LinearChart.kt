package zuper.dev.android.dashboard.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import java.util.Random


val previewList = listOf(
    ChartItem(10, Color.Red,"Job Old"),
    ChartItem(50, Color.Cyan,"Job New"),
    ChartItem(12, Color.Blue,"Job Finding"),
    ChartItem(31, Color.Green,"Job Calender"),
    ChartItem(9, Color.Yellow,"Job TV"),
).sortedByDescending { it.itemValue }/*.toMutableStateList()*/

@Preview
@Composable
private fun LinearChartPreview() {
    LinearChart(
        modifier = Modifier.fillMaxWidth(),
        values = previewList
    )
}


/**
 * Linear chart
 *
 * @param modifier
 * @param values -> chart item lists
 */
@Composable
fun LinearChart(
    modifier: Modifier = Modifier,
    values: List<ChartItem>,
) {

    val total = remember(values) {
        values.sumOf { it.itemValue }
    }



    Canvas(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .size(LinearChartTokens.ChartDefaultWidth, LinearChartTokens.ChartHeight)
    ) {

        val canvasWidth = size.width

        values.fastForEachIndexed { index, item ->

            val widthItemWithPrevious = values.subList(0, index).map {
                (it.itemValue.toFloat() / total * canvasWidth)
            }.sum()


            drawRect(
                color = item.color,
                topLeft = Offset(widthItemWithPrevious, 0f),//x for left right,
            )


        }


    }
}


@Stable
data class ChartItem(
    val itemValue: Int = 0,
    val color: Color = Color(Random().nextInt(), Random().nextInt(256), Random().nextInt(256)),
    val text: Any = Any(),
    val key:Int = 0,
)


internal object LinearChartTokens {
    val ChartHeight = 23.dp
    val ChartDefaultWidth = 240.dp
}