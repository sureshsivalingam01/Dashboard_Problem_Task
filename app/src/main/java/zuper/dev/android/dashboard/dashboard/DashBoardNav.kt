package zuper.dev.android.dashboard.dashboard

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable


const val dashboardRoute = "dashboard_route"


/**
 * Dashboard composable route
 *
 * @param navHostController
 */
fun NavGraphBuilder.dashboard(
    navHostController: NavHostController
){


    composable(dashboardRoute){
        DashBoardScreenVM(navHostController)
    }

}