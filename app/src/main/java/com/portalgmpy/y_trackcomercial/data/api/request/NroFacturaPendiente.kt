package com.portalgmpy.y_trackcomercial.data.api.request

data class nroFacturaPendiente(
    val slpcode:String,
    val ult_nro_fact: String,
    val cardCode: String
)

data class balanceOcrd(
    val Balance:Long,
    val CreditDisp: Long,
    val CreditLine: Long
)
