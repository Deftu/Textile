package xyz.deftu.text

interface MutableText : Text {
    override val children: MutableList<Pair<Text.TextChildPosition, Text>>

    fun set(text: Any): MutableText
    fun set(text: () -> Any): MutableText

    fun prepend(text: Any): MutableText
    fun prepend(text: () -> Any): MutableText

    fun append(text: Any): MutableText
    fun append(text: () -> Any): MutableText

    fun truncate(maxLength: Int): MutableText
    fun truncate(maxLength: () -> Int): MutableText
}
