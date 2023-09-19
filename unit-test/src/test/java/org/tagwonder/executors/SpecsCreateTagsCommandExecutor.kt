package org.tagwonder.executors

import autoparams.AutoSource
import com.healingpaper.solution.domain.exceptions.InvalidCommandException
import com.healingpaper.solution.domain.exceptions.InvalidRequestException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.tagwonder.commands.CreateTagsCommand
import org.tagwonder.entities.Tag
import org.tagwonder.repositories.ITagRepository
import org.tagwonder.usecases.commands.CreateTagsCommandExecutor

@SpringBootTest
class SpecsCreateTagsCommandExecutor(
    @Autowired val tagRepository: ITagRepository
) {

    @BeforeEach
    fun init(){
        tagRepository.deleteAll()
    }

    @ParameterizedTest
    @AutoSource
    fun `Sut는 요청 커맨드의 Titles 배열의 갯수만큼 Tag를 만들어야한다`(
        title1: String,
        title2: String,
        memberId: Long
    ) {
        //Arrange
        val sut = CreateTagsCommandExecutor(tagRepository)
        val command = CreateTagsCommand(
            titles = listOf(
                title1.substring(0 until 10),
                title2.substring(0 until 10)
            ),
            memberId = memberId
        )

        //Act
        sut.execute(command)

        //Assert
        val actual = tagRepository.getList(memberId)
        assertThat(actual.size).isEqualTo(command.titles.size)
    }

    @ParameterizedTest
    @AutoSource
    fun `Sut는 요청 커맨드의 Titles 배열의 요소가 Tag의 Title이 되어야한다`(
        title: String,
        memberId: Long
    ) {
        //Arrange
        val sut = CreateTagsCommandExecutor(tagRepository)
        val command = CreateTagsCommand(
            titles = listOf(title.substring(0 until 10)),
            memberId = memberId
        )

        //Act
        sut.execute(command)

        //Assert
        val actual = tagRepository.getList(memberId)
        assertThat(actual).hasSize(1)
        assertThat(actual[0].title).contains(command.titles[0])
    }

    @ParameterizedTest
    @AutoSource
    fun `Sut는 요청 커맨드의 MemberId가 생성 된 tag의 MemberId에 주입되어야한다`(
        title: String,
        memberId: Long
    ) {
        //Arrange
        val sut = CreateTagsCommandExecutor(tagRepository)
        val command = CreateTagsCommand(
            titles = listOf(title.substring(0 until 10)),
            memberId = memberId
        )

        //Act
        sut.execute(command)

        //Assert
        val actual = tagRepository.getList(memberId)
        val firstActual = actual.first()
        assertThat(firstActual.memberId).isEqualTo(command.memberId)
    }

    @ParameterizedTest
    @AutoSource
    fun `Sut는 요청 커맨드의 Titles 요소 중 하나라도 길이가 15가 넘을 경우, InvalidCommandException을 발생시킨다`(
        title: String,
        memberId: Long
    ) {
        //Arrange
        val sut = CreateTagsCommandExecutor(tagRepository)
        val command = CreateTagsCommand(
            titles = listOf(title.substring(0 until 20)),
            memberId = memberId
        )

        //Act
        var actual: InvalidCommandException? = null
        try {
            sut.execute(command)
        } catch (e: InvalidCommandException) {
            actual = e
        }

        //Assert
        assertThat(actual).isNotNull
        assertThat(actual).isInstanceOf(InvalidCommandException::class.java)
    }

    @ParameterizedTest
    @AutoSource
    fun `Sut는 요청 커맨드의 Titles 요소 간 중복이 있을 경우, InvalidCommandException을 발생시킨다`(
        title: String,
        memberId: Long
    ) {
        //Arrange
        val sut = CreateTagsCommandExecutor(tagRepository)
        val command = CreateTagsCommand(
            titles = listOf(
                title.substring(0 until 10),
                title.substring(0 until 10)
            ),
            memberId = memberId
        )

        //Act
        var actual: InvalidCommandException? = null
        try {
            sut.execute(command)
        } catch (e: InvalidCommandException) {
            actual = e
        }

        //Assert
        assertThat(actual).isNotNull
        assertThat(actual).isInstanceOf(InvalidCommandException::class.java)
    }

    @ParameterizedTest
    @AutoSource
    fun `Sut는 요청 커맨드의 Titles 요소가 해당 멤버의 태그에 같은 타이틀로 영속되어있는 경우, InvalidRequestException을 발생시킨다`(
        title: String,
        memberId: Long,
        tag: Tag
    ) {
        //Arrange
        val sut = CreateTagsCommandExecutor(tagRepository)
        val validTitle = title.substring(0 until 10)

        tagRepository.creates(
            listOf(
                tag.copy(title = validTitle)
            )
        )

        val command = CreateTagsCommand(
            titles = listOf(validTitle),
            memberId = memberId
        )

        //Act
        var actual: InvalidRequestException? = null
        try {
            sut.execute(command)
        } catch (e: InvalidRequestException) {
            actual = e
        }

        //Assert
        assertThat(actual).isNotNull
        assertThat(actual).isInstanceOf(InvalidRequestException::class.java)
    }
}
