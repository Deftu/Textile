package dev.deftu.textile.minecraft

//#if MC <= 1.18.2 && MC >= 1.16.5
//$$ import net.minecraft.text.LiteralText
//#endif

import dev.deftu.textile.SimpleMutableTextHolder
import dev.deftu.textile.TextFormat
import dev.deftu.textile.TextFormat.*
import dev.deftu.textile.TextHolder
import net.minecraft.text.MutableText
import net.minecraft.util.Formatting
import java.util.Optional

public object VanillaConverter {

    @JvmStatic
    @Suppress("EnumValuesSoftDeprecate")
    public val VANILLA_MAPPED_FORMATTING: Map<TextFormat, Formatting> = TextFormat.values().associateWith { formatting ->
        when (formatting) {
            BLACK -> Formatting.BLACK
            DARK_BLUE -> Formatting.DARK_BLUE
            DARK_GREEN -> Formatting.DARK_GREEN
            DARK_AQUA -> Formatting.DARK_AQUA
            DARK_RED -> Formatting.DARK_RED
            DARK_PURPLE -> Formatting.DARK_PURPLE
            GOLD -> Formatting.GOLD
            GRAY -> Formatting.GRAY
            DARK_GRAY -> Formatting.DARK_GRAY
            BLUE -> Formatting.BLUE
            GREEN -> Formatting.GREEN
            AQUA -> Formatting.AQUA
            RED -> Formatting.RED
            LIGHT_PURPLE -> Formatting.LIGHT_PURPLE
            YELLOW -> Formatting.YELLOW
            WHITE -> Formatting.WHITE
            OBFUSCATED -> Formatting.OBFUSCATED
            BOLD -> Formatting.BOLD
            STRIKETHROUGH -> Formatting.STRIKETHROUGH
            UNDERLINE -> Formatting.UNDERLINE
            ITALIC -> Formatting.ITALIC
            RESET -> Formatting.RESET
        }
    }

    @JvmStatic
    private fun createLiteralText(content: String): MutableText {
        //#if MC >= 1.19.2
        return net.minecraft.text.Text.literal(content)
        //#else
        //$$ return LiteralText(content)
        //#endif
    }

    @JvmStatic
    public fun toVanillaFormatting(format: TextFormat): Formatting {
        return VANILLA_MAPPED_FORMATTING[format] ?: Formatting.RESET
    }

    @JvmStatic
    public fun toVanillaText(textHolder: TextHolder): net.minecraft.text.Text {
        val result = createLiteralText("")
        result.append(createLiteralText(textHolder.asLeafString()).apply {
            val formatting = textHolder.formatting.map(::toVanillaFormatting)
            //#if MC >= 1.16.5
            formatting.forEach(this::formatted)
            //#else
            //$$ formatting.forEach { formatting ->
            //#if MC >= 1.12.2
            //$$     val style = style.createDeepCopy()
            //#else
            //$$     val style = chatStyle.createDeepCopy()
            //#endif
            //$$     if (formatting.isColor) style.setColor(formatting)
            //$$     if (formatting == TextFormatting.BOLD) style.setBold(true)
            //$$     if (formatting == TextFormatting.ITALIC) style.setItalic(true)
            //$$     if (formatting == TextFormatting.STRIKETHROUGH) style.setStrikethrough(true)
            //$$     if (formatting == TextFormatting.UNDERLINE) style.setUnderlined(true)
            //$$     if (formatting == TextFormatting.OBFUSCATED) style.setObfuscated(true)
            //$$     setStyle(style)
            //$$ }
            //#endif
        })

        textHolder.children.map(::toVanillaText).forEach {
            //#if MC >= 1.16.5
            result.append(it)
            //#else
            //$$ result.appendSibling(it)
            //#endif
        }

        return result
    }

    @JvmStatic
    public fun fromVanillaFormatting(format: Formatting): TextFormat {
        return VANILLA_MAPPED_FORMATTING.entries.firstOrNull { (_, vanilla) ->
            vanilla == format
        }?.key ?: TextFormat.RESET
    }

    @JvmStatic
    public fun fromVanillaText(text: net.minecraft.text.Text, format: Set<TextFormat>): TextHolder {
        if (!text.style.isEmpty) {
            return fromVanillaText(text)
        }

        val result = SimpleMutableTextHolder("")
        val content = StringBuilder()
        //#if MC >= 1.19.2
        text.content.visit { string ->
            content.append(string)
            Optional.empty<String>()
        }
        //#elseif MC >= 1.16.5
        //$$ content.append(text.asString())
        //#else
        //$$ content.append(text.unformattedText)
        //#endif
        result.append(SimpleMutableTextHolder(content.toString()).apply {
            format(*format.toTypedArray())
        })

        text.siblings.map { sibling -> fromVanillaText(sibling, format) }.forEach(result::append)
        return result
    }

    @JvmStatic
    public fun fromVanillaText(text: net.minecraft.text.Text): TextHolder {
        val result = SimpleMutableTextHolder("")
        val content = StringBuilder()
        //#if MC >= 1.19.2
        text.content.visit { string ->
            content.append(string)
            Optional.empty<String>()
        }
        //#elseif MC >= 1.16.5
        //$$ content.append(text.asString())
        //#else
        //$$ content.append(text.unformattedText)
        //#endif
        var copiedFormatting: Set<TextFormat>
        result.append(SimpleMutableTextHolder(content.toString()).apply {
            //#if MC >= 1.16.5
            val formatting = Formatting.byName(text.style.color?.name)
            formatting?.let(::fromVanillaFormatting)?.let { format(it) }
            //#else
            //$$ text.style.color?.let(::fromVanillaFormatting)?.let { formatting -> format(formatting) }
            //#endif
            if (text.style.isBold) format(TextFormat.BOLD)
            if (text.style.isItalic) format(TextFormat.ITALIC)
            if (text.style.isStrikethrough) format(TextFormat.STRIKETHROUGH)
            if (text.style.isUnderlined) format(TextFormat.UNDERLINE)
            if (text.style.isObfuscated) format(TextFormat.OBFUSCATED)
        }.apply { copiedFormatting = formatting })

        text.siblings.map { text ->
            if (copiedFormatting.isEmpty()) fromVanillaText(text)
            else fromVanillaText(text, copiedFormatting)
        }.forEach(result::append)
        return result
    }

}

public fun TextHolder.toVanilla(): net.minecraft.text.Text = VanillaConverter.toVanillaText(this)
public fun TextFormat.toVanilla(): Formatting = VanillaConverter.toVanillaFormatting(this)

public fun net.minecraft.text.Text.convert(): TextHolder = VanillaConverter.fromVanillaText(this)
public fun Formatting.convert(): TextFormat = VanillaConverter.fromVanillaFormatting(this)
