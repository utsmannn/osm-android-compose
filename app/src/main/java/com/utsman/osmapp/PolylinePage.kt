package com.utsman.osmapp

import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.Polyline
import com.utsman.osmandcompose.PolylineCap
import com.utsman.osmandcompose.rememberCameraState

@Composable
fun PolylinePage() {

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
        Polyline(
            geoPoints = geoPoint,
            color = Color.Red,
            cap = PolylineCap.ROUND,
            width = 18f,
            onPolylineLoaded = { paint ->
               // customize here
            }
        )
    }
}