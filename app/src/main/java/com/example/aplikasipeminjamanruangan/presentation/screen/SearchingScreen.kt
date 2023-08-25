package com.example.aplikasipeminjamanruangan.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aplikasipeminjamanruangan.domain.model.PengajuanModel
import com.example.aplikasipeminjamanruangan.domain.model.RoomsMataKuliah
import com.example.aplikasipeminjamanruangan.domain.model.RoomsModelMain
import com.example.aplikasipeminjamanruangan.presentation.screen.home.CardFeature
import com.example.aplikasipeminjamanruangan.presentation.screen.home.ListRoomBasedOnFloor
import com.example.aplikasipeminjamanruangan.presentation.states.RealtimeDBGetPengajuanState
import com.example.aplikasipeminjamanruangan.presentation.states.RealtimeDBRoomsState
import com.example.aplikasipeminjamanruangan.presentation.states.RealtimeDbMataKuliahState
import com.example.aplikasipeminjamanruangan.presentation.utils.AnimateShimmer
import com.example.aplikasipeminjamanruangan.presentation.utils.CustomDialog
import com.example.aplikasipeminjamanruangan.presentation.utils.DataStore
import com.example.aplikasipeminjamanruangan.presentation.utils.textColor
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.AppViewModel
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.MataKuliahViewModel
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.PengajuanViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchingScreen(
    onNavBack: () -> Unit,
    appViewModel: AppViewModel,
    onHeadingToDetail: (RoomsModelMain) -> Unit,
    pengajuanViewModel: PengajuanViewModel,
    mataKuliahViewModel: MataKuliahViewModel,
    modifier: Modifier = Modifier
) {
    val getPengajuan = pengajuanViewModel.getPengajuan.collectAsStateWithLifecycle().value
    val roomsState by appViewModel.roomsState.collectAsStateWithLifecycle()
    val mataKuliahState by mataKuliahViewModel.mataKuliahState.collectAsStateWithLifecycle()

    var onSearchClicked by rememberSaveable {
        mutableStateOf(true)
    }

    var tanggalValue by rememberSaveable { mutableStateOf("") }
    var jMulaiValue by rememberSaveable { mutableStateOf("") }
    var jSelesaiValue by rememberSaveable { mutableStateOf("") }
    var isSplashScreenStart by rememberSaveable {
        mutableStateOf(false)
    }
    var reloadData by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
                .fillMaxSize()
        ) {
            val scope = rememberCoroutineScope()
            val context = LocalContext.current
            if (onSearchClicked) {
                CustomDialog(onDismiss = { onSearchClicked = false }, onConfirm = { data ->
                    tanggalValue = data[0]
                    jMulaiValue = data[1]
                    jSelesaiValue = data[2]
                    reloadData = true
                    onSearchClicked = false
                    scope.launch {
                        DataStore(context).saveData(jMulaiValue, jSelesaiValue, tanggalValue)
                    }
                })
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(color = textColor, text = buildAnnotatedString {
                    append("Hai, ")
                    withStyle(
                        style = SpanStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                    ) {
                        append("Mahasiswa PCR")
                    }
                })
                Spacer(modifier = modifier.weight(1f))
                Card(
                    elevation = (-20).dp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(18.dp))
                        .alpha(0.75f)
                        .height(35.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 12.dp, end = 4.dp)
                    ) {
                        Text(
                            text = "Cari Ruangan", fontSize = 14.sp,
                            style = MaterialTheme.typography.body1, color = Color.Black,
                            modifier = Modifier.clickable { onSearchClicked = true }
                        )
                        IconButton(onClick = { onSearchClicked = true }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search",
                                tint = Color.Black
                            )
                        }
                    }
                }
            }
//            TopAppBar(
//                onNavBack = onNavBack,
//                modifier = modifier,
//                bigText = "Ruangan",
//                smallText = "Cari",
//                onSearch = {
//                    onSearchClicked = true
//                },
//                onSearchAvailable = true
//            )
            Spacer(modifier = Modifier.height(16.dp))
            CardFeature(modifier = Modifier)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(top = 24.dp)
            ) {

                if (reloadData) {
                    when {
                        roomsState.isLoading -> {
                            isSplashScreenStart = false
                            AnimateShimmer(modifier = modifier, items = 200, height = 1000)
                        }

                        roomsState.data.isNullOrEmpty() -> {
                            isSplashScreenStart = true
                            NoDataAvailableLottie(isSplashScreenStart)
                        }

                        !roomsState.data.isNullOrEmpty() -> {
                            isSplashScreenStart = false
                            SpreadingDataSearch(
                                getPengajuan = getPengajuan,
                                roomsStates = roomsState,
                                mataKuliahState = mataKuliahState,
                                onHeadingToDetail = onHeadingToDetail,
                                jMulaiValue = jMulaiValue,
                                jSelesaiValue = jSelesaiValue,
                                tanggalValue = tanggalValue,
                                modifier = Modifier
                            )
                        }
                    }
                }
            }
        }
    }
}

