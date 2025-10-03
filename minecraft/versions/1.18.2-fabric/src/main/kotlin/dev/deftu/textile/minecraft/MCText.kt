package dev.deftu.textile.minecraft

import dev.deftu.textile.Text
import net.minecraft.text.LiteralText
import net.minecraft.text.Text as VanillaText
import java.util.Optional

public object MCText {
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
