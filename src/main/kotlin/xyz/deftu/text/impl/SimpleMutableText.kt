package xyz.deftu.text.impl

import xyz.deftu.text.MutableText
import xyz.deftu.text.Text

public class SimpleMutableText(
    content: String
) : SimpleText(content), MutableText {
    private val _content = StringBuilder(content)
    override var content: String = content
        get() = _content.toString()

    private val _children = mutableListOf<Pair<Text.TextChildPosition, Text>>()
    override var children: MutableList<Pair<Text.TextChildPosition, Text>> = _children
        get() = _children

    override fun copy(): SimpleMutableText = SimpleMutableText(content).apply {
        formatting.addAll(this@SimpleMutableText.formatting)
        children.addAll(this@SimpleMutableText.children)
    }

    override fun asTruncated(maxLength: Int): SimpleMutableText = copy().apply {
        truncate(maxLength)
    }

    override fun set(text: Any): SimpleMutableText = copy().apply {
        _content.setLength(0)
        _content.append(text)
    }

    override fun set(text: () -> Any): SimpleMutableText = set(text())

    override fun prepend(text: Any): SimpleMutableText = apply {
        _children.add(Text.TextChildPosition.BEFORE to Text.from(text))
    }

    override fun prepend(text: () -> Any): SimpleMutableText = prepend(text())

    override fun append(text: Any): SimpleMutableText = apply {
        _children.add(Text.TextChildPosition.AFTER to Text.from(text))
    }

    override fun append(text: () -> Any): SimpleMutableText = append(text())

    override fun truncate(maxLength: Int): SimpleMutableText = apply {
        _content.setLength(0)
        _content.append(asTruncatedString(maxLength))
    }

    override fun truncate(maxLength: () -> Int): SimpleMutableText = apply {
        truncate(maxLength())
    }
}
