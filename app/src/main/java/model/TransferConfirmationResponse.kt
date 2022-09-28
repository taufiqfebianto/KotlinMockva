package model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransferConfirmationResponse(
    val accountSrcId: String?,
    val accountDstId: String?,
    val amount: Double?,
    val transactionTimestamp: String?,
    val clientRef: String?,
): Parcelable