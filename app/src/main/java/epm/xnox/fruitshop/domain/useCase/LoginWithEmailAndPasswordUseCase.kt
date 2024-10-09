package epm.xnox.fruitshop.domain.useCase

import epm.xnox.fruitshop.common.Result
import epm.xnox.fruitshop.domain.repository.firebase.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginWithEmailAndPasswordUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(email: String, password: String): Flow<Result<String>> =
        repository.loginWithEmailAndPassword(email, password)
}