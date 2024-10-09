package epm.xnox.fruitshop.domain.useCase

import epm.xnox.fruitshop.domain.repository.PreferenceRepository
import javax.inject.Inject

class PutPreferenceBooleanValueUseCase @Inject constructor(private val repository: PreferenceRepository){
    suspend operator fun invoke(key: String, value: Boolean) {
        repository.putPreferenceBooleanValue(key, value)
    }
}