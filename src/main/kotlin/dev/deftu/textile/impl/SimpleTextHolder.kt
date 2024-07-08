package dev.deftu.textile.impl

import dev.deftu.textile.TextHolder
import dev.deftu.textile.TextFormat
import dev.deftu.textile.TextHolderVisitor
import java.util.*

public open class SimpleTextHolder(
    public open var content: String
) : TextHolder {

    internal val _children = mutableListOf<TextHolder>()
    internal val _formatting = mutableSetOf<TextFormat>()

    override val formatting: Set<TextFormat>
        get() = _formatting.toSet()
    override val children: List<TextHolder>
        get() = _children.toList()

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

    override fun copy(): SimpleTextHolder = SimpleTextHolder(content).apply {
        _formatting.addAll(this@SimpleTextHolder.formatting)
        _children.addAll(this@SimpleTextHolder.children)
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

    override fun asLeafString(): String {
        val builder = StringBuilder()
        formatting.forEach(builder::append)
        builder.append(content)
        return builder.toString()
    }

    override fun formatted(vararg formatting: TextFormat): TextHolder = copy().apply {
        _formatting.addAll(formatting)
    }

}
