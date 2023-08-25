package com.example.aplikasipeminjamanruangan.presentation.states

import com.example.aplikasipeminjamanruangan.domain.model.ItemModel
import com.example.aplikasipeminjamanruangan.domain.model.RetrofitImageModel
import com.example.aplikasipeminjamanruangan.domain.model.RetrofitPcrModel

data class RetrofitTextDetectionState(
    val data: RetrofitImageModel? = null,
    val isLoading: Boolean = false,
    val errMsg: String? = null
)

data class RetrofitNimValidation(
    val data: List<ItemModel>? = null,
    val isLoading: Boolean = false,
    val errMsg: String? = null
)