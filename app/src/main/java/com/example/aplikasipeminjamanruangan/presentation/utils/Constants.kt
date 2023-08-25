package com.example.aplikasipeminjamanruangan.presentation.utils

import androidx.compose.ui.graphics.Color

const val FIRST_FLOOR = "Lantai 1"
const val SECOND_FLOOR = "Lantai 2"
const val THIRD_FLOOR = "Lantai 3"

// Firebase Realtime Database
const val DB_ROOMS = "rooms"
const val DB_PENGAJUAN = "pengajuan"
const val DB_PEMINJAMAN = "peminjaman"
const val DB_MATAKULIAH = "matakuliah"
const val DB_DOSEN = "dosen"

// Swipping collapsing layout state
enum class SwipingStates {
    //our own enum class for stoppages e.g. expanded and collapsed
    EXPANDED, COLLAPSED
}

// Floating Action Button
enum class MultiFloatingState{
    EXPANDED, COLLAPSED
}

// List of label

// Image Detection URL
const val BASE_URL_IMAGE_DETECTION = "http://103.193.176.122:5000"
const val BASE_URL_PCR = "https://v2.api.pcr.ac.id"
const val COLLECTION = "mahasiswa"

const val URL_PCR = "URL PCR"
const val URL_IMAGE_DETECTION = "URL IMAGE DETECTION"

val textColor = Color.Black
