package com.example.e2logypracticaltest

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiResponse(
        @SerializedName("Status") val status: Int,
        @SerializedName("Result") val result: List<Store>,
        @SerializedName("Message") val message: String
)

data class Store(
        @SerializedName("DealID") val dealID: String,
        @SerializedName("Title") val title: String,
        @SerializedName("Image") val image: String,
        @SerializedName("CategName") val cateName: String,
        @SerializedName("ValidityStart") val startDate: String,
        @SerializedName("ValidityEnd") val endDate: String,
        @SerializedName("Description") val description: String
) : Serializable
