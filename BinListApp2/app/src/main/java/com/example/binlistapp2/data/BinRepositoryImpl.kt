package com.example.binlistapp2.data

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class BinRepositoryImpl @Inject constructor(private val database: AppDatabase) : BinRepository {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val binInfoAdapter = moshi.adapter(BinInfo::class.java)

    private val client = OkHttpClient()

    override suspend fun getBinInfo(bin: String): BinInfo? {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url("https://lookup.binlist.net/$bin")
                    .header("Accept-Version", "3")
                    .build()

                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val body = response.body?.string()
                    val binInfo = body?.let { binInfoAdapter.fromJson(it) }
                    binInfo?.let { saveBinInfoToDatabase(it) }
                    binInfo
                } else {
                    Log.e("BinRepository", "Failed to fetch BIN info: ${response.code}")
                    null
                }
            } catch (e: Exception) {
                Log.e("BinRepository", "Error fetching BIN info", e)
                null
            }
        }
    }

    private suspend fun saveBinInfoToDatabase(binInfo: BinInfo) {
        val binInfoEntity = BinInfoEntity(
            bin = binInfo.number.toString(),
            scheme = binInfo.scheme,
            type = binInfo.type,
            brand = binInfo.brand,
            prepaid = binInfo.prepaid,
            countryName = binInfo.country.name,
            bankName = binInfo.bank.name,
            bankUrl = binInfo.bank.url,
            bankPhone = binInfo.bank.phone,
            bankCity = binInfo.bank.city
        )
        database.binInfoDao().insertBinInfo(binInfoEntity)
    }

    override suspend fun getAllBinInfos(): List<BinInfoEntity> {
        return database.binInfoDao().getAllBinInfos().toList()
    }
}