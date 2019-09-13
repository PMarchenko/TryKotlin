package com.pmarchenko.itdroid.pocketkotlin.model.project

/**
 * @author Pavel Marchenko
 */
class HelloWorldProject : SimpleKotlinProject("Hello world", "HelloWorld.kt", "HelloWorldProject") {

    init {
        setCode(
            """
                /**
                 * Simple 'Hello world' example!
                 */
                fun main() {
                    println("Hello world!")
                }
                """.trimIndent()
        )
    }
}