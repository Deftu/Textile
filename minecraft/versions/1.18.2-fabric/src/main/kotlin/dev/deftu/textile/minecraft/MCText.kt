package dev.deftu.textile.minecraft

import dev.deftu.textile.MutableText
import dev.deftu.textile.Text
import dev.deftu.textile.TextStyle
import net.minecraft.network.chat.Component as VanillaText
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent as VanillaTranslatableText

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
        if (text is VanillaTranslatableText) {
            return translatable(
                key = text.key,
                fallback = null,
                replacements = text.args.map { arg ->
                    when (arg) {
                        is VanillaText -> wrap(arg)
                        else -> arg
                    }
                }.toTypedArray()
            ).setStyle(MCTextStyle.wrap(text.style)).also { result ->
                text.siblings.map(::wrap).forEach(result::append)
            }
        }

        return literal((text as? TextComponent)?.text ?: text.string).apply {
            this.setStyle(MCTextStyle.wrap(text.style))
            text.siblings.map(::wrap).forEach(this::append)
        }
    }

    @JvmStatic
    public fun convert(text: Text): VanillaText {
        val style = MCTextStyle.get(text)
        val content = text.content
        if (content is TranslatableTextContent) {
            return VanillaTranslatableText(
                content.key,
                *content.replacements.map { arg ->
                    when (arg) {
                        is Text -> convert(arg)
                        else -> arg
                    }
                }.toTypedArray()
            ).apply {
                //#if MC >= 1.16.5
                this.withStyle(style::applyTo)
                //#else
                //$$ this.setStyle(style.applyTo(this.style))
                //#endif
                text.siblings.map(::convert).forEach(this::append)
            }
        }

        return TextComponent(content.string).apply {
            //#if MC >= 1.16.5
            this.withStyle(style::applyTo)
            //#else
            //$$ this.setStyle(style.applyTo(this.style))
            //#endif
            text.siblings.map(::convert).forEach(this::append)
        }
    }

    @JvmStatic
    public fun parse(input: String, baseStyle: MCTextStyle = MCTextStyle()): Text {
        var currentStyle = baseStyle
        val root = Text.empty()
        val builder = StringBuilder()

        fun flush() {
            if (builder.isEmpty()) {
                return
            }

            root.append(Text.literal(builder.toString(), currentStyle.build()))
            builder.setLength(0)
        }

        var i = 0
        while (i < input.length) {
            val c = input[i]
            if (c == FormattingCodes.COLOR_CHAR && i + 1 < input.length) {
                flush()
                val code = input[i + 1]
                currentStyle = FormattingCodes.applyCode(code, currentStyle)
                i += 2
                continue
            }

            builder.append(c)
            i++
        }

        flush()
        return root
    }
}
