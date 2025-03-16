package dev.deftu.textile

public class SimpleTextHolder(content: String) : ValueBackedTextHolder<SimpleTextHolder, TextFormat>(content) {

    override fun copy(): SimpleTextHolder {
        return SimpleTextHolder(content).apply {
            _children.addAll(this@SimpleTextHolder.children)
            _formatting.addAll(this@SimpleTextHolder.formatting)
        }
    }

}
