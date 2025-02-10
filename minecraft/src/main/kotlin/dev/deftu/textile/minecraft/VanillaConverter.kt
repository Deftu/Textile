package dev.deftu.textile.minecraft

//#if MC <= 1.18.2 && MC >= 1.16.5
//$$ import net.minecraft.text.LiteralText
//#endif

import dev.deftu.textile.SimpleMutableTextHolder
import dev.deftu.textile.TextFormat
import dev.deftu.textile.TextHolder
import net.minecraft.text.MutableText
import net.minecraft.util.Formatting
import java.util.Optional

public object VanillaConverter {

    @JvmStatic
    @Suppress("EnumValuesSoftDeprecate")
    public val VANILLA_MAPPED_FORMATTING: Map<MinecraftTextFormat, Formatting> = MinecraftTextFormat.entries.associateWith { formatting ->
        when (formatting) {
            MinecraftTextFormat.BLACK -> Formatting.BLACK
            MinecraftTextFormat.DARK_BLUE -> Formatting.DARK_BLUE
            MinecraftTextFormat.DARK_GREEN -> Formatting.DARK_GREEN
            MinecraftTextFormat.DARK_AQUA -> Formatting.DARK_AQUA
            MinecraftTextFormat.DARK_RED -> Formatting.DARK_RED
            MinecraftTextFormat.DARK_PURPLE -> Formatting.DARK_PURPLE
            MinecraftTextFormat.GOLD -> Formatting.GOLD
            MinecraftTextFormat.GRAY -> Formatting.GRAY
            MinecraftTextFormat.DARK_GRAY -> Formatting.DARK_GRAY
            MinecraftTextFormat.BLUE -> Formatting.BLUE
            MinecraftTextFormat.GREEN -> Formatting.GREEN
            MinecraftTextFormat.AQUA -> Formatting.AQUA
            MinecraftTextFormat.RED -> Formatting.RED
            MinecraftTextFormat.LIGHT_PURPLE -> Formatting.LIGHT_PURPLE
            MinecraftTextFormat.YELLOW -> Formatting.YELLOW
            MinecraftTextFormat.WHITE -> Formatting.WHITE
            MinecraftTextFormat.OBFUSCATED -> Formatting.OBFUSCATED
            MinecraftTextFormat.BOLD -> Formatting.BOLD
            MinecraftTextFormat.STRIKETHROUGH -> Formatting.STRIKETHROUGH
            MinecraftTextFormat.UNDERLINE -> Formatting.UNDERLINE
            MinecraftTextFormat.ITALIC -> Formatting.ITALIC
            else -> Formatting.RESET
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
            //$$     val style = style.createDeepCopy()
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
        }?.key ?: MinecraftTextFormat.RESET
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
            if (text.style.isBold) format(MinecraftTextFormat.BOLD)
            if (text.style.isItalic) format(MinecraftTextFormat.ITALIC)
            if (text.style.isStrikethrough) format(MinecraftTextFormat.STRIKETHROUGH)
            if (text.style.isUnderlined) format(MinecraftTextFormat.UNDERLINE)
            if (text.style.isObfuscated) format(MinecraftTextFormat.OBFUSCATED)
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
