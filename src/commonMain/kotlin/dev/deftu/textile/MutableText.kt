package dev.deftu.textile

public class MutableText(
    override var content: TextContent,
    override var siblings: List<Text>,
    override var style: TextStyle,
) : Text {
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

    public fun setStyle(style: TextStyle): MutableText {
        this.style = style
        return this
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
