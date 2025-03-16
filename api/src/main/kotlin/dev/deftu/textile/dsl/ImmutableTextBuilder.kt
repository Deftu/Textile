package dev.deftu.textile.dsl

import dev.deftu.textile.SimpleMutableTextHolder
import dev.deftu.textile.SimpleTextHolder
import dev.deftu.textile.TextFormat
import dev.deftu.textile.TextHolder
import kotlin.reflect.KClass

@TextDsl
public open class ImmutableTextBuilder<T : TextHolder<T, F>, F : TextFormat>(
    private val initialValue: String,
    private val targetHolder: KClass<T>,
    private val targetFormat: KClass<F>,
    private val factory: ((String, Set<F>, List<T>) -> T)?
) {

    private val content = StringBuilder()
    private val formatting = mutableSetOf<F>()

    public fun content(content: String) {
        this.content.clear()
        this.content.append(content)
    }

    public fun append(content: String) {
        this.content.append(content)
    }

    public fun formatting(vararg formats: F) {
        formatting.addAll(formats)
    }

    public fun build(): T {
        val formatting = java.lang.reflect.Array.newInstance(targetFormat.java, this.formatting.size) as Array<F>
        for ((index, item) in this.formatting.withIndex()) {
            formatting[index] = item
        }

        return when (targetHolder) {
            SimpleTextHolder::class -> SimpleTextHolder(content.toString()).withFormatting(*formatting) as T
            SimpleMutableTextHolder::class -> SimpleMutableTextHolder(content.toString()).withFormatting(*formatting) as T
            else -> factory?.invoke(content.toString(), formatting.toSet(), emptyList<T>()) ?: throw IllegalStateException("No factory provided for target class")
        }
    }

}
