package dev.deftu.textile.minecraft

import net.minecraft.util.Formatting

public data class TextColor(public val rgba: Int) {
    public var formatting: Formatting? = null
        private set

    public var isFormattingStringifiable: Boolean = false
        private set

    public val hex: String
        get() = String.format("#%06X", 0xFFFFFF and rgba)

    public var isCustom: Boolean = true
        private set

    public val formattingString: String?
        get() {
            return if (isCustom) {
                if (isFormattingStringifiable) {
                    formatting?.toString()
                } else {
                    null
                }
            } else {
                formatting?.toString()
            }
        }

    public fun withFallback(
        formatting: Formatting,
        stringified: Boolean = true
    ): TextColor {
        return if (this.formatting != formatting && this.isCustom) {
            TextColor(this.rgba).apply {
                this.formatting = formatting
                this.isFormattingStringifiable = stringified
            }
        } else {
            this
        }
    }

    internal fun default(formatting: Formatting): TextColor {
        this.formatting = formatting
        this.isFormattingStringifiable = true
        this.isCustom = false
        return this
    }

    override fun toString(): String {
        return if (!isCustom && isFormattingStringifiable) {
            formatting?.toString()
        } else {
//            "TextColor($rgba)"
            buildString {
                append("TextColor(")
                append(rgba)
                append(")")

                if (isCustom || formatting != null) {
                    append(" { ")
                    if (isCustom) {
                        append("custom")
                        append(", ")
                        append("hex=")
                        append(hex)
                    }

                    if (formatting != null) {
                        if (isCustom) {
                            append(", ")
                        }

                        append("fallback=")
                        append(formatting)
                    }
                    append(" }")
                }
            }
        } ?: "none"
    }
}
