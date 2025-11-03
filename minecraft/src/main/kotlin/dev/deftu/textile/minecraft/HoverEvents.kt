package dev.deftu.textile.minecraft

import net.minecraft.network.chat.HoverEvent as VanillaHoverEvent

//#if MC <= 1.12.2
//#if FABRIC
//$$ import net.minecraft.nbt.NBTTagCompound
//#endif
//$$
//$$ import dev.deftu.textile.Text
//$$ import net.minecraft.item.ItemStack
//$$ import net.minecraft.nbt.JsonToNBT
//#endif

public object HoverEvents {
    @JvmStatic
    public fun wrap(event: VanillaHoverEvent): HoverEvent? {
        //#if MC >= 1.21.5
        return when (event) {
            is VanillaHoverEvent.ShowText -> HoverEvent.ShowText(MCText.wrap(event.value))
            is VanillaHoverEvent.ShowItem -> HoverEvent.ShowItem(event.item)
            is VanillaHoverEvent.ShowEntity -> HoverEvent.ShowEntity(EntityTooltipInfo.wrap(event.entity))
            else -> null
        }
        //#elseif MC >= 1.16.5
        //$$ return when (event.action) {
        //$$     VanillaHoverEvent.Action.SHOW_TEXT -> {
        //$$         val value = event.getValue(VanillaHoverEvent.Action.SHOW_TEXT)?.let(MCText::wrap)
        //$$             ?: return null
        //$$         HoverEvent.ShowText(value)
        //$$     }
        //$$
        //$$     VanillaHoverEvent.Action.SHOW_ITEM -> {
        //$$         val value = event.getValue(VanillaHoverEvent.Action.SHOW_ITEM)?.itemStack ?: return null
        //$$         HoverEvent.ShowItem(value)
        //$$     }
        //$$
        //$$     VanillaHoverEvent.Action.SHOW_ENTITY -> {
        //$$         val value = event.getValue(VanillaHoverEvent.Action.SHOW_ENTITY)?.let(EntityTooltipInfo::wrap) ?: return null
        //$$         HoverEvent.ShowEntity(value)
        //$$     }
        //$$
        //$$     else -> null
        //$$ }
        //#else
        //$$ val action = event.action
        //$$ val value = event.value
        //$$ return when (action) {
        //$$     VanillaHoverEvent.Action.SHOW_TEXT -> HoverEvent.ShowText(MCText.wrap(value))
        //$$
        //$$     VanillaHoverEvent.Action.SHOW_ITEM -> {
        //$$         val nbt = JsonToNBT.getTagFromJson(value.unformattedText)
                //#if MC >= 1.12.2
                //$$ val stack = ItemStack(nbt)
                //#else
                //$$ val stack = ItemStack.loadItemStackFromNBT(nbt)
                //#endif
        //$$         HoverEvent.ShowItem(stack)
        //$$     }
        //$$
        //$$     VanillaHoverEvent.Action.SHOW_ENTITY -> HoverEvent.ShowEntity(EntityTooltipInfo.wrap(value))
        //$$
        //$$     else -> null
        //$$ }
        //#endif
    }

    @JvmStatic
    public fun convert(event: HoverEvent): VanillaHoverEvent {
        //#if MC >= 1.21.5
        return when (event) {
            is HoverEvent.ShowText -> VanillaHoverEvent.ShowText(MCText.convert(event.value))
            is HoverEvent.ShowItem -> VanillaHoverEvent.ShowItem(event.value)
            is HoverEvent.ShowEntity -> VanillaHoverEvent.ShowEntity(event.createVanillaWrapper())
        }
        //#elseif MC >= 1.16.5
        //$$ return when (event) {
        //$$     is HoverEvent.ShowText -> VanillaHoverEvent(VanillaHoverEvent.Action.SHOW_TEXT, MCText.convert(event.value))
        //$$     is HoverEvent.ShowItem -> VanillaHoverEvent(VanillaHoverEvent.Action.SHOW_ITEM, event.vanilla)
        //$$     is HoverEvent.ShowEntity -> VanillaHoverEvent(VanillaHoverEvent.Action.SHOW_ENTITY, event.createVanillaWrapper())
        //$$ }
        //#else
        //$$ val action = when (event) {
        //$$     is HoverEvent.ShowText -> VanillaHoverEvent.Action.SHOW_TEXT
        //$$     is HoverEvent.ShowItem -> VanillaHoverEvent.Action.SHOW_ITEM
        //$$     is HoverEvent.ShowEntity -> VanillaHoverEvent.Action.SHOW_ENTITY
        //$$ }
        //$$
        //$$ val value = when (event) {
        //$$     is HoverEvent.ShowText -> MCText.convert(event.value)
        //$$     is HoverEvent.ShowItem -> MCText.convert(Text.literal(event.value.serializeNBT().toString()))
        //$$     is HoverEvent.ShowEntity -> event.createVanillaWrapper()
        //$$ }
        //$$
        //$$ return VanillaHoverEvent(action, value)
        //#endif
    }

    //#if FABRIC && MC <= 1.12.2
    //$$ private fun ItemStack.serializeNBT(): NBTTagCompound {
    //$$     val value = NBTTagCompound()
    //$$     writeToNBT(value)
    //$$     return value
    //$$ }
    //#endif
}
