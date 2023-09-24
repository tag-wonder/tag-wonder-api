package org.tagwonder.usecases.queries

import org.tagwonder.queries.GetTagsQuery
import org.tagwonder.queries.GetTagsQueryResponse
import org.tagwonder.queries.TagContract
import org.tagwonder.repositories.ITagRepository

class GetTagsQueryProcessor(
    private val tagRepository: ITagRepository
) {

    fun process(query: GetTagsQuery): GetTagsQueryResponse {
        return GetTagsQueryResponse(
            data = tagRepository.getList(query.memberId)
                .map {
                    TagContract(
                    id = it.id!!,
                    title = it.title,
                    memberId = it.memberId
                ) }
        )
    }
}
