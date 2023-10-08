package org.tagwonder.repositories

import org.tagwonder.entities.Tag

interface ITagRepository {
    fun getList(memberId: Long): List<Tag>
    fun creates(tags: List<Tag>)
    fun find(memberId: Long, title: String): Tag?
    fun deleteAll()
}
