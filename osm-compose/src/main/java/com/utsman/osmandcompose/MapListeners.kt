package com.utsman.osmandcompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.osmdroid.util.GeoPoint

internal class MapListeners {
    var onMapClick: (GeoPoint) -> Unit by mutableStateOf({})
    var onMapLongClick: (GeoPoint) -> Unit by mutableStateOf({})
    var onFirstLoadListener: (String) -> Unit by mutableStateOf({})
}