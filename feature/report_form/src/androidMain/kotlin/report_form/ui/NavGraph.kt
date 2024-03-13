package report_form.ui


import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import report_form.domain.ReportFormController
import report_form.ui.navigation.Destination
import report_form.ui.navigation.ReportForm

/**
 * These are top level destination that is why we do not need separate nav host
 * This graph will be used with the drawer or the nav rails
 *
 */

/**
 * Defining as interface instead of `expect` so that the it can have additional parameter in the implementation side,
 * such as in case of android it may need the GraphBuilder instance and the NavController
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
                    ReportForm(
                        formStateManager = controller
                    )

                }
            }
        }
    }

}
