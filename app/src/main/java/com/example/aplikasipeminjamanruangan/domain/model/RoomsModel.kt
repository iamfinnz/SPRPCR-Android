package com.example.aplikasipeminjamanruangan.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoomsModel(
    var deskripsi_ruangan: String? =null,
    var fasilitas_ruangan: String? = null,
    var foto_ruangan: String? = null,
    var id_ruangan: Int? = 0,
    var isLent: Boolean? = false,
    var lantai_ruangan: String? = null,
    var nama_ruangan: String? = null
): Parcelable