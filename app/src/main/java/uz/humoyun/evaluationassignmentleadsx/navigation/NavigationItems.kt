package uz.humoyun.evaluationassignmentleadsx.navigation

import uz.humoyun.evaluationassignmentleadsx.R


sealed class NavigationItems(val icon: Int, val title: String, val screen: Screen) {

    object Home : NavigationItems(R.drawable.ic_home, "Home", Screen.HomeScreen)
    object Calls : NavigationItems(R.drawable.ic_phone, "Calls", Screen.CallsScreen)
    object Chat : NavigationItems(R.drawable.ic_messages_square, "Chat", Screen.ChatScreen)
    object Leads : NavigationItems(R.drawable.ic_users, "Leads", Screen.AllLeadsScreen)
    object More : NavigationItems(R.drawable.ic_more_horizontal, "More", Screen.MoreScreen)

}
