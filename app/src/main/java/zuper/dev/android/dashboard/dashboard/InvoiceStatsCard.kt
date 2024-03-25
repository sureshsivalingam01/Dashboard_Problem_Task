package zuper.dev.android.dashboard.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.components.ChartItem
import zuper.dev.android.dashboard.components.ChartUnit
import zuper.dev.android.dashboard.data.model.InvoiceApiModel
import zuper.dev.android.dashboard.data.model.InvoiceStatus
import zuper.dev.android.dashboard.ui.theme.medium14
import zuper.dev.android.dashboard.ui.theme.medium18
import zuper.dev.android.dashboard.ui.theme.regular14


@Preview
@Composable
private fun InvoiceStatsPreview() {
    InvoiceStatsCard(
        modifier = Modifier
            .fillMaxWidth(),
        invoices = emptyList()
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InvoiceStatsCard(
    modifier: Modifier = Modifier,
    invoices: List<InvoiceApiModel>,
) {

    val context = LocalContext.current


    val totalInvoiceAmount = remember(invoices) {
        invoices.sumOf { it.total }
    }


    val filteredInvoices = remember(invoices) {
        invoices.groupBy { it.status }
    }


    val sumOfPaid = remember(filteredInvoices) {
        (filteredInvoices[InvoiceStatus.Paid]?.sumOf { it.total } ?: 0)
    }


    val chartList = remember(filteredInvoices, context) {
        filteredInvoices.map { entry ->
            ChartItem(
                itemValue = entry.value.count(),
                text = context.resources.getString(
                    R.string.invoice_status_with_amount,
                    entry.key.value,
                    entry.value.sumOf { it.total }
                ),
                color = entry.key.color,
                key = entry.key.ordinal
            )
        }.sortedByDescending { it.itemValue }
    }


    val flowList = remember(chartList) {
        chartList.sortedBy { it.key }
    }


    OutlinedCard(
        modifier = modifier
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Text(
                text = stringResource(R.string.invoice_stats),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                style = MaterialTheme.typography.medium18
            )

            HorizontalDivider()

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ChartUnit(
                    modifier = Modifier
                        .fillMaxWidth(),
                    leftText = stringResource(
                        id = R.string.total_invoice_amount,
                        totalInvoiceAmount
                    ),
                    rightText = stringResource(
                        id = R.string.total_invoice_collected_amount,
                        sumOfPaid
                    ),
                    chartList = chartList,
                    rightTextStyle = MaterialTheme.typography.medium14
                )

                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp,Alignment.CenterHorizontally),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    flowList.forEach {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Spacer(
                                Modifier
                                    .size(12.dp)
                                    .clip(RoundedCornerShape(2.5.dp))
                                    .background(it.color)
                            )
                            Text(
                                text = "${it.text}",
                                style = MaterialTheme.typography.regular14
                            )
                        }
                    }
                }
            }
        }
    }

}