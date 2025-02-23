package com.portalgmpy.y_trackcomercial.data.model.entities.ventas_entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "A0_YTV_ORDEN_VENTA")
data class A0_YTV_ORDEN_VENTA_Entity(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("DocEntry")
    val docEntry: String,
    @SerializedName("LineNumDet")
    val lineNumDet: Int,
    @SerializedName("DocNum")
    val docNum: String?,
    @SerializedName("DocDate")
    val docDate: String,
    @SerializedName("DocDueDate")
    val docDueDate: String?,
    @SerializedName("DocTotal")
    val docTotal: String,
    @SerializedName("CardCode")
    val cardCode: String?,
    @SerializedName("IdCliente")
    val idCliente: String?,
    @SerializedName("CardName")
    val cardName: String?,
    @SerializedName("ShipToCode")
    val shipToCode: String?,
    @SerializedName("LineNum")
    val lineNum: String,
    @SerializedName("LicTradNum")
    val licTradNum: String,
    @SerializedName("ItemCode")
    val itemCode: String,
    @SerializedName("ItemName")
    val itemName: String,
    @SerializedName("Quantity")
    val quantity: String,
    @SerializedName("unitMsr")
    val unitMsr: String?,
    @SerializedName("PriceAfVAT")
    val priceAfVAT: String?,
    @SerializedName("TaxCode")
    val taxCode: String?,
    @SerializedName("LineTotal")
    val lineTotal: String?,
    @SerializedName("VatSum")
    val vatSum: String?,
    @SerializedName("SlpCode")
    val slpCode: String?,
    @SerializedName("SlpName")
    val slpName: String?,
    @SerializedName("GroupNum")
    val groupNum: String?,
    @SerializedName("PymntGroup")
    val pymntGroup: String?,
    @SerializedName("DocStatus")
    val docStatus: String?,
    @SerializedName("CANCELED")
    val canceled: String?,
    @SerializedName("U_DEPTOCOD")
    var u_deptocod: String?,
    @SerializedName("U_SIFENNCASA")
    var u_sifenncasa: String?,
    @SerializedName( "U_SIFENCIUDAD")
    var u_sifenciudad: String?,
    @SerializedName( "correo")
    var correo: String?,
    @SerializedName("estado")
    val estado: String = "P",
    @SerializedName("STREET")
    val STREET: String?,
    @SerializedName("uMedida")
    val uMedida: Int,
    @SerializedName("CodeBars")
    val CodeBars: String?



)
