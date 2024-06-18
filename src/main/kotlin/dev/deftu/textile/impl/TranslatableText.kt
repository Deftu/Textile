package dev.deftu.textile.impl

import net.minecraft.client.resource.language.I18n
import dev.deftu.textile.Text
import dev.deftu.textile.Format

public open class TranslatableText(
    public val key: String,
    public vararg val args: Any
): Text {
    public open var content: String = I18n.translate(key, *args)
    override val formatting: MutableList<Format> = mutableListOf()
    override val children: MutableList<Pair<Text.TextChildPosition, Text>> = mutableListOf()

    override fun copy(): TranslatableText = TranslatableText(key, *args).apply {
        formatting.addAll(this@TranslatableText.formatting)
        children.addAll(this@TranslatableText.children)
    }

    override fun asTruncatedString(maxLength: Int): String = asContentString().substring(0, maxLength)
    override fun asTruncated(maxLength: Int): TranslatableText = copy().apply {
        content = asTruncatedString(maxLength)
    }

    override fun asContentString(): String = asString()
    override fun asFormattedContentString(): String {
        val builder = StringBuilder()
        formatting.sortedWith(Format.TextFormattingComparator()).forEach(builder::append)
        builder.append(asContentString())
        return builder.toString()
    }

    override fun asString(): String {
        val builder = StringBuilder()
        children.filter { it.first == Text.TextChildPosition.BEFORE }.forEach { builder.append(it.second.asString()) }
        builder.append(I18n.translate(key, *args))
        children.filter { it.first == Text.TextChildPosition.AFTER }.forEach { builder.append(it.second.asString()) }
        return builder.toString()
    }

    override fun asFormattedString(): String {
        val builder = StringBuilder()
        children.filter { it.first == Text.TextChildPosition.BEFORE }.forEach { builder.append(it.second.asFormattedString()) }
        builder.append(Format.RESET)
        formatting.forEach(builder::append)
        builder.append(I18n.translate(key, *args))
        builder.append(Format.RESET)
        children.filter { it.first == Text.TextChildPosition.AFTER }.forEach { builder.append(it.second.asFormattedString()) }
        return builder.toString()
    }

    override fun replace(key: String, value: Any): TranslatableText = copy().apply { content = content.replace(key, value.toString()) }
    override fun replace(key: String, value: () -> Any): TranslatableText = copy().apply { content = content.replace(key, value().toString()) }

    override fun replaceFirst(key: String, value: Any): TranslatableText = copy().apply { content = content.replaceFirst(key, value.toString()) }
    override fun replaceFirst(key: String, value: () -> Any): TranslatableText = copy().apply { content = content.replaceFirst(key, value().toString()) }

    override fun format(vararg formatting: Format): TranslatableText = copy().apply {
        this.formatting.addAll(formatting)
    }

    override fun format(vararg formatting: () -> Format): TranslatableText = copy().apply {
        this.formatting.addAll(formatting.map { it() })
    }
}