package com.utsman.osmapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.rememberCameraState

@Composable
fun SimplePage() {
    val cameraState = rememberCameraState {
        geoPoint = Coordinates.depok
        zoom = 12.0
    }

    OpenStreetMap(
        modifier = Modifier.fillMaxSize(),
        cameraState = cameraState
    )
}