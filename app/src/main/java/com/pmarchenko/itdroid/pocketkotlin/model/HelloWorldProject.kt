package com.pmarchenko.itdroid.pocketkotlin.model

/**
 * @author Pavel Marchenko
 */
class HelloWorldProject : SimpleKotlinProject("Hello world") {

    init {
        setCode("""
            package com.test.helloworld
            
            fun main() {
            TODO("hello:)")
                println("Hello World")
            }
        """.trimIndent())
    }
}