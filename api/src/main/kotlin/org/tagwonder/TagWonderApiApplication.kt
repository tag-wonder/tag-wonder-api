package org.tagwonder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class TagWonderApiApplication

fun main(args: Array<String>) {
    runApplication<TagWonderApiApplication>(*args)
}
