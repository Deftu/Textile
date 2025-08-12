package dev.deftu.textile

public open class TextFormat(public open val color: TextColor? = null) {
    internal val startValue: String
        get() = start.ifEmpty(::toString)

    public open val start: String = ""
    public open val end: String = ""

    public open operator fun plus(other: TextFormat): String {
        return "$start${other.start}$end${other.end}"
    }

    public open operator fun plus(other: String): String {
        return "$start$other$end"
    }

    @Suppress("UNCHECKED_CAST")
    public open operator fun <T : TextHolder<T, F>, F : TextFormat> plus(other: TextHolder<T, F>): T {
        return other.withFormatting(this as F)
    }

    /**
     * By default, [TextFormat.toString] returns the hexadecimal representation of the color.
     */
    override fun toString(): String {
        return color?.hex ?: "null"
    }
}
