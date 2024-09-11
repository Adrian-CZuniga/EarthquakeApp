package com.aprendo.earthquake_2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aprendo.earthquake_2.Earthquake

@Database(entities = [Earthquake::class], version = 1)
abstract class EqDataBase: RoomDatabase(){
    abstract val eqDao: EqDao
}
private lateinit var INSTANCE: EqDataBase

fun getDataBase(context: Context): EqDataBase {
    synchronized(EqDataBase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                EqDataBase::class.java,
                "earthquake_db"
            ).build()
        }
        return INSTANCE
    }
}