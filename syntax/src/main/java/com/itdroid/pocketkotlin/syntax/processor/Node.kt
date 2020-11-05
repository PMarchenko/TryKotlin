package com.itdroid.pocketkotlin.syntax.processor

/**
 * @author Pavel Marchenko
 */
internal sealed class Node {

    abstract val parent: Node

    val children: MutableList<Node> = mutableListOf()

    private fun describeSelf(indent: String): String =
        StringBuilder()
            .also {
                it.append("\n$indent ${this::class.java.simpleName}")
                for (child in children) {
                    it.append("$indent ${child.describeSelf("$indent  ")}")
                }
            }
            .toString()

    override fun toString(): String = describeSelf("")

    abstract fun forLParent(): Node

    abstract fun forLCurl(): Node

}

internal class Program() : Node() {
    override val parent: Node
        get() = this

    override fun forLParent(): Node = Block(this)

    override fun forLCurl(): Node = Block(this)
}

internal class Block(override val parent: Node) : Node() {

    override fun forLParent(): Node = Block(this)

    override fun forLCurl(): Node = Block(this)
}

internal class Class(override val parent: Node) : Node() {

    override fun forLParent(): Node = ClassConstructor(this)

    override fun forLCurl(): Node = ClassBody(this)
}

internal class ClassConstructor(override val parent: Node) : Node() {

    override fun forLParent() = Block(this)

    override fun forLCurl(): Node = Block(this)
}

internal class ClassBody(override val parent: Node) : Node() {

    override fun forLParent() = Block(this)

    override fun forLCurl(): Node = Block(this)
}

internal class Property(override val parent: Node) : Node() {

    override fun forLParent() = Block(this)

    override fun forLCurl(): Node = Block(this)
}

internal class Function(override val parent: Node) : Node() {

    override fun forLParent(): Node = FunctionParams(this)

    override fun forLCurl(): Node = FunctionBody(this)
}

internal class FunctionParams(override val parent: Node) : Node() {

    override fun forLParent() = Block(this)

    override fun forLCurl(): Node = Block(this)
}

internal class FunctionBody(override val parent: Node) : Node() {

    override fun forLParent() = Block(this)

    override fun forLCurl(): Node = Block(this)
}

internal class Annotation(val start: Int, override val parent: Node) : Node() {

    override fun forLParent() = Block(this)

    override fun forLCurl(): Node = Block(this)
}

internal data class Identifier(
    val start: Int,
    val end: Int,
    val name: String
)