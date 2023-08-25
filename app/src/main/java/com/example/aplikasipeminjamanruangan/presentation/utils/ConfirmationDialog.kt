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
import androidx.compose.foundation.shape.CircleShape
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
import com.example.aplikasipeminjamanruangan.domain.model.PengajuanModel
import com.example.aplikasipeminjamanruangan.presentation.components.Calendar
import com.example.aplikasipeminjamanruangan.presentation.components.Clock
import com.maxkeppeker.sheets.core.models.base.rememberSheetState

@Composable
fun ConfirmationDialog(
    onDismiss: () -> Unit, onConfirm:() -> Unit, pengajuanModel: PengajuanModel
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
                .height(350.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Apakah kamu mau meminjam ruangan ini ?",
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center,
                    color = textColor,
                    fontSize = 19.sp,
                    modifier = Modifier.fillMaxWidth()
                )
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
                        Text(text = tanggalValue.ifEmpty { "Ruangan:" },
                            color = if (tanggalValue.isEmpty()) textColor else textColor)
                        Spacer(Modifier.weight(1f))
                        Text(text = tanggalValue.ifEmpty { "${pengajuanModel.ruangan}" },
                            color = if (tanggalValue.isEmpty()) textColor else textColor)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = tanggalValue.ifEmpty { "Tanggal:" },
                            color = if (tanggalValue.isEmpty()) textColor else textColor)
                        Spacer(Modifier.weight(1f))
                        Text(text = tanggalValue.ifEmpty { "${pengajuanModel.tanggal}" },
                            color = if (tanggalValue.isEmpty()) textColor else textColor)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = tanggalValue.ifEmpty { "Mulai:" },
                            color = if (tanggalValue.isEmpty()) textColor else textColor)
                        Spacer(Modifier.weight(1f))
                        Text(text = tanggalValue.ifEmpty { "${pengajuanModel.jmulai}" },
                            color = if (tanggalValue.isEmpty()) textColor else textColor)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = tanggalValue.ifEmpty { "Selesai:" },
                            color = if (tanggalValue.isEmpty()) textColor else textColor)
                        Spacer(Modifier.weight(1f))
                        Text(text = tanggalValue.ifEmpty { "${pengajuanModel.jselesai}" },
                            color = if (tanggalValue.isEmpty()) textColor else textColor)
                    }
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(text = "Keperluan:",
//                            color = if (tanggalValue.isEmpty()) textColor else textColor)
//                        Spacer(Modifier.weight(1f))
//                        Text(text = tanggalValue.ifEmpty { "${pengajuanModel.keperluan}" },
//                            color = if (tanggalValue.isEmpty()) textColor else textColor)
//                    }
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(text = "Unit:",
//                            color = if (tanggalValue.isEmpty()) textColor else textColor)
//                        Spacer(Modifier.weight(1f))
//                        Text(text = tanggalValue.ifEmpty { "${pengajuanModel.unit}" },
//                            color = if (tanggalValue.isEmpty()) textColor else textColor)
//                    }
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(text = "Penanggung Jawab:",
//                            color = if (tanggalValue.isEmpty()) textColor else textColor)
//                        Spacer(Modifier.weight(1f))
//                        Text(text = tanggalValue.ifEmpty { "${pengajuanModel.penanggungJawab}" },
//                            color = if (tanggalValue.isEmpty()) textColor else textColor)
//                    }
                    Divider()
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start=42.dp, end = 42.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            onConfirm()
                        }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                        ), modifier = Modifier, shape = CircleShape
                    ) {
                        Text(
                            text = "Ya",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Button(
                        onClick = {
                            onDismiss()
                        }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                        ), modifier = Modifier, shape = CircleShape
                    ) {
                        Text(
                            text = "Tidak",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }

            }
        }
    }
}