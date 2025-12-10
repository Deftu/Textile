package dev.deftu.textile

public fun text(baseStyle: TextStyle = TextStyle.EMPTY, block: TextBuilder.() -> Unit): Text {
    val builder = TextBuilder(baseStyle)
    builder.block()
    return builder.build()
}

public class TextBuilder internal constructor(baseStyle: TextStyle = TextStyle.EMPTY) {
    private val content = Text.empty().setStyle(baseStyle)

    public fun of(content: TextContent): TextBuilder {
        this.content.append(Text.of(content))
        return this
    }

    public fun of(content: TextContent, style: TextStyle): TextBuilder {
        this.content.append(Text.of(content, style))
        return this
    }

    public fun of(content: TextContent, builder: TextStyleBuilder): TextBuilder {
        this.content.append(Text.of(content, builder))
        return this
    }

    public fun empty(): TextBuilder {
        this.content.append(Text.empty())
        return this
    }

    public fun literal(content: String): TextBuilder {
        this.content.append(Text.literal(content))
        return this
    }

    public fun literal(content: String, style: TextStyle): TextBuilder {
        this.content.append(Text.literal(content, style))
        return this
    }

    public fun literal(content: String, builder: TextStyleBuilder): TextBuilder {
        this.content.append(Text.literal(content, builder))
        return this
    }

    public fun append(text: Text): TextBuilder {
        this.content.append(text)
        return this
    }

    public fun append(block: TextBuilder.() -> Unit): TextBuilder {
        val nestedBuilder = TextBuilder()
        nestedBuilder.block()
        this.content.append(nestedBuilder.build())
        return this
    }

    public fun append(style: TextStyle, block: TextBuilder.() -> Unit): TextBuilder {
        val nestedBuilder = TextBuilder()
        nestedBuilder.block()
        this.content.append((nestedBuilder.build() as MutableText).setStyle(style))
        return this
    }

    public fun append(builder: TextStyleBuilder, block: TextBuilder.() -> Unit): TextBuilder {
        val nestedBuilder = TextBuilder()
        nestedBuilder.block()
        this.content.append((nestedBuilder.build() as MutableText).setStyle(builder))
        return this
    }

    public fun String.unaryPlus(): TextBuilder {
        this@TextBuilder.content.append(Text.literal(this))
        return this@TextBuilder
    }

    public fun String.invoke(): TextBuilder {
        this@TextBuilder.content.append(Text.literal(this))
        return this@TextBuilder
    }

    public fun String.invoke(style: TextStyle): TextBuilder {
        this@TextBuilder.content.append(Text.literal(this, style))
        return this@TextBuilder
    }

    public fun String.invoke(builder: TextStyleBuilder): TextBuilder {
        this@TextBuilder.content.append(Text.literal(this, builder))
        return this@TextBuilder
    }

    public fun build(): Text {
        return content.shallowCopy()
    }
}
