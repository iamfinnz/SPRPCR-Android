package com.example.aplikasipeminjamanruangan.presentation.components.waiting

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.aplikasipeminjamanruangan.R
import com.example.aplikasipeminjamanruangan.domain.model.PengajuanModel


@Composable
fun ItemCardWaiting(
    item: PengajuanModel,
    onHeadToDetailWaiting: (PengajuanModel) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFDAE1E7),
        modifier = Modifier
            .height(210.dp)
            .padding(10.dp)
            .clickable {
                onHeadToDetailWaiting.invoke(item)
            },
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val textColor = Color.Black
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
                    Text(
                        text = if (item.pengajuanDiterima) "Diterima" else if (item.pengembalianDiterima) "Selesai" else "Belum Diterima",
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.h2,
                        color = if (item.pengajuanDiterima) MaterialTheme.colors.primary else if (item.pengembalianDiterima) Color(
                            0xFF00A86B
                        ) else Color(
                            0xFFAD1457
                        ),
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                item.ruangan?.let {
                    Text(
                        text = it,
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.h2,
                        fontWeight = FontWeight.SemiBold,
                        color = textColor
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Dipinjam untuk tgl:\n${item.tanggal}", fontSize = 14.sp,
                    color = textColor
                )

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Peminjam",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.h2,
                        color = textColor
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        imageVector = Icons.Filled.Person,
                        tint = Color(0xFFF6B266),
                        contentDescription = null
                    )
                    Text(
                        text = item.nama!!,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.h2,
                        color = textColor
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))
            }
            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(width = 100.dp, height = 140.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .height(100.dp)
                        .weight(3f),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.fotoRuangan)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.loading_img),
                    error = painterResource(id = R.drawable.ic_broken_image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}


