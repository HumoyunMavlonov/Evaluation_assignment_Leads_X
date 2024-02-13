package uz.humoyun.evaluationassignmentleadsx.data.response

data class LeadsGeneral(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val createdAt: String,
    val status: StatusData,
    val avatar: AvatarDto,
    val countryDto: CountryDto?
)

data class AvatarDto(
    val path: String? = null,
    val name: String? = null,
)