fun getUnusedRooms(
    roomsModelList: List<RoomsModelMain?>?,
    pengajuanModelList: List<PengajuanModel?>?,
    roomsMataKuliahList: List<RoomsMataKuliah?>?,
    desiredJmulai: String,
    desiredJselesai: String,
    desiredTanggal: String
): List<RoomsModelMain?> {
//    val filterPengajuan = pengajuanModelList?.filter { pengajuan ->
//        pengajuan?.tanggal?.contains(desiredTanggal) == true && pengajuan?.jmulai?.contains(
//            desiredJmulai
//        ) == true && pengajuan?.jselesai?.contains(
//            desiredJselesai
//        ) == true
//    }
//
//
//    val filterRooms = roomsMataKuliahList?.filterNot { roomMataKuliah ->
//        roomMataKuliah?.field5 == convertTime(desiredJmulai) && roomMataKuliah.field6?.equals(
//            convertTime(desiredJselesai)
//        ) == true
//                && roomMataKuliah.field4?.equals(
//            convertDateStringtoDay(desiredTanggal)
//        ) == true
//    }

    return roomsModelList!!.filter { room ->
        !pengajuanModelList?.any { pengajuan ->
            pengajuan?.tanggal?.contains(desiredTanggal) == true && pengajuan?.ruangan?.contains(
                room?.item?.nama_ruangan!!
            ) == true && pengajuan?.jmulai?.contains(desiredJmulai) == true && pengajuan?.jselesai?.contains(
                desiredJselesai
            ) == true
        }!! && !roomsMataKuliahList?.any { roomMataKuliah ->
            "Ruang ${roomMataKuliah?.field3}" == room?.item?.nama_ruangan && roomMataKuliah?.field5?.contains(
                convertTime(desiredJmulai)
            ) == true && roomMataKuliah?.field6?.contains(convertTime(desiredJselesai)) == true && roomMataKuliah?.field4?.contains(
                convertDateStringtoDay(desiredTanggal)
            ) == true
        }!!
    }
}


//return roomsModelList!!.filter { room ->
//    !filterPengajuan?.any { pengajuan ->
//        pengajuan?.ruangan?.contains(
//            room?.item?.nama_ruangan!!
//        ) == true
//    }!! && !filterRooms?.any { roomData ->
//        "Ruang ${roomData?.field3}" == room?.item?.nama_ruangan
//    }!!
//}


fun convertTime(timeValue: String): String {
    return timeValue.replace(":", ".")
}

fun convertDateStringtoDay(dateString: String): String {
    val dateFormat = SimpleDateFormat("yy-MM-dd", Locale.ENGLISH)
    val date = dateFormat.parse(dateString)

    val calendar = Calendar.getInstance()
    if (date != null) {
        calendar.time = date
    }

    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

    val dayName = when (dayOfWeek) {
        Calendar.SUNDAY -> "Minggu"
        Calendar.MONDAY -> "Senin"
        Calendar.TUESDAY -> "Selasa"
        Calendar.WEDNESDAY -> "Rabu"
        Calendar.THURSDAY -> "Kamis"
        Calendar.FRIDAY -> "Jumat"
        Calendar.SATURDAY -> "Sabtu"
        else -> "Invalid day"
    }
    return dayName
}

@Composable
fun SpreadingDataSearch(
    getPengajuan: RealtimeDBGetPengajuanState,
    roomsStates: RealtimeDBRoomsState,
    mataKuliahState: RealtimeDbMataKuliahState,
    onHeadingToDetail: (RoomsModelMain) -> Unit,
    tanggalValue: String,
    jMulaiValue: String,
    jSelesaiValue: String,
    modifier: Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4), content = {
            items(
                getUnusedRooms(
                    roomsModelList = roomsStates.data,
                    pengajuanModelList = getPengajuan.data,
                    roomsMataKuliahList = mataKuliahState.data,
                    desiredJmulai = jMulaiValue,
                    desiredJselesai = jSelesaiValue,
                    desiredTanggal = tanggalValue
                )
            ) { data ->
                ListRoomBasedOnFloor(
                    item = data, onHeadingToDetail = onHeadingToDetail, modifier = modifier
                )
            }
        }, modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Start
    )
}

