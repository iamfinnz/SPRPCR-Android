package com.example.aplikasipeminjamanruangan.presentation.components

import androidx.compose.runtime.Composable
import com.example.aplikasipeminjamanruangan.domain.model.RoomsModel
import com.maxkeppeker.sheets.core.models.base.SheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import com.maxkeppeler.sheets.list.ListDialog
import com.maxkeppeler.sheets.list.models.ListOption
import com.maxkeppeler.sheets.list.models.ListSelection
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun Calendar(calendarState: SheetState, dateValue: (LocalDate) -> Unit) {
    CalendarDialog(state = calendarState, config = CalendarConfig(
        monthSelection = true, yearSelection = true, style = CalendarStyle.MONTH
    ), selection = CalendarSelection.Date { date ->
        dateValue.invoke(date)
    })
}

@Composable
fun Clock(clockState: SheetState, clockValue: (String) -> Unit) {
    ClockDialog(state = clockState,
        config = ClockConfig(
            is24HourFormat = true
        ),
        selection = ClockSelection.HoursMinutes { hours, minutes ->
            val value = LocalTime.of(hours, minutes)
            clockValue.invoke(value.toString())
        })
}

@Composable
fun ListDialog(listState: SheetState, listDialogValue: (String) -> Unit, roomData: RoomsModel) {
    val sentence = roomData.fasilitas_ruangan
    val wordsArray = sentence?.split(" ")?.toList()
    val charactersToRemove = "[,! ]"

    val options = wordsArray!!.map { word ->
        ListOption(
            titleText = word!!.replace(Regex(charactersToRemove), "")
        )
    }

    ListDialog(
        state = listState,
        selection = ListSelection.Multiple(
            showCheckBoxes = true,
            options = options,
        ) { _, selectedOptions ->
            val output = selectedOptions.map {
                it.titleText
            }.joinToString(separator = ", ")
            listDialogValue.invoke(output)
        }
    )
}