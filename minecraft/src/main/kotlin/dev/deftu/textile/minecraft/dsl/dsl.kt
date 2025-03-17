package dev.deftu.textile.minecraft.dsl

import dev.deftu.textile.dsl.Immutable
import dev.deftu.textile.dsl.Mutable
import dev.deftu.textile.dsl.TextDsl
import dev.deftu.textile.minecraft.MCClickEvent
import dev.deftu.textile.minecraft.MCHoverEvent
import dev.deftu.textile.minecraft.MCTextFormat
import dev.deftu.textile.minecraft.MCTextHolder
import kotlin.reflect.KClass

@TextDsl
@JvmName("text1")
public fun <T : MCTextHolder<T>> text(
    type: Immutable,
    targetHolder: KClass<T>,
    initialValue: String = "",
    factory: ((String, Set<MCTextFormat>, List<T>, MCClickEvent?, MCHoverEvent?) -> T)? = null,
    block: ImmutableTextBuilder<T>.() -> Unit,
): T {
    val builder = ImmutableTextBuilder(initialValue, targetHolder, factory)
    builder.block()
    return builder.build()
}

@TextDsl
@JvmName("text2")
public inline fun <reified T : MCTextHolder<T>> text(
    type: Immutable,
    initialValue: String = "",
    noinline factory: ((String, Set<MCTextFormat>, List<T>, MCClickEvent?, MCHoverEvent?) -> T)? = null,
    noinline block: ImmutableTextBuilder<T>.() -> Unit,
): T {
    return text(Immutable, T::class, initialValue, factory, block)
}

@TextDsl
@JvmName("text1")
public fun <T : MCTextHolder<T>> text(
    type: Mutable,
    targetHolder: KClass<T>,
    initialValue: String = "",
    factory: ((String, Set<MCTextFormat>, List<T>, MCClickEvent?, MCHoverEvent?) -> T)? = null,
    block: MutableTextBuilder<T>.() -> Unit,
): T {
    val builder = MutableTextBuilder(initialValue, targetHolder, factory)
    builder.block()
    return builder.build()
}

@TextDsl
@JvmName("text2")
public inline fun <reified T : MCTextHolder<T>> text(
    type: Mutable,
    initialValue: String = "",
    noinline factory: ((String, Set<MCTextFormat>, List<T>, MCClickEvent?, MCHoverEvent?) -> T)? = null,
    noinline block: MutableTextBuilder<T>.() -> Unit,
): T {
    return text(Mutable, T::class, initialValue, factory, block)
}
