package com.hafidrf.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hafidrf.app.data.local.dao.PostDao
import com.hafidrf.app.data.local.entity.PostEntity


@Database(
    entities = [
        PostEntity::class
    ],
    version = 1
)
abstract class CustomRoomDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao

    companion object {

        private const val DATABASE_VERSION = "_db_v1"

        @Volatile
        private var INSTANCE: CustomRoomDatabase? = null

        fun getDatabase(context: Context): CustomRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CustomRoomDatabase::class.java,
                    context.packageName + DATABASE_VERSION
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
