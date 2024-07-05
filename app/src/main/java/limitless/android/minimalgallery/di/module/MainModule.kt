package limitless.android.minimalgallery.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import limitless.android.minimalgallery.data.MainRepository
import limitless.android.minimalgallery.data.local.LocalDataSource
import limitless.android.minimalgallery.data.local.LocalDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    @Singleton
    fun provideLocalDataSource() : LocalDataSource = LocalDataSourceImpl()

    @Provides
    @Singleton
    fun provideMainRepository(localDataSource: LocalDataSource) : MainRepository = MainRepository(localDataSource)

}