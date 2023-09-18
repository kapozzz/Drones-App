package com.example.vozdux.presenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vozdux.DronesMainApplication
import com.example.vozdux.presenter.drone_list.DronesList
import com.example.vozdux.presenter.drone_list.DronesListViewModel
import com.example.vozdux.presenter.new_drone.NewDrone
import com.example.vozdux.presenter.new_drone.NewDroneViewModel
import com.example.vozdux.presenter.ui.theme.VozduxTheme
import com.example.vozdux.presenter.util.Screen
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dronesListViewModel: DronesListViewModel

    @Inject
    lateinit var newDroneViewModelFactory: NewDroneViewModel.NewDroneViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as DronesMainApplication).injector.inject(this)

        setContent {
            VozduxTheme {
                Surface {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.NewDroneScreen.route
                    ) {
                        composable(route = Screen.DronesScreen.route) {
                            DronesList(
                                viewModel = dronesListViewModel,
                                navController = navController
                            )
                        }
                        composable(
                            route = "${Screen.NewDroneScreen.route}?drone={drone}",
                            arguments = listOf(
                                navArgument(name = "drone") {
                                    type = NavType.StringType
                                    defaultValue = "-1"
                                }
                            )
                        ) {
                            val droneId = it.arguments?.getString("droneId") ?: "-1"
                            val newDroneViewModel = newDroneViewModelFactory.create(droneId)
                            NewDrone(
                                newDroneViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
