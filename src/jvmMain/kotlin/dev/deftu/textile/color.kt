package dev.deftu.textile

import java.awt.Color

public fun Color.toTextColor(): TextColor {
    return TextColor(this.rgb)
}

public fun TextColor.toAwtColor(): Color {
    return Color(this.red, this.green, this.blue, this.alpha)
}
