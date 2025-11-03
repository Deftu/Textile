package dev.deftu.textile.minecraft

import dev.deftu.textile.StringVisitable
import dev.deftu.textile.StringVisitor
import dev.deftu.textile.TextContent
import dev.deftu.textile.TextStyle
import net.minecraft.locale.Language

//#if MC <= 1.12.2
//$$ import net.minecraft.client.Minecraft
//$$ import net.minecraft.client.resources.I18n
//#endif

public class TranslatableTextContent(
    public val key: String,
    public val fallback: String?,
    public val replacements: Array<out Any>
) : TextContent {
    public companion object {
        @JvmField public val TYPE: TextContent.ContentType<TranslatableTextContent> = TextContent.ContentType("translatable")
        
        private val REPLACEMENTS_REGEX = "%(?:(\\d+)\\$)?([A-Za-z%]|$)".toRegex()
        private val LITERAL_PERCENT = StringVisitable.plain("%")
        private val NULL = StringVisitable.plain("null")
    }

    override val type: TextContent.ContentType<out TextContent>
        get() = TYPE

    private var translations: List<StringVisitable> = emptyList()
    private var cachedLanguage: Language? = null

    public constructor(key: String, replacements: Array<out Any>) : this(key, null, replacements)

    override fun <T> visit(visitor: StringVisitor.Plain<T>): T? {
        update()

        for (part in translations) {
            return part.visit(visitor) ?: continue
        }

        return null
    }

    override fun <T> visit(visitor: StringVisitor.Styled<T>, style: TextStyle): T? {
        update()

        for (part in translations) {
            return part.visit(visitor, style) ?: continue
        }

        return null
    }

    override fun toString(): String {
        return buildString {
            append("Translatable($key)")
            if (fallback != null || replacements.isNotEmpty()) {
                append(" { ")

                if (fallback != null) {
                    append("fallback=\"$fallback\"")
                }

                if (replacements.isNotEmpty()) {
                    if (fallback != null) {
                        append(", ")
                    }

                    append("replacements=${replacements.contentToString()}")
                }

                append(" }")
            }
        }
    }

    private fun update() {
        val language =
            //#if MC >= 1.16.5
            Language.getInstance()
            //#else
            //$$ Minecraft.getMinecraft().languageManager.currentLanguage
            //#endif
        if (language == cachedLanguage) {
            return
        }

        cachedLanguage = language
        val value =
            //#if MC >= 1.19.4
            if (fallback != null) language.getOrDefault(key, fallback) else language.getOrDefault(key)
            //#elseif MC >= 1.16.5
            //$$ if (fallback != null) {
            //$$     if (language.has(key)) {
            //$$         language.getOrDefault(key)
            //$$     } else {
            //$$         fallback
            //$$     }
            //$$ } else {
            //$$     language.getOrDefault(key)
            //$$ }
            //#elseif MC >= 1.12.2
            //$$ if (fallback != null) {
            //$$     if (I18n.hasKey(key)) {
            //$$         I18n.format(key)
            //$$     } else {
            //$$         fallback
            //$$     }
            //$$ } else {
            //$$     I18n.format(key)
            //$$ }
            //#else
            //$$ if (fallback != null) {
            //$$     val translation = I18n.format(key)
            //$$     if (translation == key) {
            //$$         fallback
            //$$     } else {
            //$$         translation
            //$$     }
            //$$ } else {
            //$$     I18n.format(key)
            //$$ }
            //#endif

        try {
            val list = mutableListOf<StringVisitable>()
            parse(value, list::add)
            translations = list.toList()
        } catch (t: Throwable) {
            translations = listOf(StringVisitable.plain(value))
            t.printStackTrace()
        }
    }

    private fun parse(translation: String, emit: (StringVisitable) -> Unit) {
        var lastIndex = 0
        var scanPos = 0

        for (match in REPLACEMENTS_REGEX.findAll(translation)) {
            val start = match.range.first
            val end = match.range.last + 1

            if (start > scanPos) {
                val segment = translation.substring(scanPos, start)
                if ('%' in segment) {
                    throw TranslationException("Unescaped % character")
                }

                emit(StringVisitable.plain(segment))
            }

            val specifier = match.groupValues[2]
            val token = translation.substring(start, end)
            if (specifier == "%" && token == "%%") {
                emit(LITERAL_PERCENT)
            } else {
                if (specifier != "s") {
                    throw TranslationException("Unsupported format: $token")
                }

                val index = match.groupValues[1]
                val replacementIndex = if (index.isNotEmpty()) {
                    index.toInt() - 1
                } else {
                    lastIndex++
                    lastIndex - 1
                }

                emit(arg(replacementIndex))
            }

            scanPos = end
        }

        if (scanPos < translation.length) {
            val tail = translation.substring(scanPos)
            if ('%' in tail) {
                throw TranslationException("Unescaped % character")
            }

            emit(StringVisitable.plain(tail))
        }
    }

    private fun arg(index: Int): StringVisitable {
        if (index < 0 || index >= replacements.size) {
            throw TranslationException(this, index)
        }

        return when (val item = replacements.getOrNull(index)) {
            is StringVisitable -> item
            null -> NULL
            else -> StringVisitable.plain(item.toString())
        }
    }
}
