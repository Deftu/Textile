package dev.deftu.textile.minecraft

import dev.deftu.textile.ValueBackedMutableTextHolder

public open class MCSimpleMutableTextHolder(
    content: String
) : MCMutableTextHolder<MCSimpleMutableTextHolder>, ValueBackedMutableTextHolder<MCSimpleMutableTextHolder, MCTextFormat>(content) {

    override var clickEvent: MCClickEvent? = null
        protected set

    override var hoverEvent: MCHoverEvent? = null
        protected set

    override fun copy(): MCSimpleMutableTextHolder {
        return MCSimpleMutableTextHolder(content).apply {
            _children.addAll(this@MCSimpleMutableTextHolder.children)
            _formatting.addAll(this@MCSimpleMutableTextHolder.formatting)
            clickEvent = this@MCSimpleMutableTextHolder.clickEvent
            hoverEvent = this@MCSimpleMutableTextHolder.hoverEvent
        }
    }

    override fun withoutClickEvent(): MCSimpleMutableTextHolder {
        return copy().apply {
            clickEvent = null
        }
    }

    override fun withClickEvent(event: MCClickEvent): MCSimpleMutableTextHolder {
        return copy().apply {
            clickEvent = event
        }
    }

    override fun withoutHoverEvent(): MCSimpleMutableTextHolder {
        return copy().apply {
            hoverEvent = null
        }
    }

    override fun withHoverEvent(event: MCHoverEvent): MCSimpleMutableTextHolder {
        return copy().apply {
            hoverEvent = event
        }
    }

    override fun setClickEvent(event: MCClickEvent?): MCSimpleMutableTextHolder {
        clickEvent = event
        return this
    }

    override fun setHoverEvent(event: MCHoverEvent?): MCSimpleMutableTextHolder {
        hoverEvent = event
        return this
    }

    override fun toString(): String {
        return "MCSimpleMutableTextHolder(content='$_content', cache='$content', isDirty=$isDirty, formatting=$_formatting, children=$_children, clickEvent=$clickEvent, hoverEvent=$hoverEvent)"
    }

}
