package br.com.dio.coinconverter.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.dio.coinconverter.data.model.ExChangeResponseValue
import com.google.android.material.circularreveal.CircularRevealHelper
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(exChangeResponseValue: ExChangeResponseValue)

    @Query("SELECT * FROM tb_exchange")
    fun getExChange(): Flow<List<ExChangeResponseValue>>

}