package com.kis.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kis.classes.Meme
// create table data base room
@Database(entities = [Meme::class], version = 1, exportSchema = false)
abstract class UserDb: RoomDatabase() {

    abstract fun userDao(): UserRoomDao

    companion object {
        private var INSTANCE: UserDb? = null
        private val LOCK = Any()
        private const val NAME_DB = "userDb.db"

        fun getInstance(application: Application): UserDb {

            INSTANCE?.let {
                return it
            }

            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }

                val db = Room.databaseBuilder(
                    application,
                    UserDb::class.java,
                    NAME_DB
                )
                    .build()
                INSTANCE = db
                return db
            }

        }
    }

}