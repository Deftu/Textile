package dev.deftu.textile.discord

import dev.deftu.textile.TextStyle
import dev.deftu.textile.TextStyleBuilder
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

public data class DCTextStyle @JvmOverloads public constructor(
    public val isBold: Boolean? = null,
    public val isItalic: Boolean? = null,
    public val isUnderlined: Boolean? = null,
    public val isStrikethrough: Boolean? = null,
    public val isSpoiler: Boolean? = null,
    public val isInlineCode: Boolean? = null,
    public val codeBlock: CodeBlock? = null,
    public val isQuoteLine: Boolean? = null,
    public val isQuoteBlock: Boolean? = null
) : TextStyleBuilder {
    public companion object {
        @JvmStatic
        public fun get(style: TextStyle): DCTextStyle {
            return DCTextStyle(
                isBold = style[DCTextStyleProperties.BOLD_KEY]?.value,
                isItalic = style[DCTextStyleProperties.ITALIC_KEY]?.value,
                isUnderlined = style[DCTextStyleProperties.UNDERLINED_KEY]?.value,
                isStrikethrough = style[DCTextStyleProperties.STRIKETHROUGH_KEY]?.value,
                isSpoiler = style[DCTextStyleProperties.SPOILER_KEY]?.value,
                isInlineCode = style[DCTextStyleProperties.INLINE_CODE_KEY]?.value,
                codeBlock = style[DCTextStyleProperties.CODE_BLOCK_KEY]?.value,
                isQuoteLine = style[DCTextStyleProperties.QUOTE_LINE_KEY]?.value,
                isQuoteBlock = style[DCTextStyleProperties.QUOTE_BLOCK_KEY]?.value
            )
        }
    }

    public fun setBold(bold: Boolean?): DCTextStyle {
        return if (bold == isBold) this else copy(isBold = bold)
    }

    public fun bold(): DCTextStyle {
        return setBold(true)
    }

    public fun notBold(): DCTextStyle {
        return setBold(false)
    }

    public fun setItalic(italic: Boolean?): DCTextStyle {
        return if (italic == isItalic) this else copy(isItalic = italic)
    }

    public fun italic(): DCTextStyle {
        return setItalic(true)
    }

    public fun notItalic(): DCTextStyle {
        return setItalic(false)
    }

    public fun setUnderlined(underlined: Boolean?): DCTextStyle {
        return if (underlined == isUnderlined) this else copy(isUnderlined = underlined)
    }

    public fun underlined(): DCTextStyle {
        return setUnderlined(true)
    }

    public fun notUnderlined(): DCTextStyle {
        return setUnderlined(false)
    }

    public fun setStrikethrough(strikethrough: Boolean?): DCTextStyle {
        return if (strikethrough == isStrikethrough) this else copy(isStrikethrough = strikethrough)
    }

    public fun strikethrough(): DCTextStyle {
        return setStrikethrough(true)
    }

    public fun notStrikethrough(): DCTextStyle {
        return setStrikethrough(false)
    }

    public fun setSpoiler(spoiler: Boolean?): DCTextStyle {
        return if (spoiler == isSpoiler) this else copy(isSpoiler = spoiler)
    }

    public fun spoiler(): DCTextStyle {
        return setSpoiler(true)
    }

    public fun notSpoiler(): DCTextStyle {
        return setSpoiler(false)
    }

    public fun setInlineCode(inlineCode: Boolean?): DCTextStyle {
        return if (inlineCode == isInlineCode) this else copy(isInlineCode = inlineCode)
    }

    public fun inlineCode(): DCTextStyle {
        return setInlineCode(true)
    }

    public fun notInlineCode(): DCTextStyle {
        return setInlineCode(false)
    }

    public fun setCodeBlock(codeBlock: CodeBlock?): DCTextStyle {
        return if (codeBlock == this.codeBlock) this else copy(codeBlock = codeBlock)
    }

    public fun setCodeBlockLanguage(language: String?): DCTextStyle {
        return if (language == codeBlock?.language) this else copy(codeBlock = language?.let(::CodeBlock))
    }

    public fun notCodeBlock(): DCTextStyle {
        return setCodeBlock(null)
    }

    public fun setQuoteLine(quoteLine: Boolean?): DCTextStyle {
        return if (quoteLine == isQuoteLine) this else copy(isQuoteLine = quoteLine)
    }

    public fun quoteLine(): DCTextStyle {
        return setQuoteLine(true)
    }

    public fun notQuoteLine(): DCTextStyle {
        return setQuoteLine(false)
    }

    public fun setQuoteBlock(quoteBlock: Boolean?): DCTextStyle {
        return if (quoteBlock == isQuoteBlock) this else copy(isQuoteBlock = quoteBlock)
    }

    public fun quoteBlock(): DCTextStyle {
        return setQuoteBlock(true)
    }

    public fun notQuoteBlock(): DCTextStyle {
        return setQuoteBlock(false)
    }

    override fun build(): TextStyle {
        val props = mutableListOf<TextStyle.Property<*>>()
        isBold?.let { props += DCTextStyleProperties.bold(it) }
        isItalic?.let { props += DCTextStyleProperties.italic(it) }
        isUnderlined?.let { props += DCTextStyleProperties.underlined(it) }
        isStrikethrough?.let { props += DCTextStyleProperties.strikethrough(it) }
        isSpoiler?.let { props += DCTextStyleProperties.spoiler(it) }
        isInlineCode?.let { props += DCTextStyleProperties.inlineCode(it) }
        codeBlock?.let { props += DCTextStyleProperties.codeBlock(it) }
        isQuoteLine?.let { props += DCTextStyleProperties.quoteLine(it) }
        isQuoteBlock?.let { props += DCTextStyleProperties.quoteBlock(it) }
        return TextStyle(props.associateBy(TextStyle.Property<*>::key))
    }
}
