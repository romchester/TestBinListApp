package com.example.binlistapp2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bin_info_history")
data class BinInfoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bin: String,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val countryName: String?,
    val bankName: String?,
    val bankUrl: String?,
    val bankPhone: String?,
    val bankCity: String?
)