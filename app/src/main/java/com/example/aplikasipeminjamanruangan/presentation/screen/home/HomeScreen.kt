package com.example.aplikasipeminjamanruangan.presentation.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aplikasipeminjamanruangan.domain.model.RoomsModelMain
import com.example.aplikasipeminjamanruangan.presentation.components.home.ItemCard
import com.example.aplikasipeminjamanruangan.presentation.states.RealtimeDBRoomsState
import com.example.aplikasipeminjamanruangan.presentation.utils.AnimateShimmer
import com.example.aplikasipeminjamanruangan.presentation.utils.FIRST_FLOOR
import com.example.aplikasipeminjamanruangan.presentation.utils.SECOND_FLOOR
import com.example.aplikasipeminjamanruangan.presentation.utils.THIRD_FLOOR
import com.example.aplikasipeminjamanruangan.presentation.utils.textColor
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.AppViewModel


@Composable
fun HomeScreen(
    appViewModel: AppViewModel,
    onHeadingToDetail: (RoomsModelMain) -> Unit,
    onSearchRooms: () -> Unit,
    modifier: Modifier
) {
    val roomsState by appViewModel.roomsState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
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
                    append("Mahasiswa")
                }
            })
            Spacer(modifier = modifier.weight(1f))
            Card(
                elevation = (-20).dp,
                modifier = Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .alpha(0.75f)
                    .height(35.dp)
                    .clickable { onSearchRooms() }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 12.dp, end = 4.dp)
                ) {
                    Text(
                        text = "Cari Ruangan", fontSize = 14.sp,
                        style = MaterialTheme.typography.body1, color = Color.Black,
                    )
                    IconButton(onClick = { onSearchRooms() }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = Color.Black
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        CardFeature(modifier = Modifier)
        Spacer(modifier = Modifier.height(8.dp))
        when {
            roomsState.isLoading -> {
                FloorDescription(floorName = "I")
                AnimateShimmer(modifier = modifier)
                FloorDescription(floorName = "II")
                AnimateShimmer(modifier = modifier)
                FloorDescription(floorName = "III")
                AnimateShimmer(modifier = modifier)
            }

            roomsState.data.isNullOrEmpty() -> {

            }

            !roomsState.data.isNullOrEmpty() -> {
                FloorDescription(floorName = "I")
                SpreadingData(
                    roomsState = roomsState,
                    floor = FIRST_FLOOR,
                    onHeadingToDetail = onHeadingToDetail,
                    modifier = modifier
                )
                FloorDescription(floorName = "II")
                SpreadingData2(
                    roomsState = roomsState,
                    floor = SECOND_FLOOR,
                    onHeadingToDetail = onHeadingToDetail,
                    modifier = modifier
                )
                FloorDescription(floorName = "III")
                SpreadingData2(
                    roomsState = roomsState,
                    floor = THIRD_FLOOR,
                    onHeadingToDetail = onHeadingToDetail,
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun SpreadingData(
    roomsState: RealtimeDBRoomsState,
    onHeadingToDetail: (RoomsModelMain) -> Unit,
    floor: String = "",
    modifier: Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4), content = {
            items(roomsState.data!!) { data ->
                if (data?.item?.lantai_ruangan.equals(floor)) {
                    ListRoomBasedOnFloor(
                        item = data,
                        onHeadingToDetail = onHeadingToDetail,
                        modifier = modifier
                    )
                }
            }
        },
        modifier = Modifier.height(285.dp), horizontalArrangement = Arrangement.Start
    )
}

@Composable
fun SpreadingData2(
    roomsState: RealtimeDBRoomsState,
    onHeadingToDetail: (RoomsModelMain) -> Unit,
    floor: String,
    modifier: Modifier
) {
    LazyRow {
        items(roomsState.data!!) { data ->
            if (data?.item?.lantai_ruangan.equals(floor)) {
                ListRoomBasedOnFloor(
                    item = data,
                    onHeadingToDetail = onHeadingToDetail,
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun CardFeature(modifier: Modifier) {
    Card(
        modifier = modifier
            .clip(
                CutCornerShape(
                    topStart = 8.dp,
                    topEnd = 0.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 8.dp
                )
            )
            .fillMaxWidth(),
        elevation = 20.dp,
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        Text(
            text = "\"Mau pinjam Ruangan yang mana ?\"",
            fontSize = 16.sp,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = modifier.padding(12.dp)
        )
    }
}


@Composable
fun ListRoomBasedOnFloor(
    item: RoomsModelMain?,
    onHeadingToDetail: (RoomsModelMain) -> Unit,
    modifier: Modifier
) {
    if (item != null) {
        ItemCard(
            item = item,
            onHeadingToDetail = onHeadingToDetail,
            modifier = modifier
        )
    }
}

@Composable
fun FloorDescription(floorName: String) {
    Spacer(modifier = Modifier.size(3.dp))
    Text(
        text = "Lantai $floorName",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = textColor
    )
}

