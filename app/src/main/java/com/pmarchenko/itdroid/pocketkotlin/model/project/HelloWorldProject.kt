package com.pmarchenko.itdroid.pocketkotlin.model.project

/**
 * @author Pavel Marchenko
 */
class HelloWorldProject :
        SimpleKotlinProject("Hello world", "HelloWorld.kt", "HelloWorldProject") {

    init {
        setCode(
                """
                /**
                 * Test
                 */
                fun main() {
                    println("Hello world!")
                }
                """.trimIndent())
    }
}