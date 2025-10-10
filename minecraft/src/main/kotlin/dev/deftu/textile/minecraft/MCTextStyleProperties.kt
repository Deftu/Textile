package dev.deftu.textile.minecraft

import dev.deftu.textile.TextStyle

public object MCTextStyleProperties {
    @JvmField public val COLOR_KEY: TextStyle.PropertyKey<TextColor> = TextStyle.PropertyKey(name = "minecraft:color", sortIndex = 10)
    @JvmField public val OBFUSCATED_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey(name = "minecraft:obfuscated", sortIndex = 20)
    @JvmField public val BOLD_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey(name = "minecraft:bold", sortIndex = 30)
    @JvmField public val STRIKETHROUGH_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey(name = "minecraft:strikethrough", sortIndex = 40)
    @JvmField public val UNDERLINED_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey(name = "minecraft:underlined", sortIndex = 50)
    @JvmField public val ITALIC_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey(name = "minecraft:italic", sortIndex = 60)
    @JvmField public val CLICK_EVENT_KEY: TextStyle.PropertyKey<ClickEvent> = TextStyle.PropertyKey(name = "minecraft:click_event", sortIndex = 100)
    @JvmField public val HOVER_EVENT_KEY: TextStyle.PropertyKey<HoverEvent> = TextStyle.PropertyKey(name = "minecraft:hover_event", sortIndex = 110)

    @JvmStatic
    public fun bold(bold: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(BOLD_KEY, bold, left = if (bold) FormattingCodes.BOLD else null)
    }

    @JvmStatic
    public fun italic(italic: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(ITALIC_KEY, italic, left = if (italic) FormattingCodes.ITALIC else null)
    }

    @JvmStatic
    public fun underlined(underlined: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(UNDERLINED_KEY, underlined, left = if (underlined) FormattingCodes.UNDERLINED else null)
    }

    @JvmStatic
    public fun strikethrough(strikethrough: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(STRIKETHROUGH_KEY, strikethrough, left = if (strikethrough) FormattingCodes.STRIKETHROUGH else null)
    }

    @JvmStatic
    public fun obfuscated(obfuscated: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(OBFUSCATED_KEY, obfuscated, left = if (obfuscated) FormattingCodes.OBFUSCATED else null)
    }

    @JvmStatic
    public fun color(color: TextColor): TextStyle.Property<TextColor> {
        return TextStyle.Property(COLOR_KEY, color, left = color.formattingString)
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
