package dev.deftu.textile

public class SimpleMutableTextHolder(content: String) : ValueBackedMutableTextHolder<SimpleMutableTextHolder, TextFormat>(content) {

    override fun copy(): SimpleMutableTextHolder {
        return SimpleMutableTextHolder(content).apply {
            _children.addAll(this@SimpleMutableTextHolder.children)
            _formatting.addAll(this@SimpleMutableTextHolder.formatting)
        }
    }

}
