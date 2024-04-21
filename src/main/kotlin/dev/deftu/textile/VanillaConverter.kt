package dev.deftu.textile

//#if MC <= 1.18.2 && MC >= 1.14
//$$ public typealias VanillaLiteralText = net.minecraft.text.LiteralText
//#elseif MC <= 1.12.2
//$$ public typealias VanillaLiteralText = net.minecraft.util.text.TextComponentString
//#endif

//#if MC >= 1.14
public typealias VanillaMutableText = net.minecraft.text.MutableText
//#else
//$$ public typealias VanillaMutableText = net.minecraft.util.text.TextComponentString
//#endif

public typealias VanillaText = net.minecraft.text.Text
public typealias VanillaFormatting = net.minecraft.util.Formatting

public object VanillaConverter {
    @JvmStatic
    public fun createLiteralText(content: String): VanillaMutableText {
        //#if MC>=11900
        return VanillaText.literal(content)
        //#else
        //$$ return VanillaLiteralText(content)
        //#endif
    }

    // TODO: Add support for creating text from vanilla text

    @JvmStatic
    public fun toVanillaText(text: Text): VanillaText {
        val result = createLiteralText("")
        text.children.filter { it.first == Text.TextChildPosition.BEFORE }.map(Pair<Text.TextChildPosition, Text>::second).map(::toVanillaText).forEach {
            //#if MC >= 1.14
            result.append(it)
            //#else
            //$$ result.appendSibling(it)
            //#endif
        }

        //#if MC >= 1.14
        result.append(createLiteralText(text.asContentString()).apply {
            //#else
            //$$ result.appendSibling(createLiteralText(text.asContentString()).apply {
            //#endif
            val formatting = text.formatting.map(::toVanillaFormatting)
            //#if MC >= 1.14
            formatting.forEach(this::formatted)
            //#else
            //$$ formatting.forEach { formatting ->
            //#if MC >= 1.12.2
            //$$     val style = style.createDeepCopy()
            //#else
            //$$     val style = chatStyle.createDeepCopy()
            //#endif
            //$$     if (formatting.isColor) style.setColor(formatting)
            //$$     if (formatting == VanillaFormatting.BOLD) style.setBold(true)
            //$$     if (formatting == VanillaFormatting.ITALIC) style.setItalic(true)
            //$$     if (formatting == VanillaFormatting.STRIKETHROUGH) style.setStrikethrough(true)
            //$$     if (formatting == VanillaFormatting.UNDERLINE) style.setUnderlined(true)
            //$$     if (formatting == VanillaFormatting.OBFUSCATED) style.setObfuscated(true)
            //#if MC >= 1.12.2
            //$$     setStyle(style)
            //#else
            //$$     setChatStyle(style)
            //#endif
            //$$ }
            //#endif
        })

        text.children.filter { it.first == Text.TextChildPosition.AFTER }.map(Pair<Text.TextChildPosition, Text>::second).map(::toVanillaText).forEach {
            //#if MC >= 1.14
            result.append(it)
            //#else
            //$$ result.appendSibling(it)
            //#endif
        }

        return result
    }

    @JvmStatic
    public fun toVanillaFormatting(format: TextFormatting): VanillaFormatting {
        return when (format) {
            TextFormatting.BLACK -> VanillaFormatting.BLACK
            TextFormatting.DARK_BLUE -> VanillaFormatting.DARK_BLUE
            TextFormatting.DARK_GREEN -> VanillaFormatting.DARK_GREEN
            TextFormatting.DARK_AQUA -> VanillaFormatting.DARK_AQUA
            TextFormatting.DARK_RED -> VanillaFormatting.DARK_RED
            TextFormatting.DARK_PURPLE -> VanillaFormatting.DARK_PURPLE
            TextFormatting.GOLD -> VanillaFormatting.GOLD
            TextFormatting.GRAY -> VanillaFormatting.GRAY
            TextFormatting.DARK_GRAY -> VanillaFormatting.DARK_GRAY
            TextFormatting.BLUE -> VanillaFormatting.BLUE
            TextFormatting.GREEN -> VanillaFormatting.GREEN
            TextFormatting.AQUA -> VanillaFormatting.AQUA
            TextFormatting.RED -> VanillaFormatting.RED
            TextFormatting.LIGHT_PURPLE -> VanillaFormatting.LIGHT_PURPLE
            TextFormatting.YELLOW -> VanillaFormatting.YELLOW
            TextFormatting.WHITE -> VanillaFormatting.WHITE
            TextFormatting.OBFUSCATED -> VanillaFormatting.OBFUSCATED
            TextFormatting.BOLD -> VanillaFormatting.BOLD
            TextFormatting.STRIKETHROUGH -> VanillaFormatting.STRIKETHROUGH
            TextFormatting.UNDERLINE -> VanillaFormatting.UNDERLINE
            TextFormatting.ITALIC -> VanillaFormatting.ITALIC
            TextFormatting.RESET -> VanillaFormatting.RESET
        }
    }
}

public fun Text.toVanilla(): VanillaText = VanillaConverter.toVanillaText(this)
public fun TextFormatting.toVanilla(): VanillaFormatting = VanillaConverter.toVanillaFormatting(this)
