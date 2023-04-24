package com.utsman.osmapp.navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

class Navigation(
    private val navHostController: NavHostController
) {
    fun goToSimpleNode() = navHostController launch Route.Simple

    fun goToMarker() = navHostController launch Route.Marker

    fun goToPolyline() = navHostController launch Route.Polyline

    fun goToPolygon() = navHostController launch Route.Polygon
}

private infix fun NavHostController.launch(navigationRoute: NavigationRoute) {
    navigate(route = navigationRoute.routeArg)
}

val LocalNavigation = compositionLocalOf<Navigation> { error("navigation") }