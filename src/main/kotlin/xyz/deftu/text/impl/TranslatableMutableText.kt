package xyz.deftu.text.impl

import net.minecraft.client.resource.language.I18n
import xyz.deftu.text.MutableText
import xyz.deftu.text.Text

class TranslatableMutableText(
    key: String,
    vararg args: Any
) : TranslatableText(key, *args), MutableText {
    private val _content = StringBuilder(key)
    override var content: String = I18n.translate(key, *args)
        get() = _content.toString()

    private val _children = mutableListOf<Pair<Text.TextChildPosition, Text>>()
    override val children: MutableList<Pair<Text.TextChildPosition, Text>>
        get() = _children

    override fun copy() = TranslatableMutableText(key, *args).apply {
        formatting.addAll(this@TranslatableMutableText.formatting)
        children.addAll(this@TranslatableMutableText.children)
    }

    override fun asTruncated(maxLength: Int) = copy().apply {
        truncate(maxLength)
    }

    override fun set(text: Any) = apply {
        _content.clear()
        _content.append(text)
    }

    override fun set(text: () -> Any) = set(text())

    override fun prepend(text: Any) = apply {
        _children.add(Text.TextChildPosition.BEFORE to Text.from(text))
    }

    override fun prepend(text: () -> Any) = prepend(text())

    override fun append(text: Any) = apply {
        _children.add(Text.TextChildPosition.AFTER to Text.from(text))
    }

    override fun append(text: () -> Any) = append(text())

    override fun truncate(maxLength: Int) = apply {
        _content.clear()
        _content.append(asTruncatedString(maxLength))
    }

    override fun truncate(maxLength: () -> Int) = apply {
        truncate(maxLength())
    }
}
