package data_submission.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import ui.form.components.PickedLocation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GMapLocationPicker(
    onLocationPicked: (PickedLocation) -> Unit = {}
) {
    var location by remember {
        mutableStateOf<PickedLocation?>(null)
    }

    val scope = rememberCoroutineScope()
    val hostState = remember { SnackbarHostState() }
    Scaffold(
        modifier=Modifier.fillMaxSize(),//disconnecting modifier for debug
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            if (location == null)
                                hostState.showSnackbar("Click marker to select location")
                            location?.let { loc ->
                                location = loc
                                onLocationPicked(loc)
                            }

                        }
                    }) {
                        Icon(Icons.Default.Done, null)

                    }


                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = hostState)
        },
    ) { innerPadding ->
        GMap(
            Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onLocationPicked = {
                location = it
            }
        )

    }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GMap(
    modifier: Modifier = Modifier,
    onLocationPicked: (PickedLocation) -> Unit
) {


    val context = LocalContext.current
    val locationPermissionState =
        rememberPermissionState(permission = "android.permission.ACCESS_FINE_LOCATION")
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    val cameraPositionState = rememberCameraPositionState()

    HandleLocationPermission(locationPermissionState, fusedLocationClient) { location ->
        currentLocation = location
    }
    MoveCameraToCurrentLocation(cameraPositionState, currentLocation)

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        currentLocation?.let { location ->
            Marker(
                state = MarkerState(position = location),
                title = "Current Location",
                snippet = "Drag me to select location",
                onClick = {
                    onLocationPicked(PickedLocation(it.position.latitude, it.position.longitude))
                    false
                },
                draggable = true, // Make the marker draggable
            )
        }
    }

}

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")//remove waring for permission
@Composable
fun HandleLocationPermission(
    locationPermissionState: PermissionState,
    fusedLocationClient: FusedLocationProviderClient,
    onLocationFound: (LatLng) -> Unit
) {
    LaunchedEffect(key1 = locationPermissionState.status) {
        if (locationPermissionState.status.isGranted) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    onLocationFound(LatLng(location.latitude, location.longitude))
                }
            }
        } else {
            locationPermissionState.launchPermissionRequest()
        }
    }
}

@Composable
fun MoveCameraToCurrentLocation(
    cameraPositionState: CameraPositionState,
    currentLocation: LatLng?
) {
    LaunchedEffect(currentLocation) {
        currentLocation?.let {
            cameraPositionState.move(
                CameraUpdateFactory.newLatLngZoom(
                    it,
                    15f
                )
            ) // Adjust zoom level as needed
        }
    }
}
