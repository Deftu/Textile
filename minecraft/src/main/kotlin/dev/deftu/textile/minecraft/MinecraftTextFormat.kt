package dev.deftu.textile.minecraft

import dev.deftu.textile.TextFormat
import java.awt.Color

public class MinecraftTextFormat private constructor(
    public val code: Char,
    public override val color: Color? = null
) : TextFormat(color) {

    public val isColor: Boolean
        get() = color != null

    public val isFormat: Boolean
        get() = !isColor

    /**
     * Sorts the two formats via [COMPARATOR] then returns the result of concatenating the two format codes.
     */
    public operator fun plus(other: MinecraftTextFormat): String {
        return listOf(this, other).sortedWith(COMPARATOR).joinToString("", transform = MinecraftTextFormat::toString)
    }

    override fun toString(): String {
        return "$COLOR_CHAR$code"
    }

    public companion object {

        public const val COLOR_CHAR: Char = '\u00a7'

        @JvmStatic
        public val COMPARATOR: MinecraftTextFormatComparator = MinecraftTextFormatComparator()

        @JvmStatic
        public val FORMATTING_CODE_PATTERN: Regex = Regex("§[0-9a-fk-or]", RegexOption.IGNORE_CASE)

        @JvmField
        public val BLACK: MinecraftTextFormat = noInline { MinecraftTextFormat('0', Color(0x000000)) }

        @JvmField
        public val DARK_BLUE: MinecraftTextFormat = noInline { MinecraftTextFormat('1', Color(0x0000AA)) }

        @JvmField
        public val DARK_GREEN: MinecraftTextFormat = noInline { MinecraftTextFormat('2', Color(0x00AA00)) }

        @JvmField
        public val DARK_AQUA: MinecraftTextFormat = noInline { MinecraftTextFormat('3', Color(0x00AAAA)) }

        @JvmField
        public val DARK_RED: MinecraftTextFormat = noInline { MinecraftTextFormat('4', Color(0xAA0000)) }

        @JvmField
        public val DARK_PURPLE: MinecraftTextFormat = noInline { MinecraftTextFormat('5', Color(0xAA00AA)) }

        @JvmField
        public val GOLD: MinecraftTextFormat = noInline { MinecraftTextFormat('6', Color(0xFFAA00)) }

        @JvmField
        public val GRAY: MinecraftTextFormat = noInline { MinecraftTextFormat('7', Color(0xAAAAAA)) }

        @JvmField
        public val DARK_GRAY: MinecraftTextFormat = noInline { MinecraftTextFormat('8', Color(0x555555)) }

        @JvmField
        public val BLUE: MinecraftTextFormat = noInline { MinecraftTextFormat('9', Color(0x5555FF)) }

        @JvmField
        public val GREEN: MinecraftTextFormat = noInline { MinecraftTextFormat('a', Color(0x55FF55)) }

        @JvmField
        public val AQUA: MinecraftTextFormat = noInline { MinecraftTextFormat('b', Color(0x55FFFF)) }

        @JvmField
        public val RED: MinecraftTextFormat = noInline { MinecraftTextFormat('c', Color(0xFF5555)) }

        @JvmField
        public val LIGHT_PURPLE: MinecraftTextFormat = noInline { MinecraftTextFormat('d', Color(0xFF55FF)) }

        @JvmField
        public val YELLOW: MinecraftTextFormat = noInline { MinecraftTextFormat('e', Color(0xFFFF55)) }

        @JvmField
        public val WHITE: MinecraftTextFormat = noInline { MinecraftTextFormat('f', Color(0xFFFFFF)) }

        @JvmField
        public val OBFUSCATED: MinecraftTextFormat = noInline { MinecraftTextFormat('k') }

        @JvmField
        public val BOLD: MinecraftTextFormat = noInline { MinecraftTextFormat('l') }

        @JvmField
        public val STRIKETHROUGH: MinecraftTextFormat = noInline { MinecraftTextFormat('m') }

        @JvmField
        public val UNDERLINE: MinecraftTextFormat = noInline { MinecraftTextFormat('n') }

        @JvmField
        public val ITALIC: MinecraftTextFormat = noInline { MinecraftTextFormat('o') }

        @JvmField
        public val RESET: MinecraftTextFormat = noInline { MinecraftTextFormat('r') }

        @JvmField
        public val entries: List<MinecraftTextFormat> = noInline {
            listOf(
                BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY,
                DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE,
                OBFUSCATED, BOLD, STRIKETHROUGH, UNDERLINE, ITALIC, RESET
            )
        }

        @JvmStatic
        public fun values(): Array<MinecraftTextFormat> {
            return entries.toTypedArray()
        }

        @JvmStatic
        public fun from(code: Char): MinecraftTextFormat {
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
        public fun from(code: String): MinecraftTextFormat {
            val char = code.firstOrNull() ?: return RESET
            return from(char)
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

        /**
         * Adapted from EssentialGG UniversalCraft under LGPL-3.0
         * https://github.com/EssentialGG/UniversalCraft/blob/f4917e139b5f6e5346c3bafb6f56ce8877854bf1/LICENSE
         */
        private inline fun <T> noInline(init: () -> T): T = init()

    }

    public class MinecraftTextFormatComparator : Comparator<MinecraftTextFormat> {

        /**
         * Sorts colors before formats.
         * Resets are always last.
         */
        override fun compare(o1: MinecraftTextFormat, o2: MinecraftTextFormat): Int {
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
