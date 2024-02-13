package uz.humoyun.evaluationassignmentleadsx.data.response


import uz.humoyun.evaluationassignmentleadsx.AllLeadsQuery
import uz.humoyun.evaluationassignmentleadsx.util.toDateTime

data class LeadsPaginated(
    val cursor: String?,
    val hasMore: Boolean,
    val totalCount: Int,
    val data: List<LeadsGeneral>
)

fun AllLeadsQuery.Data.toPaginatedResponse(): LeadsPaginated {
    return LeadsPaginated(
        cursor = this.fetchLeads.cursor,
        hasMore = this.fetchLeads.hasMore,
        totalCount = this.fetchLeads.totalCount,
        data = (this.fetchLeads.data.map {
            LeadsGeneral(
                id = it.id,
                firstName = it.firstName ?: "",
                lastName = it.lastName ?: "",
                createdAt = it.createdAt?.toDateTime() ?: "",
                status = StatusData(
                    id = it.status?.id ?: 0,
                    step = it . status ?. step ?: 0,
                    stepsCount = it.status?.stepsCount ?: 0,
                    title = it.status?.title ?: "",
                    color = it.status?.color ?: "",
                    backgroundColor = it.status?.backgroundColor ?: ""
                ),
                avatar = AvatarDto(it.avatar?.path),
                countryDto = CountryDto(0, "", "", it.country?.emoji ?: "", it.country?.title ?: "")
            )
        })
    )
}