package com.portalgmpy.y_trackcomercial.data.model.entities.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Transaction
import com.portalgmpy.y_trackcomercial.data.model.dao.*
import com.portalgmpy.y_trackcomercial.data.model.dao.registroDaos.MovimientosDao
import com.portalgmpy.y_trackcomercial.data.model.dao.registroDaos.NewPassDao
import com.portalgmpy.y_trackcomercial.data.model.dao.registroDaos.UbicacionesNuevasDao
import com.portalgmpy.y_trackcomercial.data.model.dao.registroDaos.logsDaos.*
import com.portalgmpy.y_trackcomercial.data.model.dao.registroDaos.VisitasDao
import com.portalgmpy.y_trackcomercial.data.model.dao.ventasDaos.A0_YTV_LISTA_PRECIOSDAO
import com.portalgmpy.y_trackcomercial.data.model.dao.ventasDaos.A0_YTV_ORDEN_VENTADAO
import com.portalgmpy.y_trackcomercial.data.model.dao.ventasDaos.A0_YTV_STOCK_ALMACENDAO
import com.portalgmpy.y_trackcomercial.data.model.dao.ventasDaos.A0_YTV_VENDEDORDAO
import com.portalgmpy.y_trackcomercial.data.model.dao.ventasDaos.INV1_DAO
import com.portalgmpy.y_trackcomercial.data.model.dao.ventasDaos.INV1_LOTES_DAO
import com.portalgmpy.y_trackcomercial.data.model.dao.ventasDaos.OINV_DAO
import com.portalgmpy.y_trackcomercial.data.model.dao.ventasDaos.views.daos.StockDao
import com.portalgmpy.y_trackcomercial.data.model.dao.ventasDaos.views.dataviews.V_STOCK_ALMACEN
import com.portalgmpy.y_trackcomercial.data.model.entities.*
import com.portalgmpy.y_trackcomercial.data.model.entities.logs.*
import com.portalgmpy.y_trackcomercial.data.model.entities.ventas_entities.INV1_POS
import com.portalgmpy.y_trackcomercial.data.model.entities.registro_entities.MovimientosEntity
import com.portalgmpy.y_trackcomercial.data.model.entities.registro_entities.NewPassEntity
import com.portalgmpy.y_trackcomercial.data.model.entities.ventas_entities.OINV_POS
import com.portalgmpy.y_trackcomercial.data.model.entities.registro_entities.UbicacionesNuevasEntity
import com.portalgmpy.y_trackcomercial.data.model.entities.registro_entities.VisitasEntity
import com.portalgmpy.y_trackcomercial.data.model.entities.ventas_entities.A0_YTV_LISTA_PRECIOS_Entity
import com.portalgmpy.y_trackcomercial.data.model.entities.ventas_entities.A0_YTV_ORDEN_VENTA_Entity
import com.portalgmpy.y_trackcomercial.data.model.entities.ventas_entities.A0_YTV_STOCK_ALMACEN_Entity
import com.portalgmpy.y_trackcomercial.data.model.entities.ventas_entities.A0_YTV_VENDEDOR_Entity
import com.portalgmpy.y_trackcomercial.data.model.entities.ventas_entities.INV1_LOTES_POS

@Database(
    entities =
    [
        OCRDEntity::class,
        LotesListasEntity::class,
        OcrdUbicacionEntity::class,
        VisitasEntity::class,
        RutasAccesosEntity::class,
        HorariosUsuarioEntity::class,
        LogEntity::class,
        AuditTrailEntity::class,
        ParametrosEntity::class,
        OitmEntity::class,
        OcrdOitmEntity::class,
        MovimientosEntity::class,
        UbicacionesPvEntity::class,
        PermisosVisitasEntity::class,
        UbicacionesNuevasEntity::class,
        NewPassEntity::class ,
        OINV_POS::class,
        INV1_POS::class,
        A0_YTV_VENDEDOR_Entity::class,
        A0_YTV_LISTA_PRECIOS_Entity::class,
        A0_YTV_STOCK_ALMACEN_Entity::class,
        A0_YTV_ORDEN_VENTA_Entity::class,
        INV1_LOTES_POS::class
     ],
    views = [V_STOCK_ALMACEN::class],
    version =1,
    exportSchema = false
)
abstract class YtrackDatabase : RoomDatabase() {
    @Transaction
    open suspend fun insertarVentaCompleta(
        oinv: OINV_POS,
        inv1List: List<INV1_POS>,
        inv1LotesList: List<INV1_LOTES_POS>,
        idOrdenVenta: String,
        ultimoNroFactura: String
    ): Long {
        // Insertamos el registro principal y obtenemos su ID autogenerado
        val docEntry = OinvDao().insertOinv(oinv)
        // Preparamos los registros relacionados con el ID obtenido
        inv1List.forEach { it.docEntry = docEntry }
        inv1LotesList.forEach { it.docEntry = docEntry }
        // Insertamos los registros relacionados
        INV1_DAO().insertAllInv1(inv1List)
        INV1_LOTES_DAO().insertAll(inv1LotesList)
        A0_YTV_VENDEDOR_DAO().updateNumeroFactura(ultimoNroFactura)
        A0_YTV_VENDEDOR_DAO().updateEstadoPendiente()
        A0_YTV_ORDEN_VENTADAO_DAO().updateCerrado(idOrdenVenta)
        return docEntry
    }
    abstract fun OinvDao(): OINV_DAO
    abstract fun INV1_DAO(): INV1_DAO
    abstract fun INV1_LOTES_DAO(): INV1_LOTES_DAO
    abstract fun A0_YTV_VENDEDOR_DAO(): A0_YTV_VENDEDORDAO

    abstract fun customerDao():  CustomerDao
    abstract fun lotesListasDao():  LotesListasDao
    abstract fun ocrdUbicacionesDao(): OcrdUbicacionesDao
    abstract fun rutasAccesosDao(): RutasAccesosDao
    abstract fun VisitasDao():  VisitasDao
    abstract fun PermisosVisitasDao():  PermisosVisitasDao
    abstract fun HorariosUsuarioDao(): HorariosUsuarioDao
    abstract fun LogDao():  LogDao
    abstract fun AuditTrailDao(): AuditTrailDao
    abstract fun ParametrosDao():  ParametrosDao
    abstract fun OitmDao():  OitmDao
    abstract fun OcrdOitmDao():  OcrdOitmDao
    abstract fun MovimientosDao(): MovimientosDao
    abstract fun UbicacionesPvDao():  UbicacionesPvDao
    abstract fun UbicacionesNuevasDao():  UbicacionesNuevasDao
    abstract fun NewPassDao(): NewPassDao

    abstract fun A0_YTV_LISTA_PRECIOS_DAO(): A0_YTV_LISTA_PRECIOSDAO
    abstract fun  A0_YTV_STOCK_ALMACEN_DAO(): A0_YTV_STOCK_ALMACENDAO
    abstract fun  A0_YTV_ORDEN_VENTADAO_DAO(): A0_YTV_ORDEN_VENTADAO
    abstract fun stockDao(): StockDao

}