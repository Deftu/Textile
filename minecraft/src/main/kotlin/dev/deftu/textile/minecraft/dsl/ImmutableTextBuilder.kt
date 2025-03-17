package dev.deftu.textile.minecraft.dsl

import dev.deftu.textile.dsl.TextDsl
import dev.deftu.textile.minecraft.*
import kotlin.reflect.KClass

@TextDsl
public open class ImmutableTextBuilder<T : MCTextHolder<T>>(
    private val initialValue: String,
    private val targetHolder: KClass<T>,
    private val factory: ((String, Set<MCTextFormat>, List<T>, MCClickEvent?, MCHoverEvent?) -> T)?
) {

    private val content = StringBuilder(initialValue)
    private val formatting = mutableSetOf<MCTextFormat>()

    public val clickEvent: MCClickEvent? = null

    public val hoverEvent: MCHoverEvent? = null

    public fun content(content: String) {
        this.content.clear()
        this.content.append(content)
    }

    public fun append(content: String) {
        this.content.append(content)
    }

    public fun formatting(vararg formats: MCTextFormat) {
        formatting.addAll(formats)
    }

    public fun build(): T {
        return when (targetHolder) {
            MCSimpleTextHolder::class -> {
                var text = MCSimpleTextHolder(content.toString()).withFormatting(*formatting.toTypedArray())

                if (clickEvent != null) {
                    text = text.withClickEvent(clickEvent)
                }

                if (hoverEvent != null) {
                    text = text.withHoverEvent(hoverEvent)
                }

                text as T
            }

            MCSimpleMutableTextHolder::class -> {
                var text = MCSimpleMutableTextHolder(content.toString()).withFormatting(*formatting.toTypedArray())

                if (clickEvent != null) {
                    text = text.setClickEvent(clickEvent)
                }

                if (hoverEvent != null) {
                    text = text.setHoverEvent(hoverEvent)
                }

                text as T
            }

            else -> factory?.invoke(content.toString(), formatting.toSet(), emptyList(), clickEvent, hoverEvent) ?: throw IllegalStateException("No factory provided for target class")
        }
    }

}
