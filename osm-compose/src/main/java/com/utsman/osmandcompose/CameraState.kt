package com.utsman.osmandcompose

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.parcelize.Parcelize
import org.osmdroid.api.IGeoPoint
import org.osmdroid.api.IMapController
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint

@Parcelize
data class CameraProperty(
    var geoPoint: GeoPoint = GeoPoint(0.0, 0.0),
    var zoom: Double = 5.0,
    var speed: Long = 1000L
) : Parcelable

class CameraState(cameraProperty: CameraProperty) {

    var geoPoint: GeoPoint by mutableStateOf(cameraProperty.geoPoint)
    var zoom: Double by mutableStateOf(cameraProperty.zoom)
    var speed: Long by mutableStateOf(cameraProperty.speed)

    private var map: OsmMapView? = null

    private var prop: CameraProperty
        get() {
            val currentGeoPoint =
                map?.let { GeoPoint(it.mapCenter.latitude, it.mapCenter.longitude) } ?: geoPoint
            val currentZoom = map?.zoomLevelDouble ?: zoom
            return CameraProperty(currentGeoPoint, currentZoom, speed)
        }
        set(value) {
            synchronized(Unit) {
                geoPoint = value.geoPoint
                zoom = value.zoom
                speed = value.speed
            }
        }

    internal fun setMap(osmMapView: OsmMapView) {
        map = osmMapView
    }

    private fun getController(): IMapController {
        return map?.controller ?: throw IllegalStateException("Invalid Map attached!")
    }

    fun animateTo(geoPoint: GeoPoint) = getController().animateTo(geoPoint)
    fun animateTo(x: Int, y: Int) = getController().animateTo(x, y)
    fun scrollBy(x: Int, y: Int) = getController().scrollBy(x, y)
    fun setCenter(point: GeoPoint) = getController().setCenter(point)
    fun setZoom(pZoomLevel: Double): Double = getController().setZoom(pZoomLevel)
    fun stopAnimation(jumpToFinish: Boolean) = getController().stopAnimation(jumpToFinish)
    fun stopPanning() = getController().stopPanning()
    fun zoomIn(animationSpeed: Long? = null) = getController().zoomIn(animationSpeed)
    fun zoomInFixing(xPixel: Int, yPixel: Int, zoomAnimation: Long?): Boolean =
        getController().zoomInFixing(xPixel, yPixel, zoomAnimation)

    fun zoomInFixing(xPixel: Int, yPixel: Int): Boolean =
        getController().zoomInFixing(xPixel, yPixel)

    fun zoomOut(animationSpeed: Long? = null) = getController().zoomOut(animationSpeed)
    fun zoomOutFixing(xPixel: Int, yPixel: Int): Boolean =
        getController().zoomOutFixing(xPixel, yPixel)

    fun zoomToFixing(zoomLevel: Int, xPixel: Int, yPixel: Int, zoomAnimationSpeed: Long?): Boolean =
        getController().zoomToFixing(zoomLevel, xPixel, yPixel, zoomAnimationSpeed)

    fun zoomTo(pZoomLevel: Double, animationSpeed: Long? = null): Boolean =
        getController().zoomTo(pZoomLevel, animationSpeed)

    fun zoomToSpan(latSpan: Double, lonSpan: Double) = getController().zoomToSpan(latSpan, lonSpan)

    fun animateTo(point: GeoPoint, pZoom: Double? = null, pSpeed: Long? = null) =
        getController().animateTo(point, pZoom, pSpeed)

    fun animateTo(point: GeoPoint, pZoom: Double? = null, pSpeed: Long? = null, pOrientation: Float = 0f) =
        getController().animateTo(point, pZoom, pSpeed, pOrientation)

    fun zoomToBoundingBox(boundingBox: BoundingBox, animated: Boolean) =
        map?.zoomToBoundingBox(boundingBox, animated)

    fun animateTo(
        point: GeoPoint,
        pZoom: Double? = null,
        pSpeed: Long? = null,
        pOrientation: Float = 0f,
        pClockwise: Boolean = false
    ) = getController().animateTo(point, pZoom, pSpeed, pOrientation, pClockwise)

    fun normalizeRotation() {
        getController().animateTo(geoPoint, zoom, null, 0f)
    }

    companion object {
        val Saver: Saver<CameraState, CameraProperty> = Saver(
            save = { it.prop },
            restore = { CameraState(it) }
        )
    }
}

@Composable
fun rememberCameraState(
    key: String? = null,
    cameraProperty: CameraProperty.() -> Unit = {}
): CameraState = rememberSaveable(key = key, saver = CameraState.Saver) {
    val prop = CameraProperty().apply(cameraProperty)
    CameraState(prop)
}