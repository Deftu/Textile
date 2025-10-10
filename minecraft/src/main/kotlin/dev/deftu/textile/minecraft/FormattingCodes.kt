package dev.deftu.textile.minecraft

public object FormattingCodes {
    public const val COLOR_CHAR: Char = '\u00A7'

    public const val BLACK: String = "${COLOR_CHAR}0"
    public const val DARK_BLUE: String = "${COLOR_CHAR}1"
    public const val DARK_GREEN: String = "${COLOR_CHAR}2"
    public const val DARK_AQUA: String = "${COLOR_CHAR}3"
    public const val DARK_RED: String = "${COLOR_CHAR}4"
    public const val DARK_PURPLE: String = "${COLOR_CHAR}5"
    public const val GOLD: String = "${COLOR_CHAR}6"
    public const val GRAY: String = "${COLOR_CHAR}7"
    public const val DARK_GRAY: String = "${COLOR_CHAR}8"
    public const val BLUE: String = "${COLOR_CHAR}9"
    public const val GREEN: String = "${COLOR_CHAR}a"
    public const val AQUA: String = "${COLOR_CHAR}b"
    public const val RED: String = "${COLOR_CHAR}c"
    public const val LIGHT_PURPLE: String = "${COLOR_CHAR}d"
    public const val YELLOW: String = "${COLOR_CHAR}e"
    public const val WHITE: String = "${COLOR_CHAR}f"
    public const val OBFUSCATED: String = "${COLOR_CHAR}k"
    public const val BOLD: String = "${COLOR_CHAR}l"
    public const val STRIKETHROUGH: String = "${COLOR_CHAR}m"
    public const val UNDERLINED: String = "${COLOR_CHAR}n"
    public const val ITALIC: String = "${COLOR_CHAR}o"
    public const val RESET: String = "${COLOR_CHAR}r"

    public val COLOR_CODE_PATTERN: Regex = "${COLOR_CHAR}[0-9a-fk-or]".toRegex(RegexOption.IGNORE_CASE)

    @JvmStatic
    public fun codeToColor(code: Char): TextColor? {
        return when (code.lowercaseChar()) {
            '0' -> TextColors.BLACK
            '1' -> TextColors.DARK_BLUE
            '2' -> TextColors.DARK_GREEN
            '3' -> TextColors.DARK_AQUA
            '4' -> TextColors.DARK_RED
            '5' -> TextColors.DARK_PURPLE
            '6' -> TextColors.GOLD
            '7' -> TextColors.GRAY
            '8' -> TextColors.DARK_GRAY
            '9' -> TextColors.BLUE
            'a' -> TextColors.GREEN
            'b' -> TextColors.AQUA
            'c' -> TextColors.RED
            'd' -> TextColors.LIGHT_PURPLE
            'e' -> TextColors.YELLOW
            'f' -> TextColors.WHITE
            else -> null
        }
    }

    @JvmStatic
    public fun codeToColor(code: String): TextColor? {
        if (code.length != 2 || code[0] != COLOR_CHAR) {
            return null
        }

        return codeToColor(code[1])
    }

    @JvmStatic
    public fun strip(text: String): String {
        return text.replace(COLOR_CODE_PATTERN, "")
    }

    @JvmStatic
    public fun stripColor(text: String): String {
        return text.replace(COLOR_CODE_PATTERN) {
            if (it.value[1] in '0'..'9') {
                ""
            } else {
                it.value
            }
        }
    }

    @JvmStatic
    public fun stripFormatting(text: String): String {
        return text.replace(COLOR_CODE_PATTERN) {
            if (it.value[1] in 'a'..'r' || it.value[1] in 'A'..'R') {
                ""
            } else {
                it.value
            }
        }
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
}
