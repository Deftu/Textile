package com.test

//#if FABRIC==1
import net.fabricmc.api.ClientModInitializer
//#else
//#if MC>=11502
//$$ import net.minecraftforge.fml.common.Mod
//$$ import net.minecraftforge.fml.common.Mod.EventBusSubscriber
//$$ import net.minecraftforge.fml.common.event.FMLInitializationEvent
//#else
//$$ import net.minecraftforge.fml.common.Mod
//$$ import net.minecraftforge.fml.common.Mod.EventHandler
//$$ import net.minecraftforge.fml.common.event.FMLInitializationEvent
//#endif
//#endif

import dev.deftu.textful.Text
import dev.deftu.textful.impl.SimpleText
import dev.deftu.textful.toVanilla

//#if FABRIC==1
class TestMod : ClientModInitializer {
//#else
//#if MC>=11502
//$$ @Mod(value = "test-mod")
//#else
//$$ @Mod(modid = "test-mod")
//#endif
//$$ class TestMod {
//#endif

    //#if FABRIC==1
    override fun onInitializeClient() {
        val text = makeTestText("Hello Fabric world!")
        println("Text: $text")
        println("Vanilla Text: ${text.toVanilla()}")
    }
    //#else
    //#if MC>=11502
    //$$ @Mod.EventBusSubscriber
    //#else
    //$$ @Mod.EventHandler
    //#endif
    //$$ fun initialize(event: FMLInitializationEvent) {
    //$$     val text = makeTestText("Hello Forge world!")
    //$$     println("Text: $text")
    //$$     println("Vanilla Text: ${text.toVanilla()}")
    //$$ }
    //#endif

    private fun makeTestText(string: String): Text {
        return SimpleText(string)
    }

}
