package epm.xnox.fruitshop.domain.useCase

import com.google.firebase.auth.AuthCredential
import epm.xnox.fruitshop.common.Result
import epm.xnox.fruitshop.domain.repository.firebase.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginWithCredentialUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(credential: AuthCredential): Flow<Result<Void?>> =
        repository.loginWithCredential(credential)
}