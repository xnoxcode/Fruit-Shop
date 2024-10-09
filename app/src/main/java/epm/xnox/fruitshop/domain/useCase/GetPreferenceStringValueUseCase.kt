package epm.xnox.fruitshop.domain.useCase

import epm.xnox.fruitshop.domain.repository.PreferenceRepository
import javax.inject.Inject

class GetPreferenceStringValueUseCase @Inject constructor(private val repository: PreferenceRepository){
    suspend operator fun invoke(key: String) = repository.getPreferenceStringValue(key)
}