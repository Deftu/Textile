package dev.deftu.textile.minecraft

import net.minecraft.client.resource.language.I18n

public class MCTranslatableMutableTextHolder(
    public val key: String,
    public val args: Array<Any>
) : MCSimpleMutableTextHolder(I18n.translate(key, *args)) {

    public constructor(key: String): this(key, emptyArray())

    override fun copy(): MCSimpleMutableTextHolder {
        return MCTranslatableMutableTextHolder(key, args).apply {
            clickEvent = this@MCTranslatableMutableTextHolder.clickEvent
            hoverEvent = this@MCTranslatableMutableTextHolder.hoverEvent
        }
    }

    override fun toString(): String {
        return "MCTranslatableMutableTextHolder(content='$content', formatting=$_formatting, children=$_children, clickEvent=$clickEvent, hoverEvent=$hoverEvent, key='$key', args=${args.contentToString()})"
    }

}
