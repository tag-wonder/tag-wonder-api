package org.tagwonder.repositories

import org.tagwonder.entities.Tag
import org.tagwonder.models.TagDataModel

class TagRepositoryImpl(
    private val database: TagJpaRepository
) : ITagRepository {
    override fun getList(memberId: Long): List<Tag> {
        return database.findAll()
            .map { it.toEntity() }
    }

    override fun creates(tags: List<Tag>) {
        database.saveAll(
            tags.map { tag ->
                TagDataModel(
                    id = tag.id,
                    contents = tag.contents,
                    memberId = tag.memberId
                )
            }
        )
    }
}
