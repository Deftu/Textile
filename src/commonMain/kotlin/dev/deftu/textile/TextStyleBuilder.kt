package dev.deftu.textile

import kotlin.jvm.JvmField

public fun interface TextStyleBuilder {
    public companion object {
        @JvmField public val EMPTY: TextStyleBuilder = TextStyleBuilder { TextStyle.EMPTY }
    }

    public fun build(): TextStyle
}
