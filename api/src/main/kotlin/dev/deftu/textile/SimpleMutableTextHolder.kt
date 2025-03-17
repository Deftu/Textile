package dev.deftu.textile

public class SimpleMutableTextHolder(content: String) : ValueBackedMutableTextHolder<SimpleMutableTextHolder, TextFormat>(content) {

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
