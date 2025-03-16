package dev.deftu.textile.dsl

import dev.deftu.textile.*
import kotlin.reflect.KClass

@TextDsl
public open class MutableTextBuilder<T : MutableTextHolder<T, F>, F : TextFormat>(
    private val initialValue: String,
    private val targetHolder: KClass<T>,
    private val targetFormat: KClass<F>,
    private val factory: ((String, Set<F>, List<T>) -> T)?
) {

    private val content = StringBuilder()
    private val children = mutableListOf<T>()
    private val formatting = mutableSetOf<F>()

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

    public fun formatting(vararg formats: F) {
        formatting.addAll(formats)
    }

    public fun child(child: T) {
        children.add(child)
    }

    public fun child(
        initialValue: String = "",
        block: MutableTextBuilder<T, F>.() -> Unit
    ) {
        child(text(Mutable, targetHolder, targetFormat, initialValue, factory, block))
    }

    public fun build(): T {
        val formatting = java.lang.reflect.Array.newInstance(targetFormat.java, this.formatting.size) as Array<F>
        for ((index, item) in this.formatting.withIndex()) {
            formatting[index] = item
        }

        return when (targetHolder) {
            SimpleTextHolder::class -> SimpleTextHolder(content.toString()).withFormatting(*formatting) as T

            SimpleMutableTextHolder::class -> {
                val text = SimpleMutableTextHolder(content.toString()).withFormatting(*formatting)
                for (child in children) {
                    text.append(child)
                }

                text as T
            }

            else -> factory?.invoke(content.toString(), formatting.toSet(), children) ?: throw IllegalStateException("No factory provided for target class")
        }
    }

}
