package epm.xnox.fruitshop.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import epm.xnox.fruitshop.data.repository.DataBaseRepositoryImpl
import epm.xnox.fruitshop.domain.repository.DatabaseRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideDatabaseRepository(impl: DataBaseRepositoryImpl): DatabaseRepository
}