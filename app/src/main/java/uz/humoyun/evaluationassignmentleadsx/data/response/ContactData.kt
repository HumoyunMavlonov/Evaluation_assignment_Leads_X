package uz.humoyun.evaluationassignmentleadsx.data.response

data class ContactData(
    val data: List<ContactDto>
)

data class ContactDto(
    val phoneContact: PhoneCode,
    val emailContact: Email
)

data class PhoneCode(
    val phone: String,
    val title: String
)

data class Email(
    val email: String,
    val title: String
)