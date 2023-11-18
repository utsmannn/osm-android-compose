package com.utsman.osmandcompose

import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import com.utsman.osmandcompose.extendedosm.MarkerWithLabel
import com.utsman.osmandcompose.model.LabelProperties
import org.osmdroid.views.overlay.Marker

/**
 * Marker with label with default parameters that can be customized
 *
 * Parameters:
 * - state: MarkerState = rememberMarkerState()
 * - icon: Drawable? = null
 * - visible: Boolean = true
 * - title: String? = null
 * - snippet: String? = null
 * - onClick: (Marker) -> Boolean = { false }
 * - id: String? = null
 * - label : String? = null
 * - labelProperties: LabelProperties = LabelProperties()
 * - infoWindowContent: @Composable (InfoWindowData) -> Unit = {}
 * */

@Composable
@OsmAndroidComposable
fun MarkerLabeled(
    state: MarkerState = rememberMarkerState(),
    icon: Drawable? = null,
    visible: Boolean = true,
    title: String? = null,
    snippet: String? = null,
    onClick: (Marker) -> Boolean = { false },
    id: String? = null,
    label : String? = null,
    labelProperties: LabelProperties = LabelProperties(),
    infoWindowContent: @Composable (InfoWindowData) -> Unit = {}
) {
    val context = LocalContext.current
    val applier = currentComposer.applier as? MapApplier ?: throw IllegalStateException("Invalid Applier")

    ComposeNode<MarkerNode, MapApplier>(
        factory = {
            val mapView = applier.mapView
            val marker = MarkerWithLabel(
                mapView,
                label,
                labelProperties
            ).apply {
                position = state.geoPoint
                rotation = state.rotation

                setVisible(visible)
                icon?.let { this.icon = it }
                id?.let { this.id = it }
                if(icon == null)
                    setTextIcon(title)
                else{
                    this.icon = icon
                }
            }

            mapView.overlayManager.add(marker)

            val composeView = ComposeView(context)
                .apply {
                    setContent {
                        infoWindowContent.invoke(InfoWindowData(title.orEmpty(), snippet.orEmpty()))
                    }
                }

            val infoWindow = OsmInfoWindow(composeView, mapView)
            infoWindow.view.setOnClickListener {
                if (infoWindow.isOpen) infoWindow.close()
            }
            marker.infoWindow = infoWindow
            MarkerNode(
                mapView = mapView,
                markerState = state,
                marker = marker,
                onMarkerClick = onClick
            ).also { it.setupListeners() }
        },
        update = {
            update(state.geoPoint) {
                marker.position = it
            }
            update(state.rotation) {
                marker.rotation = it
            }
            update(icon) {
                if (it == null) {
                    marker.setDefaultIcon()
                } else {
                    marker.icon = it
                }
            }
            update(visible) {
                marker.setVisible(it)
            }
            applier.invalidate()
        })
}