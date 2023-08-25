package com.example.aplikasipeminjamanruangan.presentation.screen.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.aplikasipeminjamanruangan.R
import com.example.aplikasipeminjamanruangan.presentation.components.home.TopAppBar
import com.example.aplikasipeminjamanruangan.presentation.states.CameraXState
import com.example.aplikasipeminjamanruangan.presentation.states.RetrofitNimValidation
import com.example.aplikasipeminjamanruangan.presentation.states.RetrofitTextDetectionState
import com.example.aplikasipeminjamanruangan.presentation.utils.CameraView
import com.example.aplikasipeminjamanruangan.presentation.utils.MultiFloatingState
import com.example.aplikasipeminjamanruangan.presentation.utils.textColor
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.CameraXViewModel
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.RetrofitViewModel
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.SharedViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import java.io.File

class MinFabItem(
    val Icon: ImageBitmap, val label: String, val identifier: String
)

enum class Identifier {
    Camera, Gallery
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LendingScreen(
    cameraXViewModel: CameraXViewModel,
    sharedViewModel: SharedViewModel,
    retrofitViewModel: RetrofitViewModel,
    onNavBack: () -> Unit,
    onNextPage: () -> Unit,
    modifier: Modifier
) {
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    val data = sharedViewModel.sharedStated.collectAsStateWithLifecycle().value
    val uriState by cameraXViewModel.uriState.collectAsStateWithLifecycle()
    val isCameraLaunched by cameraXViewModel.isCameraLaunched.collectAsStateWithLifecycle()
    val isGalleryLaunched by cameraXViewModel.isCameraLaunched.collectAsStateWithLifecycle()
    val isTextDetected by retrofitViewModel.isTextDetected.collectAsStateWithLifecycle()
    val isNimValid by retrofitViewModel.isNimValid.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var multiFloatingState by rememberSaveable {
        mutableStateOf(MultiFloatingState.COLLAPSED)
    }

    // image picking
    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                if (uri != null) {
                    val contentUri = Uri.parse(uri.toString())
                    val imageFile = getRealPathFromURI(context, contentUri)?.let { File(it) }
                    cameraXViewModel.saveUriFromGallery(uri)
                    if (imageFile != null) {
                        retrofitViewModel.uploadImage(imageFile)
                    }
                } else {
                    Toast.makeText(context, "Canceling Image Selection", Toast.LENGTH_SHORT).show()
                }
            })

    val items = listOf(
        MinFabItem(
            Icon = ImageBitmap.imageResource(id = R.drawable.camera),
            label = "Camera",
            identifier = Identifier.Camera.name
        ), MinFabItem(
            Icon = ImageBitmap.imageResource(id = R.drawable.gallery),
            label = "Gallery",
            identifier = Identifier.Gallery.name
        )
    )

    Scaffold(floatingActionButton = {
        if (!isCameraLaunched) {
            MultiFloatingButton(multiFloatingState = multiFloatingState, onMultiFabStateChange = {
                multiFloatingState = it
            }, onMinFabItemClick = { minFabItem ->
                retrofitViewModel.deleteData()
                when (minFabItem.identifier) {
                    Identifier.Camera.name -> {
                        cameraXViewModel.isLaunched(true)
                        multiFloatingState = MultiFloatingState.COLLAPSED
                        permissionState.launchPermissionRequest()
                    }

                    Identifier.Gallery.name -> {
                        cameraXViewModel.isGalleryLaunched(true)
                        multiFloatingState = MultiFloatingState.COLLAPSED
                        launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                }
            }, items = items
            )
        }
    }) {
        if (isCameraLaunched) {
            PermissionProcess(
                permissionState = permissionState,
                uriState = uriState,
                cameraXViewModel = cameraXViewModel,
                retrofitViewModel = retrofitViewModel
            )
        }
        if (!isCameraLaunched || !isGalleryLaunched) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                TopAppBar(
                    onNavBack = onNavBack,
                    modifier = modifier,
                    bigText = "KTM",
                    smallText = "Pinjam dengan"
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier.padding(top = 24.dp)
                ) {
                    UploadingInstruction(modifier = modifier)
                    Spacer(modifier = Modifier.height(12.dp))
                    if (uriState.uri != null) {
                        LaunchedEffect(key1 = true) {
                            val imageFile =
                                getRealPathFromURI(context, uriState.uri!!)?.let {
                                    File(it)
                                }
                            if (imageFile != null) {
                                retrofitViewModel.uploadImage(imageFile)
                            }
                        }
                        ShowingImageAndStatusOutput(
                            uriState = uriState,
                            isTextDetected = isTextDetected,
                            retrofitViewModel = retrofitViewModel,
                            isNimVerified = isNimValid
                        )
                    }

                    if (isNimValid.data != null && !isTextDetected.isLoading && !isTextDetected.data?.nim.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        FinalVerificationStatus(isNimValid = isNimValid, onNextPage = onNextPage)
                    }
                }
            }
        }
    }
}

