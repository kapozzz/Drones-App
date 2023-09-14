package com.example.vozdux.presenter.util

sealed class Screen(val route: String) {
    object DronesScreen: Screen("DronesScreen")
}
