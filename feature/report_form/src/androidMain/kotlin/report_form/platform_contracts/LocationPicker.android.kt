package report_form.platform_contracts

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import report_form.ui.form.components.PickedLocation
import kotlinx.coroutines.launch
import ui.permission.PermissionDecorator
import ui.permission.PermissionFactory

@Composable
actual fun LocationPicker(
    modifier: Modifier,
    onLocationPicked: (PickedLocation) -> Unit
) {
    GMapLocationPicker(onLocationPicked = onLocationPicked)
}

/**
 * Explicitly showing which are not granted,it is easier to debug,
 * because in some cases for some reason the some permission is not launched,
 * in that case it is hard to find the bug,so that is why explicit showing the list
 */
@Composable
internal fun GMapLocationPicker(
    onLocationPicked: (PickedLocation) -> Unit = {}
) {
    PermissionDecorator(
        permissions = PermissionFactory.googleMapsPermissions()
    ){
        _GMapLocationPicker(onLocationPicked)
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun _GMapLocationPicker(
    onLocationPicked: (PickedLocation) -> Unit = {}
) {
    var location by remember {
        mutableStateOf<PickedLocation?>(null)
    }

    val scope = rememberCoroutineScope()
    val hostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        //not defining size is causes crash while the parent is scrollable
        //may be the compose framework this version has bug,fix it later
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                if (location == null)
                                    hostState.showSnackbar("Click marker to select location")
                                location?.let { loc ->
                                    location = loc
                                    onLocationPicked(loc)
                                }

                            }
                        }) {
                        Icon(
                            Icons.TwoTone.Done,
                            null,
                            tint = MaterialTheme.colorScheme.primary//since importance is high(click-able)
                        )

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

@SuppressLint("MissingPermission")
//remove waring for permission,because user will navigate here with permission
/**
 * Must Call it with proper permission,other wise may be bug or exception occurs
 */
@Composable
fun GMap(
    modifier: Modifier = Modifier,
    onLocationPicked: (PickedLocation) -> Unit
) {
    val defaultLatLong = LatLng(23.6850, 90.3563)
    val context = LocalContext.current

    var currentLocation by remember { mutableStateOf(defaultLatLong) }
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(Unit){
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = LatLng(location.latitude, location.longitude)
            }
        }
    }

    MoveCameraToCurrentLocation(cameraPositionState, currentLocation)

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = currentLocation),
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
