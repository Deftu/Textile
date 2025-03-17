package dev.deftu.textile

import java.util.*

public abstract class ValueBackedTextHolder<T : ValueBackedTextHolder<T, F>, F : TextFormat>(
    public open var content: String
) : TextHolder<T, F> {

    protected val _children: MutableList<TextHolder<*, *>> = mutableListOf()
    protected val _formatting: MutableSet<F> = mutableSetOf()

    override val children: List<TextHolder<*, *>>
        get() = _children.toList()

    override val formatting: Set<F>
        get() = _formatting.toSet()

    override fun <T> visit(visitor: TextHolderVisitor<T>): Optional<out T> {
        val optional = visitor.accept(content)
        if (optional.isPresent) {
            return optional
        } else {
            for (child in children) {
                val result = child.visit(visitor)
                if (result.isPresent) {
                    return result
                }
            }

            return Optional.empty<T>()
        }
    }

    override fun asString(): String {
        val builder = StringBuilder()
        formatting.forEach(builder::append)
        builder.append(content)
        children.forEach { child -> builder.append(child.asString()) }
        return builder.toString()
    }

    override fun asUnformattedString(): String {
        val builder = StringBuilder()
        builder.append(content)
        children.forEach { child -> builder.append(child.asUnformattedString()) }
        return builder.toString()
    }

    override fun asExclusiveString(): String {
        val builder = StringBuilder()
        formatting.forEach(builder::append)
        builder.append(content)
        return builder.toString()
    }

    override fun withFormatting(vararg formatting: F): T = copy().apply {
        _formatting.addAll(formatting)
    }

    override fun toString(): String {
        return "ValueBackedTextHolder(content='$content', formatting=$_formatting, children=$_children)"
    }

}
