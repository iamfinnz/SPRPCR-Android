package com.example.aplikasipeminjamanruangan.presentation.screen

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.aplikasipeminjamanruangan.R
import com.example.aplikasipeminjamanruangan.domain.model.PengajuanModel
import com.example.aplikasipeminjamanruangan.presentation.components.waiting.ItemCardWaiting
import com.example.aplikasipeminjamanruangan.presentation.states.RealtimeDBGetPengajuanState
import com.example.aplikasipeminjamanruangan.presentation.utils.AnimateWaitingListShimmer
import com.example.aplikasipeminjamanruangan.presentation.utils.textColor
import com.example.aplikasipeminjamanruangan.presentation.viewmodel.PengajuanViewModel

@Composable
fun WaitingScreen(
    pengajuanViewModel: PengajuanViewModel,
    onHeadToDetailWaiting: (PengajuanModel) -> Unit
) {
    val getPengajuan = pengajuanViewModel.getPengajuan.collectAsStateWithLifecycle().value
    var isSplashScreenStart by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
            .fillMaxSize(),
    ) {
        Text(color = textColor, text = buildAnnotatedString {
            append("Status ")
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            ) {
                append("Peminjaman")
            }
        })

        when {
            getPengajuan.isLoading -> {
                isSplashScreenStart = false
                AnimateWaitingListShimmer(modifier = Modifier)
            }

            getPengajuan.data.isNullOrEmpty() -> {
                isSplashScreenStart = true
                NoDataAvailableLottie(isSplashScreenStart)
            }

            !getPengajuan.data.isNullOrEmpty() -> {
                isSplashScreenStart = false
                SpreadingData(
                    getPengajuan = getPengajuan,
                    onHeadToDetailWaiting = onHeadToDetailWaiting
                )
            }

            getPengajuan.errMsg!!.isNotEmpty() -> {
                Text(text = getPengajuan.errMsg)
            }
        }
    }
}

@Composable
fun NoDataAvailableLottie(isSplashScreenStart: Boolean) {
    val animEndDelay = 1300
    val alphaAnim = animateFloatAsState(
        targetValue = if (isSplashScreenStart) 1f else 0f,
        animationSpec = tween(
            durationMillis = animEndDelay
        )
    )

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_data))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isSplashScreenStart,
        restartOnPlay = true
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .alpha(alphaAnim.value)
                .width(320.dp)
                .height(320.dp)
        )
        Text(
            color = textColor,
            text = "No data Available",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SpreadingData(
    onHeadToDetailWaiting: (PengajuanModel) -> Unit,
    getPengajuan: RealtimeDBGetPengajuanState
) {
    LazyColumn {
        items(getPengajuan.data!!) { data ->
            ItemCardWaiting(item = data!!, onHeadToDetailWaiting = onHeadToDetailWaiting)
        }
    }
    Log.d("Ilham", getPengajuan.toString())
}
