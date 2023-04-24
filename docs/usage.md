# Maps Node

## Manifest permission

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## Maps Node
```kotlin
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OsmAndroidComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    
                    // define camera state
                    val cameraState = rememberCameraState {
                        geoPoint = GeoPoint(-6.3970066, 106.8224316)
                        zoom = 12.0 // optional, default is 5.0
                    }
                    
                    // add node
                    OpenStreetMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraState = cameraState
                    )
                }
            }
        }
    }
}
```

Result

![](/images/simple-maps.png){ width=500 }

---

## Camera State
Camera state refers to the state that controls the camera projection for maps. It supports the position, zoom level, and animation duration.

```kotlin
val cameraState = rememberCameraState {
    geoPoint = GeoPoint(-6.3970066, 106.8224316)
    zoom = 12.0 // optional, default is 5.0
}
```

## Map Properties
These are properties that affect the display of maps, such as map orientation, min and max zoom levels, setting multi touch controls and others, see reference

```kotlin
// define properties with remember with default value
var mapProperties by remember {
    mutableStateOf(DefaultMapProperties)
}

// setup mapProperties in side effect
SideEffect {
    mapProperties = mapProperties
        .copy(isTilesScaledToDpi = true)
        .copy(tileSources = TileSourceFactory.MAPNIK)
        .copy(isEnableRotationGesture = true)
        .copy(zoomButtonVisibility = ZoomButtonVisibility.NEVER)
}

OpenStreetMap(
    modifier = Modifier.fillMaxSize(),
    cameraState = cameraState,
    properties = mapProperties // add properties
)
```

## Overlay Manager State
Overlay is an additional layer on OSM. With `OverlayManagerState`, you can obtain an `OverlayManager` to add other overlays.

```kotlin
val overlayManagerState = rememberOverlayManagerState()

OpenStreetMap(
    modifier = Modifier.fillMaxSize(),
    cameraState = cameraState,
    overlayManagerState = overlayManagerState, // setup overlay manager state
    onFirstLoadListener = {
        val copyright = CopyrightOverlay(context)
        overlayManagerState.overlayManager.add(copyright) // add another overlay in this listener
    }
)
```

If you want to add an overlay, you must do it in the `onFirstLoadListener` because `OverlayManagerState` must obtain the `MapView` instance first before having an `OverlayManager`.

## Others parameters

`onMapClick`
: when the user clicks maps at any location, this listener will display the geopoints

`onMapLongClick`
: same as onMapClick, but this is for long clicks.

`onFirstLoadListener`
: when the map is first loaded

`content`
: a block that contains nodes from OSM such as markers, polylines and polygons if you want to add them

---