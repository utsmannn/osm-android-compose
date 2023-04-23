package com.utsman.osmandcompose

import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Polyline


@Composable
@OsmAndroidComposable
fun Polyline(
    geoPoints: List<GeoPoint>,
    color: Color = Color.Black,
    visible: Boolean = true,
    onClick: (Polyline) -> Unit = {},
    title: String? = null,
    snippet: String? = null,
    id: String? = null,
    infoWindowContent: @Composable (InfoWindowData) -> Unit = {}
) {

    val context = LocalContext.current
    val applier =
        currentComposer.applier as? MapApplier ?: throw IllegalStateException("Invalid Applier")

    ComposeNode<PolylineNode, MapApplier>(
        factory = {
            val mapView = applier.mapView
            val polyline = Polyline(mapView)
            polyline.apply {
                setPoints(geoPoints)
                outlinePaint.color = color.toArgb()

                isVisible = visible
                id?.let { this.id = id }
                mapView.overlayManager.add(this)
                infoWindow = null
            }

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
            polyline.infoWindow = infoWindow

            PolylineNode(mapView, polyline, onClick).also { it.setupListeners() }
        }, update = {
            set(geoPoints) { polyline.setPoints(it) }
            set(color) { polyline.outlinePaint.color = it.toArgb() }

            update(visible) { polyline.isVisible = visible }
        })
}