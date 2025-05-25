package com.app.presentation.route

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.domain.model.dto.CrewDTO
import com.app.domain.model.location.Coordinate
import com.app.domain.model.state.Voice
import com.app.domain.model.user.User
import com.app.presentation.animation.Screens
import com.app.presentation.ui.feature.login.LoginScreen
import com.app.presentation.ui.feature.login.ReportScreen
import com.app.presentation.ui.feature.login.UserInfoScreen
import com.app.presentation.ui.main.home.screen.HomeScreen
import com.app.presentation.ui.main.home.screen.ProfileScreen
import com.app.presentation.ui.feature.OnBoardingScreen
import com.app.presentation.ui.feature.activate.ActivateScreen
import com.app.presentation.ui.feature.crew.CrewScreen
import com.app.presentation.ui.feature.activate.detail.ActivateDetailScreen
import com.app.presentation.ui.feature.auth.SettingScreen
import com.app.presentation.ui.feature.crew.detail.CrewDetailScreen
import com.app.presentation.ui.feature.activate.chart.ActivateChart
import com.app.presentation.ui.main.home.screen.CalendarScreen
import com.app.presentation.viewmodel.ActivityLocationViewModel
import com.app.presentation.viewmodel.JsonParseViewModel
import com.app.presentation.viewmodel.StateViewModel
import com.app.presentation.viewmodel.UserViewModel
import com.google.android.gms.location.LocationServices
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.serialization.json.Json

@Composable
fun AppNavHost() {

    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            OnBoardingScreen(navController = navController)
        }
        composable("login") {
            LoginScreen(navController = navController)
        }

        composable(
            route = "userInfo?authState={authState}",
            arguments = listOf(navArgument("authState") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val authStateJson = backStackEntry.arguments?.getString("authState")
            val user = Json.decodeFromString<User>(authStateJson!!)

            UserInfoScreen(
                navController = navController,
                user = user
            )
        }

        composable(
            route = "report?userState={userState}&voiceState={voiceState}",
            arguments = listOf(
                navArgument("userState") {
                    type = NavType.StringType
                },
                navArgument("voiceState") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val userStateJson = backStackEntry.arguments?.getString("userState")
            val userState = Json.decodeFromString<User>(userStateJson!!)

            val voiceStateJson = backStackEntry.arguments?.getString("voiceState")
            val voiceState = Json.decodeFromString<Voice>(voiceStateJson!!)

            ReportScreen(userState, voiceState)
        }
    }
}

@Composable
fun ScreenNavigationConfiguration(
    navController: NavHostController,
    context: Context,
    userViewModel: UserViewModel = hiltViewModel(),
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel(),
    jsonParseViewModel: JsonParseViewModel = hiltViewModel(),
    stateViewModel: StateViewModel
) {

    val isClickable = remember {
        mutableStateOf(true)
    }

    val userList = userViewModel.user.collectAsState()
    val activateList = activityLocationViewModel.activateData.collectAsState()
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    LaunchedEffect(key1 = Unit) {
        val googleId = userViewModel.getSavedLoginState()

        userViewModel.selectUserFindById(googleId)
        activityLocationViewModel.selectActivityFindByGoogleId(googleId)
    }

    NavHost(navController = navController, startDestination = Screens.HomeScreen.route) {

        composable(Screens.HomeScreen.route) {
            HomeScreen(
                fusedLocationClient = fusedLocationClient,
                context = context,
                stateViewModel = stateViewModel
            )
        }

        if (isClickable.value) {
            composable(Screens.AnalyzeScreen.route) {
                CalendarScreen(
                    activateList = activateList.value,
                    userList = userList,
                    navController = navController,
                    stateViewModel = stateViewModel
                )
            }
        }

        composable(Screens.ProfileScreen.route) {
            ProfileScreen(
                navController = navController,
                context = context,
                userList = userList,
                stateViewModel = stateViewModel
            )
        }

        composable(
            route = "activateDetail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")

            ActivateDetailScreen(
                id = id!!,
                navController = navController,
                stateViewModel = stateViewModel
            )
        }

        composable(
            route = "activateChart?coords={coords}",
            arguments = listOf(navArgument("coords") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val coords = backStackEntry.arguments?.getString("coords")
            val coordsList: List<Coordinate> = jsonParseViewModel.dataFromJson(coords!!, "coordinate") as List<Coordinate>

            ActivateChart(
                coordsList = coordsList
            )
        }

        composable(
            route = "crew") {
            CrewScreen(
                context = context
            )
        }

        composable(
            route = "crewDetail/{crew}",
            arguments = listOf(navArgument("crew") {
              type = NavType.StringType
            })
        ) { backStackEntry ->
            val crewItem = backStackEntry.arguments?.getString("crew")
            val crewList = jsonParseViewModel.dataFromJson(crewItem!!, "crew") as List<CrewDTO>

            CrewDetailScreen(
                crewList = crewList,
                context = context
            )
        }

        composable(
            route = "activate"
        ) {
            ActivateScreen(
                context = context,
                navController = navController,
                stateViewModel = stateViewModel
            )
        }

        composable("settings/{userJson}") { backStackEntry ->
            val userJson = backStackEntry.arguments?.getString("userJson")
            val user = userJson.let {
                Json.decodeFromString<User>(it!!)
            }

            SettingScreen(
                user = user,
                stateViewModel = stateViewModel
            )
        }

        composable("report") {
            ReportScreen(
                userState = User(),
                voiceState = Voice()
            )
        }
    }
}