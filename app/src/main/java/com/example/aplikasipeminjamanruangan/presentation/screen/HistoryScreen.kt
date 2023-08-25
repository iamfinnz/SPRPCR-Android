package com.example.aplikasipeminjamanruangan.presentation.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aplikasipeminjamanruangan.presentation.components.history.ItemCardHistory
import com.example.aplikasipeminjamanruangan.presentation.states.RealtimeDBGetPeminjamanState
import com.example.aplikasipeminjamanruangan.presentation.utils.AnimateWaitingListShimmer
import com.example.aplikasipeminjamanruangan.presentation.utils.textColor
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.PeminjamanViewModel

@Composable
fun HistoryScreen(peminjamanViewModel: PeminjamanViewModel) {
    val getPeminjaman = peminjamanViewModel.getPeminjaman.collectAsStateWithLifecycle().value
    var isSplashScreenStart by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
            .fillMaxSize()

    ) {
        Text(color = textColor, text = buildAnnotatedString {
            append("History ")
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            ) {
                append("List")
            }
        })

        when {
            getPeminjaman.isLoading -> {
                isSplashScreenStart = false
                AnimateWaitingListShimmer(modifier = Modifier)
            }

            getPeminjaman.data.isNullOrEmpty() -> {
                isSplashScreenStart = true
                NoDataAvailableLottie(isSplashScreenStart)
            }

            !getPeminjaman.data.isNullOrEmpty() -> {
                isSplashScreenStart = false
                SpreadingData(getPeminjaman = getPeminjaman)
            }

            getPeminjaman.errMsg!!.isNotEmpty() -> {
                Text(text = getPeminjaman.errMsg)
            }
        }
    }
}

@Composable
fun SpreadingData(
    getPeminjaman: RealtimeDBGetPeminjamanState,
) {
    var number = 0
    LazyColumn {
        items(getPeminjaman.data!!) { data ->
            number++
            ItemCardHistory(item = data!!, itemNumber = number )
        }
    }
}
