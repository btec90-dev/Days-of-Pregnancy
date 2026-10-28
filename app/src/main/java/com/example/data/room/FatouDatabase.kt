package com.example.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PregnancyStateEntity::class], version = 1, exportSchema = false)
abstract class FatouDatabase : RoomDatabase() {
    abstract fun pregnancyDao(): PregnancyDao

    companion object {
        @Volatile
        private var INSTANCE: FatouDatabase? = null

        fun getDatabase(context: Context): FatouDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FatouDatabase::class.java,
                    "fatou_pregnancy_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
