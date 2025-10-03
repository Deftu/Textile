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
        val text = createTestText().setStyle(DCTextStyle(isBold = true).build())
        assertEquals("**${TEST_PHRASE}**", text.collapseToString())
    }

    @Test
    fun italic_formatting() {
        val text = createTestText().setStyle(DCTextStyle(isItalic = true).build())
        assertEquals("*${TEST_PHRASE}*", text.collapseToString())
    }

    @Test
    fun underline_formatting() {
        val text = createTestText().setStyle(DCTextStyle(isUnderlined = true).build())
        assertEquals("__${TEST_PHRASE}__", text.collapseToString())
    }

    @Test
    fun strikethrough_formatting() {
        val text = createTestText().setStyle(DCTextStyle(isStrikethrough = true).build())
        assertEquals("~~${TEST_PHRASE}~~", text.collapseToString())
    }

    @Test
    fun inline_code_formatting() {
        val text = createTestText().setStyle(DCTextStyle(isInlineCode = true).build())
        assertEquals("`${TEST_PHRASE}`", text.collapseToString())
    }

    @Test
    fun code_block_formatting() {
        val text = createTestText().setStyle(DCTextStyle(codeBlockLanguage = "kotlin").build())
        assertEquals("```kotlin\n${TEST_PHRASE}\n```", text.collapseToString())
    }

    @Test
    fun spoiler_formatting() {
        val text = createTestText().setStyle(DCTextStyle(isSpoiler = true).build())
        assertEquals("||${TEST_PHRASE}||", text.collapseToString())
    }

    @Test
    fun quote_line_formatting() {
        val text = createTestText().setStyle(DCTextStyle(isQuoteLine = true).build())
        assertEquals("> $TEST_PHRASE", text.collapseToString())
    }

    @Test
    fun quote_block_formatting() {
        val text = createTestText().setStyle(DCTextStyle(isQuoteBlock = true).build())
        assertEquals(">>> $TEST_PHRASE", text.collapseToString())
    }

    @Test
    fun bold_and_italic_formatting() {
        val text = createTestText().setStyle(DCTextStyle(isBold = true, isItalic = true).build())
        assertEquals("***$TEST_PHRASE***", text.collapseToString())
    }

    @Test
    fun multiple_formats() {
        val text = createTestText().setStyle(DCTextStyle(isBold = true, isItalic = true, isUnderlined = true).build())
        assertEquals("***__${TEST_PHRASE}__***", text.collapseToString())
    }

//    @Test
//    fun rendered_correctly_in_siblings() {
//        val parentText = SimpleMutableTextHolder.fromImmutable(createTestText().withFormatting(DiscordTextFormat.Bold))
//        val childText = createTestText().withFormatting(DiscordTextFormat.Spoiler)
//        parentText.append(childText)
//        assertEquals("**${TEST_PHRASE}**||${TEST_PHRASE}||", parentText.asString())
//    }
//
//    @Test
//    fun rendered_correctly_in_siblings_with_multiple_formats() {
//        val parentText = SimpleMutableTextHolder.fromImmutable(createTestText().withFormatting(DiscordTextFormat.Bold, DiscordTextFormat.Italic))
//        val childText = createTestText().withFormatting(DiscordTextFormat.Spoiler, DiscordTextFormat.Underline)
//        parentText.append(childText)
//        assertEquals("***${TEST_PHRASE}***||__${TEST_PHRASE}__||", parentText.asString())
//    }
//
//    @Test
//    fun rendered_correctly_in_siblings_with_clashing_formats() {
//        val parentText = SimpleMutableTextHolder.fromImmutable(createTestText().withFormatting(DiscordTextFormat.Bold, DiscordTextFormat.Italic))
//        val childText = createTestText().withFormatting(DiscordTextFormat.Bold, DiscordTextFormat.Underline)
//        parentText.append(childText)
//        assertEquals("***${TEST_PHRASE}*****__${TEST_PHRASE}__**", parentText.asString())
//    }
}
