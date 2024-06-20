package com.portalgmpy.y_trackcomercial.repository.ventasRepositories

import android.util.Log
import com.portalgmpy.y_trackcomercial.data.api.A0_YTV_VENDEDORClient
import com.portalgmpy.y_trackcomercial.data.api.request.nroFacturaPendiente
import com.portalgmpy.y_trackcomercial.data.api.response.ApiResponseLimiteCreditoNroFactura
import com.portalgmpy.y_trackcomercial.data.model.dao.CustomerDao
import com.portalgmpy.y_trackcomercial.data.model.dao.ventasDaos.A0_YTV_VENDEDORDAO
import com.portalgmpy.y_trackcomercial.data.model.entities.ventas_entities.A0_YTV_VENDEDOR_Entity
import com.portalgmpy.y_trackcomercial.util.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class A0_YtvVentasRepository @Inject constructor(
    private val A0_YTV_VENDEDORClient: A0_YTV_VENDEDORClient,
    private val A0_YTV_VENDEDORDao: A0_YTV_VENDEDORDAO,
    private val customerDao: CustomerDao,
    private val sharedPreferences: SharedPreferences,
) {
   suspend fun getNroFacturaPendienteCount():  Int = A0_YTV_VENDEDORDao.nroFacturaPendienteCount()


    /*suspend fun fetchA0_YTV_VENDEDOR(): Int {
        return withContext(Dispatchers.IO) {
            val datos = A0_YTV_VENDEDORClient.getVENDEDOR( sharedPreferences.getToken().toString())

            Log.i("MensajeYtrack",datos.toString())
            A0_YTV_VENDEDORDao.eliminarTodos()
            A0_YTV_VENDEDORDao.insertAll(datos)
            return@withContext getTotalCount()
        }
    }*/
    suspend fun fetchA0_YTV_VENDEDOR(): Int {
        return withContext(Dispatchers.IO) {
            try {
                val datos = A0_YTV_VENDEDORClient.getVENDEDOR(sharedPreferences.getToken().toString())

                Log.i("MensajeYtrack", datos.toString())
                A0_YTV_VENDEDORDao.eliminarTodos()
                A0_YTV_VENDEDORDao.insertAll(datos)
                return@withContext getTotalCount()
            } catch (e: Exception) {
                // Manejar la excepción y devolver 1
                Log.e("Error", "Error al obtener VENDEDOR: ${e.message}")
                return@withContext -1
            }
        }
    }

    fun getTotalCount():  Int  {
        return A0_YTV_VENDEDORDao.getCount()
    }
    suspend fun getDatosFactura(): List<A0_YTV_VENDEDOR_Entity> = A0_YTV_VENDEDORDao.getDatosFactura()
    suspend fun exportarDatos(lotes: nroFacturaPendiente) {
        try {
            val apiResponse = A0_YTV_VENDEDORClient.uploadData(lotes,sharedPreferences.getToken().toString())
            // Puedes también manejar la respuesta de la API según el campo "tipo" del ApiResponse
            if (apiResponse.tipo == 0) {
                Log.i("Mensaje", "Datos exportados correctamente")
                updateEstadoCerrado()
            } else {
                Log.i("Mensaje", "Error al exportar los datos")
            }
        } catch (e: Exception) {
            Log.i("Mensaje", e.toString())
        }

    }

    /**NOTA:
     * DESDE EL VIEWE MODEL SE DEBE AGREGAR LA FACTURA QUE SE VA A FACTURAR EN ESE MOMENTO, AL BALANCE Y AL CREDITO DISPONIBLE**/
    suspend fun consultarTransaccionFacturacion(cardCode: String): ApiResponseLimiteCreditoNroFactura {
        return try {
            val apiResponse: ApiResponseLimiteCreditoNroFactura? = A0_YTV_VENDEDORClient.getUltimoNroFactura(
                getSlpCode(cardCode),
                sharedPreferences.getToken().toString()
            )

            if (apiResponse != null) {
                // Obtener el credito usado
                val balance = apiResponse.Balance
                // Obtener el saldo disponible
                val creditDisp = apiResponse.CreditDisp
                // Obtener el límite de crédito
                val creditLine = apiResponse.creditLine
                // Obtener el número de factura Azure
                val nroFacturaAzure = apiResponse.ult_nro_fact
                // Obtener el número de factura local
                var nroFacturaLocal = A0_YTV_VENDEDORDao.nroFacturaUltimo(cardCode).ult_nro_fact.toInt() + 1
                // Verificar si el número de factura Azure es mayor que el número de factura local
                if (nroFacturaAzure > nroFacturaLocal) {
                    nroFacturaLocal = nroFacturaAzure + 1
                }
                // Obtener el monto total de todo lo facturado sin docEntrySAP para el cliente actual
                val montoTotalFactura = A0_YTV_VENDEDORDao.getTotalFacturaLocal(cardCode)
                // Actualizar el saldo y el límite de crédito en la base de datos
                customerDao.updateBalanceOcrd(cardCode, balance, creditLine, creditDisp)
                // Devolver la respuesta
                ApiResponseLimiteCreditoNroFactura(
                    ult_nro_fact = nroFacturaLocal,
                    cardCode = cardCode,
                    creditLine = creditLine,
                    totalFactura = 0,
                    Balance = balance + montoTotalFactura,
                    CreditDisp = creditDisp - montoTotalFactura
                )
            } else {
                // Manejar el caso en el que apiResponse es null
                handleLocalTransaction(cardCode)
            }
        } catch (e: Exception) {
            // EN CASO DE QUE LA RED FALLE, SE CONSULTA TODO DE MANERA LOCAL
            handleLocalTransaction(cardCode)
        }
    }
   suspend private fun handleLocalTransaction(cardCode: String): ApiResponseLimiteCreditoNroFactura {
       // EN CASO DE QUE LA RED FALLE, SE CONSULTA TODO DE MANERA LOCAL

       val nroFacturaLocal = A0_YTV_VENDEDORDao.nroFacturaUltimo(cardCode).ult_nro_fact.toInt() + 1
        val montoTotalFactura = A0_YTV_VENDEDORDao.getTotalFacturaLocal(cardCode)
        val balanceOcrd = A0_YTV_VENDEDORDao.getBalanceOcrdLocal(cardCode)
        return ApiResponseLimiteCreditoNroFactura(
            ult_nro_fact = nroFacturaLocal,
            cardCode = cardCode,  // Asumiendo que quieres devolver el cardCode proporcionado
            creditLine = balanceOcrd.CreditLine,
            totalFactura = 0,
            Balance = balanceOcrd.Balance + montoTotalFactura,
            CreditDisp = balanceOcrd.CreditDisp - montoTotalFactura
        )
    }


    suspend fun getSlpCode(cardCode:String):  nroFacturaPendiente  = A0_YTV_VENDEDORDao.nroFacturaUltimo(cardCode)
    suspend fun getNroFacturaPendiente():  nroFacturaPendiente = A0_YTV_VENDEDORDao.nroFacturaPendiente()
     suspend fun updateEstadoCerrado()   = A0_YTV_VENDEDORDao.updateEstadoCerrado()
}