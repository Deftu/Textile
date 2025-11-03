package dev.deftu.textile.minecraft

import dev.deftu.textile.Text
import net.minecraft.world.item.ItemStack
import net.minecraft.network.chat.HoverEvent as VanillaHoverEvent

//#if MC <= 1.12.2
//$$ import net.minecraft.nbt.NBTTagCompound
//$$ import net.minecraft.util.text.ITextComponent as VanillaText
//#endif

public sealed interface HoverEvent {
    public data class ShowText(public val value: Text) : HoverEvent

    public data class ShowItem(public val value: ItemStack) : HoverEvent {
        //#if MC >= 1.16.5
        public val vanilla: VanillaHoverEvent.ShowItem
            get() = VanillaHoverEvent.ShowItem(value)
        //#endif
    }

    public data class ShowEntity(public val value: EntityTooltipInfo) : HoverEvent {
        //#if MC >= 1.16.5
        internal fun createVanillaWrapper(): VanillaHoverEvent.EntityTooltipInfo {
            val name = value.name.map(MCText::convert)
            return VanillaHoverEvent.EntityTooltipInfo(
                value.type,
                value.uuid,
                //#if MC >= 1.20.4
                name,
                //#else
                //$$ name.orElse(null),
                //#endif
            )
        }
        //#else
        //$$ internal fun createVanillaWrapper(): VanillaText {
        //$$     val nbt = NBTTagCompound()
        //$$     nbt.setString("id", value.uuid.toString())
        //$$     nbt.setString("type", value.type.toString())
        //$$     value.name.ifPresent { nbt.setString("name", it.collapseToString()) }
        //$$     return MCText.convert(Text.literal(nbt.toString()))
        //$$ }
        //#endif
    }
}
