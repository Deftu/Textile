package dev.deftu.textile.discord

import dev.deftu.textile.SimpleMutableTextHolder
import dev.deftu.textile.SimpleTextHolder
import kotlin.test.Test
import kotlin.test.assertEquals

class FormattingTests {
    companion object {
        const val TEST_PHRASE = "The quick brown fox jumps over the lazy dog"

        fun createTestText(): SimpleTextHolder {
            return SimpleTextHolder(TEST_PHRASE)
        }
    }

    @Test
    fun bold_formatting() {
        val text = createTestText().withFormatting(DiscordTextFormat.Bold)
        assertEquals("**${TEST_PHRASE}**", text.asString())
    }

    @Test
    fun italic_formatting() {
        val text = createTestText().withFormatting(DiscordTextFormat.Italic)
        assertEquals("*${TEST_PHRASE}*", text.asString())
    }

    @Test
    fun underline_formatting() {
        val text = createTestText().withFormatting(DiscordTextFormat.Underline)
        assertEquals("__${TEST_PHRASE}__", text.asString())
    }

    @Test
    fun strikethrough_formatting() {
        val text = createTestText().withFormatting(DiscordTextFormat.Strikethrough)
        assertEquals("~~${TEST_PHRASE}~~", text.asString())
    }

    @Test
    fun inline_code_formatting() {
        val text = createTestText().withFormatting(DiscordTextFormat.InlineCode)
        assertEquals("`${TEST_PHRASE}`", text.asString())
    }

    @Test
    fun code_block_formatting() {
        val text = createTestText().withFormatting(DiscordTextFormat.CodeBlock("kotlin"))
        assertEquals("```kotlin\n${TEST_PHRASE}\n```", text.asString())
    }

    @Test
    fun spoiler_formatting() {
        val text = createTestText().withFormatting(DiscordTextFormat.Spoiler)
        assertEquals("||${TEST_PHRASE}||", text.asString())
    }

    @Test
    fun quote_line_formatting() {
        val text = createTestText().withFormatting(DiscordTextFormat.QuoteLine)
        assertEquals("> $TEST_PHRASE", text.asString())
    }

    @Test
    fun quote_block_formatting() {
        val text = createTestText().withFormatting(DiscordTextFormat.QuoteBlock)
        assertEquals(">>> $TEST_PHRASE", text.asString())
    }

    @Test
    fun bold_and_italic_formatting() {
        val text = createTestText().withFormatting(
            DiscordTextFormat.Bold,
            DiscordTextFormat.Italic
        )

        assertEquals("***$TEST_PHRASE***", text.asString())
    }

    @Test
    fun multiple_formats() {
        val text = createTestText().withFormatting(
            DiscordTextFormat.Bold,
            DiscordTextFormat.Italic,
            DiscordTextFormat.Underline
        )

        assertEquals("***__${TEST_PHRASE}__***", text.asString())
    }

    @Test
    fun multiple_formats_with_different_orders() {
        val text = createTestText().withFormatting(
            DiscordTextFormat.Underline,
            DiscordTextFormat.Bold,
            DiscordTextFormat.Italic
        )

        assertEquals("__***$TEST_PHRASE***__", text.asString())
    }

    @Test
    fun rendered_correctly_in_siblings() {
        val parentText = SimpleMutableTextHolder.fromImmutable(createTestText().withFormatting(DiscordTextFormat.Bold))
        val childText = createTestText().withFormatting(DiscordTextFormat.Spoiler)
        parentText.append(childText)
        assertEquals("**${TEST_PHRASE}**||${TEST_PHRASE}||", parentText.asString())
    }

    @Test
    fun rendered_correctly_in_siblings_with_multiple_formats() {
        val parentText = SimpleMutableTextHolder.fromImmutable(createTestText().withFormatting(DiscordTextFormat.Bold, DiscordTextFormat.Italic))
        val childText = createTestText().withFormatting(DiscordTextFormat.Spoiler, DiscordTextFormat.Underline)
        parentText.append(childText)
        assertEquals("***${TEST_PHRASE}***||__${TEST_PHRASE}__||", parentText.asString())
    }

    @Test
    fun rendered_correctly_in_siblings_with_clashing_formats() {
        val parentText = SimpleMutableTextHolder.fromImmutable(createTestText().withFormatting(DiscordTextFormat.Bold, DiscordTextFormat.Italic))
        val childText = createTestText().withFormatting(DiscordTextFormat.Bold, DiscordTextFormat.Underline)
        parentText.append(childText)
        assertEquals("***${TEST_PHRASE}*****__${TEST_PHRASE}__**", parentText.asString())
    }
}
