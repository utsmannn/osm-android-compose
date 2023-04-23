package com.utsman.osmandcompose

import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import org.osmdroid.views.overlay.Marker

data class InfoWindowData(
    val title: String,
    val snippet: String
)

@Composable
@OsmAndroidComposable
fun Marker(
    state: MarkerState = rememberMarkerState(),
    icon: Drawable? = null,
    visible: Boolean = true,
    title: String? = null,
    snippet: String? = null,
    onClick: (Marker) -> Boolean = { false },
    id: String? = null,
    infoWindowContent: @Composable (InfoWindowData) -> Unit = {}
) {

    val context = LocalContext.current
    val applier = currentComposer.applier as? MapApplier ?: throw IllegalStateException("Invalid Applier")

    ComposeNode<MarkerNode, MapApplier>(
        factory = {
            val mapView = applier.mapView
            val marker = Marker(mapView).apply {
                position = state.geoPoint
                rotation = state.rotation

                setVisible(visible)
                icon?.let { this.icon = it }
                id?.let { this.id = it }
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