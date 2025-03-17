package dev.deftu.textile.minecraft.dsl

import dev.deftu.textile.dsl.Mutable
import dev.deftu.textile.dsl.TextDsl
import dev.deftu.textile.minecraft.*
import kotlin.reflect.KClass

@TextDsl
public open class MutableTextBuilder<T : MCTextHolder<T>>(
    private val initialValue: String,
    private val targetHolder: KClass<T>,
    private val factory: ((String, Set<MCTextFormat>, List<T>, MCClickEvent?, MCHoverEvent?) -> T)?
) {

    private val content = StringBuilder(initialValue)
    private val children = mutableListOf<T>()
    private val formatting = mutableSetOf<MCTextFormat>()

    public var clickEvent: MCClickEvent? = null

    public var hoverEvent: MCHoverEvent? = null

    public fun content(content: String) {
        this.content.clear()
        this.content.append(content)
    }

    public fun append(content: String) {
        this.content.append(content)
    }

    public fun append(child: T) {
        children.add(child)
    }

    public fun formatting(vararg formats: MCTextFormat) {
        formatting.addAll(formats)
    }

    public fun child(child: T) {
        children.add(child)
    }

    public fun child(
        initialValue: String = "",
        block: MutableTextBuilder<T>.() -> Unit
    ) {
        child(text(Mutable, targetHolder, initialValue, factory, block))
    }

    public fun build(): T {
        return when (targetHolder) {
            MCSimpleTextHolder::class -> {
                var text = MCSimpleTextHolder(content.toString()).withFormatting(*formatting.toTypedArray())
                clickEvent?.let { text = text.withClickEvent(it) }
                hoverEvent?.let { text = text.withHoverEvent(it) }

                text as T
            }

            MCSimpleMutableTextHolder::class -> {
                var text = MCSimpleMutableTextHolder(content.toString()).withFormatting(*formatting.toTypedArray())

                for (child in children) {
                    text.append(child)
                }

                clickEvent?.let { text = text.setClickEvent(it) }
                hoverEvent?.let { text = text.setHoverEvent(it) }

                text as T
            }

            else -> factory?.invoke(content.toString(), formatting.toSet(), children, clickEvent, hoverEvent) ?: throw IllegalStateException("No factory provided for target class")
        }
    }

}
