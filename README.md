# OpenStreetMap Android Compose

[//]: # (## Load Map)

[//]: # (```kotlin)

[//]: # (OpenStreetMap&#40;)

[//]: # (    modifier = Modifier.fillMaxSize&#40;&#41;,)

[//]: # (    cameraState = cameraState,)

[//]: # (    overlayManagerState = overlayManagerState,)

[//]: # (    properties = mapProperties,)

[//]: # (    onMapClick = {)

[//]: # (        println&#40;"on click  -> $it"&#41;)

[//]: # (    },)

[//]: # (    onMapLongClick = {)

[//]: # (        depokState.geoPoint = it)

[//]: # (        println&#40;"on long click -> ${it.latitude}, ${it.longitude}"&#41;)

[//]: # ()
[//]: # (    },)

[//]: # (    onFirstLoadListener = {)

[//]: # (        println&#40;"on loaded ... "&#41;)

[//]: # (    })

[//]: # (&#41;)

[//]: # (```)

[//]: # ()
[//]: # (## Marker)

[//]: # (```kotlin)

[//]: # (OpenStreetMap&#40;)

[//]: # (    modifier = Modifier.fillMaxSize&#40;&#41;,)

[//]: # (    cameraState = cameraState)

[//]: # (&#41; {)

[//]: # (    Marker&#40;)

[//]: # (        state = depokState,)

[//]: # (        icon = depokIcon,)

[//]: # (        title = "Depok",)

[//]: # (        snippet = "Kota Depok")

[//]: # (    &#41; {)

[//]: # (        // info window &#40;optional&#41;)

[//]: # (        Column&#40;)

[//]: # (            modifier = Modifier)

[//]: # (                .size&#40;100.dp&#41;)

[//]: # (                .background&#40;color = Color.Gray, shape = RoundedCornerShape&#40;12.dp&#41;&#41;)

[//]: # (        &#41; {)

[//]: # (            Text&#40;text = it.title&#41;)

[//]: # (            Text&#40;text = it.snippet&#41;)

[//]: # (        })

[//]: # (    })

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (### Polyline)

[//]: # (```kotlin)

[//]: # (OpenStreetMap&#40;)

[//]: # (    modifier = Modifier.fillMaxSize&#40;&#41;,)

[//]: # (    cameraState = cameraState)

[//]: # (&#41; {)

[//]: # (    Polyline&#40;)

[//]: # (        geoPoints = points,)

[//]: # (        color = Color.Cyan,)

[//]: # (        cap = PolylineCap.ROUND)

[//]: # (    &#41; {)

[//]: # (        // info window &#40;optional&#41;)

[//]: # (        Column&#40;)

[//]: # (            modifier = Modifier)

[//]: # (                .size&#40;100.dp&#41;)

[//]: # (                .background&#40;color = Color.Red, shape = RoundedCornerShape&#40;6.dp&#41;&#41;)

[//]: # (        &#41; {)

[//]: # (            Text&#40;text = it.title&#41;)

[//]: # (            Text&#40;text = it.snippet&#41;)

[//]: # (        })

[//]: # (    })

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (### Polygon)

[//]: # (```kotlin)

[//]: # (OpenStreetMap&#40;)

[//]: # (    modifier = Modifier.fillMaxSize&#40;&#41;,)

[//]: # (    cameraState = cameraState)

[//]: # (&#41; {)

[//]: # (    Polygon&#40;)

[//]: # (        geoPoints = points,)

[//]: # (        color = Color.Blue,)

[//]: # (        outlineColor = Color.Green)

[//]: # (    &#41;)

[//]: # (})

[//]: # (```)

### Under deployment