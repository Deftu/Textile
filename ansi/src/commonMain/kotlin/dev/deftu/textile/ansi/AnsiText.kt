package dev.deftu.textile.ansi

import dev.deftu.textile.CollapseMode
import dev.deftu.textile.Text
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

public object AnsiText {
    public const val ESC: String = "\u001B"

    @JvmStatic
    @JvmOverloads
    public fun stringify(
        text: Text,
        collapseMode: CollapseMode = CollapseMode.AUTO,
        forceResetAtEnd: Boolean = true,
    ): String {
        val string = text.collapseToString(collapseMode)
        if (!forceResetAtEnd) {
            return string
        }

        val reset = "${ESC}[0m"
        return if (string.endsWith(reset)) reset else string + reset
    }
}
