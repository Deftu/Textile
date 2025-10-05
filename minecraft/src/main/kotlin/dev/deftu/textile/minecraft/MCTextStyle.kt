package dev.deftu.textile.minecraft

import dev.deftu.textile.Text
import dev.deftu.textile.TextStyle
import net.minecraft.text.Style

//#if MC <= 1.16.5
//$$ import net.minecraft.util.Formatting
//#endif

public data class MCTextStyle @JvmOverloads public constructor(
    public val isBold: Boolean? = null,
    public val isItalic: Boolean? = null,
    public val isUnderlined: Boolean? = null,
    public val isStrikethrough: Boolean? = null,
    public val isObfuscated: Boolean? = null,
    public val color: TextColor? = null,
    public val clickEvent: ClickEvent? = null,
    public val hoverEvent: HoverEvent? = null
) {
    public companion object {
        public const val COLOR_CHAR: Char = '\u00A7'
        
        @JvmStatic
        public fun colored(color: TextColor): MCTextStyle {
            return MCTextStyle(color = color)
        }

        @JvmStatic
        public fun get(style: TextStyle): MCTextStyle {
            return MCTextStyle(
                isBold = style[MCTextStyleProperties.BOLD_KEY]?.value,
                isItalic = style[MCTextStyleProperties.ITALIC_KEY]?.value,
                isUnderlined = style[MCTextStyleProperties.UNDERLINED_KEY]?.value,
                isStrikethrough = style[MCTextStyleProperties.STRIKETHROUGH_KEY]?.value,
                isObfuscated = style[MCTextStyleProperties.OBFUSCATED_KEY]?.value,
                color = style[MCTextStyleProperties.COLOR_KEY]?.value,
                clickEvent = style[MCTextStyleProperties.CLICK_EVENT_KEY]?.value,
                hoverEvent = style[MCTextStyleProperties.HOVER_EVENT_KEY]?.value
            )
        }

        @JvmStatic
        public fun get(text: Text): MCTextStyle {
            return get(text.style)
        }

        @JvmStatic
        public fun wrap(style: Style): MCTextStyle {
            return MCTextStyle(
                isBold = if (style.isBold) style.isBold else null,
                isItalic = if (style.isItalic) style.isItalic else null,
                isUnderlined = if (style.isUnderlined) style.isUnderlined else null,
                isStrikethrough = if (style.isStrikethrough) style.isStrikethrough else null,
                isObfuscated = if (style.isObfuscated) style.isObfuscated else null,
                //#if MC >= 1.16.5
                color = style.color?.let(TextColors::wrap),
                //#else
                //$$ color = style.color?.let(TextColors::wrapFormatting),
                //#endif
                clickEvent = style.clickEvent?.let(ClickEvents::wrap),
                hoverEvent = style.hoverEvent?.let(HoverEvents::wrap),
            )
        }
    }

    public fun setBold(bold: Boolean?): MCTextStyle {
        return if (bold == isBold) this else copy(isBold = bold)
    }

    public fun bold(): MCTextStyle {
        return setBold(true)
    }

    public fun notBold(): MCTextStyle {
        return setBold(false)
    }

    public fun setItalic(italic: Boolean?): MCTextStyle {
        return if (italic == isItalic) this else copy(isItalic = italic)
    }

    public fun italic(): MCTextStyle {
        return setItalic(true)
    }

    public fun notItalic(): MCTextStyle {
        return setItalic(false)
    }

    public fun setUnderlined(underlined: Boolean?): MCTextStyle {
        return if (underlined == isUnderlined) this else copy(isUnderlined = underlined)
    }

    public fun underlined(): MCTextStyle {
        return setUnderlined(true)
    }

    public fun notUnderlined(): MCTextStyle {
        return setUnderlined(false)
    }

    public fun setStrikethrough(strikethrough: Boolean?): MCTextStyle {
        return if (strikethrough == isStrikethrough) this else copy(isStrikethrough = strikethrough)
    }

    public fun strikethrough(): MCTextStyle {
        return setStrikethrough(true)
    }

    public fun notStrikethrough(): MCTextStyle {
        return setStrikethrough(false)
    }

    public fun setObfuscated(obfuscated: Boolean?): MCTextStyle {
        return if (obfuscated == isObfuscated) this else copy(isObfuscated = obfuscated)
    }

    public fun obfuscated(): MCTextStyle {
        return setObfuscated(true)
    }

    public fun notObfuscated(): MCTextStyle {
        return setObfuscated(false)
    }

    public fun setColor(color: TextColor?): MCTextStyle {
        return if (color == this.color) this else copy(color = color)
    }

    public fun colored(color: TextColor): MCTextStyle {
        return setColor(color)
    }

    public fun clearColor(): MCTextStyle {
        return setColor(null)
    }

    public fun setClickEvent(event: ClickEvent?): MCTextStyle {
        return if (event == clickEvent) this else copy(clickEvent = event)
    }

    public fun clearClickEvent(): MCTextStyle {
        return setClickEvent(null)
    }

    public fun setHoverEvent(event: HoverEvent?): MCTextStyle {
        return if (event == hoverEvent) this else copy(hoverEvent = event)
    }

    public fun clearHoverEvent(): MCTextStyle {
        return setHoverEvent(null)
    }

    public fun build(): TextStyle {
        val props = mutableListOf<TextStyle.Property<*>>()
        isBold?.let { value -> props.add(MCTextStyleProperties.bold(value)) }
        isItalic?.let { value -> props.add(MCTextStyleProperties.italic(value)) }
        isUnderlined?.let { value -> props.add(MCTextStyleProperties.underlined(value)) }
        isStrikethrough?.let { value -> props.add(MCTextStyleProperties.strikethrough(value)) }
        isObfuscated?.let { value -> props.add(MCTextStyleProperties.obfuscated(value)) }
        color?.let { value -> props.add(MCTextStyleProperties.color(value)) }
        clickEvent?.let { value -> props.add(MCTextStyleProperties.clickEvent(value)) }
        hoverEvent?.let { value -> props.add(MCTextStyleProperties.hoverEvent(value)) }
        return TextStyle(props.associateBy(TextStyle.Property<*>::key))
    }

    public fun applyTo(vanilla: Style): Style {
        var style = vanilla
        isBold?.let { style = style.withBold(it) }
        isItalic?.let { style = style.withItalic(it) }
        isUnderlined?.let { style = style.withUnderline(it) }

        //#if MC >= 1.17.1
        isStrikethrough?.let { style = style.withStrikethrough(it) }
        isObfuscated?.let { style = style.withObfuscated(it) }
        //#elseif MC >= 1.16.5
        //$$ style = style.withFormatting(*listOfNotNull(
        //$$     if (isStrikethrough == true) Formatting.STRIKETHROUGH else null,
        //$$     if (isObfuscated == true) Formatting.OBFUSCATED else null
        //$$ ).toTypedArray())
        //#else
        //$$ isStrikethrough?.let { style = style.setStrikethrough(it) }
        //$$ isObfuscated?.let { style = style.setObfuscated(it) }
        //#endif

        //#if MC >= 1.16.5
        color?.let { style = style.withColor(TextColors.convert(it)) }
        //#else
        //$$ color?.let {
        //$$     val formatting = TextColors.convertFormatting(it) ?: return@let
        //$$     style = style.setColor(formatting)
        //$$ }
        //#endif

        clickEvent?.let { style = style.withClickEvent(ClickEvents.convert(it)) }
        hoverEvent?.let { style = style.withHoverEvent(HoverEvents.convert(it)) }
        return style
    }
}
