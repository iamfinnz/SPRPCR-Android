package com.example.aplikasipeminjamanruangan.presentation.utils

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.aplikasipeminjamanruangan.presentation.components.Calendar
import com.example.aplikasipeminjamanruangan.presentation.components.Clock
import com.maxkeppeker.sheets.core.models.base.rememberSheetState

@Composable
fun CustomDialog(
    onDismiss: () -> Unit, onConfirm: (List<String>) -> Unit
) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        }, properties = DialogProperties(
            usePlatformDefaultWidth = true
        )
    ) {
        var tanggalValue by rememberSaveable { mutableStateOf("") }
        var jMulaiValue by rememberSaveable { mutableStateOf("") }
        var jSelesaiValue by rememberSaveable { mutableStateOf("") }

        val calendarState = rememberSheetState()
        val beginClockState = rememberSheetState()
        val endClockState = rememberSheetState()
        val context = LocalContext.current

        Calendar(calendarState = calendarState, dateValue = {
            tanggalValue = it.toString()
        })

        Clock(clockState = beginClockState, clockValue = {
            jMulaiValue = it
        })

        Clock(clockState = endClockState, clockValue = {
            jSelesaiValue = it
        })

        Card(
            elevation = 5.dp,
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
//                Text(
//                    text = "Mau cari ruangan yang mana ?",
//                    style = MaterialTheme.typography.h2,
//                    textAlign = TextAlign.Center,
//                    color = textColor,
//                    fontSize = 19.sp,
//                    modifier = Modifier.fillMaxWidth()
//                )
                Spacer(Modifier.height(18.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier
                        .padding(start = 25.dp, end = 50.dp)
                        .fillMaxWidth()

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = null,
                            tint = textColor,
                            modifier = Modifier
                                .clickable { calendarState.show() }
                                .height(30.dp)
                                .width(30.dp))
                        Spacer(Modifier.weight(1f))
                        Text(text = tanggalValue.ifEmpty { "pilih" },
                            color = if (tanggalValue.isEmpty()) Color.LightGray else textColor,
                            modifier = Modifier.clickable { calendarState.show() })
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Schedule,
                            contentDescription = null,
                            tint = textColor,
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp)
                        )
                        Spacer(Modifier.weight(1f))
                        Text(text = jMulaiValue.ifEmpty { "pilih" },
                            color = if (jMulaiValue.isEmpty()) Color.LightGray else textColor,
                            modifier = Modifier.clickable { beginClockState.show() })
                        Icon(imageVector = Icons.Filled.ExpandMore,
                            contentDescription = null,
                            tint = textColor,
                            modifier = Modifier.clickable { beginClockState.show() })

                        Text(text = jSelesaiValue.ifEmpty { "pilih" },
                            color = if (jSelesaiValue.isEmpty()) Color.LightGray else textColor,
                            modifier = Modifier.clickable { endClockState.show() })
                        Icon(imageVector = Icons.Filled.ExpandMore,
                            contentDescription = null,
                            tint = textColor,
                            modifier = Modifier.clickable { endClockState.show() })
                    }
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Icon(imageVector = Icons.Filled.TimerOff,
//                            contentDescription = null,
//                            tint = textColor,
//                            modifier = Modifier.clickable { endClockState.show() })
//                        Text(
//                            text = "Selesai",
//                            fontWeight = FontWeight.Bold,
//                            color = textColor, modifier = Modifier.padding(start = 8.dp)
//                        )
//                        Spacer(Modifier.weight(1f))
//                        Text(
//                            text = jSelesaiValue.ifEmpty { "tekan disini" },
//                            color = if (jSelesaiValue.isEmpty()) Color.LightGray else textColor,
//                            modifier = Modifier.clickable { endClockState.show() }
//                        )
//                    }
                    Divider()
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
//                    Button(
//                        onClick = {
//                            onDismiss()
//                        }, colors = ButtonDefaults.buttonColors(
//                            backgroundColor = MaterialTheme.colors.primary,
//                        ), modifier = Modifier, shape = CircleShape
//                    ) {
//                        Text(
//                            text = "Cancel",
//                            style = MaterialTheme.typography.h6,
//                            fontWeight = FontWeight.Bold,
//                            textAlign = TextAlign.Center,
//                            color = Color.White,
//                            fontSize = 14.sp
//                        )
//                    }
                    Button(
                        onClick = {
                            if (tanggalValue.isEmpty() || jMulaiValue.isEmpty() || jSelesaiValue.isEmpty()) {
                                Toast.makeText(context, "Field cannot empty !", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                val data = listOf(tanggalValue, jMulaiValue, jSelesaiValue)
                                onConfirm.invoke(data)
                            }
                        }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                        ), modifier = Modifier.width(200.dp), shape = RectangleShape
                    ) {
                        Text(
                            text = "Cari Ruangan",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 15.sp
                        )
                    }
                }

            }
        }
    }
}