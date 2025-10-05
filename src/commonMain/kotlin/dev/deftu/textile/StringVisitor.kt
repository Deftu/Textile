package dev.deftu.textile

public sealed interface StringVisitor<T> {
    public fun interface Plain<T> : StringVisitor<T> {
        public fun visit(content: String): T?
    }

    public fun interface Styled<T> : StringVisitor<T> {
        public fun visit(content: String, style: TextStyle): T?
    }
}
