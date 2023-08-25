package com.example.aplikasipeminjamanruangan.presentation.utils

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Lens
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.aplikasipeminjamanruangan.presentation.screen.home.getRealPathFromURI
import com.example.aplikasipeminjamanruangan.presentation.states.CameraXState
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.CameraXViewModel
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.RetrofitViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.io.File

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraView(
    viewModel: CameraXViewModel,
    uriState: CameraXState,
    retrofitViewModel: RetrofitViewModel

) {
    val permissions = if (Build.VERSION.SDK_INT <= 28) {
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    } else listOf(Manifest.permission.CAMERA)

    val permissionState = rememberMultiplePermissionsState(
        permissions = permissions
    )

    if (!permissionState.allPermissionsGranted) {
        SideEffect {
            permissionState.launchMultiplePermissionRequest()
        }
    }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val configuration = LocalConfiguration.current
    val screeHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    var previewView: PreviewView

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        // we will show camera preview once permission is granted
        if (permissionState.allPermissionsGranted) {
            Box(
                modifier = Modifier
                    .height(screeHeight * 0.85f)
                    .width(screenWidth)
            ) {
                AndroidView(
                    factory = {
                        previewView = PreviewView(it)
                        viewModel.showCameraPreview(previewView, lifecycleOwner)
                        previewView
                    },
                    modifier = Modifier
                        .height(screeHeight * 0.85f)
                        .width(screenWidth)
                )
            }
        }

        Box(
            modifier = Modifier
                .height(screeHeight * 0.15f),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = {
                if (permissionState.allPermissionsGranted) {
                    viewModel.captureAndSave(context)
                    viewModel.isLaunched(false)
                } else {
                    Toast.makeText(
                        context,
                        "Please accept permission in app settings",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Camera,
                    contentDescription = "",
                    modifier = Modifier.size(65.dp),
                    tint = Color.White
                )

            }
        }

    }
}