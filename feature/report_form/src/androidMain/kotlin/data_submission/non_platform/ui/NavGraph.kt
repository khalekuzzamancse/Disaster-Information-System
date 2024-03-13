package data_submission.non_platform.ui


import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import data_submission.domain.ReportFormController
import data_submission.ui.navigation.Destination
import data_submission.ui.navigation.ReportFormCommon

/**
 * These are top level destination that is why we do not need separate nav host
 * This graph will be used with the drawer or the nav rails
 *
 */

object ReportFormNavGraph {
    private const val ROUTE = "ReportFormRoute"

    fun navigateToReportForm(navController: NavHostController) {
        navigateAsTopMostDestination(Destination.ReportForm, navController)
    }

    private fun navigateAsTopMostDestination(
        destination: Destination,
        navController: NavHostController
    ) {
        navController.navigate(destination.toString()) {
            try {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            } catch (_: Exception) {

            }
        }
    }

    fun graph(
        navGraphBuilder: NavGraphBuilder,
        controller: ReportFormController,
    ) {
        with(navGraphBuilder) {
            navigation(
                route = ROUTE,
                startDestination = Destination.ReportForm.toString()
            ) {

                composable(route = Destination.ReportForm.toString(),
                    enterTransition = { slideInHorizontally() + fadeIn() },
                    exitTransition = { slideOutHorizontally() + fadeOut() }) {
                    ReportFormCommon(
                        formStateManager = controller
                    )

                }
            }
        }
    }

}
