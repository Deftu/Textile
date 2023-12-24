package dev.deftu.textful.impl

import dev.deftu.textful.MutableText
import dev.deftu.textful.Text

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

    override fun set(text: Text): SimpleMutableText = copy().apply {
        _content.setLength(0)
        _content.append(text)
    }

    override fun set(text: () -> Text): SimpleMutableText = set(text())

    override fun prepend(text: Text): SimpleMutableText = apply {
        _children.add(Text.TextChildPosition.BEFORE to text)
    }

    override fun prepend(text: () -> Text): SimpleMutableText = prepend(text())

    override fun append(text: Text): SimpleMutableText = apply {
        _children.add(Text.TextChildPosition.AFTER to text)
    }

    override fun append(text: () -> Text): SimpleMutableText = append(text())

    override fun truncate(maxLength: Int): SimpleMutableText = apply {
        _content.setLength(0)
        _content.append(asTruncatedString(maxLength))
    }

    override fun truncate(maxLength: () -> Int): SimpleMutableText = apply {
        truncate(maxLength())
    }
}
