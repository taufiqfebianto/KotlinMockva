package model

data class TransferConfirmationRequest(
    val accountSrcId: String?,
    val accountDstId: String?,
    val amount: Double?,
    val inquiryId: String?
)