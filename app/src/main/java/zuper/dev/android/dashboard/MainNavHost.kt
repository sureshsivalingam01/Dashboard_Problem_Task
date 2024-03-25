package zuper.dev.android.dashboard

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import zuper.dev.android.dashboard.dashboard.dashboard
import zuper.dev.android.dashboard.dashboard.dashboardRoute
import zuper.dev.android.dashboard.jobs.jobs


@Composable
fun MainNavHost(
    navHostController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navHostController,
        startDestination = dashboardRoute,
    ) {

        dashboard(navHostController)

        jobs(navHostController)

    }

}