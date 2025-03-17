package dev.deftu.textile.minecraft

import dev.deftu.textile.ValueBackedTextHolder

public open class MCSimpleTextHolder(
    public override var content: String
) : MCTextHolder<MCSimpleTextHolder>, ValueBackedTextHolder<MCSimpleTextHolder, MCTextFormat>(content) {

    override var clickEvent: MCClickEvent? = null
        protected set

    override var hoverEvent: MCHoverEvent? = null
        protected set

    override fun copy(): MCSimpleTextHolder {
        return MCSimpleTextHolder(content).apply {
            clickEvent = this@MCSimpleTextHolder.clickEvent
            hoverEvent = this@MCSimpleTextHolder.hoverEvent
        }
    }

    override fun withoutClickEvent(): MCSimpleTextHolder {
        return copy().apply {
            clickEvent = null
        }
    }

    override fun withClickEvent(event: MCClickEvent): MCSimpleTextHolder {
        return copy().apply {
            clickEvent = event
        }
    }

    override fun withoutHoverEvent(): MCSimpleTextHolder {
        return copy().apply {
            hoverEvent = null
        }
    }

    override fun withHoverEvent(event: MCHoverEvent): MCSimpleTextHolder {
        return copy().apply {
            hoverEvent = event
        }
    }

    override fun toString(): String {
        return "MCSimpleTextHolder(content='$content', formatting=$_formatting, children=$_children, clickEvent=$clickEvent, hoverEvent=$hoverEvent)"
    }

}
