package com.test

import dev.deftu.textile.dsl.Mutable
import dev.deftu.textile.minecraft.MCClickEvent
import dev.deftu.textile.minecraft.MCSimpleMutableTextHolder
import dev.deftu.textile.minecraft.MCTextFormat
import dev.deftu.textile.minecraft.dsl.text

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
        val text = text<MCSimpleMutableTextHolder>(Mutable, "Hello, World!") {
            formatting(MCTextFormat.RED)

            clickEvent = MCClickEvent.runCommand("/say \"Hello, World!\"")

            child {
                content("This is a child")
                formatting(MCTextFormat.BOLD)

                clickEvent = MCClickEvent.runCommand("/say \"This is a child\"")
            }
        }

        println(text)
        println(text.asVanilla())

        text.set("Hey hey hey!")

        println(text)
        println(text.asVanilla())
    }
}
