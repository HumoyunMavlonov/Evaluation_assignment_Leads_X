package uz.humoyun.evaluationassignmentleadsx.network

import uz.humoyun.evaluationassignmentleadsx.data.response.CountryDto
import uz.humoyun.evaluationassignmentleadsx.data.response.IntentionDto
import uz.humoyun.evaluationassignmentleadsx.data.response.LeadDetailed
import uz.humoyun.evaluationassignmentleadsx.data.response.LeadsPaginated
import uz.humoyun.evaluationassignmentleadsx.data.response.StatusData
import uz.humoyun.evaluationassignmentleadsx.type.CreateLeadInput
import uz.humoyun.evaluationassignmentleadsx.type.FetchLeadInput
import uz.humoyun.evaluationassignmentleadsx.type.FetchLeadsInput
import uz.humoyun.evaluationassignmentleadsx.type.PaginationInput
import uz.humoyun.evaluationassignmentleadsx.type.UpdateLeadInput

interface LeadsApi {

    suspend fun getLeads(
        pagination: PaginationInput,
        params: FetchLeadsInput
    ): Result<LeadsPaginated>

    suspend fun getLead(request: FetchLeadInput): Result<LeadDetailed>

    suspend fun createLead(request: CreateLeadInput): Result<LeadDetailed>

    suspend fun updateLead(request: UpdateLeadInput): Result<LeadDetailed>

    suspend fun getIntentions(): Result<List<IntentionDto>?>

    suspend fun getAdSources(): Result<List<IntentionDto>?>

    suspend fun getCountries(): Result<List<CountryDto>?>

    suspend fun getStatuses(): Result<List<StatusData>?>

    suspend fun getCities(id: Int): Result<List<IntentionDto>?>

    suspend fun getLanguages(): Result<List<IntentionDto>?>

}