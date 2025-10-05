package dev.deftu.textile

public class MutableText(
    content: TextContent,
    siblings: List<Text>,
    style: TextStyle,
) : Text {
    override var content: TextContent = content
        private set

    override var siblings: List<Text> = siblings
        private set

    override var style: TextStyle = style
        private set

    public fun append(text: Text): MutableText {
        this.siblings += text
        return this
    }

    public fun append(content: String): MutableText {
        return append(Text.literal(content))
    }

    public fun fillStyle(style: TextStyle): MutableText {
        this.style = this.style.inherited(style)
        return this
    }

    public fun fillStyle(builder: TextStyleBuilder): MutableText {
        return fillStyle(builder.build())
    }

    public fun setStyle(style: TextStyle): MutableText {
        this.style = style
        return this
    }

    public fun setStyle(builder: TextStyleBuilder): MutableText {
        return setStyle(builder.build())
    }

    override fun toString(): String {
        return buildString {
            append(content.toString())

            val isStyled = style != TextStyle.EMPTY
            val hasSiblings = siblings.isNotEmpty()
            if (isStyled || hasSiblings) {
                append(" { ")
                if (isStyled) {
                    append("style=$style")
                }

                if (hasSiblings) {
                    if (isStyled) {
                        append(", ")
                    }

                    append("siblings=$siblings")
                }

                append(" }")
            }
        }
    }
}
