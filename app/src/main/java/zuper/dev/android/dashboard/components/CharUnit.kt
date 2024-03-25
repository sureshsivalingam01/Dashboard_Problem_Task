package zuper.dev.android.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zuper.dev.android.dashboard.ui.theme.regular14


@Preview
@Composable
private fun ChartUnitPreview() {
    Surface {
        ChartUnit(
            modifier = Modifier
                .fillMaxWidth(),
            leftText = "50 Jobs",
            rightText = "25 of 50 completed",
            chartList = previewList
        )
    }
}



@Composable
fun ChartUnit(
    modifier: Modifier = Modifier,
    leftText: String,
    rightText: String,
    chartList: List<ChartItem>,
    verticalSpace:Dp = 12.dp,
    leftTextStyle: TextStyle = MaterialTheme.typography.regular14,
    rightTextStyle: TextStyle = MaterialTheme.typography.regular14,
) {
    Column (
        modifier= modifier,
        verticalArrangement = Arrangement.spacedBy(verticalSpace)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = leftText,
                style = leftTextStyle
            )
            Text(
                text = rightText,
                style = rightTextStyle
            )
        }
        LinearChart(
            modifier = Modifier
                .fillMaxWidth(),
            values = chartList
        )
    }
}