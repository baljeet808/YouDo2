package data.local.mappers

import data.local.entities.UserEntity
import domain.models.User


fun User.toUserEntity() : UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        joined = joined,
        avatarUrl = avatarUrl
    )
}


fun UserEntity.toUser(): User{
    return User(
        id = id,
        name = name,
        email = email,
        joined = joined,
        avatarUrl = avatarUrl
    )
}