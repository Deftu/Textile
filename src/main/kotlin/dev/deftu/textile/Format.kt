package dev.deftu.textile

import net.minecraft.util.Formatting
import java.awt.Color

public enum class Format(
    public val code: Char,
    public val color: Color? = null
) {
    BLACK('0', Color(0x000000)),
    DARK_BLUE('1', Color(0x0000AA)),
    DARK_GREEN('2', Color(0x00AA00)),
    DARK_AQUA('3', Color(0x00AAAA)),
    DARK_RED('4', Color(0xAA0000)),
    DARK_PURPLE('5', Color(0xAA00AA)),
    GOLD('6', Color(0xFFAA00)),
    GRAY('7', Color(0xAAAAAA)),
    DARK_GRAY('8', Color(0x555555)),
    BLUE('9', Color(0x5555FF)),
    GREEN('a', Color(0x55FF55)),
    AQUA('b', Color(0x55FFFF)),
    RED('c', Color(0xFF5555)),
    LIGHT_PURPLE('d', Color(0xFF55FF)),
    YELLOW('e', Color(0xFFFF55)),
    WHITE('f', Color(0xFFFFFF)),
    OBFUSCATED('k'),
    BOLD('l'),
    STRIKETHROUGH('m'),
    UNDERLINE('n'),
    ITALIC('o'),
    RESET('r');

    public val isColor: Boolean
        get() = color != null
    public val isFormat: Boolean
        get() = !isColor

    public operator fun plus(other: Format): String = "$this$other"
    public operator fun plus(other: String): String = "$this$other"
    public operator fun plus(other: Text): Text = other.format(this)

    override fun toString(): String = "$COLOR_CHAR$code"

    public companion object {

        public const val COLOR_CHAR: Char = '\u00a7'
        public val FORMATTING_CODE_PATTERN: Regex = Regex("§[0-9a-fk-or]", RegexOption.IGNORE_CASE)

        @Suppress("EnumValuesSoftDeprecate")
        public val VANILLA_MAPPED: Map<Format, Formatting> = values().associateWith { formatting ->
            when (formatting) {
                BLACK -> Formatting.BLACK
                DARK_BLUE -> Formatting.DARK_BLUE
                DARK_GREEN -> Formatting.DARK_GREEN
                DARK_AQUA -> Formatting.DARK_AQUA
                DARK_RED -> Formatting.DARK_RED
                DARK_PURPLE -> Formatting.DARK_PURPLE
                GOLD -> Formatting.GOLD
                GRAY -> Formatting.GRAY
                DARK_GRAY -> Formatting.DARK_GRAY
                BLUE -> Formatting.BLUE
                GREEN -> Formatting.GREEN
                AQUA -> Formatting.AQUA
                RED -> Formatting.RED
                LIGHT_PURPLE -> Formatting.LIGHT_PURPLE
                YELLOW -> Formatting.YELLOW
                WHITE -> Formatting.WHITE
                OBFUSCATED -> Formatting.OBFUSCATED
                BOLD -> Formatting.BOLD
                STRIKETHROUGH -> Formatting.STRIKETHROUGH
                UNDERLINE -> Formatting.UNDERLINE
                ITALIC -> Formatting.ITALIC
                RESET -> Formatting.RESET
            }
        }

        public fun from(code: Char): Format = values().first { it.code == code }
        public fun from(code: String): Format = from(code.firstOrNull() ?: RESET.code)

        public fun translateAlternateColorCodes(altColorChar: Char, textToTranslate: String): String {
            val b = textToTranslate.toCharArray()
            for (i in 0 until b.size - 1) {
                if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                    b[i] = COLOR_CHAR
                    b[i + 1] = Character.toLowerCase(b[i + 1])
                }
            }
            return String(b)
        }

        public fun strip(text: String): String = text.replace(COLOR_CHAR, '§').replace(FORMATTING_CODE_PATTERN, "")

        public fun stripColor(text: String): String = text.replace(COLOR_CHAR, '§').replace(FORMATTING_CODE_PATTERN) {
            if (it.value[1] in '0'..'9') "" else it.value
        }

        public fun stripFormat(text: String): String = text.replace(COLOR_CHAR, '§').replace(FORMATTING_CODE_PATTERN) {
            if (it.value[1] in 'a'..'r') "" else it.value
        }

    }

    public class TextFormattingComparator : Comparator<Format> {

        override fun compare(o1: Format, o2: Format): Int {
            return if (o1.isColor && o2.isColor) {
                // Colors are sorted by their ordinal
                o1.ordinal - o2.ordinal
            } else if (o1.isFormat && o2.isFormat) {
                // Formats are sorted by their ordinal
                o1.ordinal - o2.ordinal
            } else if (o1.isColor && o2.isFormat) {
                // Colors are sorted before formats
                -1
            } else if (o1.isFormat && o2.isColor) {
                // Formats are sorted after colors
                1
            } else {
                // This should never happen
                0
            }
        }

    }
}
