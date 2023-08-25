package com.example.aplikasipeminjamanruangan.presentation.screen.home

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.TimerOff
import androidx.compose.material.icons.filled.ViewCozy
import androidx.compose.material.icons.filled.Workspaces
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aplikasipeminjamanruangan.domain.model.PeminjamanModel
import com.example.aplikasipeminjamanruangan.domain.model.PengajuanModel
import com.example.aplikasipeminjamanruangan.domain.model.RoomsModel
import com.example.aplikasipeminjamanruangan.domain.model.RoomsModelMain
import com.example.aplikasipeminjamanruangan.presentation.components.Calendar
import com.example.aplikasipeminjamanruangan.presentation.components.Clock
import com.example.aplikasipeminjamanruangan.presentation.components.ListDialog
import com.example.aplikasipeminjamanruangan.presentation.components.home.TopAppBar
import com.example.aplikasipeminjamanruangan.presentation.states.RealtimeDBDosenState
import com.example.aplikasipeminjamanruangan.presentation.states.RealtimeDBPengajuanState
import com.example.aplikasipeminjamanruangan.presentation.states.RetrofitNimValidation
import com.example.aplikasipeminjamanruangan.presentation.utils.ConfirmationDialog
import com.example.aplikasipeminjamanruangan.presentation.utils.DataStore
import com.example.aplikasipeminjamanruangan.presentation.utils.textColor
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.DosenViewModel
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.PengajuanViewModel
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.RetrofitViewModel
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.SharedViewModel
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

enum class UnitEnum {
    Mhs, Bagian, Prodi, BEM
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LendingFormScreen(
    dosenViewModel: DosenViewModel,
    sharedViewModel: SharedViewModel,
    retrofitViewModel: RetrofitViewModel,
    pengajuanViewModel: PengajuanViewModel,
    onPinjamRuangan: (PengajuanModel, RoomsModelMain) -> Unit,
    onSaveToHistory: (PeminjamanModel) -> Unit,
    onNavBack: () -> Unit,
    onActionClick: () -> Unit,
    modifier: Modifier
) {
    val data = sharedViewModel.sharedStated.collectAsStateWithLifecycle().value
    val dataRetrofit = retrofitViewModel.isNimValid.collectAsStateWithLifecycle().value
    val pengajuanState = pengajuanViewModel.pengajuanState.collectAsStateWithLifecycle().value
    val dosenState = dosenViewModel.dosenState.collectAsStateWithLifecycle().value

    val scaffoldState = rememberScaffoldState()
    val snackBarCoroutineScope = rememberCoroutineScope()
    val snackState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val flag = rememberSaveable {
        mutableStateOf(true)
    }

    if (pengajuanState.data != null && flag.value) {
        Toast.makeText(context, "${pengajuanState.data}", Toast.LENGTH_LONG).show()
        flag.value = false
    }

    if (pengajuanState.errMsg != null) {
        Toast.makeText(context, "${pengajuanState.errMsg}", Toast.LENGTH_LONG).show()
    }

    Scaffold(scaffoldState = scaffoldState, snackbarHost = { scaffoldState.snackbarHostState }) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            TopAppBar(
                onNavBack = onNavBack,
                modifier = modifier,
                bigText = "Form",
                smallText = "Peminjaman Ruangan"
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(top = 24.dp)
            ) {
                LendingForm(
                    modifier = modifier,
                    roomsModelMain = data.data!!,
                    dosenModel = dosenState,
                    retrofitData = dataRetrofit,
                    pengajuanState = pengajuanState,
                    onPinjamRuangan = onPinjamRuangan,
                    onActionClick = onActionClick,
                    onSaveToHistory = onSaveToHistory,
                    context = context
                )
            }
        }
    }
}


