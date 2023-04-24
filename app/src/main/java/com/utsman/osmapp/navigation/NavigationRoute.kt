package com.utsman.osmapp.navigation

sealed class NavigationRoute(
    private val route: String = String.Empty,
    private val keyArg: String = String.Empty
) {

    val routeArg: String
        get() {
            return if (keyArg.isNotEmpty()) {
                "$route{$keyArg}"
            } else {
                route
            }
        }
}

val String.Companion.Empty get() = ""