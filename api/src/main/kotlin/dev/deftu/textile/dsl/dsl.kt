package dev.deftu.textile.dsl

import dev.deftu.textile.MutableTextHolder
import dev.deftu.textile.TextFormat
import kotlin.reflect.KClass

@TextDsl
@JvmName("text1")
public fun <T : MutableTextHolder<T, F>, F : TextFormat> text(
    type: Immutable,
    targetHolder: KClass<T>,
    targetFormat: KClass<F>,
    initialValue: String = "",
    factory: ((String, Set<F>, List<T>) -> T)? = null,
    block: ImmutableTextBuilder<T, F>.() -> Unit,
): T {
    val builder = ImmutableTextBuilder(initialValue, targetHolder, targetFormat, factory)
    builder.block()
    return builder.build()
}

@TextDsl
@JvmName("text2")
public inline fun <reified T : MutableTextHolder<T, F>, reified F : TextFormat> text(
    type: Immutable,
    initialValue: String = "",
    noinline factory: ((String, Set<F>, List<T>) -> T)? = null,
    noinline block: ImmutableTextBuilder<T, F>.() -> Unit,
): T {
    return text(Immutable, T::class, F::class, initialValue, factory, block)
}

@TextDsl
@JvmName("text3")
public inline fun <reified T : MutableTextHolder<T, TextFormat>> text(
    type: Immutable,
    initialValue: String = "",
    noinline factory: ((String, Set<TextFormat>, List<T>) -> T)? = null,
    noinline block: ImmutableTextBuilder<T, TextFormat>.() -> Unit
): T {
    return text<T, TextFormat>(Immutable, initialValue, factory, block)
}

@TextDsl
@JvmName("text1")
public fun <T : MutableTextHolder<T, F>, F : TextFormat> text(
    type: Mutable,
    targetHolder: KClass<T>,
    targetFormat: KClass<F>,
    initialValue: String = "",
    factory: ((String, Set<F>, List<T>) -> T)? = null,
    block: MutableTextBuilder<T, F>.() -> Unit,
): T {
    val builder = MutableTextBuilder(initialValue, targetHolder, targetFormat, factory)
    builder.block()
    return builder.build()
}

@TextDsl
@JvmName("text2")
public inline fun <reified T : MutableTextHolder<T, F>, reified F : TextFormat> text(
    type: Mutable,
    initialValue: String = "",
    noinline factory: ((String, Set<F>, List<T>) -> T)? = null,
    noinline block: MutableTextBuilder<T, F>.() -> Unit,
): T {
    return text(Mutable, T::class, F::class, initialValue, factory, block)
}

@TextDsl
@JvmName("text3")
public inline fun <reified T : MutableTextHolder<T, TextFormat>> text(
    type: Mutable,
    initialValue: String = "",
    noinline factory: ((String, Set<TextFormat>, List<T>) -> T)? = null,
    noinline block: MutableTextBuilder<T, TextFormat>.() -> Unit
): T {
    return text<T, TextFormat>(Mutable, initialValue, factory, block)
}

