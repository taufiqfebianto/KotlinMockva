package model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransferInquiryResponse(
    val inquiryId: String?,
    val accountSrcId: String?,
    val accountDstId: String?,
    val accountSrcName: String?,
    val accountDstName: String?,
    val amount: Double?,
): Parcelable