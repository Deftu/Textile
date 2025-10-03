package com.test

import dev.deftu.textile.Text
import dev.deftu.textile.TextStyle
import dev.deftu.textile.minecraft.ClickEvent
import dev.deftu.textile.minecraft.HoverEvent
import dev.deftu.textile.minecraft.MCText
import dev.deftu.textile.minecraft.MCTextStyle
import dev.deftu.textile.minecraft.TextColors
import java.net.URI
import net.minecraft.util.Formatting

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
        test2()
        println("-".repeat(dividerSize))
        test3()
        println("-".repeat(dividerSize))
        collapseToString_ReopenSpam()
    }

    private fun test1() {
        val text = Text.literal("Hello, World!")
            .setStyle(MCTextStyle()
                .setColor(TextColors.RED)
                .setClickEvent(ClickEvent.OpenUrl(URI.create("https://google.com")))
                .setHoverEvent(HoverEvent.ShowText(Text.literal("This is a test")))
                .build())

        println(text)
        println(MCText.convert(text))
        println(text.collapseToString())
    }

    private fun test2() {
        val text = Text.literal("Hello, Deftu!")
            .setStyle(MCTextStyle()
                .setColor(TextColors.hex("#C33F3F"))
                .setClickEvent(ClickEvent.OpenUrl(URI.create("https://deftu.dev")))
                .setHoverEvent(HoverEvent.ShowText(Text.literal("Oh my guh!")))
                .build())

        println(text)
        println(MCText.convert(text))
        println(text.collapseToString())
    }

    private fun test3() {
        val text = Text.literal("Hello, Deftu!")
            .setStyle(MCTextStyle()
                .setColor(TextColors.hex("#C33F3F").withFallback(Formatting.RED))
                .setClickEvent(ClickEvent.OpenUrl(URI.create("https://deftu.dev")))
                .setHoverEvent(HoverEvent.ShowText(Text.literal("Oh my guh!")))
                .build())

        println(text)
        println(MCText.convert(text))
        println(text.collapseToString())
    }

    private fun collapseToString_ReopenSpam() {
        val bold = TextStyle.Property(TextStyle.PropertyKey<Boolean>("bold", sortIndex = 10), true, left="**", right="**")
        val a = Text.literal("A").setStyle(TextStyle.EMPTY.withProperties(bold))
        val b = Text.literal("B").setStyle(TextStyle.EMPTY.withProperties(bold))
        val doc = a.append(b)

        // Your current collapseToString:
        // -> "**A****B**"  (re-opens around every chunk)
        // Desired:
        // -> "**AB**"
        println(doc.collapseToString())
    }
}
