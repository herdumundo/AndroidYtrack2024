package com.portalgmpy.y_trackcomercial.data.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.portalgmpy.y_trackcomercial.data.model.entities.HorariosUsuarioEntity

@Dao
interface HorariosUsuarioDao {


    @Query("SELECT * FROM turnosHorarios   ")
    fun getHorarioUsuario(): LiveData<List<HorariosUsuarioEntity>>


    @Query("SELECT count(*) FROM turnosHorarios   ")
    fun getTurnosHorariosCount():  Int


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertAllTurnosHorarios(turnos: List<HorariosUsuarioEntity>)

    @Query("SELECT * FROM turnosHorarios WHERE strftime('%H:%M', inicioEnt) <= strftime('%H:%M', :horaActual) AND strftime('%H:%M', finSal) >= strftime('%H:%M', :horaActual)")
    fun getHorarioActual(horaActual: String): List<HorariosUsuarioEntity>

    @Query("delete from turnosHorarios")
    suspend fun eliminarTodosHorarios()

    @Query("SELECT  case when COUNT(*)>0 then id else 0  end id FROM turnosHorarios WHERE strftime('%H:%M', inicioEnt) <= strftime('%H:%M', :horaActual) AND strftime('%H:%M', finSal) >= strftime('%H:%M', :horaActual)")
    suspend fun getIdTurno(horaActual: String): Int

//VER LA HORA DE SALIDA, TIENE QUE SER LA HORA MAXIMA PERMITIDA

}