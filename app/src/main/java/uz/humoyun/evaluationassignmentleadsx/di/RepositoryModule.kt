package uz.humoyun.evaluationassignmentleadsx.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.humoyun.evaluationassignmentleadsx.repository.MainRepository
import uz.humoyun.evaluationassignmentleadsx.repository.MainRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun getMainRepository(impl: MainRepositoryImpl): MainRepository
}