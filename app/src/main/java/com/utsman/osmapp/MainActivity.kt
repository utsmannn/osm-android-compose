package com.utsman.osmapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.utsman.osmandcompose.MapProperties
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.Polygon
import com.utsman.osmandcompose.Polyline
import com.utsman.osmandcompose.PolylineCap
import com.utsman.osmandcompose.ZoomButtonVisibility
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberMarkerState
import com.utsman.osmandcompose.rememberOverlayManagerState
import com.utsman.osmapp.navigation.LocalNavigation
import com.utsman.osmapp.navigation.Navigation
import com.utsman.osmapp.navigation.Route
import com.utsman.osmapp.ui.theme.OsmAndroidComposeTheme
import org.osmdroid.tileprovider.MapTileProviderBasic
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.util.MapTileIndex
import org.osmdroid.views.overlay.CopyrightOverlay
import org.osmdroid.views.overlay.TilesOverlay

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
                    val navHostController = rememberNavController()

                    val navigation = remember {
                        Navigation(navHostController)
                    }

                    CompositionLocalProvider(LocalNavigation provides navigation) {
                        MainGraph(navHostController = navHostController)
                    }
                }
            }
        }
    }
}

@Composable
fun MainGraph(
    navHostController: NavHostController
) {

    NavHost(navController = navHostController, startDestination = Route.Main.routeArg) {
        composable(route = Route.Main.routeArg) {
            Main()
        }

        composable(route = Route.Simple.routeArg) {
            SimplePage()
        }

        composable(route = Route.Marker.routeArg) {
            MarkerPage()
        }

        composable(route = Route.Polyline.routeArg) {
            PolylinePage()
        }

        composable(route = Route.Polygon.routeArg) {
            PolygonPage()
        }
    }
}

@Composable
fun Main() {

    val navigation = LocalNavigation.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            navigation.goToSimpleNode()
        }) {
            Text(text = "Simple maps")
        }

        Button(onClick = {
            navigation.goToMarker()
        }) {
            Text(text = "Marker")
        }

        Button(onClick = {
            navigation.goToPolyline()
        }) {
            Text(text = "Polyline")
        }

        Button(onClick = {
            navigation.goToPolygon()
        }) {
            Text(text = "Polygon")
        }
    }
}


/**
 * Playground
 * */

@Composable
fun MarkerPage1() {
    val depokState = rememberMarkerState(geoPoint = GeoPoint(-6.3970066, 106.8224316))
    val jakartaState = rememberMarkerState(geoPoint = GeoPoint(-6.1907982, 106.8315909))
    val depokState2 = rememberMarkerState(geoPoint = GeoPoint(-6.3729963,106.75806))

    val cameraState = rememberCameraState {
        geoPoint = depokState.geoPoint
        zoom = 12.0
    }

    val overlayManagerState = rememberOverlayManagerState()

    val context = LocalContext.current

    var depokIcon: Drawable? by remember {
        mutableStateOf(context.getDrawable(R.drawable.round_eject_24))
    }

    var depokVisible by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    var mapProperties by remember {
        mutableStateOf(MapProperties())
    }

    val tileOverlay = remember {
        val tileUrl = "https://osm.rrze.fau.de/osmhd/"

        val rrzeSource = object : OnlineTileSourceBase(
            "RRZE",
            0,
            20,
            256,
            "",
            arrayOf(tileUrl)
        ) {
            override fun getTileURLString(pMapTileIndex: Long): String {
                val url = baseUrl + MapTileIndex.getZoom(pMapTileIndex) +
                        "/" + MapTileIndex.getX(pMapTileIndex) +
                        "/" + MapTileIndex.getY(pMapTileIndex) +
                        ".png"
                return url
            }
        }

        val tileProvider = MapTileProviderBasic(
            context,
            rrzeSource
        )

        TilesOverlay(tileProvider, context)
    }

    val polygonHoles = remember {
        val hole1 = listOf(
            GeoPoint(-6.3690298,106.7791744),
            GeoPoint(-6.3393337,106.8030781),
            GeoPoint(-6.3537767,106.7629521)
        )

        val hole2 = listOf(
            GeoPoint(-6.3083577,106.7829421),
            GeoPoint(-6.3105647,106.7866221)
        )

        listOf(hole1, hole2)
    }

    SideEffect {
        mapProperties = mapProperties
            .copy(isTilesScaledToDpi = true)
            .copy(tileSources = TileSourceFactory.MAPNIK)
            .copy(isEnableRotationGesture = true)
            .copy(zoomButtonVisibility = ZoomButtonVisibility.NEVER)
    }

    Box {
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
                overlayManagerState.overlayManager.add(CopyrightOverlay(context))
            }
        ) {

            Marker(
                state = depokState,
                icon = depokIcon,
                title = "anuan",
                snippet = "haah"
            ) {
                Column(
                    modifier = Modifier
                        .size(100.dp)
                        .background(color = Color.Gray, shape = RoundedCornerShape(12.dp))
                ) {
                    Text(text = it.title)
                    Text(text = it.snippet)
                }
            }

            Polyline(
                geoPoints = listOf(depokState.geoPoint, jakartaState.geoPoint),
                color = Color.Cyan,
                cap = PolylineCap.ROUND
            ) {
                Column(
                    modifier = Modifier
                        .size(100.dp)
                        .background(color = Color.Red, shape = RoundedCornerShape(6.dp))
                ) {
                    Text(text = it.title)
                    Text(text = it.snippet)
                }
            }

            Polygon(
                geoPoints = listOf(depokState.geoPoint, GeoPoint(-6.2076517,106.7439701), depokState2.geoPoint),
                geoPointHoles = polygonHoles,
                color = Color.Blue,
                outlineColor = Color.Green
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 100.dp)
        ) {
            Button(
                onClick = {
                    cameraState.geoPoint = depokState.geoPoint
                    cameraState.speed = 1000
                    cameraState.zoom = 16.0
                }) {
                Text(text = "marker visible")
            }

            Button(
                onClick = {
                    depokState.rotation = depokState.rotation + 90f
                }) {
                Text(text = "rotasi")
            }

            Button(
                onClick = {
                    cameraState.normalizeRotation()
                }) {
                Text(text = "rotasi normal")
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OsmAndroidComposeTheme {
        MarkerPage()
    }
}