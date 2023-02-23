package xyz.deftu.text.utils

import xyz.deftu.text.Text
import xyz.deftu.text.TextFormatting

//#if MC>=11200
typealias VanillaText = net.minecraft.text.Text
//#else
//$$ typealias VanillaText = net.minecraft.util.IChatComponent
//#endif

//#if MC<=11802 && MC>=11400
//$$ typealias VanillaLiteralText = net.minecraft.text.LiteralText
//#elseif MC>=11202 && MC<=11202
//$$ typealias VanillaLiteralText = net.minecraft.util.text.TextComponentString
//#elseif MC<11200
//$$ typealias VanillaLiteralText = net.minecraft.util.ChatComponentText
//#endif

//#if MC>=11500
typealias VanillaMutableText = net.minecraft.text.MutableText
//#elseif MC>=11400
//$$ typealias VanillaMutableText = net.minecraft.text.BaseText
//#elseif MC>=11200
//$$ typealias VanillaMutableText = net.minecraft.util.text.ITextComponent
//#elseif MC<11200
//$$ typealias VanillaMutableText = net.minecraft.util.IChatComponent
//#endif

//#if MC>=11200
typealias VanillaFormatting = net.minecraft.util.Formatting
//#else
//$$ typealias VanillaFormatting = net.minecraft.util.EnumChatFormatting
//#endif

object VanillaHelper {
    @JvmStatic
    fun createLiteralText(content: String): VanillaMutableText {
        //#if MC>=11900
        return VanillaText.literal(content)
        //#else
        //$$ return VanillaLiteralText(content)
        //#endif
    }

    @JvmStatic
    fun toVanillaText(text: Text): VanillaText {
        // create the base text
        val result = createLiteralText("")
        // go through each "before" position children recursively
        text.children.filter { it.first == Text.TextChildPosition.BEFORE }.map(Pair<Text.TextChildPosition, Text>::second).map(::toVanillaText).forEach(result::append)
        // append the content
        result.append(createLiteralText(text.asContentString()).apply {
            //#if MC>=11400
            text.formatting.map(::toVanillaFormatting).forEach(this::formatted)
            //#else
            //#if MC>=11200
            //$$ val style = this.style
            //#else
            //$$ val style = this.chatStyle
            //#endif
            //$$ text.formatting.map(::toVanillaFormatting).forEach {
            //$$     when (it) {
            //$$         VanillaFormatting.BOLD -> style.bold = true
            //$$         VanillaFormatting.ITALIC -> style.italic = true
            //$$         VanillaFormatting.UNDERLINE -> style.underlined = true
            //$$         VanillaFormatting.STRIKETHROUGH -> style.strikethrough = true
            //$$         VanillaFormatting.OBFUSCATED -> style.obfuscated = true
            //$$         VanillaFormatting.BLACK -> style.color = VanillaFormatting.BLACK
            //$$         VanillaFormatting.DARK_BLUE -> style.color = VanillaFormatting.DARK_BLUE
            //$$         VanillaFormatting.DARK_GREEN -> style.color = VanillaFormatting.DARK_GREEN
            //$$         VanillaFormatting.DARK_AQUA -> style.color = VanillaFormatting.DARK_AQUA
            //$$         VanillaFormatting.DARK_RED -> style.color = VanillaFormatting.DARK_RED
            //$$         VanillaFormatting.DARK_PURPLE -> style.color = VanillaFormatting.DARK_PURPLE
            //$$         VanillaFormatting.GOLD -> style.color = VanillaFormatting.GOLD
            //$$         VanillaFormatting.GRAY -> style.color = VanillaFormatting.GRAY
            //$$         VanillaFormatting.DARK_GRAY -> style.color = VanillaFormatting.DARK_GRAY
            //$$         VanillaFormatting.BLUE -> style.color = VanillaFormatting.BLUE
            //$$         VanillaFormatting.GREEN -> style.color = VanillaFormatting.GREEN
            //$$         VanillaFormatting.AQUA -> style.color = VanillaFormatting.AQUA
            //$$         VanillaFormatting.RED -> style.color = VanillaFormatting.RED
            //$$         VanillaFormatting.LIGHT_PURPLE -> style.color = VanillaFormatting.LIGHT_PURPLE
            //$$         VanillaFormatting.YELLOW -> style.color = VanillaFormatting.YELLOW
            //$$         VanillaFormatting.WHITE -> style.color = VanillaFormatting.WHITE
            //$$     }
            //$$ }
            //#if MC>=11200
            //$$ setStyle(style)
            //#else
            //$$ setChatStyle(style)
            //#endif
            //#endif
        })
        // go through each "after" position children recursively
        text.children.filter { it.first == Text.TextChildPosition.AFTER }.map(Pair<Text.TextChildPosition, Text>::second).map(::toVanillaText).forEach(result::append)

        return result
    }

    @JvmStatic
    fun toVanillaFormatting(format: TextFormatting): VanillaFormatting {
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

fun Text.toVanilla(): VanillaText = VanillaHelper.toVanillaText(this)
fun TextFormatting.toVanilla(): VanillaFormatting = VanillaHelper.toVanillaFormatting(this)
