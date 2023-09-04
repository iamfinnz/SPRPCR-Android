package com.example.aplikasipeminjamanruangan.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PengajuanModel(
    val jmulai: String? = null,
    val jselesai: String? = null,
    val nama: String? = null,
    val nim: String? = null,
    val prodi: String? = null,
    val ruangan: String? = null,
    val tanggal: String? = null,
    val pengajuanDiterima: String? = "Belum Diterima",
    val pengembalianDiterima: String? = "Belum Dikembalikan",
    val keperluan: String? = null,
    val unit: String? = null,
    val penanggungJawab: String? = null,
    val catatan: String? = null,
    val fotoRuangan: String? = null
) : Parcelable