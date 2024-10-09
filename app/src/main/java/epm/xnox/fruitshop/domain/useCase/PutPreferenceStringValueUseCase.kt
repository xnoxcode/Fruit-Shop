package epm.xnox.fruitshop.domain.useCase

import epm.xnox.fruitshop.domain.repository.PreferenceRepository
import javax.inject.Inject

class PutPreferenceStringValueUseCase @Inject constructor(private val repository: PreferenceRepository){
    suspend operator fun invoke(key: String, value: String) {
        repository.putPreferenceStringValue(key, value)
    }
}