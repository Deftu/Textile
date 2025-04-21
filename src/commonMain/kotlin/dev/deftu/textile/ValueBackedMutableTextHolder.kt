package dev.deftu.textile

@Suppress("UNCHECKED_CAST")
public abstract class ValueBackedMutableTextHolder<T : ValueBackedMutableTextHolder<T, F>, F : TextFormat>(
    content: String
) : MutableTextHolder<T, F>, ValueBackedTextHolder<T, F>(content) {

    protected val _content: StringBuilder = StringBuilder(content)
    protected var isDirty: Boolean = false

    override var content: String = content
        get() {
            if (isDirty) {
                field = _content.toString()
                isDirty = false
            }

            return field
        }
        set(value) {
            _content.setLength(0)
            _content.append(value)
            isDirty = true
        }

    override fun set(text: TextHolder<*, *>): T = apply {
        content = text.asExclusiveString()
        _children.addAll(text.children)
        _formatting.addAll(text.formatting as Set<F>)
    } as T

    override fun set(text: String): T = apply {
        content = text
    } as T

    override fun append(text: TextHolder<*, *>): T = apply {
        _children.add(text)
    } as T

    override fun addFormatting(vararg formatting: F): T {
        _formatting.addAll(formatting)
        return this as T
    }

    override fun setFormatting(vararg formatting: F): T {
        _formatting.clear()
        _formatting.addAll(formatting)
        return this as T
    }

    override fun replace(key: String, value: Any): T {
        content = content.replace(key, value.toString())
        return this as T
    }

    override fun replaceFirst(key: String, value: Any): T {
        content = content.replaceFirst(key, value.toString())
        return this as T
    }

    override fun replaceLast(key: String, value: Any): T {
        val lastIndex = content.lastIndexOf(key)
        if (lastIndex == -1) {
            return this as T
        }

        content = content.substring(0, lastIndex) + value + content.substring(lastIndex + key.length)
        return this as T
    }

    override fun toString(): String {
        return "ValueBackedMutableTextHolder(content='$_content', cache='$content', isDirty=$isDirty, formatting=$_formatting, children=$_children)"
    }

}
