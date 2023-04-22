package com.utsman.osmandcompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

class MarkerState(geoPoint: GeoPoint = GeoPoint(0.0, 0.0), rotation: Float = 0f) {
    var geoPoint: GeoPoint by mutableStateOf(geoPoint)
    var rotation: Float by mutableStateOf(0f)

    private val markerState: MutableState<Marker?> = mutableStateOf(null)

    var marker: Marker?
        get() = markerState.value
        set(value) {
            if (markerState.value == null && value == null) return
            if (markerState.value != null && value != null) {
                error("MarkerState may only be associated with one Marker at a time.")
            }
            markerState.value = value
        }

    companion object {
        val Saver: Saver<MarkerState, Pair<GeoPoint, Float>> = Saver(
            save = {
                   Pair(it.geoPoint, it.rotation)
            },
            restore = { MarkerState(it.first, it.second) }
        )
    }
}

@Composable
fun rememberMarkerState(
    key: String? = null,
    geoPoint: GeoPoint = GeoPoint(0.0, 0.0),
    rotation: Float = 0f
): MarkerState = rememberSaveable(key = key, saver = MarkerState.Saver) {
    MarkerState(geoPoint, rotation)
}