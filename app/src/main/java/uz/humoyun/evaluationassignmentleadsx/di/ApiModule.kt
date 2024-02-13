package uz.humoyun.evaluationassignmentleadsx.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.humoyun.evaluationassignmentleadsx.network.LeadsApi
import uz.humoyun.evaluationassignmentleadsx.network.LeadsApiImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @[Provides Singleton]
    fun provideLeadsApi(
        apollo: ApolloClient
    ): LeadsApi = LeadsApiImpl(apollo)

}