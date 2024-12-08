package presentation.shared.shareCodeGenerator.helper

sealed class CodeGeneratorScreenEvent {
    data class RegenerateCode(val userId : String) : CodeGeneratorScreenEvent()
}