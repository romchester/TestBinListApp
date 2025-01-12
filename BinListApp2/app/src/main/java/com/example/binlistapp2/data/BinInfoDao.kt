package com.example.binlistapp2.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BinInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBinInfo(binInfo: BinInfoEntity)

    @Query("SELECT * FROM bin_info_history ORDER BY id DESC")
    fun getAllBinInfos(): Flow<List<BinInfoEntity>>
}