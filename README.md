# OpenStreetMap Android Compose

## Load Map
```kotlin
OpenStreetMap(
    modifier = Modifier.fillMaxSize(),
    cameraState = cameraState,
    overlayManagerState = overlayManagerState,
    properties = mapProperties,
    onMapClick = {
        println("on click  -> $it")
    },
    onMapLongClick = {
        depokState.geoPoint = it
        println("on long click -> ${it.latitude}, ${it.longitude}")

    },
    onFirstLoadListener = {
        println("on loaded ... ")
    }
)
```

## Marker
```kotlin
OpenStreetMap(
    modifier = Modifier.fillMaxSize(),
    cameraState = cameraState
) {
    Marker(
        state = depokState,
        icon = depokIcon,
        title = "Depok",
        snippet = "Kota Depok"
    ) {
        // info window (optional)
        Column(
            modifier = Modifier
                .size(100.dp)
                .background(color = Color.Gray, shape = RoundedCornerShape(12.dp))
        ) {
            Text(text = it.title)
            Text(text = it.snippet)
        }
    }
}
```

### Polyline
```kotlin
OpenStreetMap(
    modifier = Modifier.fillMaxSize(),
    cameraState = cameraState
) {
    Polyline(
        geoPoints = points,
        color = Color.Cyan,
        cap = PolylineCap.ROUND
    ) {
        // info window (optional)
        Column(
            modifier = Modifier
                .size(100.dp)
                .background(color = Color.Red, shape = RoundedCornerShape(6.dp))
        ) {
            Text(text = it.title)
            Text(text = it.snippet)
        }
    }
}
```

### Polygon
```kotlin
OpenStreetMap(
    modifier = Modifier.fillMaxSize(),
    cameraState = cameraState
) {
    Polygon(
        geoPoints = points,
        color = Color.Blue,
        outlineColor = Color.Green
    )
}
```