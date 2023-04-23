package com.utsman.osmandcompose

import android.graphics.Paint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Polygon

@Composable
@OsmAndroidComposable
fun Polygon(
    geoPoints: List<GeoPoint>,
    geoPointHoles: List<List<GeoPoint>> = emptyList(),
    color: Color = Color.Black,
    width: Float = 12f,
    outlineColor: Color = Color.Gray,
    visible: Boolean = true,
    onClick: (Polygon) -> Unit = {},
    title: String? = null,
    snippet: String? = null,
    id: String? = null,
    onPolygonLoaded: (outlinePaint: Paint, fillPaint: Paint) -> Unit = {_, _ ->},
    infoWindowContent: @Composable (InfoWindowData) -> Unit = {}
) {

    val context = LocalContext.current
    val applier =
        currentComposer.applier as? MapApplier ?: throw IllegalStateException("Invalid Applier")

    val point = remember {
        geoPoints + geoPoints[0]
    }

    val holes = remember {
        if (geoPointHoles.isNotEmpty()) {
            geoPointHoles.map {
                val newHole = if (it.isNotEmpty()) {
                    it + it[0]
                } else {
                    it
                }
                newHole
            }
        } else {
            geoPointHoles
        }
    }

    ComposeNode<PolygonNode, MapApplier>(
        factory = {
            val mapView = applier.mapView
            val polygon = Polygon(mapView)
            polygon.apply {
                points = point
                outlinePaint.color = outlineColor.toArgb()
                fillPaint.color = color.toArgb()

                outlinePaint.strokeWidth = width

                isVisible = visible
                id?.let { this.id = id }

                mapView.overlayManager.add(this)
                onPolygonLoaded.invoke(outlinePaint, fillPaint)

                infoWindow = null
                setHoles(holes)
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
            polygon.infoWindow = infoWindow

            PolygonNode(mapView, polygon, onClick).also { it.setupListeners() }
        }, update = {
            set(geoPoints) { polygon.points = it }
            set(color) { polygon.fillPaint.color = it.toArgb() }
            set(outlineColor) { polygon.outlinePaint.color = it.toArgb() }

            update(visible) { polygon.isVisible = visible }
        })
}