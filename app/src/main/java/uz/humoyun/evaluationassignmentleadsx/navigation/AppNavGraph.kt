package uz.humoyun.evaluationassignmentleadsx.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Screen = Screen.AllLeadsScreen,
    allLeadsScreenContent: @Composable () -> Unit,
    leadsItemScreenContent: @Composable (Int) -> Unit,
    createLeadScreenContent: @Composable () -> Unit,
    editLeadScreenContent: @Composable (Int) -> Unit,
    homeContent: @Composable () -> Unit,
    callsContent: @Composable () -> Unit,
    chatContent: @Composable () -> Unit,
    moreContent: @Composable () -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination.route
    ) {

        composable(Screen.AllLeadsScreen.route) {
            allLeadsScreenContent()
        }

        composable(Screen.LeadItemScreen.route, arguments = listOf(navArgument("itemId") {
            type = NavType.IntType
        })) {
            val id = it.arguments?.getInt("itemId")
            id?.let { itemId ->
                leadsItemScreenContent(itemId)
            }
        }

        composable(Screen.CreateLeadScreen.route) {
            createLeadScreenContent()
        }

        composable(Screen.EditLeadScreen.route, arguments = listOf(navArgument("itemId") {
            type = NavType.IntType
        })) {
            val id = it.arguments?.getInt("itemId")
            id?.let { itemId ->
                editLeadScreenContent(itemId)
            }
        }

        composable(Screen.HomeScreen.route){
            homeContent()
        }

        composable(Screen.ChatScreen.route){
            chatContent()
        }

        composable(Screen.CallsScreen.route){
            callsContent()
        }

        composable(Screen.MoreScreen.route){
            moreContent()
        }

    }
}
