package uz.humoyun.evaluationassignmentleadsx

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import dagger.hilt.android.AndroidEntryPoint
import uz.humoyun.evaluationassignmentleadsx.navigation.AppNavGraph
import uz.humoyun.evaluationassignmentleadsx.navigation.NavigationItems
import uz.humoyun.evaluationassignmentleadsx.navigation.Screen
import uz.humoyun.evaluationassignmentleadsx.navigation.rememberNavigationState
import uz.humoyun.evaluationassignmentleadsx.ui.screen.AllLeadsScreen
import uz.humoyun.evaluationassignmentleadsx.ui.screen.CreateLeadScreen
import uz.humoyun.evaluationassignmentleadsx.ui.screen.EditLeadScreen
import uz.humoyun.evaluationassignmentleadsx.ui.screen.LeadDetailScreen
import uz.humoyun.evaluationassignmentleadsx.ui.viewmodel.allleads.AllLeadViewModel
import uz.humoyun.evaluationassignmentleadsx.ui.viewmodel.allleads.AllLeadViewModelImpl
import uz.humoyun.evaluationassignmentleadsx.ui.viewmodel.createlead.CreateLeadViewModel
import uz.humoyun.evaluationassignmentleadsx.ui.viewmodel.createlead.CreateLeadViewModelImpl
import uz.humoyun.evaluationassignmentleadsx.ui.viewmodel.leaddetails.LeadDetailViewModel
import uz.humoyun.evaluationassignmentleadsx.ui.viewmodel.leaddetails.LeadDetailViewModelImpl

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val navigationItems = listOf(
        NavigationItems.Home,
        NavigationItems.Calls,
        NavigationItems.Chat,
        NavigationItems.Leads,
        NavigationItems.More
    )

    private val leadDetailViewModel: LeadDetailViewModel by viewModels<LeadDetailViewModelImpl>()
    private val allLeadsViewModel: AllLeadViewModel by viewModels<AllLeadViewModelImpl>()
    private val createLeadViewModel: CreateLeadViewModel by viewModels<CreateLeadViewModelImpl>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navigationState = rememberNavigationState()
            val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

            val visible = when (navBackStackEntry?.destination?.route) {
                Screen.AllLeadsScreen.route -> true
                Screen.HomeScreen.route -> true
                Screen.CallsScreen.route -> true
                Screen.ChatScreen.route -> true
                Screen.MoreScreen.route -> true
                else -> false
            }


            Scaffold(modifier = Modifier.fillMaxSize()) { pad ->
                Box(
                    modifier = Modifier
                        .padding(pad)
                        .fillMaxSize()
                ) {


                    AppNavGraph(
                        navController = navigationState.navHostController,
                        allLeadsScreenContent = {
                            AllLeadsScreen(
                                mainViewModel = allLeadsViewModel,
                                onLeadClick = {
                                    navigationState.navigateItemScreen(it)
                                },
                                onCreateClick = {
                                    navigationState.navigateTo(Screen.CreateLeadScreen.route)
                                }
                            )
                        },
                        leadsItemScreenContent = { id ->
                            LeadDetailScreen(
                                id = id,
                                viewModel = leadDetailViewModel,
                                onBackPress = {
                                    navigationState.navHostController.popBackStack()
                                },
                                onEditClick = {
                                    navigationState.navigateEditItemScreen(it)
                                }
                            )
                        },
                        createLeadScreenContent = {
                            CreateLeadScreen(
                                viewModel = createLeadViewModel,
                                onBackPress = {
                                    navigationState.navHostController.popBackStack()
                                })
                        },
                        editLeadScreenContent = { id ->
                            EditLeadScreen(
                                id = id,
                                viewModel = createLeadViewModel,
                                onBackPress = {
                                    navigationState.navHostController.popBackStack()
                                }
                            )
                        },
                        modifier = Modifier.fillMaxSize(),
                        startDestination = Screen.HomeScreen,
                        homeContent = {},
                        callsContent = {},
                        chatContent = {},
                        moreContent = {}
                    )

                    AnimatedVisibility(
                        visible = visible,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it }),
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        NavigationBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .align(Alignment.BottomCenter)
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    color = Color(0xD9000000),
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(7.dp),
                            containerColor = Color.Transparent,
                            contentColor = Color(0xD9000000),
                        ) {
                            for (item in navigationItems) {
                                NavigationBarItem(
                                    selected = navBackStackEntry?.destination?.hierarchy?.any {
                                        item.screen.route == it.route
                                    } ?: false,
                                    onClick = {
                                        navigationState.navigateTo(
                                            route = item.screen.route,
                                            isPopUpTo = true
                                        )
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = item.icon),
                                            contentDescription = "icon"
                                        )
                                    },
                                    label = {
                                        Text(text = item.title)
                                    },
                                    alwaysShowLabel = true,
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = Color.Black,
                                        selectedTextColor = Color.White,
                                        indicatorColor = Color(0xFFE8A889),
                                        unselectedTextColor = Color(0xFF757575),
                                        unselectedIconColor = Color(0xFF757575)
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationCheck = ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!notificationCheck) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 12345
                )
            }
        }


    }
}

