package dev.deftu.textile

public interface MutableText : Text {
    override val children: MutableList<Pair<Text.TextChildPosition, Text>>

    public fun set(text: Text): MutableText
    public fun set(text: () -> Text): MutableText

    public fun prepend(text: Text): MutableText
    public fun prepend(text: () -> Text): MutableText

    public fun append(text: Text): MutableText
    public fun append(text: () -> Text): MutableText

    public fun truncate(maxLength: Int): MutableText
    public fun truncate(maxLength: () -> Int): MutableText
}
