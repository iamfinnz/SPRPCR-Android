package com.example.aplikasipeminjamanruangan.presentation.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.aplikasipeminjamanruangan.R
import com.example.aplikasipeminjamanruangan.presentation.navigation.Home
import com.example.aplikasipeminjamanruangan.presentation.navigation.HomeSearch
import com.example.aplikasipeminjamanruangan.presentation.utils.textColor
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun SplashScreen(navController: NavHostController, modifier: Modifier) {
    val splashEndDelay = 2200.milliseconds
    val animEndDelay = 1300

    var isSplashScreenStart by rememberSaveable {
        mutableStateOf(false)
    }

    val alphaAnim = animateFloatAsState(
        targetValue = if (isSplashScreenStart) 1f else 0f,
        animationSpec = tween(
            durationMillis = animEndDelay
        )
    )

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.history))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isSplashScreenStart,
    )

    LaunchedEffect(key1 = true) {
        isSplashScreenStart = true
        delay(splashEndDelay)
        navController.popBackStack()
        navController.navigate(HomeSearch.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        LottieAnimation(
//            composition = composition,
//            progress = { progress },
//            modifier = modifier.alpha(alphaAnim.value)
//        )
        Image(painter = painterResource(R.drawable.logo_psti), contentDescription = "Logo PSTI", modifier = Modifier.width(300.dp).height(300.dp) )
        Image(painter = painterResource(R.drawable.logo_pcr), contentDescription = "Logo PCR", modifier = Modifier.width(200.dp).height(25.dp) )

//        Text(
//            text = stringResource(id = R.string.splash_text), style = MaterialTheme.typography.h2,
//            color = textColor,
//            modifier = modifier.alpha(alphaAnim.value)
//        )
//        Text(
//            text = stringResource(id = R.string.splash_caption),
//            style = MaterialTheme.typography.caption,
//            color = textColor,
//            modifier = modifier.alpha(alphaAnim.value)
//        )
    }
}