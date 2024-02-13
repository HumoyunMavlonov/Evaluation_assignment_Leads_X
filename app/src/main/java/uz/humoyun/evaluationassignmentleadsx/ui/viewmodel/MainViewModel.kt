package uz.humoyun.evaluationassignmentleadsx.ui.viewmodel

//import androidx.lifecycle.LiveData
//import kotlinx.coroutines.flow.Flow
//import uz.humoyun.evaluationassignmentleadsx.data.response.CountryDto
//import uz.humoyun.evaluationassignmentleadsx.data.response.IntentionDto
//import uz.humoyun.evaluationassignmentleadsx.data.response.LeadDetailed
//import uz.humoyun.evaluationassignmentleadsx.data.response.LeadsGeneral
//import uz.humoyun.evaluationassignmentleadsx.data.response.StatusData
//import uz.humoyun.evaluationassignmentleadsx.type.CreateLeadInput
//import uz.humoyun.evaluationassignmentleadsx.type.FetchLeadInput
//import uz.humoyun.evaluationassignmentleadsx.type.FetchLeadsInput
//import uz.humoyun.evaluationassignmentleadsx.type.UpdateLeadInput
//
//interface MainViewModel {
//
//
//    val progressFlow: Flow<Boolean>
//    val createProgressFlow: Flow<Boolean>
//    val updateProgressFlow: Flow<Boolean>
//    val errorFlow: Flow<String?>
//    val leadsFlow: LiveData<List<LeadsGeneral>>
//    val leadItemFlow: LiveData<LeadDetailed?>
//    val createLeadFlow: LiveData<LeadDetailed?>
//    val updateLeadFlow: LiveData<LeadDetailed?>
//    val navigateItemScreen: LiveData<Boolean>
//    val intentions: LiveData<List<IntentionDto>>
//    val adSources: LiveData<List<IntentionDto>>
//    val countries: LiveData<List<CountryDto>>
//    val cities: LiveData<List<IntentionDto>>
//    val languages: LiveData<List<IntentionDto>>
//    val statuses: LiveData<List<StatusData>>
//    val hasLoadedStatuses: LiveData<Boolean>
//    val hasLoadedIntentions: LiveData<Boolean>
//    val hasLoadedAdSources: LiveData<Boolean>
//    val hasLoadedCountries: LiveData<Boolean>
//    val hasLoadedLanguages: LiveData<Boolean>
//
//    val gotLoadedLead: LiveData<Boolean>
//    val gotCreatedLead: LiveData<Boolean>
//    val gotUpdatedLead: LiveData<Boolean>
//
//    val firstInit: LiveData<Boolean>
//
//    fun getLeads(filter: FetchLeadsInput? = null)
//
//    fun getLead(lead: FetchLeadInput, onSuccess: ((Int) -> Unit)? = null)
//
//    fun createLead(createLeadInput: CreateLeadInput)
//
//    fun updateLead(updateLeadInput: UpdateLeadInput)
//
//    fun firstInit()
//
//    fun refreshLeads()
//
//    fun gotCreateSuccess()
//
//    fun gotUpdateSuccess()
//
//    fun gotError()
//
//    fun gotNavigateToItemScreen()
//
//    fun getIntentions()
//
//    fun getAdSources()
//    fun getCities(id: Int)
//    fun getCountries()
//    fun getLanguages()
//    fun searchCountry(str: String)
//
//    fun getStatuses()
//
//
//    fun gotLoaded()
//
//    fun gotCreated()
//
//    fun gotUpdated()
//
//}