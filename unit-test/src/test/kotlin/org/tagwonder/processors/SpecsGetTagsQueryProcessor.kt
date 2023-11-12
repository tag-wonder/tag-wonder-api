package org.tagwonder.processors

import autoparams.AutoSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.tagwonder.entities.Tag
import org.tagwonder.queries.GetTagsQuery
import org.tagwonder.queries.GetTagsQueryResponse
import org.tagwonder.repositories.ITagRepository
import org.tagwonder.usecases.queries.GetTagsQueryProcessor
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@SpringBootTest
class SpecsGetTagsQueryProcessor(
    @Autowired val tagRepository: ITagRepository
) {

    @BeforeEach
    fun init(){
        tagRepository.deleteAll()
    }

    @ParameterizedTest
    @AutoSource
    fun `Sut는 요청 Query의 MemberId에 해당하는 Tag들의 정보로 TagQueryResponse를 반환한다`(
        expectedMemberId: Long,
        expectedTitle: String,
        @Min(1) @Max(10) size: Int
    ) {
        //Arrange
        val sut = GetTagsQueryProcessor(tagRepository)
        val query = GetTagsQuery(expectedMemberId)
        arrangeTags(expectedMemberId, expectedTitle,  size)

        //Act
        val response = sut.process(query)

        //Assert
        val actual = response.data
        assertThat(response).isInstanceOf(GetTagsQueryResponse::class.java)
        assertThat(actual.size).isEqualTo(size)
        actual.forEach { actualData ->
            assertThat(actualData.id).isNotNull
            assertThat(actualData.title).isEqualTo(expectedTitle)
        }
    }

    private fun arrangeTags(memberId: Long, title: String, size: Int) {
        tagRepository.creates(
            (0 until size).map { _ ->
                Tag(
                    id = null,
                    title = title,
                    memberId = memberId
                )
            }
        )
    }
}
