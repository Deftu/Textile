package dev.deftu.textile

public interface Text {
    public val children: List<Pair<TextChildPosition, Text>>
    public val formatting: List<TextFormatting>

    public fun copy(): Text

    public fun asTruncatedString(maxLength: Int): String
    public fun asTruncated(maxLength: Int): Text

    public fun asContentString(): String
    public fun asFormattedContentString(): String

    public fun asString(): String
    public fun asFormattedString(): String

    public fun replace(key: String, value: Any): Text
    public fun replace(key: String, value: () -> Any): Text

    public fun replaceFirst(key: String, value: Any): Text
    public fun replaceFirst(key: String, value: () -> Any): Text

    public fun format(vararg formatting: TextFormatting): Text
    public fun format(vararg formatting: () -> TextFormatting): Text

    public enum class TextChildPosition {
        BEFORE,
        AFTER
    }
}
