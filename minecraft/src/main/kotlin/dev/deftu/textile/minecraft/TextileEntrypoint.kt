package dev.deftu.textile.minecraft

import org.apache.logging.log4j.LogManager

//#if FABRIC
import net.fabricmc.api.ModInitializer
//#elseif FORGE
//#if MC >= 1.16.5
//$$ import net.minecraftforge.eventbus.api.IEventBus
//$$ import net.minecraftforge.fml.common.Mod
//$$ import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
//$$ import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
//#else
//$$ import net.minecraftforge.fml.common.Mod
//$$ import net.minecraftforge.fml.common.event.FMLInitializationEvent
//#endif
//#elseif NEOFORGE
//$$ import net.neoforged.bus.api.IEventBus
//$$ import net.neoforged.fml.common.Mod
//$$ import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
//#endif

//#if FORGE-LIKE
//#if MC >= 1.16.5
//$$ @Mod("@MOD_ID@")
//#else
//$$ @Mod(modid = "@MOD_ID@", version = "@MOD_ID@")
//#endif
//#endif
public class TextileEntrypoint
//#if FABRIC
    : ModInitializer
//#endif
{

    private val logger = LogManager.getLogger(TextileEntrypoint::class.java)

    //#if FORGE && MC >= 1.16.5
    //$$ init {
    //$$     setupForgeEvents(FMLJavaModLoadingContext.get().modEventBus)
    //$$ }
    //#elseif NEOFORGE
    //$$ public constructor(modEventBus: IEventBus) {
    //$$     setupForgeEvents(modEventBus)
    //$$ }
    //#endif

    //#if FABRIC
    override
    //#elseif FORGE && MC <= 1.12.2
    //$$ @Mod.EventHandler
    //$$ private
    //#else
    //$$ private
    //#endif
    fun onInitialize(
        //#if FORGE-LIKE
        //#if MC >= 1.16.5
        //$$ event: FMLCommonSetupEvent
        //#else
        //$$ event: FMLInitializationEvent
        //#endif
        //#endif
    ) {
        logger.info("Initializing @MOD_NAME@ @MOD_VERSION@")
    }

    //#if FORGE-LIKE && MC >= 1.16.5
    //$$ private fun setupForgeEvents(modEventBus: IEventBus) {
    //$$     modEventBus.addListener(this::onInitialize)
    //$$ }
    //#endif

}