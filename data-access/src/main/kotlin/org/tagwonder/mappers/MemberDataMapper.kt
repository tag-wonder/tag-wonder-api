package org.tagwonder.mappers

import org.tagwonder.entities.Member
import org.tagwonder.models.MemberDataModel

class MemberDataMapper {
    fun toEntity(dataModel: MemberDataModel): Member {
        return Member(
            id = dataModel.id!!,
            email = dataModel.email,
            nickname = dataModel.nickname,
            oAuthProvider = dataModel.oAuthProvider
        )
    }

    fun toDataModel(entity: Member): MemberDataModel {
        return MemberDataModel(
            id = entity.id,
            email = entity.email,
            nickname = entity.nickname,
            oAuthProvider = entity.oAuthProvider
        )
    }
}
