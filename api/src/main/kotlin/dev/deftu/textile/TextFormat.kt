package dev.deftu.textile

import java.awt.Color

public open class TextFormat(public open val color: Color? = null) {

    public open operator fun plus(other: TextFormat): String = "$this$other"

    public open operator fun plus(other: String): String = "$this$other"

    public open operator fun plus(other: TextHolder): TextHolder = other.formatted(this)

    /**
     * By default, [TextFormat.toString] returns the hexadecimal representation of the color.
     */
    override fun toString(): String {
        return color?.let { color ->
            "#${Integer.toHexString(color.rgb)}"
        } ?: ""
    }

}
