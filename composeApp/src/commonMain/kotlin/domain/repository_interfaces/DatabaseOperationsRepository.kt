package domain.repository_interfaces

interface DatabaseOperationsRepository {
     suspend fun deleteAllTables()
}