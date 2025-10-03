package dev.deftu.textile.minecraft

import net.minecraft.util.Formatting

//#if MC >= 1.16.5
import net.minecraft.text.TextColor as VanillaTextColor
//#endif

public object TextColors {
    // presets with color codes
    @JvmField public val BLACK: TextColor = TextColor(0x000000).default(Formatting.BLACK)
    @JvmField public val DARK_BLUE: TextColor = TextColor(0x0000AA).default(Formatting.DARK_BLUE)
    @JvmField public val DARK_GREEN: TextColor = TextColor(0x00AA00).default(Formatting.DARK_GREEN)
    @JvmField public val DARK_AQUA: TextColor = TextColor(0x00AAAA).default(Formatting.DARK_AQUA)
    @JvmField public val DARK_RED: TextColor = TextColor(0xAA0000).default(Formatting.DARK_RED)
    @JvmField public val DARK_PURPLE: TextColor = TextColor(0xAA00AA).default(Formatting.DARK_PURPLE)
    @JvmField public val GOLD: TextColor = TextColor(0xFFAA00).default(Formatting.GOLD)
    @JvmField public val GRAY: TextColor = TextColor(0xAAAAAA).default(Formatting.GRAY)
    @JvmField public val DARK_GRAY: TextColor = TextColor(0x555555).default(Formatting.DARK_GRAY)
    @JvmField public val BLUE: TextColor = TextColor(0x5555FF).default(Formatting.BLUE)
    @JvmField public val GREEN: TextColor = TextColor(0x55FF55).default(Formatting.GREEN)
    @JvmField public val AQUA: TextColor = TextColor(0x55FFFF).default(Formatting.AQUA)
    @JvmField public val RED: TextColor = TextColor(0xFF5555).default(Formatting.RED)
    @JvmField public val LIGHT_PURPLE: TextColor = TextColor(0xFF55FF).default(Formatting.LIGHT_PURPLE)
    @JvmField public val YELLOW: TextColor = TextColor(0xFFFF55).default(Formatting.YELLOW)
    @JvmField public val WHITE: TextColor = TextColor(0xFFFFFF).default(Formatting.WHITE)

    // Custom colors without color codes
    @JvmStatic
    public fun hex(hex: String): TextColor {
        val cleanHex = hex.removePrefix("#")
        val intValue = cleanHex.toInt(16)
        return if (cleanHex.length <= 6) {
            // If only RGB is provided, assume full opacity
            TextColor(intValue or (0xFF shl 24))
        } else {
            // If RGBA is provided, use it directly
            TextColor(intValue)
        }
    }

    //#if MC >= 1.16.5
    @JvmStatic
    public fun wrap(color: VanillaTextColor): TextColor {
        return TextColor(color.rgb)
    }

    @JvmStatic
    public fun convert(color: TextColor): VanillaTextColor {
        return color.formatting?.let { formatting ->
            VanillaTextColor.fromFormatting(formatting)
        } ?: VanillaTextColor.fromRgb(color.rgba)
    }
    //#endif

    @JvmStatic
    public fun wrapFormatting(formatting: Formatting): TextColor {
        //#if MC >= 1.16.5
        return TextColor(formatting.colorValue ?: 0).default(formatting)
        //#else
        //$$ return when (formatting) {
        //$$     TextFormatting.BLACK -> BLACK
        //$$     TextFormatting.DARK_BLUE -> DARK_BLUE
        //$$     TextFormatting.DARK_GREEN -> DARK_GREEN
        //$$     TextFormatting.DARK_AQUA -> DARK_AQUA
        //$$     TextFormatting.DARK_RED -> DARK_RED
        //$$     TextFormatting.DARK_PURPLE -> DARK_PURPLE
        //$$     TextFormatting.GOLD -> GOLD
        //$$     TextFormatting.GRAY -> GRAY
        //$$     TextFormatting.DARK_GRAY -> DARK_GRAY
        //$$     TextFormatting.BLUE -> BLUE
        //$$     TextFormatting.GREEN -> GREEN
        //$$     TextFormatting.AQUA -> AQUA
        //$$     TextFormatting.RED -> RED
        //$$     TextFormatting.LIGHT_PURPLE -> LIGHT_PURPLE
        //$$     TextFormatting.YELLOW -> YELLOW
        //$$     TextFormatting.WHITE -> WHITE
        //$$     else -> TextColor(-1) // Formatting.RESET and others
        //$$ }
        //#endif
    }

    @JvmStatic
    public fun convertFormatting(color: TextColor): Formatting? {
        return color.formatting ?: Formatting.byColorIndex(color.rgba and 0xFFFFFF)
    }
}
