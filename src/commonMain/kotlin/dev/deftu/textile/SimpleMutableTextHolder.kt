package dev.deftu.textile

import kotlin.jvm.JvmStatic

public class SimpleMutableTextHolder(content: String) : ValueBackedMutableTextHolder<SimpleMutableTextHolder, TextFormat>(content) {

    public companion object {

        @JvmStatic
        public fun fromImmutable(text: SimpleTextHolder): SimpleMutableTextHolder {
            return SimpleMutableTextHolder(text.content).apply {
                _children.addAll(text.children)
                _formatting.addAll(text.formatting)
            }
        }

    }

    override fun copy(): SimpleMutableTextHolder {
        return SimpleMutableTextHolder(content).apply {
            _children.addAll(this@SimpleMutableTextHolder.children)
            _formatting.addAll(this@SimpleMutableTextHolder.formatting)
        }
    }

    override fun toString(): String {
        return "SimpleMutableTextHolder(content='$_content', cache='$content', isDirty=$isDirty, children=$_children, formatting=$_formatting)"
    }

}
