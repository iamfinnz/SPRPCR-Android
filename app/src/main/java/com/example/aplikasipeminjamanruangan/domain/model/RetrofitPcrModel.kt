package com.example.aplikasipeminjamanruangan.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RetrofitPcrModel(
    @SerializedName("status")
    var status: Int? = 0,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("items")
    var items: List<ItemModel>
) : Parcelable