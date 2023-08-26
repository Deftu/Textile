package xyz.deftu.text

public interface MutableText : Text {
    override val children: MutableList<Pair<Text.TextChildPosition, Text>>

    public fun set(text: Any): MutableText
    public fun set(text: () -> Any): MutableText

    public fun prepend(text: Any): MutableText
    public fun prepend(text: () -> Any): MutableText

    public fun append(text: Any): MutableText
    public fun append(text: () -> Any): MutableText

    public fun truncate(maxLength: Int): MutableText
    public fun truncate(maxLength: () -> Int): MutableText
}
