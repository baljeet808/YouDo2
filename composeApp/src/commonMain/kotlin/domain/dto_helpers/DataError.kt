package domain.dto_helpers

sealed interface DataError : Error {
    val message: String?

    // Network-related errors
    enum class Network(override val message: String) : DataError {
        NO_INTERNET("No internet connection available."),
        ALL_OTHER("An unknown network error occurred."),
        NOT_FOUND("The requested resource was not found."),
        FORM_NOT_VALID("The submitted form is invalid."),
        UNAUTHORIZED("You are not authorized to perform this action."),
        FORBIDDEN("Access to the requested resource is forbidden."),
        CONFLICT("A conflict occurred with the requested resource.")
    }

    // Custom exception case for runtime exceptions or unexpected errors
    data class CustomException(
        override val message: String
    ) : DataError
}
