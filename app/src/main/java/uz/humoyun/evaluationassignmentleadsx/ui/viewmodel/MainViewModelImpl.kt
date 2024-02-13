package uz.humoyun.evaluationassignmentleadsx.ui.viewmodel

//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.launchIn
//import kotlinx.coroutines.flow.onEach
//import kotlinx.coroutines.launch
//import uz.humoyun.evaluationassignmentleadsx.data.response.CountryDto
//import uz.humoyun.evaluationassignmentleadsx.data.response.IntentionDto
//import uz.humoyun.evaluationassignmentleadsx.data.response.LeadDetailed
//import uz.humoyun.evaluationassignmentleadsx.data.response.LeadsGeneral
//import uz.humoyun.evaluationassignmentleadsx.data.response.StatusData
//import uz.humoyun.evaluationassignmentleadsx.repository.MainRepository
//import uz.humoyun.evaluationassignmentleadsx.type.CreateLeadInput
//import uz.humoyun.evaluationassignmentleadsx.type.FetchLeadInput
//import uz.humoyun.evaluationassignmentleadsx.type.FetchLeadsInput
//import uz.humoyun.evaluationassignmentleadsx.type.UpdateLeadInput
//import uz.humoyun.evaluationassignmentleadsx.util.eventValueFlow
//import uz.humoyun.evaluationassignmentleadsx.util.isConnected
//import javax.inject.Inject
//
//@HiltViewModel
//class MainViewModelImpl @Inject constructor(private val repository: MainRepository) : MainViewModel,
//    ViewModel() {
//
//    override val progressFlow = eventValueFlow<Boolean>()
//    override val createProgressFlow = eventValueFlow<Boolean>()
//    override val updateProgressFlow = eventValueFlow<Boolean>()
//    override val errorFlow = eventValueFlow<String?>()
//    override val leadsFlow = MutableLiveData<List<LeadsGeneral>>(listOf())
//    override val leadItemFlow = MutableLiveData<LeadDetailed?>(null)
//    override val createLeadFlow = MutableLiveData<LeadDetailed?>()
//    override val updateLeadFlow = MutableLiveData<LeadDetailed?>()
//    override val navigateItemScreen = MutableLiveData(false)
//    override val intentions = MutableLiveData<List<IntentionDto>>()
//    override val adSources = MutableLiveData<List<IntentionDto>>()
//    override val countries = MutableLiveData<List<CountryDto>>()
//
//    private val allCountries = mutableListOf<CountryDto>()
//
//    override val cities = MutableLiveData<List<IntentionDto>>()
//    override val languages = MutableLiveData<List<IntentionDto>>()
//    override val statuses = MutableLiveData<List<StatusData>>()
//
//    override val hasLoadedIntentions = MutableLiveData(false)
//    override val hasLoadedAdSources = MutableLiveData(false)
//    override val hasLoadedCountries = MutableLiveData(false)
//    override val hasLoadedLanguages = MutableLiveData(false)
//    override val hasLoadedStatuses = MutableLiveData(false)
//
//    override val gotLoadedLead = MutableLiveData(false)
//    override val gotCreatedLead = MutableLiveData(false)
//    override val gotUpdatedLead = MutableLiveData(false)
//
//    private var lastCountryId = -1
//
//    override val firstInit = MutableLiveData(false)
//
//    override fun getLeads(filter: FetchLeadsInput?) {
//        if (!isConnected()) {
//            viewModelScope.launch { errorFlow.emit("No internet connection!") }
//            return
//        }
//        viewModelScope.launch {
//            progressFlow.emit(true)
//        }
//        viewModelScope.launch {
//            repository.getLeads(filter).onEach {
//                progressFlow.emit(false)
//                it.onSuccess { res ->
//                    val list = leadsFlow.value?.toMutableList()
//                    list?.addAll(res)
//                    leadsFlow.value = list ?: listOf()
//                }
//                it.onFailure { err ->
//                    errorFlow.emit(err.message)
//                }
//            }.launchIn(viewModelScope)
//        }
//    }
//
//    override fun getLead(lead: FetchLeadInput, onSuccess: ((Int) -> Unit)?) {
//        if (!isConnected()) {
//            viewModelScope.launch { errorFlow.emit("No internet connection!") }
//            return
//        }
//        viewModelScope.launch {
//            progressFlow.emit(true)
//        }
//        viewModelScope.launch {
//            repository.getLead(lead).onEach {
//                progressFlow.emit(false)
//                it.onSuccess { res ->
//                    leadItemFlow.value = res
//                    navigateItemScreen.value = true
//                    gotLoadedLead.value = true
//                    onSuccess?.invoke(res.data.id)
//                }
//                it.onFailure { err ->
//                    errorFlow.emit(err.message)
//                }
//            }.launchIn(viewModelScope)
//        }
//    }
//
//    override fun createLead(createLeadInput: CreateLeadInput) {
//        if (!isConnected()) {
//            viewModelScope.launch { errorFlow.emit("No internet connection!") }
//            return
//        }
//        viewModelScope.launch {
//            createProgressFlow.emit(true)
//        }
//        viewModelScope.launch {
//            repository.createLead(createLeadInput).onEach {
//                createProgressFlow.emit(false)
//                it.onSuccess { res ->
//                    val leads = leadsFlow.value?.toMutableList() ?: mutableListOf()
//                    leads.add(
//                        0,
//                        LeadsGeneral(
//                            id = res.data.id,
//                            firstName = res.data.firstName,
//                            lastName = res.data.lastName,
//                            createdAt = res.data.createdAt,
//                            status = res.data.status,
//                            avatar = res.data.avatar,
//                            countryDto = res.data.country
//                        )
//                    )
//                    leadItemFlow.value = res
//                    leadsFlow.value = leads
//                    gotCreatedLead.value = true
//                }
//                it.onFailure { err ->
//                    errorFlow.emit(err.message)
//                }
//            }.launchIn(viewModelScope)
//        }
//    }
//
//    override fun updateLead(updateLeadInput: UpdateLeadInput) {
//        if (!isConnected()) {
//            viewModelScope.launch { errorFlow.emit("No internet connection!") }
//            return
//        }
//        viewModelScope.launch {
//            updateProgressFlow.emit(true)
//        }
//        viewModelScope.launch {
//            repository.updateLead(updateLeadInput).onEach {
//                updateProgressFlow.emit(false)
//                it.onSuccess { res ->
//                    leadItemFlow.value = res
//                    val leads = leadsFlow.value?.toMutableList() ?: mutableListOf()
//
//                    leads.replaceAll { dto ->
//                        if (dto.id == updateLeadInput.leadId) LeadsGeneral(
//                            id = res.data.id,
//                            firstName = res.data.firstName,
//                            lastName = res.data.lastName,
//                            createdAt = res.data.createdAt,
//                            status = res.data.status,
//                            avatar = res.data.avatar,
//                            countryDto = res.data.country
//                        ) else dto
//                    }
//
//                    leadsFlow.value = leads
//
//                    gotUpdatedLead.value = true
//
//                }
//                it.onFailure { err ->
//                    errorFlow.emit(err.message)
//                }
//            }.launchIn(viewModelScope)
//        }
//    }
//
//    override fun firstInit() {
//        viewModelScope.launch {
//            firstInit.value = true
//        }
//    }
//
//    override fun gotCreateSuccess() {
//        viewModelScope.launch {
//            createLeadFlow.value = null
//        }
//    }
//
//    override fun gotError() {
//        viewModelScope.launch {
//            errorFlow.emit(null)
//        }
//    }
//
//    override fun gotUpdateSuccess() {
//        viewModelScope.launch {
//            updateLeadFlow.value = null
//        }
//    }
//
//    override fun refreshLeads() {
//        repository.clearPagination()
//        if (!isConnected()) {
//            viewModelScope.launch { errorFlow.emit("No internet connection!") }
//            return
//        }
//        viewModelScope.launch {
//            progressFlow.emit(true)
//        }
//        viewModelScope.launch {
//            repository.getLeads().onEach {
//                progressFlow.emit(false)
//                it.onSuccess { res ->
//                    val list = leadsFlow.value?.toMutableList()
//                    list?.addAll(res)
//                    leadsFlow.value = list ?: listOf()
//                }
//                it.onFailure { err ->
//                    errorFlow.emit(err.message)
//                }
//            }.launchIn(viewModelScope)
//        }
//    }
//
//    override fun gotNavigateToItemScreen() {
//        navigateItemScreen.value = false
//    }
//
//
//    override fun getIntentions() {
//        if (!isConnected()) {
//            viewModelScope.launch { errorFlow.emit("No internet connection!") }
//            return
//        }
//        viewModelScope.launch {
//            progressFlow.emit(true)
//        }
//        viewModelScope.launch {
//            repository.getIntentions().onEach {
//                progressFlow.emit(false)
//                it.onSuccess { res ->
//                    intentions.value = res
//                    hasLoadedIntentions.value = true
//                }
//                it.onFailure { err ->
//                    errorFlow.emit(err.message)
//                }
//            }.launchIn(viewModelScope)
//        }
//    }
//
//    override fun getAdSources() {
//        if (!isConnected()) {
//            viewModelScope.launch { errorFlow.emit("No internet connection!") }
//            return
//        }
//        viewModelScope.launch {
//            progressFlow.emit(true)
//        }
//        viewModelScope.launch {
//            repository.getAdSources().onEach {
//                progressFlow.emit(false)
//                it.onSuccess { res ->
//                    adSources.value = res
//                    hasLoadedAdSources.value = true
//                }
//                it.onFailure { err ->
//                    errorFlow.emit(err.message)
//                }
//            }.launchIn(viewModelScope)
//        }
//    }
//
//    override fun getCities(id: Int) {
//        if (lastCountryId != id) {
//            if (!isConnected()) {
//                viewModelScope.launch { errorFlow.emit("No internet connection!") }
//                return
//            }
//            viewModelScope.launch {
//                progressFlow.emit(true)
//            }
//            viewModelScope.launch {
//                repository.getCities(id).onEach {
//                    progressFlow.emit(false)
//                    it.onSuccess { res ->
//                        lastCountryId = id
//                        cities.value = res
//                    }
//                    it.onFailure { err ->
//                        errorFlow.emit(err.message)
//                    }
//                }.launchIn(viewModelScope)
//            }
//        }
//    }
//
//    override fun getLanguages() {
//        if (!isConnected()) {
//            viewModelScope.launch { errorFlow.emit("No internet connection!") }
//            return
//        }
//        viewModelScope.launch {
//            progressFlow.emit(true)
//        }
//        viewModelScope.launch {
//            repository.getLanguages().onEach {
//                progressFlow.emit(false)
//                it.onSuccess { res ->
//                    languages.value = res
//                    hasLoadedLanguages.value = true
//                }
//                it.onFailure { err ->
//                    errorFlow.emit(err.message)
//                }
//            }.launchIn(viewModelScope)
//        }
//    }
//
//    override fun getStatuses() {
//        if (!isConnected()) {
//            viewModelScope.launch { errorFlow.emit("No internet connection!") }
//            return
//        }
//        viewModelScope.launch {
//            progressFlow.emit(true)
//        }
//        viewModelScope.launch {
//            repository.getStatuses().onEach {
//                progressFlow.emit(false)
//                it.onSuccess { res ->
//                    statuses.value = res
//                    hasLoadedStatuses.value = true
//                }
//                it.onFailure { err ->
//                    errorFlow.emit(err.message)
//                }
//            }.launchIn(viewModelScope)
//        }
//    }
//
//    override fun getCountries() {
//        if (!isConnected()) {
//            viewModelScope.launch { errorFlow.emit("No internet connection!") }
//            return
//        }
//        viewModelScope.launch {
//            progressFlow.emit(true)
//        }
//        viewModelScope.launch {
//            repository.getCountries().onEach {
//                progressFlow.emit(false)
//                it.onSuccess { res ->
//                    countries.value = res
//                    allCountries.clear()
//                    allCountries.addAll(res)
//                }
//                it.onFailure { err ->
//                    errorFlow.emit(err.message)
//                }
//            }.launchIn(viewModelScope)
//        }
//    }
//
//    override fun searchCountry(str: String) {
//        viewModelScope.launch {
//            if (str.isNotBlank()) {
//                val list = allCountries.filter { it.title.lowercase().contains(str.lowercase()) }
//                countries.value = list
//            } else countries.value = allCountries
//
//        }
//    }
//
//    override fun gotCreated() {
//        gotCreatedLead.value = false
//    }
//
//    override fun gotLoaded() {
//        gotLoadedLead.value = false
//    }
//
//    override fun gotUpdated() {
//        gotUpdatedLead.value = false
//    }
//}