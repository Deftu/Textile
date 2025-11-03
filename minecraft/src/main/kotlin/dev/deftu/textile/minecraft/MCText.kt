package dev.deftu.textile.minecraft

import dev.deftu.textile.MutableText
import dev.deftu.textile.Text
import dev.deftu.textile.TextStyle
import net.minecraft.network.chat.Component as VanillaText
import net.minecraft.network.chat.contents.TranslatableContents as VanillaTranslatableTextContent
import java.util.Optional

public object MCText {
    @JvmStatic
    public fun empty(): MutableText {
        return Text.empty()
    }

    @JvmStatic
    public fun literal(content: String): MutableText {
        return Text.literal(content)
    }

    @JvmStatic
    public fun literal(content: String, style: TextStyle): MutableText {
        return Text.literal(content, style)
    }

    @JvmStatic
    public fun translatable(key: String, vararg replacements: Any): MutableText {
        return Text.of(TranslatableTextContent(key, replacements))
    }

    @JvmStatic
    public fun translatable(key: String, fallback: String?, vararg replacements: Any): MutableText {
        return Text.of(TranslatableTextContent(key, fallback, replacements))
    }
    @JvmStatic
    public fun translatable(key: String, style: TextStyle, vararg replacements: Any): MutableText {
        return Text.of(TranslatableTextContent(key, replacements), style)
    }

    @JvmStatic
    public fun translatable(key: String, fallback: String?, style: TextStyle, vararg replacements: Any): MutableText {
        return Text.of(TranslatableTextContent(key, fallback, replacements), style)
    }

    @JvmStatic
    public fun wrap(text: VanillaText): Text {
        val content = text.contents
        if (content is VanillaTranslatableTextContent) {
            return translatable(
                key = content.key,
                //#if MC >= 1.19.4
                fallback = content.fallback,
                //#else
                //$$ fallback = null,
                //#endif
                replacements = content.args.map { arg ->
                    when (arg) {
                        is VanillaText -> wrap(arg)
                        else -> arg
                    }
                }.toTypedArray()
            ).setStyle(MCTextStyle.wrap(text.style).build()).also { result ->
                text.siblings.map(::wrap).forEach(result::append)
            }
        }

        return literal(buildString {
            content.visit { content ->
                append(content)
                Optional.empty<Any>()
            }
        }).setStyle(MCTextStyle.wrap(text.style).build()).also { result ->
            text.siblings.map(::wrap).forEach(result::append)
        }
    }

    @JvmStatic
    public fun convert(text: Text): VanillaText {
        val style = MCTextStyle.get(text)
        val content = text.content
        if (content is TranslatableTextContent) {
            //#if MC >= 1.19.4
            return VanillaText.translatableWithFallback(
                content.key,
                content.fallback,
            //#else
            //$$ return VanillaText.translatable(
            //$$     content.key,
            //#endif
                *content.replacements.map { replacement ->
                    when (replacement) {
                        is Text -> convert(replacement)
                        else -> replacement
                    }
                }.toTypedArray()
            ).apply {
                this.withStyle(style::applyTo)
                text.siblings.map(::convert).forEach(this::append)
            }
        }

        return VanillaText.literal(content.string).apply {
            this.withStyle(style::applyTo)
            text.siblings.map(::convert).forEach(this::append)
        }
    }
}
