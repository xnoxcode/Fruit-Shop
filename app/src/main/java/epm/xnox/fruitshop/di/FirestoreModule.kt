package epm.xnox.fruitshop.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import epm.xnox.fruitshop.data.repository.firebase.FirestoreRepositoryImpl
import epm.xnox.fruitshop.domain.repository.firebase.FirestoreRepository
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
abstract class FirestoreRepository {
    @Binds
    abstract fun provideFirestoreRepository(impl: FirestoreRepositoryImpl): FirestoreRepository
}

@Module
@InstallIn(SingletonComponent::class)

object FirestoreInstance {

    @Provides
    @Singleton
    fun provideFirestoreInstance() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFruitList(firestore: FirebaseFirestore) = firestore.collection("fruits")
}