package com.portalgmpy.y_trackcomercial.repository

import com.portalgmpy.y_trackcomercial.data.api.apiResponse.ApikKeyGMSApi
import com.portalgmpy.y_trackcomercial.util.SharedPreferences
import javax.inject.Inject


class ApiKeyGMSRepository @Inject constructor(
    private val apikKeyGMSApi: ApikKeyGMSApi,
    private val sharedPreferences: SharedPreferences,

    )  {

    suspend fun getApiKey():String  {
            val response = apikKeyGMSApi.getApikeyGMS(sharedPreferences.getToken().toString())
            return  response.apiKey
            }
    }