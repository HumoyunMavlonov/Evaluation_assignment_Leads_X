package uz.humoyun.evaluationassignmentleadsx.di

import com.apollographql.apollo3.api.ApolloRequest
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.apollographql.apollo3.interceptor.ApolloInterceptorChain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Interceptor @Inject constructor() : ApolloInterceptor {
    override fun <D : Operation.Data> intercept(
        request: ApolloRequest<D>,
        chain: ApolloInterceptorChain
    ): Flow<ApolloResponse<D>> {
        return chain.proceed(buildRequestWithToken(request = request))
    }


    private fun <D : Operation.Data> buildRequestWithToken(
        request: ApolloRequest<D>,
        tokenType: String = "Bearer",
        token: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlSWQiOjE3NjYsImZJZCI6ImxPR3N5eG9PUU1CWjd6cnQiLCJzSWQiOjEzNiwiaWF0IjoxNzA2NTMxMjA1LCJleHAiOjE3MDc3NDA4MDV9.1teBejjVNZYvSPOP8t_VN4umV5GaXQKCf7jJkVTahKE"
    ): ApolloRequest<D> {

        return request.newBuilder().addHttpHeader(
            "Authorization",
            "$tokenType $token"
        ).build()
    }

}