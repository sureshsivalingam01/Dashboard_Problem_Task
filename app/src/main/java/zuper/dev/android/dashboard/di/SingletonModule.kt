package zuper.dev.android.dashboard.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zuper.dev.android.dashboard.data.remote.ApiDataSource
import javax.inject.Singleton


/**
 * Singleton module for DI fake ApiDataSource
 *
 * @constructor Create empty Singleton module
 */
@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {


    @Provides
    @Singleton
    fun provideApiDataSource() = ApiDataSource()



}