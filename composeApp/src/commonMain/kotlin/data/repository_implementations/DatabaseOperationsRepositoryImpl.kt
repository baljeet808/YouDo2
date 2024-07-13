package data.repository_implementations

import data.local.room.YouDo2Database
import domain.repository_interfaces.DatabaseOperationsRepository


class DatabaseOperationsRepositoryImpl (
    private val localDB: YouDo2Database
) : DatabaseOperationsRepository {
    override suspend fun deleteAllTables() {
        localDB.clearAllTables()
    }
}