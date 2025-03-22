package dev.deftu.textile.minecraft

import net.minecraft.client.resource.language.I18n

public class MCTranslatableMutableTextHolder(
    public val key: String,
    public val args: Array<Any>
) : MCSimpleMutableTextHolder(I18n.translate(key, *args)) {

    public companion object {

        @JvmStatic
        public fun fromImmutable(text: MCTranslatableTextHolder): MCTranslatableMutableTextHolder {
            return MCTranslatableMutableTextHolder(text.key, text.args).apply {
                _children.addAll(text.children)
                _formatting.addAll(text.formatting)
                clickEvent = text.clickEvent
                hoverEvent = text.hoverEvent
            }
        }

    }

    public constructor(key: String): this(key, emptyArray())

    override fun copy(): MCSimpleMutableTextHolder {
        return MCTranslatableMutableTextHolder(key, args).apply {
            _children.addAll(this@MCTranslatableMutableTextHolder.children)
            _formatting.addAll(this@MCTranslatableMutableTextHolder.formatting)
            clickEvent = this@MCTranslatableMutableTextHolder.clickEvent
            hoverEvent = this@MCTranslatableMutableTextHolder.hoverEvent
        }
    }

    override fun toString(): String {
        return "MCTranslatableMutableTextHolder(content='$content', formatting=$_formatting, children=$_children, clickEvent=$clickEvent, hoverEvent=$hoverEvent, key='$key', args=${args.contentToString()})"
    }

}
