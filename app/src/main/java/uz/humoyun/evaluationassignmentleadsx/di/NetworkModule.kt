package uz.humoyun.evaluationassignmentleadsx.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApollo(
        chuckInterceptor: Interceptor,
    ): ApolloClient = ApolloClient.Builder()
        .serverUrl("http://54.246.238.84:3000/graphql")
        .addInterceptor(chuckInterceptor)
        .build()

}