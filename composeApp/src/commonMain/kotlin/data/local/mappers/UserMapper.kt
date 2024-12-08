package data.local.mappers

import data.local.entities.UserEntity
import domain.models.User


fun User.toUserEntity() : UserEntity {
    return UserEntity(
        id = id,
        name = name,
        email = email,
        joined = joined,
        avatarUrl = avatarUrl,
        sharingCcode = sharingCode
    )
}


fun UserEntity.toUser(): User{
    return User(
        id = id,
        name = name,
        email = email,
        joined = joined,
        avatarUrl = avatarUrl,
        sharingCode = sharingCcode
    )
}