package dev.deftu.textile.discord

public data class CodeBlock(public val language: String? = null) {
    public val openingTag: String
        get() {
            return if (language != null) "```$language\n" else "```\n"
        }

    public val closingTag: String
        get() {
            return "\n```"
        }
}
