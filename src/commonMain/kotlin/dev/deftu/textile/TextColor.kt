package dev.deftu.textile

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

@JvmInline
public value class TextColor(public val rgba: Int) {

    public companion object {

        @JvmStatic
        public fun fromRgba(red: Int, green: Int, blue: Int, alpha: Int): TextColor {
            return TextColor((alpha shl 24) or (red shl 16) or (green shl 8) or blue)
        }

        @JvmStatic
        public fun fromRgb(red: Int, green: Int, blue: Int): TextColor {
            return TextColor((0xFF shl 24) or (red shl 16) or (green shl 8) or blue)
        }

        @JvmStatic
        public fun fromHex(hex: String): TextColor {
            val hexValue = hex.removePrefix("#").toInt(16)
            return TextColor((hexValue and 0xFFFFFF) or ((hexValue shr 24) shl 24))
        }

    }

    public val red: Int
        get() = (rgba shr 16) and 0xFF

    public val green: Int
        get() = (rgba shr 8) and 0xFF

    public val blue: Int
        get() = rgba and 0xFF

    public val alpha: Int
        get() = (rgba shr 24) and 0xFF

    public val hex: String
        get() = "#${rgba.toString(16).padStart(6, '0')}"

    override fun toString(): String {
        return "TextColor(rgba=$rgba, red=$red, green=$green, blue=$blue, alpha=$alpha, hex=$hex)"
    }

}
