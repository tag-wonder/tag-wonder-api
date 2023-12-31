package org.tagwonder.executors

import autoparams.AutoSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.tagwonder.commands.CreateTagsCommand
import org.tagwonder.entities.Tag
import org.tagwonder.exceptions.InvalidCommandException
import org.tagwonder.exceptions.InvalidRequestException
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
        writer: String,
        memberId: Long
    ) {
        //Arrange
        val sut = CreateTagsCommandExecutor(tagRepository)
        val command = CreateTagsCommand(
            titles = listOf(
                title1.substring(0 until 10),
                title2.substring(0 until 10)
            ),
            memberId = memberId,
            writer = writer
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
        memberId: Long,
        writer: String
    ) {
        //Arrange
        val sut = CreateTagsCommandExecutor(tagRepository)
        val command = CreateTagsCommand(
            titles = listOf(title.substring(0 until 10)),
            memberId = memberId,
            writer = writer
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
        writer: String,
        memberId: Long
    ) {
        //Arrange
        val sut = CreateTagsCommandExecutor(tagRepository)
        val command = CreateTagsCommand(
            titles = listOf(title.substring(0 until 10)),
            memberId = memberId,
            writer = writer
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
    fun `Sut는 요청 커맨드의 Titles 요소 중 하나라도 글자 수가 15가 넘을 경우, InvalidCommandException을 발생시킨다`(
        title: String,
        writer: String,
        memberId: Long
    ) {
        //Arrange
        val sut = CreateTagsCommandExecutor(tagRepository)
        val command = CreateTagsCommand(
            titles = listOf(title.substring(0 until 20)),
            memberId = memberId,
            writer = writer
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
        writer: String,
        memberId: Long
    ) {
        //Arrange
        val sut = CreateTagsCommandExecutor(tagRepository)
        val command = CreateTagsCommand(
            titles = listOf(
                title.substring(0 until 10),
                title.substring(0 until 10)
            ),
            memberId = memberId,
            writer = writer
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
        writer: String,
        tag: Tag
    ) {
        //Arrange
        val sut = CreateTagsCommandExecutor(tagRepository)
        val validTitle = title.substring(0 until 10)

        tagRepository.creates(
            listOf(
                tag.copy(
                    title = validTitle,
                    memberId = memberId
                )
            )
        )

        val command = CreateTagsCommand(
            titles = listOf(validTitle),
            memberId = memberId,
            writer = writer
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

    @ParameterizedTest
    @AutoSource
    fun `Sut는 요청 커맨드의 Titles 배열의 크기가 3개가 넘을 경우, InvalidCommandException을 발생시킨다`(
        title1: String,
        title2: String,
        title3: String,
        title4: String,
        writer: String,
        memberId: Long,
        tag: Tag
    ) {
        //Arrange
        val sut = CreateTagsCommandExecutor(tagRepository)
        val validTitle1 = title1.substring(0 until 10)
        val validTitle2 = title2.substring(0 until 10)
        val validTitle3 = title3.substring(0 until 10)
        val validTitle4 = title4.substring(0 until 10)

        val command = CreateTagsCommand(
            titles = listOf(validTitle1, validTitle2, validTitle3, validTitle4),
            memberId = memberId,
            writer = writer
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
}
