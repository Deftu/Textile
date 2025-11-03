package dev.deftu.textile.minecraft

import net.minecraft.ChatFormatting

//#if MC >= 1.16.5
import net.minecraft.network.chat.TextColor as VanillaTextColor
//#endif

public object TextColors {
    // presets with color codes
    @JvmField public val BLACK: TextColor = TextColor(0x000000).default(ChatFormatting.BLACK)
    @JvmField public val DARK_BLUE: TextColor = TextColor(0x0000AA).default(ChatFormatting.DARK_BLUE)
    @JvmField public val DARK_GREEN: TextColor = TextColor(0x00AA00).default(ChatFormatting.DARK_GREEN)
    @JvmField public val DARK_AQUA: TextColor = TextColor(0x00AAAA).default(ChatFormatting.DARK_AQUA)
    @JvmField public val DARK_RED: TextColor = TextColor(0xAA0000).default(ChatFormatting.DARK_RED)
    @JvmField public val DARK_PURPLE: TextColor = TextColor(0xAA00AA).default(ChatFormatting.DARK_PURPLE)
    @JvmField public val GOLD: TextColor = TextColor(0xFFAA00).default(ChatFormatting.GOLD)
    @JvmField public val GRAY: TextColor = TextColor(0xAAAAAA).default(ChatFormatting.GRAY)
    @JvmField public val DARK_GRAY: TextColor = TextColor(0x555555).default(ChatFormatting.DARK_GRAY)
    @JvmField public val BLUE: TextColor = TextColor(0x5555FF).default(ChatFormatting.BLUE)
    @JvmField public val GREEN: TextColor = TextColor(0x55FF55).default(ChatFormatting.GREEN)
    @JvmField public val AQUA: TextColor = TextColor(0x55FFFF).default(ChatFormatting.AQUA)
    @JvmField public val RED: TextColor = TextColor(0xFF5555).default(ChatFormatting.RED)
    @JvmField public val LIGHT_PURPLE: TextColor = TextColor(0xFF55FF).default(ChatFormatting.LIGHT_PURPLE)
    @JvmField public val YELLOW: TextColor = TextColor(0xFFFF55).default(ChatFormatting.YELLOW)
    @JvmField public val WHITE: TextColor = TextColor(0xFFFFFF).default(ChatFormatting.WHITE)

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
        return TextColor(color.value)
    }

    @JvmStatic
    public fun convert(color: TextColor): VanillaTextColor {
        return color.formatting?.let { formatting ->
            VanillaTextColor.fromLegacyFormat(formatting)
        } ?: VanillaTextColor.fromRgb(color.rgba)
    }
    //#endif

    @JvmStatic
    public fun wrapFormatting(formatting: ChatFormatting): TextColor {
        //#if MC >= 1.16.5
        val rgba = if (formatting.isColor) formatting.color ?: -1 else -1
        return TextColor(rgba).default(formatting)
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
    @Suppress("EnumValuesSoftDeprecate")
    public fun convertFormatting(color: TextColor): ChatFormatting? {
        //#if MC >= 1.16.5
        return color.formatting ?: ChatFormatting.values().firstOrNull { it.color == (color.rgba and 0xFFFFFF) }
        //#else
        //$$ return color.formatting // Otherwise we just can't map it back since there's no sane way to do it on legacy
        //#endif
    }
}
