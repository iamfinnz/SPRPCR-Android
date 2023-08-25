package com.example.aplikasipeminjamanruangan.presentation.components.history

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplikasipeminjamanruangan.domain.model.PeminjamanModel
import com.example.aplikasipeminjamanruangan.presentation.screen.home.ItemButton

@Composable
fun ItemCardHistory(
    item: PeminjamanModel,
    itemNumber: Int
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            backgroundColor = Color(0xFFDAE1E7),
            elevation = 10.dp,
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp)),
        ) {
            Text(
                text = "$itemNumber",
                fontSize = 14.sp,
                style = MaterialTheme.typography.h2,
                fontWeight = FontWeight.Bold,
                color = Color(
                    0xFFAD1457
                ),
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
            )
        }
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFDAE1E7),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            elevation = 10.dp
        ) {
            Column(
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
                    .padding(start = 12.dp, top = 8.dp, bottom = 8.dp, end = 12.dp)
            ) {
                val textColor = Color.Black
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.wrapContentSize(),
                        color = Color(0xFFD1D5E1)
                    ) {
                        Text(
                            text = "${item.ruangan}",
                            fontSize = 14.sp,
                            style = MaterialTheme.typography.h2,
                            fontWeight = FontWeight.Bold,
                            color = Color(
                                0xFFAD1457
                            ),
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

//                Text(
//                    text = item.ruangan!!,
//                    fontSize = 20.sp,
//                    style = MaterialTheme.typography.h2,
//                    fontWeight = FontWeight.SemiBold,
//                    color = textColor
//                )

                    Text(
                        text = "${item.tanggal}", fontSize = 14.sp, color = textColor
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ItemButton(
                        onClick = { expanded = !expanded },
                        onExpanded = expanded,
                        modifier = Modifier
                    )
                }
                if (expanded) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Peminjam: ",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.h2,
                            color = com.example.aplikasipeminjamanruangan.presentation.utils.textColor
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Icon(
                            imageVector = Icons.Filled.Person,
                            tint = Color(0xFFF6B266),
                            contentDescription = null
                        )
                        Text(
                            text = "${item.nama}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.h2,
                            color = com.example.aplikasipeminjamanruangan.presentation.utils.textColor
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Nim: ",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.h2,
                            color = com.example.aplikasipeminjamanruangan.presentation.utils.textColor
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${item.nim}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.h2,
                            color = com.example.aplikasipeminjamanruangan.presentation.utils.textColor
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Fasilitas: ",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.h2,
                            color = com.example.aplikasipeminjamanruangan.presentation.utils.textColor
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "${item.fasilitas}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            style = MaterialTheme.typography.h2,
                            color = com.example.aplikasipeminjamanruangan.presentation.utils.textColor
                        )
                    }
                }
            }
        }
    }
}