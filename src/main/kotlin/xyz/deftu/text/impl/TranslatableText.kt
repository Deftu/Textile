package xyz.deftu.text.impl

import net.minecraft.client.resource.language.I18n
import xyz.deftu.text.Text
import xyz.deftu.text.TextFormatting

open class TranslatableText(
    val key: String,
    vararg val args: Any
): Text {
    open var content: String = I18n.translate(key, *args)
    override val formatting = mutableListOf<TextFormatting>()
    override val children = mutableListOf<Pair<Text.TextChildPosition, Text>>()
    
    override fun copy() = TranslatableText(key, *args).apply {
        formatting.addAll(this@TranslatableText.formatting)
        children.addAll(this@TranslatableText.children)
    }

    override fun asTruncatedString(maxLength: Int) = asContentString().substring(0, maxLength)
    override fun asTruncated(maxLength: Int) = copy().apply {
        content = asTruncatedString(maxLength)
    }

    override fun asContentString() = asString()
    override fun asFormattedContentString(): String {
        val builder = StringBuilder()
        formatting.sortedWith(TextFormatting.TextFormattingComparator()).forEach(builder::append)
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
        builder.append(TextFormatting.RESET)
        formatting.forEach(builder::append)
        builder.append(I18n.translate(key, *args))
        builder.append(TextFormatting.RESET)
        children.filter { it.first == Text.TextChildPosition.AFTER }.forEach { builder.append(it.second.asFormattedString()) }
        return builder.toString()
    }

    override fun replace(key: String, value: Any) = copy().apply { content = content.replace(key, value.toString()) }
    override fun replace(key: String, value: () -> Any) = copy().apply { content = content.replace(key, value().toString()) }

    override fun replaceFirst(key: String, value: Any) = copy().apply { content = content.replaceFirst(key, value.toString()) }
    override fun replaceFirst(key: String, value: () -> Any) = copy().apply { content = content.replaceFirst(key, value().toString()) }

    override fun format(vararg formatting: TextFormatting) = copy().apply {
        this.formatting.addAll(formatting)
    }

    override fun format(vararg formatting: () -> TextFormatting) = copy().apply {
        this.formatting.addAll(formatting.map { it() })
    }
}
