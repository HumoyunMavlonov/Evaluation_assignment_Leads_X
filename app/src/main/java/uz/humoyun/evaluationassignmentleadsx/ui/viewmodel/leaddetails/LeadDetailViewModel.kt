package uz.humoyun.evaluationassignmentleadsx.ui.viewmodel.leaddetails

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.humoyun.evaluationassignmentleadsx.data.response.LeadDetailed
import uz.humoyun.evaluationassignmentleadsx.data.response.LeadsGeneral
import uz.humoyun.evaluationassignmentleadsx.data.response.StatusData
import uz.humoyun.evaluationassignmentleadsx.type.FetchLeadInput
import uz.humoyun.evaluationassignmentleadsx.type.UpdateLeadInput

interface LeadDetailViewModel {
    val updateLeadFlow: LiveData<LeadDetailed?>
    val firstInit: LiveData<Boolean>
    val errorFlow: Flow<String?>
    val progressFlow: Flow<Boolean>
    val leadItemFlow: LiveData<LeadDetailed?>
    val updateProgressFlow: Flow<Boolean>
    val gotUpdatedLead: LiveData<Boolean>
    val leadsFlow: LiveData<List<LeadsGeneral>>
    val createLeadFlow: LiveData<LeadDetailed?>
    val statuses: LiveData<List<StatusData>>
    val hasLoadedStatuses: LiveData<Boolean>
    val navigateItemScreen: LiveData<Boolean>
    val gotLoadedLead: LiveData<Boolean>

    fun firstInit()
    fun gotNavigateToItemScreen()
    fun refreshLeads()
    fun updateLead(updateLeadInput: UpdateLeadInput)
    fun getLead(lead: FetchLeadInput, onSuccess: ((Int) -> Unit)? = null)
    fun gotError()
    fun gotCreateSuccess()

    fun gotUpdateSuccess()
    fun getStatuses()

}