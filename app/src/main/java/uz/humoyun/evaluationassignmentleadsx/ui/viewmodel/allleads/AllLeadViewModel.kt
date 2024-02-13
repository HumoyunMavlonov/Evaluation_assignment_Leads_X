package uz.humoyun.evaluationassignmentleadsx.ui.viewmodel.allleads

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.humoyun.evaluationassignmentleadsx.data.response.CountryDto
import uz.humoyun.evaluationassignmentleadsx.data.response.IntentionDto
import uz.humoyun.evaluationassignmentleadsx.data.response.LeadDetailed
import uz.humoyun.evaluationassignmentleadsx.data.response.LeadsGeneral
import uz.humoyun.evaluationassignmentleadsx.data.response.StatusData
import uz.humoyun.evaluationassignmentleadsx.type.FetchLeadInput
import uz.humoyun.evaluationassignmentleadsx.type.FetchLeadsInput

interface AllLeadViewModel {
    val updateLeadFlow: LiveData<LeadDetailed?>
    val progressFlow: Flow<Boolean>
    val errorFlow: Flow<String?>
    val leadsFlow: LiveData<List<LeadsGeneral>>
    val leadItemFlow: LiveData<LeadDetailed?>
    val gotLoadedLead: LiveData<Boolean>
    val navigateItemScreen: LiveData<Boolean>
    val statuses: LiveData<List<StatusData>>
    val hasLoadedStatuses: LiveData<Boolean>
    val firstInit: LiveData<Boolean>
    val createLeadFlow: LiveData<LeadDetailed?>

    fun firstInit()

    fun refreshLeads()

    fun gotCreateSuccess()
    fun getStatuses()
    fun gotUpdateSuccess()
    fun gotError()
    fun getLeads(filter: FetchLeadsInput? = null)
    fun getLead(lead: FetchLeadInput, onSuccess: ((Int) -> Unit)? = null)
    fun gotLoaded()

}