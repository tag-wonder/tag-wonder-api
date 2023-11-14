package org.tagwonder.usecases.queries

import org.tagwonder.queries.*
import org.tagwonder.repositories.ITagRepository
import org.tagwonder.usecases.utils.hasDuplicate

class GetTagSummariesQueryProcessor(
    private val tagRepository: ITagRepository
) {

    fun process(query: GetTagSummariesQuery): GetTagSummariesQueryResponse {
        val tagMap = tagRepository.getList(query.memberId)
            .groupBy { it.title }

        return GetTagSummariesQueryResponse(
            data = tagMap.map {
                it -> TagSummaryContract(
                    title = it.key,
                    writtenCount = it.value.count(),
                    writers = it.value.map { it.writer }.let {
                        if(it.hasDuplicate()) annotateDuplicatedWriter(it) else it
                    }
                )
            }
        )
    }

    private fun annotateDuplicatedWriter(writers: List<String>): List<String> {
        val result = writers.foldIndexed(
            Pair(emptyList<String>(), emptyMap<String, Int>())) {
                _, (accList, countMap), writer ->
            val count = countMap.getOrDefault(writer, 0)
            val updatedCountMap = countMap + (writer to (count + 1))
            val updatedWriter = if (count > 0) "$writer($count)" else writer
            Pair(accList + updatedWriter, updatedCountMap)
        }

        return result.first
    }
}
