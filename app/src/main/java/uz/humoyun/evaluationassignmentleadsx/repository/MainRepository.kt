package uz.humoyun.evaluationassignmentleadsx.repository

import kotlinx.coroutines.flow.Flow
import uz.humoyun.evaluationassignmentleadsx.data.response.CountryDto
import uz.humoyun.evaluationassignmentleadsx.data.response.IntentionDto
import uz.humoyun.evaluationassignmentleadsx.data.response.LeadDetailed
import uz.humoyun.evaluationassignmentleadsx.data.response.LeadsGeneral
import uz.humoyun.evaluationassignmentleadsx.data.response.StatusData
import uz.humoyun.evaluationassignmentleadsx.type.CreateLeadInput
import uz.humoyun.evaluationassignmentleadsx.type.FetchLeadInput
import uz.humoyun.evaluationassignmentleadsx.type.FetchLeadsInput
import uz.humoyun.evaluationassignmentleadsx.type.UpdateLeadInput

interface MainRepository {

    suspend fun getLeads(
        params: FetchLeadsInput? = null
    ): Flow<Result<List<LeadsGeneral>>>

    suspend fun getLead(lead: FetchLeadInput): Flow<Result<LeadDetailed>>

    suspend fun createLead(lead: CreateLeadInput): Flow<Result<LeadDetailed>>

    suspend fun updateLead(lead: UpdateLeadInput): Flow<Result<LeadDetailed>>

    suspend fun getIntentions(): Flow<Result<List<IntentionDto>>>

    suspend fun getAdSources(): Flow<Result<List<IntentionDto>>>

    suspend fun getCountries(): Flow<Result<List<CountryDto>>>

    suspend fun getStatuses(): Flow<Result<List<StatusData>>>

    suspend fun getCities(id: Int): Flow<Result<List<IntentionDto>>>

    suspend fun getLanguages(): Flow<Result<List<IntentionDto>>>

    fun clearPagination()
}