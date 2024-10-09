package epm.xnox.fruitshop.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import epm.xnox.fruitshop.data.repository.PreferencesRepositoryImpl
import epm.xnox.fruitshop.domain.repository.PreferenceRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {
    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext context: Context): PreferenceRepository =
        PreferencesRepositoryImpl(context)
}