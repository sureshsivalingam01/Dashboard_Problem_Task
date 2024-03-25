package zuper.dev.android.dashboard.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.components.JobStatsCard
import zuper.dev.android.dashboard.data.model.InvoiceApiModel
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.jobs.navigateToJobsScreen
import zuper.dev.android.dashboard.ui.theme.bold20


@Preview
@Composable
private fun DashBoardScreenPreview() {
    DashBoardScreen(
        jobs = emptyList(),
        invoices = emptyList(),
        name = "Android",
        onJobsClick = {}
    )
}


@Composable
fun DashBoardScreenVM(
    navHostController: NavHostController,
    viewModel: DashBoardViewModel = hiltViewModel()
) {


    val jobs by viewModel.jobs.collectAsStateWithLifecycle()


    val invoices by viewModel.invoices.collectAsStateWithLifecycle()


    val name = remember {
        "Suresh"
    }


    DashBoardScreen(
        jobs = jobs,
        invoices = invoices,
        name = name,
        onJobsClick = {
            navHostController.navigateToJobsScreen()
        }
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardScreen(
    jobs: List<JobApiModel>,
    invoices: List<InvoiceApiModel>,
    name: String,
    onJobsClick: () -> Unit,
) {


    val scrollState = rememberScrollState()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.dashboard),
                        style = MaterialTheme.typography.bold20
                    )
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            ProfileGreetings(
                modifier = Modifier
                    .fillMaxWidth(),
                name = name,
            )

            JobStatsCard(
                jobs = jobs,
                onJobsClick = onJobsClick
            )

            InvoiceStatsCard(
                invoices = invoices
            )

        }
    }


}