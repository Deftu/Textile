package com.test

import dev.deftu.textile.minecraft.MCClickEvent
import dev.deftu.textile.minecraft.MCHoverEvent
import dev.deftu.textile.minecraft.MCSimpleMutableTextHolder
import dev.deftu.textile.minecraft.MCTextFormat
import java.net.URI

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
        val dividerSize = 20
        println("-".repeat(dividerSize))
        test1()
        println("-".repeat(dividerSize))
    }

    private fun test1() {
        val text = MCSimpleMutableTextHolder("Hello, World!")
            .addFormatting(MCTextFormat.RED)
            .setClickEvent(MCClickEvent.OpenUrl(URI.create("https://google.com")))
            .setHoverEvent(MCHoverEvent.ShowText("This is a test"))

        println(text)
        println(text.asVanilla())

        text.set("Hey hey hey!")
            .setFormatting(MCTextFormat.GREEN)
            .setClickEvent(MCClickEvent.RunCommand("say Hi!"))
            .setHoverEvent(MCHoverEvent.ShowText("Hey there!"))

        println(text)
        println(text.asVanilla())
    }

}
