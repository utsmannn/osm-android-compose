# Polyline and Polygon

## Polyline
See official doc [here](https://github.com/osmdroid/osmdroid/wiki/Markers,-Lines-and-Polygons-(Java)#polylines)

```kotlin
@Composable
fun PolylinePage() {

    val cameraState = rememberCameraState {
        geoPoint = Coordinates.depok
        zoom = 12.0
    }

    // define polyline
    val geoPoint = remember {
        listOf(Coordinates.bekasi, Coordinates.depok, Coordinates.tangerang)
    }

    OpenStreetMap(
        modifier = Modifier.fillMaxSize(),
        cameraState = cameraState
    ) {
        // add polyline
        Polyline(geoPoints = geoPoint)
    }
}
```

![](/images/polyline.png){ width=500 }

### Caps and color polyline

```kotlin
OpenStreetMap(
    modifier = Modifier.fillMaxSize(),
    cameraState = cameraState
) {
    Polyline(
        geoPoints = geoPoint,
        color = Color.Red, // line color
        cap = PolylineCap.ROUND, // end and start cap
        width = 18f // width
    )
}
```

![](/images/polyline-custom-1.png){ width=500 }

### Fully customizable with `android.graphics.Paint`

```kotlin
    OpenStreetMap(
        modifier = Modifier.fillMaxSize(),
        cameraState = cameraState
    ) {
        Polyline(
            geoPoints = geoPoint,
            color = Color.Red,
            cap = PolylineCap.ROUND,
            width = 18f,
            onPolylineLoaded = { paint ->
               // customize here (optional)
            }
        )
    }
```

## Polygon
See official doc [here](https://github.com/osmdroid/osmdroid/wiki/Markers,-Lines-and-Polygons-(Java)#polygons)

```kotlin
    OpenStreetMap(
        modifier = Modifier.fillMaxSize(),
        cameraState = cameraState
    ) {
        Polygon(
            geoPoints = geoPoint,
            color = Color.Red,
            width = 18f,
            onPolygonLoaded = { outlinePaint, fillPaint ->
                // customize here (optional)
            }
        )
    }
```

![](/images/polygon.png){ width=500 }

---