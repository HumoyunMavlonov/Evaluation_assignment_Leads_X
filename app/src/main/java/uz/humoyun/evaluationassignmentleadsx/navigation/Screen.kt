package uz.humoyun.evaluationassignmentleadsx.navigation

sealed class Screen(val route: String) {

    object AllLeadsScreen : Screen(ALL_LEADS_ROUTE)
    object CreateLeadScreen : Screen(CREATE_LEADS_ROUTE)

    object EditLeadScreen : Screen(EDIT_LEAD_ROUTE) {
        fun getRouteWithArgs(id: Int): String {
            return "leads/$id/edit"
        }
    }

    object LeadItemScreen : Screen(LEAD_ITEM_ROUTE) {
        fun getRouteWithArgs(id: Int): String {
            return "leads/$id"
        }
    }

    object HomeScreen : Screen(HOME_SCREEN)
    object ChatScreen : Screen(CHAT_SCREEN)
    object CallsScreen : Screen(CALLS_SCREEN)
    object MoreScreen : Screen(MORE_SCREEN)

    companion object {

        const val ALL_LEADS_ROUTE = "leads"
        const val CREATE_LEADS_ROUTE = "leads/create"
        const val LEAD_ITEM_ROUTE = "leads/{itemId}"
        const val EDIT_LEAD_ROUTE = "leads/{itemId}/edit"
        const val HOME_SCREEN = "home"
        const val CALLS_SCREEN = "calls"
        const val CHAT_SCREEN = "chat"
        const val MORE_SCREEN = "more"
    }
}
