package dev.deftu.textile

public open class SimpleMutableTextHolder(
    content: String
) : SimpleTextHolder(content), MutableTextHolder {

    private val _content = StringBuilder(content)
    override var content: String
        get() = _content.toString()
        set(value) {
            _content.setLength(0)
            _content.append(value)
        }

    override fun set(textHolder: TextHolder): SimpleMutableTextHolder = apply {
        content = textHolder.asLeafString()
        _children.addAll(textHolder.children)
        _formatting.addAll(textHolder.formatting)
    }

    override fun set(text: String): SimpleMutableTextHolder = apply {
        content = text
    }

    override fun append(textHolder: TextHolder): SimpleMutableTextHolder = apply {
        _children.add(textHolder)
    }

    override fun format(vararg formatting: TextFormat): SimpleMutableTextHolder {
        _formatting.addAll(formatting)
        return this
    }

    override fun replace(key: String, value: Any): SimpleMutableTextHolder {
        content = content.replace(key, value.toString())
        return this
    }

    override fun replaceFirst(key: String, value: Any): SimpleMutableTextHolder {
        content = content.replaceFirst(key, value.toString())
        return this
    }

    override fun replaceLast(key: String, value: Any): SimpleMutableTextHolder {
        val lastIndex = content.lastIndexOf(key)
        if (lastIndex == -1) {
            return this
        }

        content = content.substring(0, lastIndex) + value + content.substring(lastIndex + key.length)
        return this
    }

}
