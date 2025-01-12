package com.example.binlistapp2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BinInfoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun binInfoDao(): BinInfoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bin_info_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}