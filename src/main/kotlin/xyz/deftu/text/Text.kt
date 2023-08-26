package xyz.deftu.text

import xyz.deftu.text.impl.SimpleMutableText
import xyz.deftu.text.impl.SimpleText
import xyz.deftu.text.impl.TranslatableMutableText
import xyz.deftu.text.impl.TranslatableText

public interface Text {
    public companion object {
        private val serializers = mutableMapOf<Class<*>, TextObjectSerializer<*>>()

        @JvmStatic
        public fun create(
            content: String
        ): Text = SimpleText(content)

        @JvmStatic
        public fun createMutable(
            content: String
        ): MutableText = SimpleMutableText(content)

        @JvmStatic
        public fun translatable(
            key: String
        ): Text = TranslatableText(key)

        @JvmStatic
        public fun translatable(
            key: String,
            vararg args: Any
        ): Text = TranslatableText(key, *args)

        @JvmStatic
        public fun translatableMutable(
            key: String
        ): MutableText = TranslatableMutableText(key)

        @JvmStatic
        public fun translatableMutable(
            key: String,
            vararg args: Any
        ): MutableText = TranslatableMutableText(key, *args)

        @JvmStatic
        public fun from(
            obj: Any
        ): Text = when (obj) {
            is Text -> obj
            is String -> create(obj)
            else -> serializers[obj::class.java]?.serialize(obj) ?: create(obj.toString())
        }

        @JvmStatic
        public fun <T : Text> registerSerializer(
            clazz: Class<T>,
            serializer: TextObjectSerializer<T>
        ) {
            serializers[clazz] = serializer
        }
    }

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
