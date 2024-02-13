package uz.humoyun.evaluationassignmentleadsx.ui.viewmodel.leaddetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.humoyun.evaluationassignmentleadsx.data.response.LeadDetailed
import uz.humoyun.evaluationassignmentleadsx.data.response.LeadsGeneral
import uz.humoyun.evaluationassignmentleadsx.data.response.StatusData
import uz.humoyun.evaluationassignmentleadsx.repository.MainRepository
import uz.humoyun.evaluationassignmentleadsx.type.FetchLeadInput
import uz.humoyun.evaluationassignmentleadsx.type.UpdateLeadInput
import uz.humoyun.evaluationassignmentleadsx.util.eventValueFlow
import uz.humoyun.evaluationassignmentleadsx.util.isConnected
import javax.inject.Inject

@HiltViewModel
class LeadDetailViewModelImpl @Inject constructor(val repository: MainRepository):LeadDetailViewModel, ViewModel() {

    override val updateLeadFlow = MutableLiveData<LeadDetailed?>()
    override val firstInit = MutableLiveData(false)
    override val createLeadFlow = MutableLiveData<LeadDetailed?>()
    override val hasLoadedStatuses = MutableLiveData(false)
    override val statuses = MutableLiveData<List<StatusData>>()
    override val updateProgressFlow = eventValueFlow<Boolean>()
    override val leadsFlow = MutableLiveData<List<LeadsGeneral>>(listOf())
    override val progressFlow = eventValueFlow<Boolean>()
    override val errorFlow = eventValueFlow<String?>()
    override val leadItemFlow = MutableLiveData<LeadDetailed?>(null)
    override val navigateItemScreen = MutableLiveData(false)
    override val gotLoadedLead = MutableLiveData(false)
    override val gotUpdatedLead = MutableLiveData(false)
    override fun firstInit() {
        viewModelScope.launch {
            firstInit.value = true
        }
    }


    override fun gotNavigateToItemScreen() {
        navigateItemScreen.value = false
    }
    override fun updateLead(updateLeadInput: UpdateLeadInput) {
        if (!isConnected()) {
            viewModelScope.launch { errorFlow.emit("No internet connection!") }
            return
        }
        viewModelScope.launch {
            updateProgressFlow.emit(true)
        }
        viewModelScope.launch {
            repository.updateLead(updateLeadInput).onEach {
                updateProgressFlow.emit(false)
                it.onSuccess { res ->
                    leadItemFlow.value = res
                    val leads = leadsFlow.value?.toMutableList() ?: mutableListOf()

                    leads.replaceAll { dto ->
                        if (dto.id == updateLeadInput.leadId) LeadsGeneral(
                            id = res.data.id,
                            firstName = res.data.firstName,
                            lastName = res.data.lastName,
                            createdAt = res.data.createdAt,
                            status = res.data.status,
                            avatar = res.data.avatar,
                            countryDto = res.data.country
                        ) else dto
                    }

                    leadsFlow.value = leads

                    gotUpdatedLead.value = true

                }
                it.onFailure { err ->
                    errorFlow.emit(err.message)
                }
            }.launchIn(viewModelScope)
        }
    }
    override fun getLead(lead: FetchLeadInput, onSuccess: ((Int) -> Unit)?) {
        if (!isConnected()) {
            viewModelScope.launch { errorFlow.emit("No internet connection!") }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        viewModelScope.launch {
            repository.getLead(lead).onEach {
                progressFlow.emit(false)
                it.onSuccess { res ->
                    leadItemFlow.value = res
                    navigateItemScreen.value = true
                    gotLoadedLead.value = true
                    onSuccess?.invoke(res.data.id)
                }
                it.onFailure { err ->
                    errorFlow.emit(err.message)
                }
            }.launchIn(viewModelScope)
        }
    }

    override fun gotCreateSuccess() {
        viewModelScope.launch {
            createLeadFlow.value = null
        }
    }
    override fun getStatuses() {
        if (!isConnected()) {
            viewModelScope.launch { errorFlow.emit("No internet connection!") }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        viewModelScope.launch {
            repository.getStatuses().onEach {
                progressFlow.emit(false)
                it.onSuccess { res ->
                    statuses.value = res
                    hasLoadedStatuses.value = true
                }
                it.onFailure { err ->
                    errorFlow.emit(err.message)
                }
            }.launchIn(viewModelScope)
        }
    }

    override fun gotError() {
        viewModelScope.launch {
            errorFlow.emit(null)
        }
    }

    override fun gotUpdateSuccess() {
        viewModelScope.launch {
            updateLeadFlow.value = null
        }
    }

    override fun refreshLeads() {
        repository.clearPagination()
        if (!isConnected()) {
            viewModelScope.launch { errorFlow.emit("No internet connection!") }
            return
        }
        viewModelScope.launch {
            progressFlow.emit(true)
        }
        viewModelScope.launch {
            repository.getLeads().onEach {
                progressFlow.emit(false)
                it.onSuccess { res ->
                    val list = leadsFlow.value?.toMutableList()
                    list?.addAll(res)
                    leadsFlow.value = list ?: listOf()
                }
                it.onFailure { err ->
                    errorFlow.emit(err.message)
                }
            }.launchIn(viewModelScope)
        }
    }

}