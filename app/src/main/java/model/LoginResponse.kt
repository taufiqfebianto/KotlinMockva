package model

data class LoginResponse(
    val id: String?,
    val accountId: String?,
    val lastLoginTimestamp: String?,
    val sessionStatus: String?
)