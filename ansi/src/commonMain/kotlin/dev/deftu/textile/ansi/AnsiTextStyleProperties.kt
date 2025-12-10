package dev.deftu.textile.ansi

import dev.deftu.textile.TextStyle
import kotlin.jvm.JvmField

public object AnsiTextStyleProperties {
    @JvmField public val FG_COLOR_KEY: TextStyle.PropertyKey<Int> = TextStyle.PropertyKey(name = "ansi:fg_color", sortIndex = 10)
    @JvmField public val BG_COLOR_KEY: TextStyle.PropertyKey<Int> = TextStyle.PropertyKey(name = "ansi:bg_color", sortIndex = 20)
    @JvmField public val BOLD_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey(name = "ansi:bold", sortIndex = 30)
    @JvmField public val ITALIC_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey(name = "ansi:italic", sortIndex = 40)
    @JvmField public val UNDERLINE_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey(name = "ansi:underline", sortIndex = 50)
    @JvmField public val STRIKETHROUGH_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey(name = "ansi:strikethrough", sortIndex = 60)
    @JvmField public val RESET_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey(name = "ansi:reset", sortIndex = 999)

    public fun bold(enabled: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(
            key = BOLD_KEY,
            value = enabled,
            left = if (enabled) "${AnsiText.ESC}[1m" else null,
        )
    }

    public fun italic(enabled: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(
            key = ITALIC_KEY,
            value = enabled,
            left = if (enabled) "${AnsiText.ESC}[3m" else null,
        )
    }

    public fun underline(enabled: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(
            key = UNDERLINE_KEY,
            value = enabled,
            left = if (enabled) "${AnsiText.ESC}[4m" else null,
        )
    }

    public fun strikethrough(enabled: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(
            key = STRIKETHROUGH_KEY,
            value = enabled,
            left = if (enabled) "${AnsiText.ESC}[9m" else null,
        )
    }

    /**
     * For plain "reset all styles".
     */
    public fun reset(): TextStyle.Property<Boolean> {
        return TextStyle.Property(
            key = RESET_KEY,
            value = true,
            right = "${AnsiText.ESC}[0m",
        )
    }
}
