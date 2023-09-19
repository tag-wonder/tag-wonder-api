package org.tagwonder.mappers

import org.tagwonder.entities.Tag
import org.tagwonder.models.TagDataModel

class TagDataMapper {
    fun toEntity(dataModel: TagDataModel): Tag {
        return Tag(
            id = dataModel.id!!,
            title = dataModel.title,
            memberId = dataModel.memberId
        )
    }

    fun toDataModel(entity: Tag): TagDataModel {
        return TagDataModel(
            id = entity.id,
            title = entity.title,
            memberId = entity.memberId
        )
    }
}
