package com.example.vozdux.presenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vozdux.presenter.drone_list.DronesList
import com.example.vozdux.presenter.drone_list.DronesListViewModel
import com.example.vozdux.presenter.ui.theme.VozduxTheme
import com.example.vozdux.presenter.util.Screen
import javax.inject.Inject

class MainActivity : ComponentActivity() {



    @Inject
    lateinit var dronesListViewModel: DronesListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VozduxTheme {
                Surface {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.DronesScreen.route
                    ) {
                        composable(route = Screen.DronesScreen.route) {
                            DronesList(
                                viewModel = dronesListViewModel,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
