package dev.deftu.textile.minecraft

import dev.deftu.textile.TextStyle

public object MCTextStyleProperties {
    @JvmField public val BOLD_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey("minecraft:bold")
    @JvmField public val ITALIC_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey("minecraft:italic")
    @JvmField public val UNDERLINED_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey("minecraft:underlined")
    @JvmField public val STRIKETHROUGH_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey("minecraft:strikethrough")
    @JvmField public val OBFUSCATED_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey("minecraft:obfuscated")
    @JvmField public val COLOR_KEY: TextStyle.PropertyKey<TextColor> = TextStyle.PropertyKey("minecraft:color")
    @JvmField public val CLICK_EVENT_KEY: TextStyle.PropertyKey<ClickEvent> = TextStyle.PropertyKey("minecraft:click_event")
    @JvmField public val HOVER_EVENT_KEY: TextStyle.PropertyKey<HoverEvent> = TextStyle.PropertyKey("minecraft:hover_event")

    @JvmStatic
    public fun bold(bold: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(BOLD_KEY, bold, left = if (bold) "${MCTextStyle.COLOR_CHAR}l" else null)
    }

    @JvmStatic
    public fun italic(italic: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(ITALIC_KEY, italic, left = if (italic) "${MCTextStyle.COLOR_CHAR}o" else null)
    }

    @JvmStatic
    public fun underlined(underlined: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(UNDERLINED_KEY, underlined, left = if (underlined) "${MCTextStyle.COLOR_CHAR}n" else null)
    }

    @JvmStatic
    public fun strikethrough(strikethrough: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(STRIKETHROUGH_KEY, strikethrough, left = if (strikethrough) "${MCTextStyle.COLOR_CHAR}m" else null)
    }

    @JvmStatic
    public fun obfuscated(obfuscated: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(OBFUSCATED_KEY, obfuscated, left = if (obfuscated) "${MCTextStyle.COLOR_CHAR}k" else null)
    }

    @JvmStatic
    public fun color(color: TextColor): TextStyle.Property<TextColor> {
        return TextStyle.Property(COLOR_KEY, color, left = if (!color.isCustom) color.formatting?.toString() else null)
    }

    @JvmStatic
    public fun clickEvent(event: ClickEvent): TextStyle.Property<ClickEvent> {
        return TextStyle.Property(CLICK_EVENT_KEY, event)
    }

    @JvmStatic
    public fun hoverEvent(event: HoverEvent): TextStyle.Property<HoverEvent> {
        return TextStyle.Property(HOVER_EVENT_KEY, event)
    }
}
