package xyz.deftu.text

import xyz.deftu.text.impl.SimpleMutableText
import xyz.deftu.text.impl.SimpleText
import xyz.deftu.text.impl.TranslatableMutableText
import xyz.deftu.text.impl.TranslatableText

interface Text {
    companion object {
        private val serializers = mutableMapOf<Class<*>, TextObjectSerializer<*>>()

        @JvmStatic
        fun create(
            content: String
        ) = SimpleText(content)

        @JvmStatic
        fun createMutable(
            content: String
        ) = SimpleMutableText(content)

        @JvmStatic
        fun translatable(
            key: String
        ) = TranslatableText(key)

        @JvmStatic
        fun translatable(
            key: String,
            vararg args: Any
        ) = TranslatableText(key, *args)

        @JvmStatic
        fun translatableMutable(
            key: String
        ) = TranslatableMutableText(key)

        @JvmStatic
        fun translatableMutable(
            key: String,
            vararg args: Any
        ) = TranslatableMutableText(key, *args)

        @JvmStatic
        fun from(
            obj: Any
        ) = when (obj) {
            is Text -> obj
            is String -> create(obj)
            else -> serializers[obj::class.java]?.serialize(obj) ?: create(obj.toString())
        }

        @JvmStatic
        fun <T : Text> registerSerializer(
            clazz: Class<T>,
            serializer: TextObjectSerializer<T>
        ) {
            serializers[clazz] = serializer
        }
    }

    val children: List<Pair<TextChildPosition, Text>>
    val formatting: List<TextFormatting>

    fun copy(): Text

    fun asTruncatedString(maxLength: Int): String
    fun asTruncated(maxLength: Int): Text

    fun asContentString(): String
    fun asFormattedContentString(): String

    fun asString(): String
    fun asFormattedString(): String

    fun replace(key: String, value: Any): Text
    fun replace(key: String, value: () -> Any): Text

    fun replaceFirst(key: String, value: Any): Text
    fun replaceFirst(key: String, value: () -> Any): Text

    fun format(vararg formatting: TextFormatting): Text
    fun format(vararg formatting: () -> TextFormatting): Text

    enum class TextChildPosition {
        BEFORE,
        AFTER
    }
}
