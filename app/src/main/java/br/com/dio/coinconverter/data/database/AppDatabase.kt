package br.com.dio.coinconverter.data.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.dio.coinconverter.data.model.ExChangeResponse
import br.com.dio.coinconverter.data.model.ExChangeResponseValue

@Database(entities = [ExChangeResponseValue::class], version = 2)
abstract class AppDatabase: RoomDatabase() {

    abstract fun exChangeDao(): ExchangeDao

    companion object{
        fun getInstance(context: Context): AppDatabase {
               return Room.databaseBuilder(context,
                       AppDatabase::class.java,
                      "exchange_ap_db")
                      .allowMainThreadQueries()
                      .build()
        }
    }

}