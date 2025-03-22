package dev.deftu.textile.minecraft

import net.minecraft.client.resource.language.I18n

public open class MCTranslatableTextHolder(
    public val key: String,
    public val args: Array<Any>
) : MCSimpleTextHolder(I18n.translate(key, *args)) {

    public constructor(key: String): this(key, emptyArray())

    override fun copy(): MCTranslatableTextHolder {
        return MCTranslatableTextHolder(key, args).apply {
            _children.addAll(this@MCTranslatableTextHolder.children)
            _formatting.addAll(this@MCTranslatableTextHolder.formatting)
            clickEvent = this@MCTranslatableTextHolder.clickEvent
            hoverEvent = this@MCTranslatableTextHolder.hoverEvent
        }
    }

    override fun toString(): String {
        return "MCTranslatableTextHolder(content='$content', formatting=$_formatting, children=$_children, clickEvent=$clickEvent, hoverEvent=$hoverEvent, key='$key', args=${args.contentToString()})"
    }

}
