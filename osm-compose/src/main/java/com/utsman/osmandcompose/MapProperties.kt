package com.utsman.osmandcompose

import org.osmdroid.tileprovider.tilesource.ITileSource
import org.osmdroid.views.overlay.OverlayManager

data class MapProperties(
    val mapOrientation: Float = 0f,
    val isMultiTouchControls: Boolean = true,
    val isAnimating: Boolean = true,
    val minZoomLevel: Double = 6.0,
    val maxZoomLevel: Double = 29.0,
    val isFlingEnable: Boolean = true,
    val isEnableRotationGesture: Boolean = false,
    val isUseDataConnection: Boolean = true,
    val isTilesScaledToDpi: Boolean = false,
    val tileSources: ITileSource? = null,
    val overlayManager: OverlayManager? = null,
    val zoomButtonVisibility: ZoomButtonVisibility = ZoomButtonVisibility.ALWAYS
)

val DefaultMapProperties = MapProperties()