package dev.deftu.textile

import kotlin.jvm.JvmStatic

public interface StringVisitable {
    public companion object {
        @JvmStatic
        public fun plain(text: String): StringVisitable {
            return object : StringVisitable {
                override fun <T> visit(visitor: StringVisitor.Plain<T>): T? {
                    return visitor.visit(text)
                }

                override fun <T> visit(visitor: StringVisitor.Styled<T>, style: TextStyle): T? {
                    return visitor.visit(text, style)
                }

                override fun toString(): String {
                    return "StringVisitable.Plain($text)"
                }
            }
        }
    }

    public data object Empty : StringVisitable {
        override fun <T> visit(visitor: StringVisitor.Plain<T>): T? {
            return null
        }

        override fun <T> visit(visitor: StringVisitor.Styled<T>, style: TextStyle): T? {
            return null
        }
    }

    public val string: String
        get() {
            return buildString {
                visit { text ->
                    append(text)
                    null
                }
            }
        }

    public fun <T> visit(visitor: StringVisitor.Plain<T>): T?
    public fun <T> visit(visitor: StringVisitor.Styled<T>, style: TextStyle): T?
}
