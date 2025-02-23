package com.portalgmpy.y_trackcomercial.usecases.apiKeyGMS

import com.portalgmpy.y_trackcomercial.repository.ApiKeyGMSRepository
import javax.inject.Inject

class GetApiKeyGMSUseCase @Inject constructor(
    private val apiKeyGMSRepository: ApiKeyGMSRepository
) {
    suspend  fun getApiKeyGMS(): String {
        return apiKeyGMSRepository.getApiKey()
    }
}