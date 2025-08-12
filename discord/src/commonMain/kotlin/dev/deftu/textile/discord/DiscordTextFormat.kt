package dev.deftu.textile.discord

import dev.deftu.textile.TextFormat

public sealed class DiscordTextFormat : TextFormat() {
    public data object Bold : DiscordTextFormat() {
        override val start: String = "**"
        override val end: String = "**"
    }

    public data object Italic : DiscordTextFormat() {
        override val start: String = "*"
        override val end: String = "*"
    }

    public data object Underline : DiscordTextFormat() {
        override val start: String = "__"
        override val end: String = "__"
    }

    public data object Strikethrough : DiscordTextFormat() {
        override val start: String = "~~"
        override val end: String = "~~"
    }

    public data object InlineCode : DiscordTextFormat() {
        override val start: String = "`"
        override val end: String = "`"
    }

    public data class CodeBlock(public val language: String? = null) : DiscordTextFormat() {
        override val start: String = "```${language ?: ""}\n"
        override val end: String = "\n```"
    }

    public data object Spoiler : DiscordTextFormat() {
        override val start: String = "||"
        override val end: String = "||"
    }

    public data object QuoteLine : DiscordTextFormat() {
        override val start: String = "> "
        override val end: String = ""
    }

    public data object QuoteBlock : DiscordTextFormat() {
        override val start: String = ">>> "
        override val end: String = ""
    }
}
