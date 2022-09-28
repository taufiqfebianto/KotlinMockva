package model

data class TransferInquiryRequest(
    val accountSrcId: String?,
    val accountDstId: String?,
    val amount: Double?,
)