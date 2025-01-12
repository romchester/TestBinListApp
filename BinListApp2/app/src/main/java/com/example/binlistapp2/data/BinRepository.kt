package com.example.binlistapp2.data

import kotlinx.coroutines.flow.Flow

interface BinRepository {
    suspend fun getBinInfo(bin: String): BinInfo?
}