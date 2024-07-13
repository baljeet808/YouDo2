package domain.use_cases.database_operations

import domain.repository_interfaces.DatabaseOperationsRepository


class DeleteAllTablesUseCase(
    private val repository: DatabaseOperationsRepository
){
    suspend operator fun invoke()  {
        return repository.deleteAllTables()
    }
}