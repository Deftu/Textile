package com.test

import dev.deftu.textile.minecraft.ClickEvent
import dev.deftu.textile.minecraft.EntityContent
import dev.deftu.textile.minecraft.HoverEvent
import dev.deftu.textile.minecraft.MCText
import dev.deftu.textile.minecraft.MCTextStyle
import dev.deftu.textile.minecraft.TextColors
import java.net.URI
import net.minecraft.client.MinecraftClient
import net.minecraft.util.Formatting

//#if FABRIC
import net.fabricmc.api.ClientModInitializer
//#elseif FORGE
//#if MC >= 1.16.5
//$$ import net.minecraftforge.fml.common.Mod
//#else
//$$ import net.minecraftforge.fml.common.Mod
//$$ import net.minecraftforge.fml.common.event.FMLInitializationEvent
//#endif
//#elseif NEOFORGE
//$$ import net.neoforged.bus.api.IEventBus
//$$ import net.neoforged.fml.common.Mod
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
    //#if FORGE && MC >= 1.16.5
    //$$ init {
    //$$     onInitializeClient()
    //$$ }
    //#elseif NEOFORGE
    //$$ constructor(modEventBus: IEventBus) {
    //$$     onInitializeClient()
    //$$ }
    //#endif

    //#if FABRIC
    override
    //#elseif FORGE && MC <= 1.12.2
    //$$ @Mod.EventHandler
    //#else
    //$$ private
    //#endif
    fun onInitializeClient(
        //#if FORGE && MC <= 1.12.2
        //$$ event: FMLInitializationEvent
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
        test4()
        println("-".repeat(dividerSize))
        test5()
        println("-".repeat(dividerSize))
        test6()
        println("-".repeat(dividerSize))

        //#if FABRIC && MC >= 1.16.5
        var tick = 0
        net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.END_CLIENT_TICK.register { client ->
            if (tick++ < 20) {
                return@register
            }

            println("-".repeat(dividerSize))
            test4()
            println("-".repeat(dividerSize))
            test6()
            println("-".repeat(dividerSize))
            tick = 0
        }
        //#endif
    }

    private fun test1() {
        val text = MCText.literal("Hello, World!")
            .setStyle(MCTextStyle()
                .setColor(TextColors.RED)
                .setClickEvent(ClickEvent.OpenUrl(URI.create("https://google.com")))
                .setHoverEvent(HoverEvent.ShowText(MCText.literal("This is a test")))
                .build())

        println(text)
        println(MCText.convert(text))
        println(text.collapseToString())
    }

    private fun test2() {
        val text = MCText.literal("Hello, Deftu!")
            .setStyle(MCTextStyle()
                .setColor(TextColors.hex("#C33F3F"))
                .setClickEvent(ClickEvent.OpenUrl(URI.create("https://deftu.dev")))
                .setHoverEvent(HoverEvent.ShowText(MCText.literal("Oh my guh!")))
                .build())

        println(text)
        println(MCText.convert(text))
        println(text.collapseToString())
    }

    private fun test3() {
        val text = MCText.literal("Hello, Deftu!")
            .setStyle(MCTextStyle()
                .setColor(TextColors.hex("#C33F3F").withFallback(Formatting.RED, false))
                .setClickEvent(ClickEvent.OpenUrl(URI.create("https://deftu.dev")))
                .setHoverEvent(HoverEvent.ShowText(MCText.literal("Oh my guh!")))
                .build())

        println(text)
        println(MCText.convert(text))
        println(text.collapseToString())
    }

    private fun test4() {
        val text = MCText.translatable("gui.done")
            .setStyle(MCTextStyle()
                .setColor(TextColors.GREEN)
                .setClickEvent(ClickEvent.RunCommand("/say Done!"))
                .setHoverEvent(HoverEvent.ShowText(MCText.literal("Click me!")))
                .build())

        println(text)
        println(MCText.convert(text))
        println(text.collapseToString())
    }

    private fun test5() {
        val text = MCText.literal("Hello, Deftu!")
            .setStyle(MCTextStyle()
                .setColor(TextColors.hex("#C33F3F").withFallback(Formatting.RED))
                .setClickEvent(ClickEvent.OpenUrl(URI.create("https://deftu.dev")))
                .setHoverEvent(HoverEvent.ShowText(MCText.literal("Oh my guh!")))
                .build())

        println(text)
        println(MCText.convert(text))
        println(text.collapseToString())
    }

    private fun test6() {
        val player = MinecraftClient.getInstance().player ?: return println("No player found, skipping test6()")

        val text = MCText.translatable(
            key = "chat.type.text",
            replacements = arrayOf(
                MCText.literal("Deftu").setStyle(
                    MCTextStyle()
                        .setClickEvent(ClickEvent.SuggestCommand("/tell Deftu"))
                        .setHoverEvent(HoverEvent.ShowEntity(EntityContent.of(player)))
                ),
                MCText.literal("Hello, Deftu!")
            )
        )

        println(text)
        val converted = MCText.convert(text)
        println(converted)
        println(text.collapseToString())
        println(MCText.wrap(converted))
    }
}
