package dev.deftu.textful.impl

import dev.deftu.textful.Text
import dev.deftu.textful.TextFormatting

public open class SimpleText(
    content: String
) : Text {
    public open var content: String = content
    override val formatting: MutableList<TextFormatting> = mutableListOf()
    override var children: MutableList<Pair<Text.TextChildPosition, Text>> = mutableListOf()

    override fun copy(): SimpleText = SimpleText(content).apply {
        formatting.addAll(this@SimpleText.formatting)
        children.addAll(this@SimpleText.children)
    }

    override fun asTruncatedString(maxLength: Int): String = asContentString().substring(0, maxLength)
    override fun asTruncated(maxLength: Int): SimpleText = copy().apply {
        content = asTruncatedString(maxLength)
    }

    override fun asContentString(): String = content
    override fun asFormattedContentString(): String {
        val builder = StringBuilder()
        formatting.sortedWith(TextFormatting.TextFormattingComparator()).forEach(builder::append)
        builder.append(asContentString())
        return builder.toString()
    }

    override fun asString(): String {
        val builder = StringBuilder()
        children.filter { it.first == Text.TextChildPosition.BEFORE }.forEach { builder.append(it.second.asString()) }
        builder.append(content)
        children.filter { it.first == Text.TextChildPosition.AFTER }.forEach { builder.append(it.second.asString()) }
        return builder.toString()
    }

    override fun asFormattedString(): String {
        val builder = StringBuilder()
        children.filter { it.first == Text.TextChildPosition.BEFORE }.forEach { builder.append(it.second.asFormattedString()) }
        builder.append(TextFormatting.RESET)
        formatting.forEach(builder::append)
        builder.append(content)
        builder.append(TextFormatting.RESET)
        children.filter { it.first == Text.TextChildPosition.AFTER }.forEach { builder.append(it.second.asFormattedString()) }
        return builder.toString()
    }

    override fun replace(key: String, value: Any): SimpleText = copy().apply { content = content.replace(key, value.toString()) }
    override fun replace(key: String, value: () -> Any): SimpleText = copy().apply { content = content.replace(key, value().toString()) }

    override fun replaceFirst(key: String, value: Any): SimpleText = copy().apply { content = content.replaceFirst(key, value.toString()) }
    override fun replaceFirst(key: String, value: () -> Any): SimpleText = copy().apply { content = content.replaceFirst(key, value().toString()) }

    override fun format(vararg formatting: TextFormatting): SimpleText = copy().apply {
        this.formatting.addAll(formatting)
    }

    override fun format(vararg formatting: () -> TextFormatting): SimpleText = copy().apply {
        this.formatting.addAll(formatting.map { it() })
    }
}
