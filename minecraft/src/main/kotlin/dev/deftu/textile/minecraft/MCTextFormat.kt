package dev.deftu.textile.minecraft

import dev.deftu.textile.TextFormat
import net.minecraft.util.Formatting
import java.awt.Color

public class MCTextFormat private constructor(
    public val code: Char,
    public override val color: Color? = null
) : TextFormat(color) {

    public val isColor: Boolean
        get() = color != null

    public val isFormat: Boolean
        get() = !isColor

    public fun asVanilla(): Formatting {
        return convertToVanilla(this)
    }

    /**
     * Sorts the two formats via [COMPARATOR] then returns the result of concatenating the two format codes.
     */
    public operator fun plus(other: MCTextFormat): String {
        return listOf(this, other).sortedWith(COMPARATOR).joinToString("", transform = MCTextFormat::toString)
    }

    override fun toString(): String {
        return "$COLOR_CHAR$code"
    }

    public companion object {

        public const val COLOR_CHAR: Char = '\u00a7'

        @JvmField
        public val COMPARATOR: MinecraftTextFormatComparator = noInline { MinecraftTextFormatComparator() }

        @JvmField
        public val FORMATTING_CODE_PATTERN: Regex = noInline { Regex("§[0-9a-fk-or]", RegexOption.IGNORE_CASE) }

        @JvmField
        public val BLACK: MCTextFormat = noInline { MCTextFormat('0', Color(0x000000)) }

        @JvmField
        public val DARK_BLUE: MCTextFormat = noInline { MCTextFormat('1', Color(0x0000AA)) }

        @JvmField
        public val DARK_GREEN: MCTextFormat = noInline { MCTextFormat('2', Color(0x00AA00)) }

        @JvmField
        public val DARK_AQUA: MCTextFormat = noInline { MCTextFormat('3', Color(0x00AAAA)) }

        @JvmField
        public val DARK_RED: MCTextFormat = noInline { MCTextFormat('4', Color(0xAA0000)) }

        @JvmField
        public val DARK_PURPLE: MCTextFormat = noInline { MCTextFormat('5', Color(0xAA00AA)) }

        @JvmField
        public val GOLD: MCTextFormat = noInline { MCTextFormat('6', Color(0xFFAA00)) }

        @JvmField
        public val GRAY: MCTextFormat = noInline { MCTextFormat('7', Color(0xAAAAAA)) }

        @JvmField
        public val DARK_GRAY: MCTextFormat = noInline { MCTextFormat('8', Color(0x555555)) }

        @JvmField
        public val BLUE: MCTextFormat = noInline { MCTextFormat('9', Color(0x5555FF)) }

        @JvmField
        public val GREEN: MCTextFormat = noInline { MCTextFormat('a', Color(0x55FF55)) }

        @JvmField
        public val AQUA: MCTextFormat = noInline { MCTextFormat('b', Color(0x55FFFF)) }

        @JvmField
        public val RED: MCTextFormat = noInline { MCTextFormat('c', Color(0xFF5555)) }

        @JvmField
        public val LIGHT_PURPLE: MCTextFormat = noInline { MCTextFormat('d', Color(0xFF55FF)) }

        @JvmField
        public val YELLOW: MCTextFormat = noInline { MCTextFormat('e', Color(0xFFFF55)) }

        @JvmField
        public val WHITE: MCTextFormat = noInline { MCTextFormat('f', Color(0xFFFFFF)) }

        @JvmField
        public val OBFUSCATED: MCTextFormat = noInline { MCTextFormat('k') }

        @JvmField
        public val BOLD: MCTextFormat = noInline { MCTextFormat('l') }

        @JvmField
        public val STRIKETHROUGH: MCTextFormat = noInline { MCTextFormat('m') }

        @JvmField
        public val UNDERLINE: MCTextFormat = noInline { MCTextFormat('n') }

        @JvmField
        public val ITALIC: MCTextFormat = noInline { MCTextFormat('o') }

        @JvmField
        public val RESET: MCTextFormat = noInline { MCTextFormat('r') }

        @JvmField
        public val entries: List<MCTextFormat> = noInline {
            listOf(
                BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY,
                DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE,
                OBFUSCATED, BOLD, STRIKETHROUGH, UNDERLINE, ITALIC, RESET
            )
        }

        @JvmField
        public val VANILLA_MAPPED_FORMATTING: Map<MCTextFormat, Formatting> = noInline {
            entries.associateWith { formatting ->
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
                    else -> Formatting.RESET
                }
            }
        }

        @JvmStatic
        public fun values(): Array<MCTextFormat> {
            return entries.toTypedArray()
        }

        @JvmStatic
        public fun from(code: Char): MCTextFormat {
            return when (code) {
                '0' -> BLACK
                '1' -> DARK_BLUE
                '2' -> DARK_GREEN
                '3' -> DARK_AQUA
                '4' -> DARK_RED
                '5' -> DARK_PURPLE
                '6' -> GOLD
                '7' -> GRAY
                '8' -> DARK_GRAY
                '9' -> BLUE
                'a' -> GREEN
                'b' -> AQUA
                'c' -> RED
                'd' -> LIGHT_PURPLE
                'e' -> YELLOW
                'f' -> WHITE
                'k' -> OBFUSCATED
                'l' -> BOLD
                'm' -> STRIKETHROUGH
                'n' -> UNDERLINE
                'o' -> ITALIC
                'r' -> RESET
                else -> throw IllegalArgumentException("Invalid color code: $code")
            }
        }

        @JvmStatic
        public fun from(code: String): MCTextFormat {
            val char = code.firstOrNull() ?: return RESET
            return from(char)
        }

        @JvmStatic
        public fun convertFromVanilla(formatting: Formatting): MCTextFormat {
            return entries.firstOrNull { it.asVanilla() == formatting } ?: RESET
        }

        @JvmStatic
        public fun convertToVanilla(format: MCTextFormat): Formatting {
            return VANILLA_MAPPED_FORMATTING[format] ?: Formatting.RESET
        }

        @JvmStatic
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

        @JvmStatic
        public fun strip(text: String): String {
            return text.replace(COLOR_CHAR, '§').replace(FORMATTING_CODE_PATTERN, "")
        }

        @JvmStatic
        public fun stripColor(text: String): String {
            return text.replace(COLOR_CHAR, '§').replace(FORMATTING_CODE_PATTERN) {
                if (it.value[1] in '0'..'9') "" else it.value
            }
        }

        @JvmStatic
        public fun stripFormat(text: String): String {
            return text.replace(COLOR_CHAR, '§').replace(FORMATTING_CODE_PATTERN) {
                if (it.value[1] in 'a'..'r') "" else it.value
            }
        }

        private inline fun <T> noInline(init: () -> T): T = init()

    }

    public class MinecraftTextFormatComparator : Comparator<MCTextFormat> {

        /**
         * Sorts colors before formats.
         * Resets are always last.
         */
        override fun compare(o1: MCTextFormat, o2: MCTextFormat): Int {
            if (o1.isColor && o2.isFormat) {
                return -1
            }

            if (o1.isFormat && o2.isColor) {
                return 1
            }

            return o1.code.compareTo(o2.code)
        }

    }

}
