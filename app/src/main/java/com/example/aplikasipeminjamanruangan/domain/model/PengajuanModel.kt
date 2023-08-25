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
    val pengajuanDiterima: Boolean = false,
    val pengembalianDiterima: Boolean = false,
    val keperluan: String? = null,
    val unit: String? = null,
    val penanggungJawab: String? = null,
    val fotoRuangan: String? = null
) : Parcelable