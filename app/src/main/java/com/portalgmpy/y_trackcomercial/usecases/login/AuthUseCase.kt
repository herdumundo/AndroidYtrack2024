package com.portalgmpy.y_trackcomercial.usecases.login

 import com.portalgmpy.y_trackcomercial.data.api.UsuarioResponse
 import com.portalgmpy.y_trackcomercial.repository.AuthRepository
 import javax.inject.Inject
 class AuthUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(user:String, password:String): UsuarioResponse?{
        return repository.iniciarSesionRepository(user, password)
    }
}