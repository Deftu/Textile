package dev.deftu.textile

public interface TextHolderVisitor<T> {

    public fun accept(value: String): T?

}
