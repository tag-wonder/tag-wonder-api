package org.tagwonder.repositories

import org.tagwonder.entities.Tag

interface ITagRepository {
    fun getList(memberId: Long)
    fun creates(tags: List<Tag>)
}
