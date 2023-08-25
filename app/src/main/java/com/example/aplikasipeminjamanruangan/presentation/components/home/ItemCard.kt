package com.example.aplikasipeminjamanruangan.presentation.components.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.aplikasipeminjamanruangan.R
import com.example.aplikasipeminjamanruangan.domain.model.RoomsModelMain

@Composable
fun ItemCard(
    item: RoomsModelMain, onHeadingToDetail: (RoomsModelMain) -> Unit, modifier: Modifier
) {
    Card(
        backgroundColor = if(item.item?.isLent!!) Color.Black else Color.White,
        modifier = Modifier
            .height(140.dp)
            .width(95.dp)
            .padding(4.dp)
            .clickable( onClick = {
                onHeadingToDetail(item)
            })
            .fillMaxWidth(), shape = RoundedCornerShape(16.dp),
        elevation = 8.dp
    ) {
        Box {
            Column {
                AsyncImage(
                    modifier = Modifier
                        .height(100.dp)
                        .weight(3f),
                    model = ImageRequest.Builder(LocalContext.current).data(item.item.foto_ruangan)
                        .crossfade(true).build(),
                    placeholder = painterResource(id = R.drawable.loading_img),
                    error = painterResource(id = R.drawable.ic_broken_image),
                    contentDescription = item.item.nama_ruangan,
                    contentScale = ContentScale.Crop
                )

                Text(
                    color = Color.Black,
                    text = "${item.item.nama_ruangan!!}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}