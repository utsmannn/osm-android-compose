# Marker

`Marker` is an overlay in OSM, [see official](https://github.com/osmdroid/osmdroid/wiki/Markers,-Lines-and-Polygons-(Kotlin)#marker). You can add a marker with simple code like the example below.

```kotlin
@Composable
fun MarkerPage() {
    // define marker state
    val depokMarkerState = rememberMarkerState(
        geoPoint = GeoPoint(-6.3970066, 106.8224316)
    )

    OpenStreetMap(
        modifier = Modifier.fillMaxSize(),
        cameraState = cameraState
    ) {
        // add marker here
        Marker(
            state = depokMarkerState // add marker state
        )
    }
}
```

![](/images/marker-default.png){ width=500 }

---

## MarkerState
Is a state that can control the position and rotation of the marker.

```kotlin
val depokMarkerState = rememberMarkerState(
    geoPoint = Coordinates.depok,
    rotation = 90f // default is 0f
)
```

## Icon
By default, `Marker` already has an icon from OSM. However, you can change the icon with a drawable.

```kotlin
@Composable
fun MarkerPage() {
    
    // define marker icon
    val depokIcon: Drawable? by remember {
        mutableStateOf(context.getDrawable(R.drawable.custom_marker_icon))
    }

    OpenStreetMap(
        modifier = Modifier.fillMaxSize(),
        cameraState = cameraState
    ) {
        Marker(
            state = depokMarkerState,
            icon = depokIcon
        )
    }
}

```

![](/images/marker-custom.png){ width=500 }

## InfoWindow
OSM supports InfoWindow, see the [official javadoc](https://osmdroid.github.io/osmdroid/javadocs/osmdroid-android/debug/index.html?org/osmdroid/views/overlay/infowindow/InfoWindow.html). For OSM for android compose, it also supports InfoWindow with Compose node. You can create InfoWindow in various shapes using Compose.

```kotlin
val depokMarkerState = rememberMarkerState(
    geoPoint = Coordinates.depok,
    rotation = 90f
)

val depokIcon: Drawable? by remember {
    mutableStateOf(context.getDrawable(R.drawable.round_eject_24))
}

OpenStreetMap(
    modifier = Modifier.fillMaxSize(),
    cameraState = cameraState
) {
    Marker(
        state = depokMarkerState,
        icon = depokIcon,
        title = "Depok", // add title
        snippet = "Jawa barat" // add snippet
    ) {
        
        // create info window node
        Column(
            modifier = Modifier
                .size(100.dp)
                .background(color = Color.Gray, shape = RoundedCornerShape(7.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // setup content of info window
            Text(text = it.title)
            Text(text = it.snippet, fontSize = 10.sp)
        }
    }
}
```

![](/images/info-window.png){ width=500 }
---