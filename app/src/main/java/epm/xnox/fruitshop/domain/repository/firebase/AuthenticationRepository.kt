package epm.xnox.fruitshop.domain.repository.firebase

import com.google.firebase.auth.AuthCredential
import epm.xnox.fruitshop.common.Result
import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    suspend fun loginWithEmailAndPassword(email: String, password: String): Flow<Result<String>>
    suspend fun loginWithCredential(credential: AuthCredential): Flow<Result<Void?>>
    suspend fun signUpWithEmailAndPassword(name: String, email: String, password: String): Flow<Result<Void?>>
}