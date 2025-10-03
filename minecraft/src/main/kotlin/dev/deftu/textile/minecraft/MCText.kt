package dev.deftu.textile.minecraft

import dev.deftu.textile.MutableText
import dev.deftu.textile.Text
import dev.deftu.textile.TextStyle
import net.minecraft.text.Text as VanillaText
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
    public fun wrap(text: VanillaText): Text {
        val result = Text.literal(buildString {
            text.content.visit { content ->
                append(content)
                Optional.empty<Any>()
            }
        }).setStyle(MCTextStyle.wrap(text.style).build())
        text.siblings.map(::wrap).forEach(result::append)
        return result
    }

    @JvmStatic
    public fun convert(text: Text): VanillaText {
        return VanillaText.literal(text.content.string).apply {
            val style = MCTextStyle.get(text)
            this.styled(style::applyTo)
            text.siblings.map(::convert).forEach(this::append)
        }
    }
}
