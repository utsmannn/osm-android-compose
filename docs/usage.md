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