package uz.humoyun.evaluationassignmentleadsx.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationState(
    val navHostController: NavHostController
) {
    fun navigateTo(route: String, isPopUpTo: Boolean = false) {
        navHostController.navigate(route) {
            if (isPopUpTo) popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateItemScreen(id: Int) {
        navHostController.navigate(Screen.LeadItemScreen.getRouteWithArgs(id))
    }

    fun navigateEditItemScreen(id: Int) {
        navHostController.navigate(Screen.EditLeadScreen.getRouteWithArgs(id))
    }

}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}