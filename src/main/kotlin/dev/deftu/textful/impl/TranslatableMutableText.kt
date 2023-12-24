package dev.deftu.textful.impl

import net.minecraft.client.resource.language.I18n
import dev.deftu.textful.MutableText
import dev.deftu.textful.Text

public class TranslatableMutableText(
    key: String,
    vararg args: Any
) : TranslatableText(key, *args), MutableText {
    private val _content = StringBuilder(key)
    override var content: String = I18n.translate(key, *args)
        get() = _content.toString()

    private val _children = mutableListOf<Pair<Text.TextChildPosition, Text>>()
    override val children: MutableList<Pair<Text.TextChildPosition, Text>>
        get() = _children

    override fun copy(): TranslatableMutableText = TranslatableMutableText(key, *args).apply {
        formatting.addAll(this@TranslatableMutableText.formatting)
        children.addAll(this@TranslatableMutableText.children)
    }

    override fun asTruncated(maxLength: Int): TranslatableMutableText = copy().apply {
        truncate(maxLength)
    }

    override fun set(text: Text): TranslatableMutableText = apply {
        _content.clear()
        _content.append(text)
    }

    override fun set(text: () -> Text): TranslatableMutableText = set(text())

    override fun prepend(text: Text): TranslatableMutableText = apply {
        _children.add(Text.TextChildPosition.BEFORE to text)
    }

    override fun prepend(text: () -> Text): TranslatableMutableText = prepend(text())

    override fun append(text: Text): TranslatableMutableText = apply {
        _children.add(Text.TextChildPosition.AFTER to text)
    }

    override fun append(text: () -> Text): TranslatableMutableText = append(text())

    override fun truncate(maxLength: Int): TranslatableMutableText = apply {
        _content.clear()
        _content.append(asTruncatedString(maxLength))
    }

    override fun truncate(maxLength: () -> Int): TranslatableMutableText = apply {
        truncate(maxLength())
    }
}
