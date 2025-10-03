package dev.deftu.textile.minecraft

import dev.deftu.textile.MutableText
import dev.deftu.textile.Text
import dev.deftu.textile.TextStyle
import net.minecraft.text.LiteralText
import net.minecraft.text.Text as VanillaText

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
        val result = Text.literal(text.string)
            .setStyle(MCTextStyle.wrap(text.style).build())
        text.siblings.map(::wrap).forEach(result::append)
        return result
    }

    @JvmStatic
    public fun convert(text: Text): VanillaText {
        return LiteralText(text.content.string).apply {
            val style = MCTextStyle.get(text)
            //#if MC >= 1.16.5
            this.styled(style::applyTo)
            //#else
            //$$ this.setStyle(style.applyTo(this.style))
            //#endif
            text.siblings.map(::convert).forEach(this::append)
        }
    }
}
