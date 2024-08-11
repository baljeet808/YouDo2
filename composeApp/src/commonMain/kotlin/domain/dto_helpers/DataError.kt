package domain.dto_helpers

sealed interface DataError: Error {
    enum class Network: DataError {
        NO_INTERNET,
        ALL_OTHER,
        NOT_FOUND,
    }
}