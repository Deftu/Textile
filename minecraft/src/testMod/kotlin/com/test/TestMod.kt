package com.test

//#if FABRIC
import net.fabricmc.api.ClientModInitializer
//#else
//#if FORGE
//#if MC >= 1.15.2
//$$ import net.minecraftforge.fml.common.Mod
//$$ import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
//$$ import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
//#else
//$$ import net.minecraftforge.fml.common.Mod
//$$ import net.minecraftforge.fml.common.Mod.EventHandler
//$$ import net.minecraftforge.fml.common.event.FMLInitializationEvent
//#endif
//#else
//$$ import net.neoforged.bus.api.IEventBus
//$$ import net.neoforged.fml.common.Mod
//$$ import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
//#endif
//#endif

import dev.deftu.textile.SimpleTextHolder
import dev.deftu.textile.TextHolder
import dev.deftu.textile.minecraft.MinecraftTextFormat
import dev.deftu.textile.minecraft.toVanilla

//#if FABRIC
class TestMod : ClientModInitializer {
//#else
//#if MC >= 1.15.2
//$$ @Mod(value = "testmod")
//#else
//$$ @Mod(modid = "testmod")
//#endif
//$$ class TestMod {
//#endif
    //#if FORGE && MC >= 1.15.2
    //$$ init {
    //$$     FMLJavaModLoadingContext.get().modEventBus.addListener(::onInitializeClient)
    //$$ }
    //#endif

    //#if NEOFORGE
    //$$ constructor(modEventBus: IEventBus) {
    //$$     modEventBus.addListener(::onInitializeClient)
    //$$ }
    //#endif

    //#if FABRIC
    override
    //#endif
    fun onInitializeClient(
        //#if FORGE-LIKE
        //#if MC >= 1.15.2
        //$$ event: FMLClientSetupEvent
        //#else
        //$$ event: FMLInitializationEvent
        //#endif
        //#endif
    ) {
        val text = makeTestText("Hello world!").formatted(MinecraftTextFormat.RED)
        println("Text: $text")
        println("String: ${text.asString()}")
        println("Vanilla Text: ${text.toVanilla()}")
    }

    private fun makeTestText(string: String): TextHolder {
        return SimpleTextHolder(string)
    }
}