@Composable
fun MultiFloatingButton(
    multiFloatingState: MultiFloatingState,
    onMultiFabStateChange: (MultiFloatingState) -> Unit,
    onMinFabItemClick: (MinFabItem) -> Unit,
    items: List<MinFabItem>,
) {
    val transition = updateTransition(targetState = multiFloatingState, label = "transition")

    val rotate by transition.animateFloat(label = "rotate") {
        if (it == MultiFloatingState.EXPANDED) 315f else 0f
    }

    val fabScale by transition.animateFloat(label = "FabScale") {
        if (it == MultiFloatingState.EXPANDED) 62f else 0f
    }

    val alpha by transition.animateFloat(label = "alpha",
        transitionSpec = { tween(durationMillis = 50) }) {
        if (it == MultiFloatingState.EXPANDED) 1f else 0f
    }

    val textShadow by transition.animateDp(label = "alpha",
        transitionSpec = { tween(durationMillis = 50) }) {
        if (it == MultiFloatingState.EXPANDED) 2.dp else 0.dp
    }

    Column(
        horizontalAlignment = Alignment.End
    ) {
        if (transition.currentState == MultiFloatingState.EXPANDED) {
            items.forEach {
                MinFab(
                    item = it, onMinFabItemClick = { minFabItem ->
                        onMinFabItemClick(minFabItem)
                    }, alpha = alpha, textShadow = textShadow, fabScale = fabScale
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
        }
        FloatingActionButton(
            onClick = {
                onMultiFabStateChange(
                    if (transition.currentState == MultiFloatingState.EXPANDED) {
                        MultiFloatingState.COLLAPSED
                    } else {
                        MultiFloatingState.EXPANDED
                    }
                )
            }, modifier = Modifier.rotate(rotate)
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add FAB",
                tint = textColor,
            )
        }
    }
}

@Composable
fun MinFab(
    item: MinFabItem,
    alpha: Float,
    textShadow: Dp,
    fabScale: Float,
    showLabel: Boolean = true,
    onMinFabItemClick: (MinFabItem) -> Unit
) {
    val buttonColor = MaterialTheme.colors.secondary
    val shadow = Color.Black.copy(.5f)

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showLabel) {
            Text(
                text = item.label, fontSize = 14.sp, color = textColor, modifier = Modifier
                    .alpha(
                        animateFloatAsState(
                            targetValue = alpha, animationSpec = tween(50)
                        ).value
                    )
                    .shadow(textShadow)
                    .background(
                        MaterialTheme.colors.secondary, shape = RoundedCornerShape(8.dp)
                    )
                    .padding(start = 6.dp, end = 6.dp, top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Canvas(
            modifier = Modifier
                .size(58.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    onClick = { onMinFabItemClick(item) },
                    indication = rememberRipple(
                        bounded = false, radius = 20.dp, color = MaterialTheme.colors.onSurface
                    )
                )
        ) {
            drawCircle(
                color = shadow, radius = fabScale, center = Offset(
                    center.x + 2f, center.y - 2f
                )
            )

            drawCircle(
                color = buttonColor, radius = 62f
            )

            drawImage(
                image = item.Icon, topLeft = Offset(
                    center.x - (item.Icon.width / 2), center.y - (item.Icon.width / 2)
                ), alpha = alpha, colorFilter = ColorFilter.tint(textColor)
            )
        }
    }
}

@Composable
fun ShowingImageAndStatusOutput(
    uriState: CameraXState,
    isTextDetected: RetrofitTextDetectionState,
    isNimVerified: RetrofitNimValidation,
    retrofitViewModel: RetrofitViewModel
) {
    Card(
        elevation = 10.dp, modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                text = buildAnnotatedString {
                    append("Nim: ")
                    withStyle(
                        style = SpanStyle(
                            color = textColor, fontWeight = FontWeight.Normal, fontSize = 14.sp
                        )
                    ) {
                        append(
                            "${
                                if (isTextDetected.isLoading) "Loading.." else if (isTextDetected.data?.nim.isNullOrEmpty()) "Please retake Image !" else isTextDetected.data?.nim
                            }"
                        )
                    }
                })
            Spacer(Modifier.height(8.dp))
            AsyncImage(
                modifier = Modifier
                    .height(300.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(MaterialTheme.colors.secondary),
                model = ImageRequest.Builder(LocalContext.current).data(uriState.uri)
                    .crossfade(true).build(),
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.ic_broken_image),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
            )
            if (!isTextDetected.isLoading && !isTextDetected.data?.nim.isNullOrEmpty()) {
                Button(
                    onClick = {
                        retrofitViewModel.verifyNim(isTextDetected.data?.nim!!)
                    },
                    border = BorderStroke(1.dp, Color.White),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(6.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary)
                ) {
                    if (isNimVerified.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp),
                            backgroundColor = Color.White
                        )
                    } else {
                        Text(
                            "Verifikasi",
                            color = textColor,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FinalVerificationStatus(
    isNimValid: RetrofitNimValidation,
    onNextPage: () -> Unit
) {
    Card(
        elevation = 10.dp, modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            if (isNimValid.data != null) {
                isNimValid.data.forEach { item ->
                    Text(
                        color = Color.Green,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        text = "Data valid"
                    )
                    Text(color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        text = buildAnnotatedString {
                            append("Nama: ")
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Black,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            ) {
                                append(
                                    "${item.nama}"
                                )
                            }
                        })
                    Text(color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        text = buildAnnotatedString {
                            append("Nim: ")
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Black,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            ) {
                                append(
                                    "${item.nim}"
                                )
                            }
                        })
                    Text(color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        text = buildAnnotatedString {
                            append("Prodi: ")
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Black,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            ) {
                                append(
                                    "${item.prodi}"
                                )
                            }
                        })
                }
                Button(
                    onClick = {
                        onNextPage()
                    },
                    border = BorderStroke(1.dp, Color.White),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(6.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary)
                ) {
                    Text(
                        "Selanjutnya",
                        color = textColor,
                        style = MaterialTheme.typography.body1
                    )
                }
            } else if (isNimValid.errMsg != null) {
                Text(color = MaterialTheme.colors.secondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    text = buildAnnotatedString {
                        append("Error Terjadi")
                        withStyle(
                            style = SpanStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp
                            )
                        ) {
                            append(
                                isNimValid.errMsg.toString()
                            )
                        }
                    })
            } else {
                Text(color = MaterialTheme.colors.secondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    text = buildAnnotatedString {
                        append("Data tidak valid.")
                        withStyle(
                            style = SpanStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp
                            )
                        ) {
                            append(
                                "Cek kembali kartu ktm anda."
                            )
                        }
                    })
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionProcess(
    permissionState: PermissionState,
    uriState: CameraXState,
    cameraXViewModel: CameraXViewModel,
    retrofitViewModel: RetrofitViewModel
) {
    if (permissionState.status.isGranted) {
        CameraView(
            viewModel = cameraXViewModel, uriState = uriState, retrofitViewModel = retrofitViewModel
        )
    } else {
        if (permissionState.status.shouldShowRationale) {
            Snackbar() {
                Text(
                    text = " \"The camera is important for this app. Please grant the permission.\"",
                    color = textColor
                )
            }
        } else {
            Snackbar() {
                Text(
                    text = "Camera permission required for this feature to be available. " + "Please grant the permission",
                    color = textColor
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UploadingInstruction(modifier: Modifier) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Card(elevation = 10.dp,
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .fillMaxWidth(),
        onClick = { expanded = !expanded }) {
        Column(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Tata cara peminjaman ?", fontSize = 14.sp,
                    style = MaterialTheme.typography.body1, color = Color.Black,
                )
                Spacer(modifier = Modifier.weight(1f))
                ItemButton(
                    onClick = { expanded = !expanded }, onExpanded = expanded, modifier = modifier
                )
            }
            if (expanded) {
                InstructionDetail(modifier = modifier)
            }
        }
    }
}

@Composable
fun InstructionDetail(modifier: Modifier) {
    Text(
        text = buildAnnotatedString {
            append("Langkah - langkah: \n")
            withStyle(
                style = SpanStyle(
                    color = Color.Gray, fontSize = 13.sp
                )
            ) {
                append("1. Sediakan KTM Mahasiswa/i\n")
                append("2. Pastikan gambar diambil dalam keadaan cukup cahaya\n")
                append("3. Proses membutuhkan jaringan Internet\n")
                append("4. Ambil gambar dalam posisi vertikal")
            }
        },
        style = MaterialTheme.typography.caption,
        color = Color.Black,
        fontSize = 14.sp,
        modifier = modifier.padding(bottom = 12.dp)
    )
}

fun getRealPathFromURI(context: Context, contentUri: Uri): String? {
    var cursor: Cursor? = null
    return try {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        cursor = context.contentResolver.query(contentUri, proj, null, null, null)
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        cursor?.getString(columnIndex!!)
    } catch (e: Exception) {
        Log.e("Testing", "getRealPathFromURI Exception : $e")
        ""
    } finally {
        cursor?.close()
    }
}

@Composable
fun ItemButton(
    onClick: () -> Unit, onExpanded: Boolean, modifier: Modifier
) {
    IconButton(onClick = { onClick() }) {
        Icon(
            imageVector = if (onExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = "Expand More",
            tint = Color.Black
        )
    }
}
