package com.utsman.osmandcompose

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.OverlayManager

@SuppressLint("MutableCollectionMutableState")
class OverlayManagerState(private var _overlayManager: OverlayManager?) {

    val overlayManager: OverlayManager
        get() = _overlayManager
            ?: throw IllegalStateException("Invalid Map attached!, please add other overlay in OpenStreetMap#onFirstLoadListener")

    private var _mapView: MapView? = null
    fun setMap(mapView: MapView) {
        _overlayManager = mapView.overlayManager
        _mapView = mapView
    }

    fun getMap(): MapView {
        return _mapView ?: throw IllegalStateException("Invalid Map attached!")
    }
}

@Composable
fun rememberOverlayManagerState() = remember {
    OverlayManagerState(null)
}