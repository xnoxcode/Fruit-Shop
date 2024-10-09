package epm.xnox.fruitshop.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import epm.xnox.fruitshop.data.repository.firebase.AuthenticationRepositoryImpl
import epm.xnox.fruitshop.domain.repository.firebase.AuthenticationRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideAuthenticationRepository(firebaseAuth: FirebaseAuth) : AuthenticationRepository {
        return AuthenticationRepositoryImpl(firebaseAuth)
    }
}
