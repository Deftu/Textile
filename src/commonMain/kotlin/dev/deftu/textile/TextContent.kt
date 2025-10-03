package dev.deftu.textile

import kotlin.jvm.JvmField

public interface TextContent : StringVisitable {
    public data class ContentType<T : TextContent>(val id: String)

    public data object Empty : Plain {
        override fun <T> visit(visitor: StringVisitor.Plain<T>): T? {
            return visitor.visit("")
        }

        override fun <T> visit(visitor: StringVisitor.Styled<T>, style: TextStyle): T? {
            return visitor.visit("", style)
        }

        override fun toString(): String {
            return "Empty"
        }
    }

    public interface Plain : TextContent {
        public companion object {
            @JvmField public val TYPE: ContentType<Plain> = ContentType("text")
        }

        override val type: ContentType<out TextContent>
            get() = TYPE
    }

    public data class Literal(public val text: String) : Plain {
        override fun <T> visit(visitor: StringVisitor.Plain<T>): T? {
            return visitor.visit(text)
        }

        override fun <T> visit(visitor: StringVisitor.Styled<T>, style: TextStyle): T? {
            return visitor.visit(text, style)
        }

        override fun toString(): String {
            return "Literal(\"$text\")"
        }
    }

    public val type: ContentType<out TextContent>
}
