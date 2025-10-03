package dev.deftu.textile.minecraft

import net.minecraft.util.Formatting

public data class TextColor(public val rgba: Int) {
    public var formatting: Formatting? = null
        private set

    public val hex: String
        get() = String.format("#%06X", 0xFFFFFF and rgba)

    public var isCustom: Boolean = true
        private set

    public fun withFallback(formatting: Formatting): TextColor {
        return if (this.formatting != formatting && this.isCustom) {
            TextColor(this.rgba).apply {
                this.formatting = formatting
            }
        } else {
            this
        }
    }

    internal fun default(formatting: Formatting): TextColor {
        this.formatting = formatting
        this.isCustom = false
        return this
    }
}
