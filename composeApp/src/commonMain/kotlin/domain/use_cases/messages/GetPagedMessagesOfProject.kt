package domain.use_cases.messages

import domain.repository_interfaces.MessageRepository

class GetPagedMessagesOfProject(
    private val repository: MessageRepository
){
//    /*operator fun invoke(projectId : String) : Flow<PagingData<MessageEntity>> = Pager(
//        PagingConfig(
//            pageSize = 20,
//            prefetchDistance = 10
//        )
//    ){
//        repository.getAllMessageByProjectId(projectId)
//    }.flow*/
}