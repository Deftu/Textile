package dev.deftu.textile

import kotlin.jvm.JvmStatic

public interface Text : StringVisitable {
    public companion object {
        @JvmStatic
        public fun of(content: TextContent): MutableText {
            return MutableText(content, emptyList(), TextStyle.EMPTY)
        }

        @JvmStatic
        public fun of(content: TextContent, style: TextStyle): MutableText {
            return MutableText(content, emptyList(), style)
        }

        @JvmStatic
        public fun of(content: TextContent, builder: TextStyleBuilder): MutableText {
            return MutableText(content, emptyList(), builder.build())
        }

        @JvmStatic
        public fun empty(): MutableText {
            return of(TextContent.Empty)
        }

        @JvmStatic
        public fun literal(content: String): MutableText {
            return of(TextContent.Literal(content))
        }

        @JvmStatic
        public fun literal(content: String, style: TextStyle): MutableText {
            return of(TextContent.Literal(content), style)
        }

        @JvmStatic
        public fun literal(content: String, builder: TextStyleBuilder): MutableText {
            return of(TextContent.Literal(content), builder.build())
        }
    }

    public val content: TextContent
    public val style: TextStyle
    public val siblings: List<Text>

    override fun <T> visit(visitor: StringVisitor.Plain<T>): T? {
        content.visit(visitor)?.let { return it }
        for (sibling in siblings) {
            sibling.visit(visitor)?.let { return it }
        }

        return null
    }

    override fun <T> visit(visitor: StringVisitor.Styled<T>, style: TextStyle): T? {
        val merged = this.style.inherited(style)
        content.visit(visitor, merged)?.let { return it }
        for (sibling in siblings) {
            sibling.visit(visitor, merged)?.let { return it }
        }

        return null
    }

    public fun collapseToString(): String {
        val openingOrder = compareBy(TextStyle.Property<*>::key)
        val closingOrder = openingOrder.reversed()

        val builder = StringBuilder()
        var currentProperties = listOf<TextStyle.Property<*>>()
        visit({ content, style ->
            if (content.isEmpty()) {
                return@visit null
            }

            val nextProperties = style.properties

            // Check identity as fast path to skip a list comparison in the case that the instance is unchanged
            // We'll fall back to equality check in case the instance is different but the properties are the same
            @Suppress("SuspiciousEqualsCombination")
            if (nextProperties === currentProperties || nextProperties == currentProperties) {
                builder.append(content)
                currentProperties = nextProperties
                return@visit null
            }

            val currentByKey = currentProperties.associateBy(TextStyle.Property<*>::key)
            val nextByKey = nextProperties.associateBy(TextStyle.Property<*>::key)

            currentByKey.values.asSequence()
                .filter { prop -> nextByKey[prop.key]?.value != prop.value }
                .sortedWith(closingOrder)
                .mapNotNull(TextStyle.Property<*>::right)
                .forEach(builder::append)

            nextByKey.values.asSequence()
                .filter { prop -> currentByKey[prop.key]?.value != prop.value }
                .sortedWith(openingOrder)
                .mapNotNull(TextStyle.Property<*>::left)
                .forEach(builder::append)

            builder.append(content)
            currentProperties = nextProperties
            null
        }, TextStyle.EMPTY)

        currentProperties.asSequence()
            .sortedWith(closingOrder)
            .mapNotNull(TextStyle.Property<*>::right)
            .forEach(builder::append)

        return builder.toString()
    }

    public fun withStyle(style: TextStyle): Text {
        return visit({ content, style ->
            if (content.isNotEmpty()) {
                literal(content).setStyle(style)
            } else {
                empty()
            }
        }, style) ?: empty()
    }

    public fun withStyle(builder: TextStyleBuilder): Text {
        return withStyle(builder.build())
    }

    public fun withoutStyle(): Text {
        return withStyle(TextStyle.EMPTY)
    }

    public fun deepCopy(): Text {
        val copiedSiblings = siblings.map(Text::deepCopy)
        return MutableText(content, copiedSiblings, style)
    }

    public fun shallowCopy(): Text {
        return MutableText(content, emptyList(), style)
    }
}
