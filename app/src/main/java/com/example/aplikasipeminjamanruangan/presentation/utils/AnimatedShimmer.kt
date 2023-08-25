package com.example.aplikasipeminjamanruangan.presentation.utils

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun AnimateShimmer(modifier: Modifier, items: Int = 50, height: Int = 285) {
    val shimmerColors = listOf(
        Color.Gray.copy(alpha = 0.6f),
        Color.Gray.copy(alpha = 0.2f),
        Color.Gray.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, y = translateAnimation.value)
    )
    LazyVerticalGrid(
        columns = GridCells.Adaptive(80.dp),
        content = {
            items(items) {
                ItemCard(modifier = Modifier, brush = brush)
            }
        },
        userScrollEnabled = false,
        modifier = Modifier.height(height.dp), horizontalArrangement = Arrangement.Start
    )
}

@Composable
fun AnimateWaitingListShimmer(modifier: Modifier) {
    val shimmerColors = listOf(
        Color.Gray.copy(alpha = 0.6f),
        Color.Gray.copy(alpha = 0.2f),
        Color.Gray.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, y = translateAnimation.value)
    )


    LazyColumn(userScrollEnabled = false) {
        items(30) {
            ItemCardWaiting(brush = brush)
        }
    }
}


@Composable
fun ItemCardWaiting(
    brush: Brush
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFDAE1E7),
        modifier = Modifier
            .height(210.dp)
            .padding(10.dp)
            .background(brush),
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f),
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.wrapContentSize(),
                    color = Color(0xFFD1D5E1)
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(24.dp)
                            .fillMaxWidth(fraction = 0.6f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(brush)
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Spacer(
                    modifier = Modifier
                        .height(22.dp)
                        .fillMaxWidth(fraction = 0.6f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(brush)
                )

                Spacer(modifier = Modifier.height(2.dp))

                Spacer(
                    modifier = Modifier
                        .height(18.dp)
                        .fillMaxWidth(fraction = 0.6f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(brush)
                )

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth(fraction = 0.6f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(brush)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Spacer(
                        modifier = Modifier
                            .width(4.dp)
                            .fillMaxWidth(fraction = 0.6f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(brush)
                    )
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth(fraction = 0.6f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(brush)
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

//                OutlinedButton(
//                    shape = RoundedCornerShape(8.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        contentColor = Color.Black,
//                        backgroundColor = Color.White
//                    ),
//                    onClick = { /*TODO*/ }
//                ) {
//                    Text(
//                        text = "Detail",
//                        fontSize = 11.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        style = MaterialTheme.typography.h2
//                    )
//                }
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(width = 100.dp, height = 140.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .height(100.dp)
                        .weight(3f)
                        .background(brush),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}


@Composable
fun ItemCard(
    brush: Brush, modifier: Modifier
) {
    Card(
        modifier = Modifier
            .height(140.dp)
            .width(95.dp)
            .padding(4.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = 14.dp
    ) {
        Box {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    modifier = Modifier
                        .height(100.dp)
                        .background(brush),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("")
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(8.dp))
                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                        .fillMaxWidth(fraction = 0.6f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(brush)
                )
            }
        }
    }
}