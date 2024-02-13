package uz.humoyun.evaluationassignmentleadsx.network

import com.apollographql.apollo3.ApolloClient
import uz.humoyun.evaluationassignmentleadsx.AllLeadsQuery
import uz.humoyun.evaluationassignmentleadsx.CitiesQuery
import uz.humoyun.evaluationassignmentleadsx.CountriesQuery
import uz.humoyun.evaluationassignmentleadsx.CreateLeadMutation
import uz.humoyun.evaluationassignmentleadsx.LanguagesQuery
import uz.humoyun.evaluationassignmentleadsx.LeadIntentionsQuery
import uz.humoyun.evaluationassignmentleadsx.LeadQuery
import uz.humoyun.evaluationassignmentleadsx.StatusQuery
import uz.humoyun.evaluationassignmentleadsx.UpdateLeadMutation
import uz.humoyun.evaluationassignmentleadsx.data.response.CountryDto
import uz.humoyun.evaluationassignmentleadsx.data.response.IntentionDto
import uz.humoyun.evaluationassignmentleadsx.data.response.LeadDetailed
import uz.humoyun.evaluationassignmentleadsx.data.response.LeadsPaginated
import uz.humoyun.evaluationassignmentleadsx.data.response.StatusData
import uz.humoyun.evaluationassignmentleadsx.data.response.toDetailedDto
import uz.humoyun.evaluationassignmentleadsx.data.response.toPaginatedResponse
import uz.humoyun.evaluationassignmentleadsx.type.CreateLeadInput
import uz.humoyun.evaluationassignmentleadsx.type.FetchLeadInput
import uz.humoyun.evaluationassignmentleadsx.type.FetchLeadsInput
import uz.humoyun.evaluationassignmentleadsx.type.PaginationInput
import uz.humoyun.evaluationassignmentleadsx.type.UpdateLeadInput
import javax.inject.Inject

class LeadsApiImpl @Inject constructor(
    private val api: ApolloClient
) : LeadsApi {
    override suspend fun getLeads(
        pagination: PaginationInput,
        params: FetchLeadsInput
    ): Result<LeadsPaginated> {
        val response = api.query(AllLeadsQuery(pagination = pagination, params = params)).execute()

        return if (response.data != null) Result.success(response.data!!.toPaginatedResponse())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }

    override suspend fun getLead(request: FetchLeadInput): Result<LeadDetailed> {
        val response = api.query(LeadQuery(request)).execute()
        return if (response.data != null) Result.success(response.data!!.toDetailedDto())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }

    override suspend fun createLead(request: CreateLeadInput): Result<LeadDetailed> {
        val response = api.mutation(CreateLeadMutation(request)).execute()
        return if (response.data != null) Result.success(response.data!!.toDetailedDto())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }

    override suspend fun updateLead(request: UpdateLeadInput): Result<LeadDetailed> {
        val response = api.mutation(UpdateLeadMutation(request)).execute()
        return if (response.data != null) Result.success(response.data!!.toDetailedDto())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }

    override suspend fun getIntentions(): Result<List<IntentionDto>?> {
        val response = api.query(LeadIntentionsQuery()).execute()
        return if (response.data != null) Result.success(response.data.toDetailedDto())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }

    override suspend fun getAdSources(): Result<List<IntentionDto>?> {
        val response = api.query(uz.humoyun.evaluationassignmentleadsx.ADSourcesQuery()).execute()
        return if (response.data != null) Result.success(response.data!!.toDetailedDto())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }

    override suspend fun getCountries(): Result<List<CountryDto>?> {
        val response = api.query(CountriesQuery()).execute()
        return if (response.data != null) Result.success(response.data!!.toDetailedDto())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }

    override suspend fun getStatuses(): Result<List<StatusData>?> {
        val response = api.query(StatusQuery()).execute()
        return if (response.data != null) Result.success(response.data!!.toDetailedDto())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }

    override suspend fun getCities(id: Int): Result<List<IntentionDto>?> {
        val response = api.query(CitiesQuery(id)).execute()
        return if (response.data != null) Result.success(response.data!!.toDetailedDto())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }

    override suspend fun getLanguages(): Result<List<IntentionDto>?> {
        val response = api.query(LanguagesQuery()).execute()
        return if (response.data != null) Result.success(response.data!!.toDetailedDto())
        else {
            Result.failure(Throwable(response.errors?.map { it.message }.toString()))
        }
    }
}