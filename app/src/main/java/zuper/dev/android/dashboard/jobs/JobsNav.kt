package zuper.dev.android.dashboard.jobs

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import zuper.dev.android.dashboard.dashboard.dashboardRoute


private const val jobsRoute = "jobs_route"


/**
 * Navigate to jobs screen
 *
 */
fun NavController.navigateToJobsScreen(){
    navigate(jobsRoute)
}


/**
 * Jobs composable route
 *
 * @param navHostController
 */
fun NavGraphBuilder.jobs(
    navHostController: NavHostController
) {


    composable(jobsRoute) {


        val parentEntry = remember(it) {
            navHostController.getBackStackEntry(dashboardRoute)
        }

        JobsScreenVM(
            navHostController = navHostController,
            viewModel = hiltViewModel(parentEntry)
        )
    }

}