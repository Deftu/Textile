package xyz.deftu.text.impl

import net.minecraft.client.resource.language.I18n
import xyz.deftu.text.MutableText
import xyz.deftu.text.Text

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

    override fun set(text: Any): TranslatableMutableText = apply {
        _content.clear()
        _content.append(text)
    }

    override fun set(text: () -> Any): TranslatableMutableText = set(text())

    override fun prepend(text: Any): TranslatableMutableText = apply {
        _children.add(Text.TextChildPosition.BEFORE to Text.from(text))
    }

    override fun prepend(text: () -> Any): TranslatableMutableText = prepend(text())

    override fun append(text: Any): TranslatableMutableText = apply {
        _children.add(Text.TextChildPosition.AFTER to Text.from(text))
    }

    override fun append(text: () -> Any): TranslatableMutableText = append(text())

    override fun truncate(maxLength: Int): TranslatableMutableText = apply {
        _content.clear()
        _content.append(asTruncatedString(maxLength))
    }

    override fun truncate(maxLength: () -> Int): TranslatableMutableText = apply {
        truncate(maxLength())
    }
}
