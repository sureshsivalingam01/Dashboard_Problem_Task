package zuper.dev.android.dashboard.jobs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.components.ChartItem
import zuper.dev.android.dashboard.components.ChartUnit
import zuper.dev.android.dashboard.dashboard.DashBoardViewModel
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.data.model.JobStatus
import zuper.dev.android.dashboard.ui.theme.bold20
import zuper.dev.android.dashboard.ui.theme.medium14
import zuper.dev.android.dashboard.ui.theme.regular14


@Preview
@Composable
private fun JobsScreenPreview() {
    JobsScreen(
        jobs = emptyList(),
        onBackClick = {}
    )
}


@Composable
fun JobsScreenVM(
    navHostController: NavHostController,
    viewModel: DashBoardViewModel
) {


    val jobs by viewModel.jobs.collectAsStateWithLifecycle()


    JobsScreen(
        jobs = jobs,
        onBackClick = {
            navHostController.navigateUp()
        }
    )

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun JobsScreen(
    jobs: List<JobApiModel>,
    onBackClick: () -> Unit
) {


    val scope = rememberCoroutineScope()


    val jobTotal = remember(jobs) {
        jobs.count()
    }


    val filteredJobs = remember(jobs) {
        jobs.groupBy { it.status }.toList().sortedBy { it.first.ordinal }
    }


    val completedJobsCount = remember(filteredJobs) {
        filteredJobs.find { it.first == JobStatus.Completed }?.second?.count() ?: 0
    }


    val pagerState = rememberPagerState(
        pageCount = {
            filteredJobs.count()
        }
    )


    val chartList = remember(filteredJobs) {
        filteredJobs.map {
            ChartItem(
                itemValue = it.second.count(),
                text = it.first.value,
                color = it.first.color,
                key = it.first.ordinal
            )
        }.sortedByDescending { it.itemValue }
    }

    val verticalSpace = remember {
        20.dp
    }



    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.jobs_with_count_with_bracket, jobTotal),
                        style = MaterialTheme.typography.bold20
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = verticalSpace),
                verticalArrangement = Arrangement.spacedBy(verticalSpace)
            ) {
                ChartUnit(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
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
                    verticalSpace = verticalSpace
                )
                ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    edgePadding = 0.dp,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ) {
                    filteredJobs.forEachIndexed { index, chartItem ->
                        Tab(
                            selected = index == pagerState.currentPage,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text(
                                    text = stringResource(
                                        id = R.string.job_status_with_count,
                                        chartItem.first.value,
                                        chartItem.second.count()
                                    ),
                                    style = if (index == pagerState.currentPage) MaterialTheme.typography.medium14 else MaterialTheme.typography.regular14
                                )
                            },
                        )
                    }
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f),
            ) { page ->


                val modelList = remember(key1 = page, key2 = filteredJobs) {
                    filteredJobs[page].second
                }


                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(top =16.dp, bottom = 24.dp, start = 16.dp, end = 16.dp)
                ) {

                    items(modelList) { item ->
                        ListJobItem(
                            modifier = Modifier
                                .fillMaxWidth(),
                            item = item
                        )
                    }
                }
            }
        }
    }
}