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

        @JvmStatic
        public fun concat(vararg parts: Text): MutableText {
            return when (parts.size) {
                0 -> empty()
                1 -> parts[0].shallowCopy() as MutableText
                else -> {
                    val head = parts[0].shallowCopy() as MutableText
                    for (i in 1 until parts.size) {
                        head.append(parts[i].shallowCopy())
                    }

                    head
                }
            }
        }

        @JvmStatic
        public fun join(separator: Text, parts: Iterable<Text>): MutableText {
            val iterator = parts.iterator()
            if (!iterator.hasNext()) {
                return empty()
            }

            val head = iterator.next().shallowCopy() as MutableText
            while (iterator.hasNext()) {
                head.append(separator.shallowCopy())
                head.append(iterator.next().shallowCopy())
            }

            return head
        }

        @JvmStatic
        public fun join(separator: Text, vararg parts: Text): MutableText {
            return join(separator, parts.asList())
        }

        @JvmStatic
        public fun join(separator: String, parts: Iterable<Text>): MutableText {
            return join(literal(separator), parts)
        }

        @JvmStatic
        public fun join(separator: String, vararg parts: Text): MutableText {
            return join(literal(separator), parts.asList())
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

    public fun collapseToString(mode: CollapseMode = CollapseMode.AUTO): String {
        val resolvedMode: CollapseMode = when (mode) {
            CollapseMode.DELTA -> CollapseMode.DELTA
            CollapseMode.SCOPED -> CollapseMode.SCOPED
            CollapseMode.AUTO -> run {
                var isScoped = false
                this.visit({ content, style ->
                    if (!isScoped && content.isNotEmpty() && style.properties.any { it.right != null }) {
                        isScoped = true
                    }
                    null
                }, TextStyle.EMPTY)

                if (isScoped) CollapseMode.SCOPED else CollapseMode.DELTA
            }
        }

        val openingOrder = compareBy(TextStyle.Property<*>::key)
        val closingOrder = openingOrder.reversed()

        val builder = StringBuilder()
        var currentProperties = listOf<TextStyle.Property<*>>()

        fun process(content: String, style: TextStyle) {
            if (content.isEmpty()) return

            val nextProperties = style.properties

            @Suppress("SuspiciousEqualsCombination")
            if (nextProperties === currentProperties || nextProperties == currentProperties) {
                builder.append(content)
                if (resolvedMode == CollapseMode.SCOPED) {
                    val closers = nextProperties.asSequence()
                        .sortedWith(closingOrder)
                        .mapNotNull(TextStyle.Property<*>::right)
                        .toList()
                    if (closers.isNotEmpty()) {
                        closers.forEach(builder::append)
                        currentProperties = emptyList()
                    } else {
                        currentProperties = nextProperties
                    }
                } else {
                    currentProperties = nextProperties
                }
                return
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

            if (resolvedMode == CollapseMode.SCOPED) {
                val scopedClosers = nextProperties.asSequence()
                    .sortedWith(closingOrder)
                    .mapNotNull(TextStyle.Property<*>::right)
                    .toList()
                if (scopedClosers.isNotEmpty()) {
                    scopedClosers.forEach(builder::append)
                    currentProperties = emptyList()
                } else {
                    currentProperties = nextProperties
                }
            } else {
                currentProperties = nextProperties
            }
        }

        fun walk(node: Text) {
            node.content.visit { text ->
                process(text, node.style) ; null
            }
            for (s in node.siblings) walk(s)
        }

        walk(this)

        currentProperties.asSequence()
            .sortedWith(closingOrder)
            .mapNotNull(TextStyle.Property<*>::right)
            .forEach(builder::append)

        return builder.toString()
    }

    public fun collapseToString(): String {
        return collapseToString(CollapseMode.AUTO)
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
