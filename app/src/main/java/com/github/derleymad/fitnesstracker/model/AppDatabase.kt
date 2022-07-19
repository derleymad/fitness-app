package com.github.derleymad.fitnesstracker.model

import android.content.Context
import androidx.room.*

@Database(entities = [Calc::class], version = 1)
//Padrão Singleton esse objeto aki em baixo vai ser ÚNICO pois será armazenado em memória...

@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {
    abstract fun calcDao(): CalcDao

    companion object{
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return if(INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "fitness_tracker"
                    ).build()
                }
                INSTANCE as AppDatabase
            }else{
                INSTANCE as AppDatabase
            }
        }
    }
}