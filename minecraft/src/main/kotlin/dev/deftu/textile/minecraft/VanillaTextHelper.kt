package dev.deftu.textile.minecraft

//#if MC <= 1.18.2 && MC >= 1.16.5
//$$ import net.minecraft.text.LiteralText
//#endif

import net.minecraft.text.MutableText

public object VanillaTextHelper {

    @JvmStatic
    public fun createLiteralText(content: String): MutableText {
        //#if MC >= 1.19.2
        return net.minecraft.text.Text.literal(content)
        //#else
        //$$ return LiteralText(content)
        //#endif
    }

}
