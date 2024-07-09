package dev.deftu.textile.minecraft

import dev.deftu.textile.TextFormat
import java.awt.Color

public class MinecraftTextFormat(
    public val code: Char,
    public override val color: Color? = null
) : TextFormat(color) {

    public val isColor: Boolean
        get() = color != null

    public val isFormat: Boolean
        get() = !isColor

    override fun toString(): String {
        return "$COLOR_CHAR$code"
    }

    public companion object {

        public const val COLOR_CHAR: Char = '\u00a7'

        @JvmStatic
        public val COMPARATOR: MinecraftTextFormatComparator = MinecraftTextFormatComparator()

        @JvmStatic
        public val FORMATTING_CODE_PATTERN: Regex = Regex("§[0-9a-fk-or]", RegexOption.IGNORE_CASE)

        @JvmStatic
        public val BLACK: MinecraftTextFormat = MinecraftTextFormat('0', Color(0x000000))

        @JvmStatic
        public val DARK_BLUE: MinecraftTextFormat = MinecraftTextFormat('1', Color(0x0000AA))

        @JvmStatic
        public val DARK_GREEN: MinecraftTextFormat = MinecraftTextFormat('2', Color(0x00AA00))

        @JvmStatic
        public val DARK_AQUA: MinecraftTextFormat = MinecraftTextFormat('3', Color(0x00AAAA))

        @JvmStatic
        public val DARK_RED: MinecraftTextFormat = MinecraftTextFormat('4', Color(0xAA0000))

        @JvmStatic
        public val DARK_PURPLE: MinecraftTextFormat = MinecraftTextFormat('5', Color(0xAA00AA))

        @JvmStatic
        public val GOLD: MinecraftTextFormat = MinecraftTextFormat('6', Color(0xFFAA00))

        @JvmStatic
        public val GRAY: MinecraftTextFormat = MinecraftTextFormat('7', Color(0xAAAAAA))

        @JvmStatic
        public val DARK_GRAY: MinecraftTextFormat = MinecraftTextFormat('8', Color(0x555555))

        @JvmStatic
        public val BLUE: MinecraftTextFormat = MinecraftTextFormat('9', Color(0x5555FF))

        @JvmStatic
        public val GREEN: MinecraftTextFormat = MinecraftTextFormat('a', Color(0x55FF55))

        @JvmStatic
        public val AQUA: MinecraftTextFormat = MinecraftTextFormat('b', Color(0x55FFFF))

        @JvmStatic
        public val RED: MinecraftTextFormat = MinecraftTextFormat('c', Color(0xFF5555))

        @JvmStatic
        public val LIGHT_PURPLE: MinecraftTextFormat = MinecraftTextFormat('d', Color(0xFF55FF))

        @JvmStatic
        public val YELLOW: MinecraftTextFormat = MinecraftTextFormat('e', Color(0xFFFF55))

        @JvmStatic
        public val WHITE: MinecraftTextFormat = MinecraftTextFormat('f', Color(0xFFFFFF))

        @JvmStatic
        public val OBFUSCATED: MinecraftTextFormat = MinecraftTextFormat('k')

        @JvmStatic
        public val BOLD: MinecraftTextFormat = MinecraftTextFormat('l')

        @JvmStatic
        public val STRIKETHROUGH: MinecraftTextFormat = MinecraftTextFormat('m')

        @JvmStatic
        public val UNDERLINE: MinecraftTextFormat = MinecraftTextFormat('n')

        @JvmStatic
        public val ITALIC: MinecraftTextFormat = MinecraftTextFormat('o')

        @JvmStatic
        public val RESET: MinecraftTextFormat = MinecraftTextFormat('r')

        @JvmStatic
        public val values: List<MinecraftTextFormat> = listOf(
            BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY,
            DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE,
            OBFUSCATED, BOLD, STRIKETHROUGH, UNDERLINE, ITALIC, RESET
        )

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
