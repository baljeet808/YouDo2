package domain.use_cases.database_operations_use_cases

import domain.repository_interfaces.DatabaseOperationsRepository


class DeleteAllTablesUseCase(
    private val repository: DatabaseOperationsRepository
){
    suspend operator fun invoke()  {
        return repository.deleteAllTables()
    }
}