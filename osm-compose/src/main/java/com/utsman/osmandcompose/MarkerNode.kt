package com.utsman.osmandcompose

import androidx.compose.runtime.CompositionContext
import org.osmdroid.views.overlay.Marker

internal class MarkerNode(
    val mapView: OsmMapView,
    val markerState: MarkerState,
    val marker: Marker,
    var onMarkerClick: (Marker) -> Boolean
) : OsmAndNode {

    override fun onAttached() {
        markerState.marker = marker
    }

    override fun onRemoved() {
        markerState.marker = null
        marker.remove(mapView)
    }

    override fun onCleared() {
        markerState.marker = null
        marker.remove(mapView)
    }

    fun setupListeners() {
        marker.setOnMarkerClickListener { marker, _ ->
            val click = onMarkerClick.invoke(marker)
            if (marker.isInfoWindowShown) {
                marker.closeInfoWindow()
            } else {
                marker.showInfoWindow()
            }
            click
        }
    }
}