package uz.humoyun.evaluationassignmentleadsx.ui.viewmodel.createlead

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.humoyun.evaluationassignmentleadsx.data.response.CountryDto
import uz.humoyun.evaluationassignmentleadsx.data.response.IntentionDto
import uz.humoyun.evaluationassignmentleadsx.data.response.LeadDetailed
import uz.humoyun.evaluationassignmentleadsx.data.response.LeadsGeneral
import uz.humoyun.evaluationassignmentleadsx.data.response.StatusData
import uz.humoyun.evaluationassignmentleadsx.type.CreateLeadInput
import uz.humoyun.evaluationassignmentleadsx.type.UpdateLeadInput

interface CreateLeadViewModel {
    val progressFlow: Flow<Boolean>
    val errorFlow: Flow<String?>
    val adSources: LiveData<List<IntentionDto>>
    val countries: LiveData<List<CountryDto>>
    val cities: LiveData<List<IntentionDto>>
    val languages: LiveData<List<IntentionDto>>
    val hasLoadedAdSources: LiveData<Boolean>
    val hasLoadedCountries: LiveData<Boolean>
    val hasLoadedLanguages: LiveData<Boolean>
    val intentions: LiveData<List<IntentionDto>>
    val createProgressFlow: Flow<Boolean>
    val gotCreatedLead: LiveData<Boolean>
    val hasLoadedIntentions: LiveData<Boolean>
    val updateProgressFlow: Flow<Boolean>
    val updateLeadFlow: LiveData<LeadDetailed?>
    val leadItemFlow: LiveData<LeadDetailed?>
    val leadsFlow: LiveData<List<LeadsGeneral>>
    val createLeadFlow: LiveData<LeadDetailed?>
    val gotUpdatedLead: LiveData<Boolean>
    val firstInit: LiveData<Boolean>
    val statuses: LiveData<List<StatusData>>
    val hasLoadedStatuses: LiveData<Boolean>

    fun firstInit()

    fun refreshLeads()

    fun gotCreateSuccess()
    fun getStatuses()
    fun gotUpdateSuccess()
    fun gotUpdated()
    fun updateLead(updateLeadInput: UpdateLeadInput)
    fun getIntentions()
    fun gotError()
    fun gotCreated()
    fun createLead(createLeadInput: CreateLeadInput)
    fun getAdSources()
    fun getCities(id: Int)
    fun getCountries()
    fun getLanguages()
    fun searchCountry(str: String)
}