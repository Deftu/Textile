package dev.deftu.textile

import java.util.Optional

public interface TextHolderVisitor<T> {

    public fun accept(value: String): Optional<T>

}
