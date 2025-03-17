package dev.deftu.textile.minecraft

import dev.deftu.textile.TextHolder
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import java.util.*

/**
 * Represents a class which holds, formats, and manipulates text in the context of Minecraft.
 *
 * @since 0.11.0
 * @author Deftu
 */
public interface MCTextHolder<T : MCTextHolder<T>> : TextHolder<T, MCTextFormat> {

    public companion object {

        public fun convertToVanilla(text: TextHolder<*, *>): Text {
            val result = VanillaTextHelper.createLiteralText(text.asExclusiveString()).apply {
                val formatting = text.formatting.filterIsInstance<MCTextFormat>().sortedWith(MCTextFormat.COMPARATOR).map(MCTextFormat::convertToVanilla)
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

                if (text is MCTextHolder) {
                    text.clickEvent?.let { clickEvent ->
                        val event = MCClickEvent.convertToVanilla(clickEvent)
                        //#if MC >= 1.16.5
                        this.styled { it.withClickEvent(event) }
                        //#else
                        //$$ this.style.clickEvent = event
                        //#endif
                    }

                    text.hoverEvent?.let { hoverEvent ->
                        val event = MCHoverEvent.convertToVanilla(hoverEvent)
                        //#if MC >= 1.16.5
                        this.styled { it.withHoverEvent(event) }
                        //#else
                        //$$ this.style.hoverEvent = event
                        //#endif
                    }
                }
            }

            text.children.map { convertToVanilla(it) }.forEach(result::append)
            return result
        }

        public fun convertFromVanilla(text: Text): MCTextHolder<*> {
            val result = MCSimpleMutableTextHolder("")
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
            var copiedFormatting: Set<MCTextFormat>
            result.append(MCSimpleMutableTextHolder(content.toString()).apply {
                //#if MC >= 1.16.5
                val formatting = Formatting.byName(text.style.color?.name)
                formatting?.let(MCTextFormat::convertFromVanilla)?.let { addFormatting(it) }
                //#else
                //$$ text.style.color?.let(MCTextFormat::convertFromVanilla)?.let { formatting -> addFormatting(formatting) }
                //#endif
                if (text.style.isBold) addFormatting(MCTextFormat.BOLD)
                if (text.style.isItalic) addFormatting(MCTextFormat.ITALIC)
                if (text.style.isStrikethrough) addFormatting(MCTextFormat.STRIKETHROUGH)
                if (text.style.isUnderlined) addFormatting(MCTextFormat.UNDERLINE)
                if (text.style.isObfuscated) addFormatting(MCTextFormat.OBFUSCATED)

                val clickEvent = text.style.clickEvent
                if (clickEvent != null) {
                    setClickEvent(MCClickEvent.convertFromVanilla(clickEvent))
                }

                val hoverEvent = text.style.hoverEvent
                if (hoverEvent != null) {
                    setHoverEvent(MCHoverEvent.convertFromVanilla(hoverEvent))
                }
            }.apply { copiedFormatting = formatting.toSet() })

            text.siblings.map { sibling ->
                if (copiedFormatting.isEmpty()) {
                    convertFromVanilla(sibling)
                } else {
                    convertFromVanilla(sibling, copiedFormatting)
                }
            }.forEach(result::append)
            return result
        }

        public fun convertFromVanilla(text: Text, formatting: Set<MCTextFormat>): MCTextHolder<*> {
            val result = MCSimpleMutableTextHolder("")
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
            result.append(MCSimpleMutableTextHolder(content.toString()).apply {
                formatting.forEach { addFormatting(it) }

                val clickEvent = text.style.clickEvent
                if (clickEvent != null) {
                    setClickEvent(MCClickEvent.convertFromVanilla(clickEvent))
                }

                val hoverEvent = text.style.hoverEvent
                if (hoverEvent != null) {
                    setHoverEvent(MCHoverEvent.convertFromVanilla(hoverEvent))
                }
            })

            text.siblings.map { sibling -> convertFromVanilla(sibling, formatting) }.forEach(result::append)
            return result
        }

    }

    public val clickEvent: MCClickEvent?

    public val hoverEvent: MCHoverEvent?

    public fun asVanilla(): Text {
        return convertToVanilla(this)
    }

    public fun withoutClickEvent(): T

    public fun withClickEvent(event: MCClickEvent): T

    public fun withoutHoverEvent(): T

    public fun withHoverEvent(event: MCHoverEvent): T

}
