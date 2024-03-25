package zuper.dev.android.dashboard.components

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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.data.model.JobStatus
import zuper.dev.android.dashboard.ui.theme.medium18
import zuper.dev.android.dashboard.ui.theme.regular14


@Preview
@Composable
private fun JobStatsCardPreview() {
    JobStatsCard(
        modifier = Modifier
            .fillMaxWidth(),
        jobs = emptyList(),
        onJobsClick = {}
    )
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobStatsCard(
    modifier: Modifier = Modifier,
    jobs: List<JobApiModel>,
    onJobsClick: () -> Unit
) {


    val jobTotal = remember(jobs) {
        jobs.count()
    }


    val filteredJobs = remember(jobs) {
        jobs.groupBy { it.status }
    }


    val completedJobsCount = remember(filteredJobs) {
        (filteredJobs[JobStatus.Completed]?.count() ?: 0)
    }


    val chartList = remember(filteredJobs) {
        filteredJobs.map {
            ChartItem(
                itemValue = it.value.count(),
                text = it.key.value,
                color = it.key.color,
                key = it.key.ordinal
            )
        }.sortedByDescending { it.itemValue }
    }


    val flowList = remember(chartList) {
        chartList.sortedBy { it.key }
    }





    OutlinedCard(
        onClick = onJobsClick,
        modifier = modifier
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Text(
                text = stringResource(R.string.job_stats),
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
                    leftText = pluralStringResource(
                        id = R.plurals.jobs_with_count,
                        count = jobTotal,
                        jobTotal
                    ),
                    rightText = stringResource(
                        id = R.string.jobs_with_completed,
                        completedJobsCount,
                        jobTotal
                    ),
                    chartList = chartList,
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
                                text = stringResource(
                                    id = R.string.job_status_with_count,
                                    it.text,
                                    it.itemValue
                                ),
                                style = MaterialTheme.typography.regular14
                            )
                        }
                    }
                }
            }
        }
    }
}