package dev.deftu.textile.minecraft

import dev.deftu.textile.Text
import net.minecraft.network.chat.ClickEvent as VanillaClickEvent
import net.minecraft.network.chat.HoverEvent as VanillaHoverEvent
import net.minecraft.network.chat.Component as VanillaText

public fun Text.asVanilla(): VanillaText {
    return MCText.convert(this)
}

public fun VanillaText.asTextile(): Text {
    return MCText.wrap(this)
}

public fun VanillaClickEvent.asTextile(): ClickEvent {
    return ClickEvents.wrap(this)
}

public fun HoverEvent.asVanilla(): VanillaHoverEvent {
    return HoverEvents.convert(this)
}

public fun VanillaHoverEvent.asTextile(): HoverEvent? {
    return HoverEvents.wrap(this)
}
