package epm.xnox.fruitshop.data.repository.firebase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import epm.xnox.fruitshop.common.Result
import epm.xnox.fruitshop.domain.repository.firebase.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthenticationRepository {

    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Result<String>> = flow {
        var isLoginSuccefully = false
        var userEmail = ""

        emit(Result.Loading)
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    userEmail = result.user?.email!!
                    isLoginSuccefully = true
                }.await()

            if (isLoginSuccefully) {
                emit(Result.Success(data = userEmail))
            } else {
                emit(Result.Error("Error al iniciar sesión"))
            }

        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage ?: "Error desconocido"))
        }
    }

    override suspend fun loginWithCredential(credential: AuthCredential): Flow<Result<Void?>> =
        flow {
            var isLoginSucefully = false

            emit(Result.Loading)

            firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful)
                    isLoginSucefully = true
            }.await()

            if (isLoginSucefully) {
                emit(Result.Success(null))
            } else {
                emit(Result.Error("Error al iniciar sesión"))
            }
        }

    override suspend fun signUpWithEmailAndPassword(
        name: String,
        email: String,
        password: String
    ): Flow<Result<Void?>> = flow {
        var isSignUpSuccefully = false

        emit(Result.Loading)

        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                isSignUpSuccefully = true
            }.await()

            if (isSignUpSuccefully) {
                emit(Result.Success(null))
            } else {
                emit(Result.Error("Error al crear la cuenta"))
            }

        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage ?: "Error desconocido"))
        }
    }
}