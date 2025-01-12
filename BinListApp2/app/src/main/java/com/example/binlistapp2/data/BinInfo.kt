package com.example.binlistapp2.data

import kotlinx.serialization.Serializable

@Serializable
data class BinInfo(
    val number: Number,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val country: Country,
    val bank: Bank
)

@Serializable
data class Number(
    val length: Int?,
    val luhn: Boolean?
)

@Serializable
data class Country(
    val numeric: String?,
    val alpha2: String?,
    val name: String?,
    val emoji: String?,
    val currency: String?,
    val latitude: Double?,
    val longitude: Double?
)

@Serializable
data class Bank(
    val name: String?,
    val url: String?,
    val phone: String?,
    val city: String?
)