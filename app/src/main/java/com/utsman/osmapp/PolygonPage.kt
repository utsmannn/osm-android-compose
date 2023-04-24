package com.utsman.osmapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.Polygon
import com.utsman.osmandcompose.rememberCameraState

@Composable
fun PolygonPage() {

    val cameraState = rememberCameraState {
        geoPoint = Coordinates.depok
        zoom = 12.0
    }

    val geoPoint = remember {
        listOf(Coordinates.bekasi, Coordinates.depok, Coordinates.tangerang)
    }

    OpenStreetMap(
        modifier = Modifier.fillMaxSize(),
        cameraState = cameraState
    ) {
        Polygon(
            geoPoints = geoPoint,
            color = Color.Red,
            width = 18f,
            onPolygonLoaded = { outlinePaint, fillPaint ->

            }
        )
    }
}