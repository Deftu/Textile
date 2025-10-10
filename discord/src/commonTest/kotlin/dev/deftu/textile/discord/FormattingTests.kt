package dev.deftu.textile.discord

import dev.deftu.textile.MutableText
import dev.deftu.textile.Text
import kotlin.test.Test
import kotlin.test.assertEquals

class FormattingTests {
    companion object {
        const val TEST_PHRASE = "The quick brown fox jumps over the lazy dog"

        fun createTestText(): MutableText {
            return Text.literal(TEST_PHRASE)
        }
    }

    @Test
    fun bold_formatting() {
        val text = createTestText().setStyle(DCTextStyle(isBold = true))
        assertEquals("**${TEST_PHRASE}**", text.collapseToString())
    }

    @Test
    fun italic_formatting() {
        val text = createTestText().setStyle(DCTextStyle(isItalic = true))
        assertEquals("*${TEST_PHRASE}*", text.collapseToString())
    }

    @Test
    fun underline_formatting() {
        val text = createTestText().setStyle(DCTextStyle(isUnderlined = true))
        assertEquals("__${TEST_PHRASE}__", text.collapseToString())
    }

    @Test
    fun strikethrough_formatting() {
        val text = createTestText().setStyle(DCTextStyle(isStrikethrough = true))
        assertEquals("~~${TEST_PHRASE}~~", text.collapseToString())
    }

    @Test
    fun inline_code_formatting() {
        val text = createTestText().setStyle(DCTextStyle(isInlineCode = true))
        assertEquals("`${TEST_PHRASE}`", text.collapseToString())
    }

    @Test
    fun code_block_formatting() {
        val text = createTestText().setStyle(DCTextStyle().setCodeBlockLanguage("kotlin"))
        assertEquals("```kotlin\n${TEST_PHRASE}\n```", text.collapseToString())
    }

    @Test
    fun spoiler_formatting() {
        val text = createTestText().setStyle(DCTextStyle(isSpoiler = true))
        assertEquals("||${TEST_PHRASE}||", text.collapseToString())
    }

    @Test
    fun quote_line_formatting() {
        val text = createTestText().setStyle(DCTextStyle(isQuoteLine = true))
        assertEquals("> $TEST_PHRASE", text.collapseToString())
    }

    @Test
    fun quote_block_formatting() {
        val text = createTestText().setStyle(DCTextStyle(isQuoteBlock = true))
        assertEquals(">>> $TEST_PHRASE", text.collapseToString())
    }

    @Test
    fun bold_and_italic_formatting() {
        val text = createTestText().setStyle(DCTextStyle(isBold = true, isItalic = true))
        assertEquals("***$TEST_PHRASE***", text.collapseToString())
    }

    @Test
    fun multiple_formats() {
        val text = createTestText().setStyle(DCTextStyle(isBold = true, isItalic = true, isUnderlined = true))
        assertEquals("***__${TEST_PHRASE}__***", text.collapseToString())
    }

    @Test
    fun multiple_formats_hyperlink() {
        val text = createTestText().setStyle(DCTextStyle(isBold = true, isItalic = true, isUnderlined = true, hyperlink = "https://example.com"))
        assertEquals("***__[${TEST_PHRASE}](https://example.com)__***", text.collapseToString())
    }

    @Test
    fun multiple_siblings() {
        val text = createTestText().append(" ").append(createTestText().setStyle(DCTextStyle(isBold = true, hyperlink = "https://youtube.com")))
        assertEquals("$TEST_PHRASE **[$TEST_PHRASE](https://youtube.com)**", text.collapseToString())
    }
}
