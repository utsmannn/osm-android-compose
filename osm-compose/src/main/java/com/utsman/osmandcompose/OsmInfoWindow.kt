package com.utsman.osmandcompose

import android.view.View
import org.osmdroid.views.overlay.infowindow.InfoWindow

class OsmInfoWindow(private val view: View, private val mapView: OsmMapView) : InfoWindow(view, mapView) {
    override fun onOpen(item: Any?) {
    }

    override fun onClose() {
    }
}