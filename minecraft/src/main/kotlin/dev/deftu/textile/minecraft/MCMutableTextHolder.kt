package dev.deftu.textile.minecraft

import dev.deftu.textile.MutableTextHolder

public interface MCMutableTextHolder<T : MCMutableTextHolder<T>> : MCTextHolder<T>, MutableTextHolder<T, MCTextFormat> {

    public fun setClickEvent(event: MCClickEvent?): T

    public fun setHoverEvent(event: MCHoverEvent?): T

}
