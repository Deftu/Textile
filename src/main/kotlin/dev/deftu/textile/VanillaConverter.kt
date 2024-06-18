package dev.deftu.textile

//#if MC <= 1.18.2 && MC >= 1.16.5
//$$ import net.minecraft.text.LiteralText
//#endif

import dev.deftu.textile.impl.SimpleMutableText
import net.minecraft.text.MutableText
import net.minecraft.util.Formatting

public object VanillaConverter {

    @JvmStatic
    public fun createLiteralText(content: String): MutableText {
        //#if MC >= 1.19.2
        return net.minecraft.text.Text.literal(content)
        //#else
        //$$ return LiteralText(content)
        //#endif
    }

    @JvmStatic
    public fun toVanillaFormatting(format: Format): Formatting {
        return Format.VANILLA_MAPPED[format] ?: Formatting.RESET
    }

    @JvmStatic
    public fun toVanillaText(text: Text): net.minecraft.text.Text {
        val result = createLiteralText("")
        text.children.filter { (pos, _) ->
            pos == Text.TextChildPosition.BEFORE
        }.map(Pair<Text.TextChildPosition, Text>::second).map(::toVanillaText).forEach {
            //#if MC >= 1.16.5
            result.append(it)
            //#else
            //$$ result.appendSibling(it)
            //#endif
        }

        result.append(createLiteralText(text.asContentString()).apply {
            val formatting = text.formatting.map(::toVanillaFormatting)
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

        text.children.filter { (pos, _) ->
            pos == Text.TextChildPosition.AFTER
        }.map(Pair<Text.TextChildPosition, Text>::second).map(::toVanillaText).forEach {
            //#if MC >= 1.16.5
            result.append(it)
            //#else
            //$$ result.appendSibling(it)
            //#endif
        }

        return result
    }

    @JvmStatic
    public fun fromVanillaFormatting(format: Formatting): Format {
        return Format.VANILLA_MAPPED.entries.firstOrNull { (_, vanilla) ->
            vanilla == format
        }?.key ?: Format.RESET
    }

    @JvmStatic
    public fun fromVanillaText(text: net.minecraft.text.Text): Text {
        val result = SimpleMutableText("")

        result.append(SimpleMutableText(text.string).apply {
            //#if MC >= 1.16.5
            val formatting = Formatting.byName(text.style.color?.name)
            formatting?.let(::fromVanillaFormatting)?.let { format(it) }
            //#else
            //$$ text.style.color?.let(::fromVanillaFormatting)?.let { formatting -> format(formatting) }
            //#endif
            if (text.style.isBold) format(Format.BOLD)
            if (text.style.isItalic) format(Format.ITALIC)
            if (text.style.isStrikethrough) format(Format.STRIKETHROUGH)
            if (text.style.isUnderlined) format(Format.UNDERLINE)
            if (text.style.isObfuscated) format(Format.OBFUSCATED)
        })

        text.siblings.forEach {
            result.append(fromVanillaText(it))
        }

        return result
    }

}

public fun Text.toVanilla(): net.minecraft.text.Text = VanillaConverter.toVanillaText(this)
public fun Format.toVanilla(): Formatting = VanillaConverter.toVanillaFormatting(this)

public fun net.minecraft.text.Text.convert(): Text = VanillaConverter.fromVanillaText(this)
public fun Formatting.convert(): Format = VanillaConverter.fromVanillaFormatting(this)
