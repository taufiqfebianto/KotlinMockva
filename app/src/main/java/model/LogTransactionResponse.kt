package model

data class LogTransactionResponse(
    val id: String?,
    val accountSrcId: String?,
    val accountDstId: String?,
    val clientRef: String?,
    val amount: Double?,
    val transactionTimestamp: String?
)