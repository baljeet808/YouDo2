package presentation.shared.shareCodeGenerator.helper

import domain.dto_helpers.DataError

data class CodeGeneratorUIState(
    val isLoading : Boolean = false,
    val isCopying : Boolean = false,
    val error : DataError.Network? = null,
    val code : String = "",
)
