package dev.deftu.textile.ansi

import dev.deftu.textile.TextStyle
import dev.deftu.textile.TextStyleBuilder
import kotlin.jvm.JvmOverloads

public data class AnsiTextStyle @JvmOverloads public constructor(
    public val foreground: Int? = null, // 0xRRGGBB
    public val background: Int? = null, // 0xRRGGBB
    public val isBold: Boolean? = null,
    public val isItalic: Boolean? = null,
    public val isUnderlined: Boolean? = null,
    public val isStrikethrough: Boolean? = null,
    public val colorLevel: ColorLevel = ColorLevel.TRUECOLOR,
    public val autoReset: Boolean = true,
) : TextStyleBuilder {
    public fun withForeground(rgb: Int?): AnsiTextStyle {
        return if (rgb == foreground) this else copy(foreground = rgb)
    }

    public fun withBackground(rgb: Int?): AnsiTextStyle {
        return if (rgb == background) this else copy(background = rgb)
    }

    public fun setBold(bold: Boolean?): AnsiTextStyle {
        return if (bold == this.isBold) this else copy(isBold = bold)
    }

    public fun bold(): AnsiTextStyle {
        return setBold(true)
    }

    public fun notBold(): AnsiTextStyle {
        return setBold(false)
    }

    public fun setItalic(italic: Boolean?): AnsiTextStyle {
        return if (italic == this.isItalic) this else copy(isItalic = italic)
    }

    public fun italic(): AnsiTextStyle {
        return setItalic(true)
    }

    public fun notItalic(): AnsiTextStyle {
        return setItalic(false)
    }

    public fun setUnderline(underline: Boolean?): AnsiTextStyle {
        return if (underline == this.isUnderlined) this else copy(isUnderlined = underline)
    }

    public fun underline(): AnsiTextStyle {
        return setUnderline(true)
    }

    public fun notUnderline(): AnsiTextStyle {
        return setUnderline(false)
    }

    public fun setStrikethrough(strikethrough: Boolean?): AnsiTextStyle {
        return if (strikethrough == this.isStrikethrough) this else copy(isStrikethrough = strikethrough)
    }

    public fun strikethrough(): AnsiTextStyle {
        return setStrikethrough(true)
    }

    public fun notStrikethrough(): AnsiTextStyle {
        return setStrikethrough(false)
    }

    override fun build(): TextStyle {
        val props = mutableListOf<TextStyle.Property<*>>()
        isBold?.let { props.add(AnsiTextStyleProperties.bold(it)) }
        isItalic?.let { props.add(AnsiTextStyleProperties.italic(it)) }
        isUnderlined?.let { props.add(AnsiTextStyleProperties.underline(it)) }
        isStrikethrough?.let { props.add(AnsiTextStyleProperties.strikethrough(it)) }
        foreground?.let { rgb -> props.add(TextStyle.Property(key = AnsiTextStyleProperties.FG_COLOR_KEY, value = rgb, left = fgCode(rgb, colorLevel))) }
        background?.let { rgb -> props.add(TextStyle.Property(key = AnsiTextStyleProperties.BG_COLOR_KEY, value = rgb, left = bgCode(rgb, colorLevel))) }

        if (autoReset && props.isNotEmpty()) {
            props.add(AnsiTextStyleProperties.reset())
        }

        return TextStyle(props.associateBy(TextStyle.Property<*>::key))
    }

    private fun fgCode(rgb: Int, level: ColorLevel): String? {
        if (level == ColorLevel.NONE) {
            return null
        }

        return when (level) {
            ColorLevel.COLOR_16 -> {
                val index = basicAnsiIndex(rgb)
                "${AnsiText.ESC}[${30 + index}m"
            }

            ColorLevel.COLOR_256 -> {
                val index = rgbToAnsi256(rgb)
                "${AnsiText.ESC}[38;5;${index}m"
            }

            ColorLevel.TRUECOLOR -> {
                val r = (rgb shr 16) and 0xFF
                val g = (rgb shr 8) and 0xFF
                val b = rgb and 0xFF
                "${AnsiText.ESC}[38;2;${r};${g};${b}m"
            }

            ColorLevel.NONE -> null
        }
    }

    private fun bgCode(rgb: Int, level: ColorLevel): String? {
        if (level == ColorLevel.NONE) {
            return null
        }

        return when (level) {
            ColorLevel.COLOR_16 -> {
                val index = basicAnsiIndex(rgb)
                "${AnsiText.ESC}[${40 + index}m"
            }

            ColorLevel.COLOR_256 -> {
                val index = rgbToAnsi256(rgb)
                "${AnsiText.ESC}[48;5;${index}m"
            }

            ColorLevel.TRUECOLOR -> {
                val r = (rgb shr 16) and 0xFF
                val g = (rgb shr 8) and 0xFF
                val b = rgb and 0xFF
                "${AnsiText.ESC}[48;2;${r};${g};${b}m"
            }

            ColorLevel.NONE -> null
        }
    }

    /**
     * "Good enough" heuristics, not perfect.
     */
    private fun basicAnsiIndex(rgb: Int): Int {
        val r = (rgb shr 16) and 0xFF
        val g = (rgb shr 8) and 0xFF
        val b = rgb and 0xFF

        return when {
            r < 40 && g < 40 && b < 40 -> 0  // black
            r > 200 && g < 60 && b < 60 -> 1 // red
            g > 200 && r < 60 && b < 60 -> 2 // green
            b > 200 && r < 60 && g < 60 -> 4 // blue
            else -> 7 // white-ish
        }
    }

    private fun rgbToAnsi256(rgb: Int): Int {
        val r = (rgb shr 16) and 0xFF
        val g = (rgb shr 8) and 0xFF
        val b = rgb and 0xFF

        fun toCube(v: Int): Int = when {
            v < 48 -> 0
            v < 115 -> 1
            else -> (v - 35) / 40
        }

        val rc = toCube(r)
        val gc = toCube(g)
        val bc = toCube(b)
        return 16 + 36 * rc + 6 * gc + bc
    }
}