@Composable
fun LendingForm(
    modifier: Modifier,
    roomsModelMain: RoomsModelMain,
    dosenModel: RealtimeDBDosenState,
    retrofitData: RetrofitNimValidation,
    pengajuanState: RealtimeDBPengajuanState,
    onPinjamRuangan: (PengajuanModel, RoomsModelMain) -> Unit,
    onActionClick: () -> Unit,
    onSaveToHistory: (PeminjamanModel) -> Unit,
    context: Context
) {
    Card(
        elevation = 10.dp, modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 28.dp, bottom = 28.dp),
        ) {
            val spacerHeightValue = 4.dp
            val calendarState = rememberSheetState()
            val beginClockState = rememberSheetState()
            val endClockState = rememberSheetState()
            val facilityState = rememberSheetState()
            val textFieldColorsStyle: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.secondary,
                unfocusedBorderColor = MaterialTheme.colors.primary,
                focusedLabelColor = MaterialTheme.colors.secondary,
                unfocusedLabelColor = MaterialTheme.colors.primary,
                leadingIconColor = MaterialTheme.colors.primary,
                disabledLeadingIconColor = MaterialTheme.colors.secondary,
                cursorColor = MaterialTheme.colors.primaryVariant,
                textColor = Color.Black
            )

            var namaValue by rememberSaveable { mutableStateOf("") }
            var nimValue by rememberSaveable { mutableStateOf("") }
            var prodiValue by rememberSaveable { mutableStateOf("") }
            var ruanganValue by rememberSaveable { mutableStateOf("") }
            var tanggalValue by rememberSaveable { mutableStateOf("") }
            var jMulaiValue by rememberSaveable { mutableStateOf("") }
            var jSelesaiValue by rememberSaveable { mutableStateOf("") }
            var fasilitasValue by rememberSaveable { mutableStateOf("") }
            var keperluanValue by rememberSaveable { mutableStateOf("") }
            var unitValue by rememberSaveable { mutableStateOf("") }
            var penanggungJawabValue by rememberSaveable { mutableStateOf("") }
            var isCalendarClicked by rememberSaveable { mutableStateOf(false) }
            var isJamMulaiClicked by rememberSaveable { mutableStateOf(false) }
            var isJamSelesaiClicked by rememberSaveable { mutableStateOf(false) }
            var isFacilityClicked by rememberSaveable { mutableStateOf(false) }
            var confirmationDialog by rememberSaveable { mutableStateOf(false) }

            retrofitData.data?.forEach {
                namaValue = it.nama.toString()
                nimValue = it.nim.toString()
                prodiValue = it.prodi.toString()
            }

            val unit = mapOf(
                UnitEnum.Mhs to "Mahasiswa",
                UnitEnum.Bagian to "Bagian",
                UnitEnum.Prodi to "Prodi",
                UnitEnum.BEM to "BEM"
            )

            ruanganValue = roomsModelMain.item?.nama_ruangan!!

            if (isCalendarClicked) {
                calendarState.show()
                isCalendarClicked = false
            }

            if (isJamMulaiClicked) {
                beginClockState.show()
                isJamMulaiClicked = false
            }
            if (isJamSelesaiClicked) {
                endClockState.show()
                isJamSelesaiClicked = false
            }

            if (isFacilityClicked) {
                facilityState.show()
                isFacilityClicked = false
            }

            LaunchedEffect(Unit) {
                DataStore(context).getData.collect { (jMulai, jSelesai, tgl) ->
                    jMulaiValue = jMulai!!
                    jSelesaiValue = jSelesai!!
                    tanggalValue = tgl!!
                }
            }

            LaunchedEffect(Unit) {
                DataStore(context).getData.map {

                }
            }


            Calendar(calendarState = calendarState, dateValue = {
                tanggalValue = it.toString()
            })

            Clock(clockState = beginClockState, clockValue = {
                jMulaiValue = it
            })

            Clock(clockState = endClockState, clockValue = {
                jSelesaiValue = it
            })

            ListDialog(listState = facilityState, listDialogValue = {
                fasilitasValue = it
            }, roomData = roomsModelMain.item!!)

            OutlinedTextField(
                value = namaValue,
                onValueChange = { namaValue = it },
                label = { Text("Nama", style = MaterialTheme.typography.h3) },
                colors = textFieldColorsStyle,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondary
                    )
                },
                maxLines = 1,
                modifier = modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(spacerHeightValue))
            OutlinedTextField(
                value = nimValue,
                onValueChange = { nimValue = it },
                label = { Text("Nim", style = MaterialTheme.typography.h3) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = textFieldColorsStyle,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Pin,
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondary
                    )
                },
                maxLines = 1,
                modifier = modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(spacerHeightValue))
            OutlinedTextField(
                value = prodiValue,
                onValueChange = {
                    prodiValue = it
                },
                label = { Text("Prodi", style = MaterialTheme.typography.h3) },
                colors = textFieldColorsStyle,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ViewCozy,
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondary
                    )
                },
                maxLines = 1,
                modifier = modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(spacerHeightValue))
            OutlinedTextField(
                value = ruanganValue,
                onValueChange = { ruanganValue = it },
                label = { Text("Ruangan", style = MaterialTheme.typography.h3) },
                colors = textFieldColorsStyle,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.MeetingRoom,
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondary
                    )
                },
                maxLines = 1,
                modifier = modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(spacerHeightValue))
            OutlinedTextField(value = tanggalValue,
                onValueChange = {
                    calendarState.show()
                    tanggalValue = it
                },
                label = { Text("Tanggal", style = MaterialTheme.typography.h3) },
                colors = textFieldColorsStyle,
                readOnly = true,
                enabled = false,
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.CalendarToday,
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondary,
                        modifier = modifier.clickable { calendarState.show() })
                },
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusEvent { event ->
                        if (event.isFocused) isCalendarClicked = true
                    })
            Spacer(Modifier.height(spacerHeightValue))
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(value = jMulaiValue,
                    onValueChange = {
                        beginClockState.show()
                        jMulaiValue = it
                    },
                    label = { Text("Mulai", style = MaterialTheme.typography.h3) },
                    colors = textFieldColorsStyle,
                    readOnly = true,
                    enabled = false,
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Schedule,
                            contentDescription = null,
                            tint = MaterialTheme.colors.secondary,
                            modifier = modifier.clickable { beginClockState.show() })
                    },
                    maxLines = 1,
                    modifier = Modifier
                        .weight(1f)
                        .onFocusEvent { event ->
                            if (event.isFocused) isJamMulaiClicked = true
                        })
                Spacer(modifier = modifier.width(4.dp))
                OutlinedTextField(value = jSelesaiValue,
                    onValueChange = {
                        endClockState.show()
                        jSelesaiValue = it
                    },
                    label = { Text("Selesai", style = MaterialTheme.typography.h3) },
                    colors = textFieldColorsStyle,
                    enabled = false,
                    readOnly = true,
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.TimerOff,
                            contentDescription = null,
                            tint = MaterialTheme.colors.secondary,
                            modifier = modifier.clickable { endClockState.show() })
                    },
                    maxLines = 1,
                    modifier = Modifier
                        .weight(1f)
                        .onFocusEvent { event ->
                            if (event.isFocused) isJamSelesaiClicked = true
                        })
            }
            Spacer(Modifier.height(spacerHeightValue))
            OutlinedTextField(
                value = keperluanValue,
                onValueChange = {
                    keperluanValue = it
                },
                label = { Text("Keperluan", style = MaterialTheme.typography.h3) },
                colors = textFieldColorsStyle,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Workspaces,
                        contentDescription = null,
                        tint = MaterialTheme.colors.secondary
                    )
                },
                maxLines = 10,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
            )
            Spacer(Modifier.height(spacerHeightValue))
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButtonUI(unit = unit, unitValue = unitValue, unitValueCallBack = {
                    unitValue = it
                })
            }
            Spacer(Modifier.height(spacerHeightValue))
            penanggungJawab(
                dosenList = dosenModel,
                penanggungJawabValue = penanggungJawabValue, penanggungJawabCallback = {
                    penanggungJawabValue = it
                },
                textFieldColorsStyle = textFieldColorsStyle
            )
            if (confirmationDialog) {
                ConfirmationDialog(onDismiss = {
                    onActionClick()
                }, onConfirm = {
                    onPinjamRuangan.invoke(
                        PengajuanModel(
                            jmulai = jMulaiValue,
                            jselesai = jSelesaiValue,
                            nama = namaValue,
                            nim = nimValue,
                            prodi = prodiValue,
                            ruangan = ruanganValue,
                            tanggal = tanggalValue,
                            keperluan = keperluanValue,
                            unit = unitValue,
                            penanggungJawab = penanggungJawabValue,
                            fotoRuangan = roomsModelMain.item?.foto_ruangan
                        ), RoomsModelMain(
                            key = roomsModelMain.key, item = RoomsModel(
                                deskripsi_ruangan = roomsModelMain.item?.deskripsi_ruangan,
                                fasilitas_ruangan = roomsModelMain.item?.fasilitas_ruangan,
                                foto_ruangan = roomsModelMain.item?.foto_ruangan,
                                id_ruangan = roomsModelMain.item?.id_ruangan,
                                isLent = true,
                                lantai_ruangan = roomsModelMain.item?.lantai_ruangan,
                                nama_ruangan = roomsModelMain.item?.nama_ruangan
                            )
                        )
                    )
                    onSaveToHistory.invoke(
                        PeminjamanModel(
                            fasilitas = fasilitasValue,
                            jmulai = jMulaiValue,
                            jselesai = jSelesaiValue,
                            nama = namaValue,
                            nim = nimValue,
                            prodi = prodiValue,
                            ruangan = ruanganValue,
                            tanggal = tanggalValue
                        )
                    )
                    confirmationDialog = false
                }, pengajuanModel = PengajuanModel(
                    jmulai = jMulaiValue,
                    jselesai = jSelesaiValue,
                    nama = namaValue,
                    nim = nimValue,
                    prodi = prodiValue,
                    ruangan = ruanganValue,
                    tanggal = tanggalValue,
                    keperluan = keperluanValue,
                    unit = unitValue,
                    penanggungJawab = penanggungJawabValue,
                    fotoRuangan = roomsModelMain.item?.foto_ruangan
                )
                )
            }

            Spacer(Modifier.height(18.dp))

            Button(
                onClick = {
                    if (nimValue.isEmpty() || namaValue.isEmpty() || prodiValue.isEmpty() || ruanganValue.isEmpty() || tanggalValue.isEmpty() || jMulaiValue.isEmpty() || jSelesaiValue.isEmpty()) {
                        Toast.makeText(context, "Field cannot empty !", Toast.LENGTH_SHORT).show()
                    } else {
                        confirmationDialog = true
                    }
                },
                border = BorderStroke(1.dp, Color.White),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(6.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary)
            ) {
                if (pengajuanState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp),
                        backgroundColor = Color.White
                    )
                } else {
                    Text(
                        "Pinjam Ruangan", color = textColor, style = MaterialTheme.typography.body1
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            if (pengajuanState.data != null) {
                val coroutineScope = rememberCoroutineScope()
                DisposableEffect(Unit) {
                    val disposable = coroutineScope.launch {
                        delay(2000L) // Delay for 2000 milliseconds (2 seconds)
                        onActionClick()
                    }

                    onDispose {
                        disposable.cancel()
                    }
                }
                Text(
                    text = "Redirect..",
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.secondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun RadioButtonUI(
    unit: Map<UnitEnum, String>,
    unitValue: String,
    unitValueCallBack: (String) -> Unit
) {
    val unitKeys = unit.keys

    unitKeys.forEach {
        val isSelectedValue = unit.getValue(it) == unitValue
        val colors = RadioButtonDefaults.colors(
            selectedColor = MaterialTheme.colors.secondary,
            unselectedColor = MaterialTheme.colors.primary,
            disabledColor = Color.LightGray
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = isSelectedValue, colors = colors, onClick = {
                unitValueCallBack.invoke(unit.getValue(it))
            })
            Text(
                it.name, color = textColor, style = MaterialTheme.typography.body1,
                fontSize = 12.sp
            )
        }

    }
}

@Composable
fun penanggungJawab(
    dosenList: RealtimeDBDosenState,
    penanggungJawabValue: String,
    penanggungJawabCallback: (String) -> Unit,
    textFieldColorsStyle: TextFieldColors
) {
    var expanded by remember { mutableStateOf(false) }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column {
        OutlinedTextField(
            readOnly = true,
            value = penanggungJawabValue,
            onValueChange = {
                penanggungJawabCallback.invoke(it)
            },
            colors = textFieldColorsStyle,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                }
                .clickable { expanded = !expanded },
            label = { Text("Penanggung Jawab", style = MaterialTheme.typography.h3) },
            trailingIcon = {
                Icon(
                    icon,
                    "contentDescription",
                    Modifier.clickable { expanded = !expanded },
                    tint = MaterialTheme.colors.secondary
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            dosenList.data?.forEach { label ->
                DropdownMenuItem(onClick = {
                    penanggungJawabCallback.invoke(label?.nama_dosen!!)
                    expanded = false
                }) {
                    Text(
                        label?.nama_dosen!!, color = textColor, style = MaterialTheme.typography.body1,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }

}
