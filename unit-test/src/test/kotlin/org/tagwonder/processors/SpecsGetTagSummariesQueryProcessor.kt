package org.tagwonder.processors

import autoparams.AutoSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.tagwonder.entities.Tag
import org.tagwonder.queries.GetTagSummariesQuery
import org.tagwonder.queries.TagSummaryContract
import org.tagwonder.repositories.ITagRepository
import org.tagwonder.usecases.queries.GetTagSummariesQueryProcessor
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@SpringBootTest
class SpecsGetTagSummariesQueryProcessor(
    @Autowired val tagRepository: ITagRepository
) {

    @BeforeEach
    fun init(){
        tagRepository.deleteAll()
    }

    @ParameterizedTest
    @AutoSource
    fun `Sut는 TagSummaryContract 리스트를 반환한다`(
        memberId: Long,
        titles: List<String>,
        writer: String,
        @Min(1) @Max(3) size: Int
    ) {
        //Arrange
        val sut = GetTagSummariesQueryProcessor(tagRepository)
        val query = GetTagSummariesQuery(memberId)
        titles.forEach { title -> arrangeTags(memberId, title, writer, size) }

        //Act
        val response = sut.process(query)

        //Assert
        val actual = response.data
        assertThat(actual.all { it is TagSummaryContract }).isTrue
    }

    @ParameterizedTest
    @AutoSource
    fun `Sut는 Tag의 Title로 GroupBy 한 리스트를 반환해야한다`(
        memberId: Long,
        titles: List<String>,
        writer: String,
        @Min(1) @Max(3) size: Int
    ) {
        //Arrange
        val sut = GetTagSummariesQueryProcessor(tagRepository)
        val query = GetTagSummariesQuery(memberId)
        titles.forEach { title -> arrangeTags(memberId, title, writer, size) }

        //Act
        val response = sut.process(query)

        //Assert
        val actual = response.data
        assertThat(actual.size).isEqualTo(titles.size)
        assertThat(actual.sortedBy { it.title }.map { it.title }).isEqualTo(titles.sorted())
    }

    @ParameterizedTest
    @AutoSource
    fun `Sut는 반환된 값의 Writers 속성에 같은 Title을 작성한 사람들이 그룹핑되어야 한다`(
        memberId: Long,
        title: String,
        writers: List<String>,
        @Min(1) @Max(1) size: Int
    ) {
        //Arrange
        val sut = GetTagSummariesQueryProcessor(tagRepository)
        val query = GetTagSummariesQuery(memberId)

        writers.forEach { writer -> arrangeTags(memberId, title, writer, size) }

        //Act
        val response = sut.process(query)

        //Assert
        val actual = response.data
        assertThat(actual).hasSize(1)
        assertThat(actual[0].writers).isEqualTo(writers)
    }

    @ParameterizedTest
    @AutoSource
    fun `Sut는 반환된 값의 Writers 속성에 같은 값이 있다면, 1부터 증가하는 숫자를 ()안에 넣어 접미사에 추가되어야한다`(
        memberId: Long,
        title: String,
        @Min(1) @Max(1) size: Int
    ) {
        val writers = listOf("A", "A", "A", "B", "C")
        val expectedWriters = listOf("A", "A(1)", "A(2)", "B", "C")

        //Arrange
        val sut = GetTagSummariesQueryProcessor(tagRepository)
        val query = GetTagSummariesQuery(memberId)

        writers.forEach { writer -> arrangeTags(memberId, title, writer, size) }

        //Act
        val response = sut.process(query)

        //Assert
        val actual = response.data
        assertThat(actual).hasSize(1)
        assertThat(actual[0].writers).isEqualTo(expectedWriters)
    }

    private fun arrangeTags(memberId: Long, title: String, writer: String, size: Int) {
        tagRepository.creates(
            (0 until size).map { _ ->
                Tag(
                    id = null,
                    title = title,
                    memberId = memberId,
                    writer = writer
                )
            }
        )
    }
}
