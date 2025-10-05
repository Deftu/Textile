package dev.deftu.textile.discord

import dev.deftu.textile.TextStyle
import kotlin.jvm.JvmField
import kotlin.jvm.JvmStatic

public object DCTextStyleProperties {
    public const val BOLD_FMT: String = "**"
    public const val ITALIC_FMT: String = "*"
    public const val UNDERLINED_FMT: String = "__"
    public const val STRIKETHROUGH_FMT: String = "~~"
    public const val SPOILER_FMT: String = "||"
    public const val INLINE_CODE_FMT: String = "`"
    public const val CODE_BLOCK_FMT: String = "```"
    public const val QUOTE_LINE_FMT: String = "> "
    public const val QUOTE_BLOCK_FMT: String = ">>> "

    @JvmField public val BOLD_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey("discord:bold", 0)
    @JvmField public val ITALIC_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey("discord:italic", 1)
    @JvmField public val UNDERLINED_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey("discord:underlined", 2)
    @JvmField public val STRIKETHROUGH_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey("discord:strikethrough", 3)
    @JvmField public val SPOILER_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey("discord:spoiler", 4)
    @JvmField public val INLINE_CODE_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey("discord:inline_code", 5)
    @JvmField public val CODE_BLOCK_KEY: TextStyle.PropertyKey<CodeBlock> = TextStyle.PropertyKey("discord:code_block", 6)
    @JvmField public val QUOTE_LINE_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey("discord:quote_line", 7)
    @JvmField public val QUOTE_BLOCK_KEY: TextStyle.PropertyKey<Boolean> = TextStyle.PropertyKey("discord:quote_block", 8)

    @JvmStatic
    public fun bold(bold: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(
            BOLD_KEY,
            bold,
            left = if (bold) BOLD_FMT else null,
            right = if (bold) BOLD_FMT else null
        )
    }

    @JvmStatic
    public fun italic(italic: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(
            ITALIC_KEY,
            italic,
            left = if (italic) ITALIC_FMT else null,
            right = if (italic) ITALIC_FMT else null
        )
    }

    @JvmStatic
    public fun underlined(underlined: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(
            UNDERLINED_KEY,
            underlined,
            left = if (underlined) UNDERLINED_FMT else null,
            right = if (underlined) UNDERLINED_FMT else null
        )
    }

    @JvmStatic
    public fun strikethrough(strikethrough: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(
            STRIKETHROUGH_KEY,
            strikethrough,
            left = if (strikethrough) STRIKETHROUGH_FMT else null,
            right = if (strikethrough) STRIKETHROUGH_FMT else null
        )
    }

    @JvmStatic
    public fun spoiler(spoiler: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(
            SPOILER_KEY,
            spoiler,
            left = if (spoiler) SPOILER_FMT else null,
            right = if (spoiler) SPOILER_FMT else null
        )
    }

    @JvmStatic
    public fun inlineCode(inlineCode: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(
            INLINE_CODE_KEY,
            inlineCode,
            left = if (inlineCode) INLINE_CODE_FMT else null,
            right = if (inlineCode) INLINE_CODE_FMT else null
        )
    }

    @JvmStatic
    public fun codeBlock(data: CodeBlock): TextStyle.Property<CodeBlock> {
        return TextStyle.Property(
            CODE_BLOCK_KEY,
            data,
            left = data.openingTag,
            right = data.closingTag
        )
    }

    @JvmStatic
    public fun quoteLine(quoteLine: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(
            QUOTE_LINE_KEY,
            quoteLine,
            left = if (quoteLine) QUOTE_LINE_FMT else null,
            right = null
        )
    }

    @JvmStatic
    public fun quoteBlock(quoteBlock: Boolean = true): TextStyle.Property<Boolean> {
        return TextStyle.Property(
            QUOTE_BLOCK_KEY,
            quoteBlock,
            left = if (quoteBlock) QUOTE_BLOCK_FMT else null,
            right = null
        )
    }
}
