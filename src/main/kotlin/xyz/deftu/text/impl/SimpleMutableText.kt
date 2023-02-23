package xyz.deftu.text.impl

import xyz.deftu.text.MutableText
import xyz.deftu.text.Text

class SimpleMutableText(
    content: String
) : SimpleText(content), MutableText {
    private val _content = StringBuilder(content)
    override var content: String = content
        get() = _content.toString()

    private val _children = mutableListOf<Pair<Text.TextChildPosition, Text>>()
    override var children: MutableList<Pair<Text.TextChildPosition, Text>> = _children
        get() = _children

    override fun copy() = SimpleMutableText(content).apply {
        formatting.addAll(this@SimpleMutableText.formatting)
        children.addAll(this@SimpleMutableText.children)
    }

    override fun asTruncated(maxLength: Int) = copy().apply {
        truncate(maxLength)
    }

    override fun set(text: Any) = copy().apply {
        _content.setLength(0)
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
        _content.setLength(0)
        _content.append(asTruncatedString(maxLength))
    }

    override fun truncate(maxLength: () -> Int) = apply {
        truncate(maxLength())
    }
}
