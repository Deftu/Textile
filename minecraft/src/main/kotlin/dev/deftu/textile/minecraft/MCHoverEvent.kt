package dev.deftu.textile.minecraft

import net.minecraft.item.ItemStack
import net.minecraft.text.HoverEvent
import java.util.Optional
import java.util.UUID

//#if MC >= 1.16.5
import net.minecraft.entity.EntityType
//#else
//$$ import net.minecraft.util.ResourceLocation
//$$ import net.minecraft.util.text.ITextComponent
//$$ import net.minecraft.nbt.JsonToNBT
//$$ import net.minecraft.nbt.NBTTagCompound
//#endif

public sealed class MCHoverEvent private constructor() {
    public data class ShowText(public val value: String) : MCHoverEvent()

    public data class ShowItem(public val value: ItemStack) : MCHoverEvent() {
        //#if MC >= 1.16.5
        internal fun createVanillaWrapper(): HoverEvent.ItemStackContent {
            return HoverEvent.ItemStackContent(value)
        }
        //#endif
    }

    public data class ShowEntity(public val value: MCEntityContent) : MCHoverEvent() {
        //#if MC >= 1.16.5
        internal fun createVanillaWrapper(): HoverEvent.EntityContent {
            return HoverEvent.EntityContent(
                value.type,
                value.uuid,
                //#if MC >= 1.20.4
                value.name.map { it.asVanilla() }
                //#else
                //$$ value.name.map { it.asVanilla() }.orElse(null)
                //#endif
            )
        }
        //#else
        //$$ internal fun createVanillaWrapper(): ITextComponent {
        //$$     val nbt = NBTTagCompound()
        //$$     nbt.setString("id", value.uuid.toString())
        //$$     nbt.setString("type", value.type.toString())
        //$$     value.name.ifPresent { nbt.setString("name", it.asString()) }
        //$$     return MCSimpleTextHolder(nbt.toString()).asVanilla()
        //$$ }
        //#endif
    }

    public data class MCEntityContent(
        //#if MC >= 1.16.5
        public val type: EntityType<*>,
        //#else
        //$$ public val type: String?,
        //#endif
        public val uuid: UUID,
        public val name: Optional<MCSimpleTextHolder>
    ) {
        public companion object {
            @JvmStatic
            public fun convertFromVanilla(
                //#if MC >= 1.16.5
                content: HoverEvent.EntityContent
                //#else
                //$$ content: ITextComponent
                //#endif
            ): MCEntityContent {
                //#if MC >= 1.16.5
                return MCEntityContent(
                    content.entityType,
                    content.uuid,
                    //#if MC >= 1.20.4
                    content.name.map { MCTextHolder.convertFromVanilla(it) as MCSimpleTextHolder },
                    //#else
                    //$$ Optional.ofNullable(content.name).map { MCTextHolder.convertFromVanilla(it) as MCSimpleTextHolder }
                    //#endif
                )
                //#else
                //$$ val nbt = JsonToNBT.getTagFromJson(content.unformattedText)
                //#if MC >= 1.12.2
                //$$ val id = nbt.getUniqueId("id") ?: throw IllegalArgumentException("Invalid entity content (missing UUID)")
                //#else
                //$$ val id = UUID.fromString(nbt.getString("id") ?: throw IllegalArgumentException("Invalid entity content (missing UUID)"))
                //#endif
                //$$ val type = if (nbt.hasKey("type", 8)) nbt.getString("type") else null
                //$$ val name = Optional.ofNullable(nbt.getString("name")).map { MCSimpleTextHolder(it) }
                //$$ return MCEntityContent(type, id, name)
                //#endif
            }
        }
    }

    public companion object {
        @JvmStatic
        public fun convertFromVanilla(event: HoverEvent): MCHoverEvent? {
            //#if MC >= 1.21.5
            //$$ return when (event) {
            //$$     is HoverEvent.ShowText -> ShowText(event.value.tryCollapseToString() ?: event.value.string)
            //$$     is HoverEvent.ShowItem -> ShowItem(event.item)
            //$$     is HoverEvent.ShowEntity -> ShowEntity(MCEntityContent.convertFromVanilla(event.entity))
            //$$     else -> null
            //$$ }
            //#elseif MC >= 1.16.5
            return when (event.action) {
                HoverEvent.Action.SHOW_TEXT -> {
                    val value = event.getValue(HoverEvent.Action.SHOW_TEXT)?.let { MCTextHolder.convertFromVanilla(it) }?.asString()
                        ?: return null
                    ShowText(value)
                }

                HoverEvent.Action.SHOW_ITEM -> {
                    val value = event.getValue(HoverEvent.Action.SHOW_ITEM)?.asStack() ?: return null
                    ShowItem(value)
                }

                HoverEvent.Action.SHOW_ENTITY -> {
                    val value = event.getValue(HoverEvent.Action.SHOW_ENTITY)?.let { MCEntityContent.convertFromVanilla(it) } ?: return null
                    ShowEntity(value)
                }

                else -> null
            }
            //#else
            //$$ val action = event.action
            //$$ val value = event.value
            //$$ return when (action) {
            //$$     HoverEvent.Action.SHOW_TEXT -> ShowText(value.formattedText)
            //$$
            //$$     HoverEvent.Action.SHOW_ITEM -> {
            //$$         val nbt = JsonToNBT.getTagFromJson(value.unformattedText)
            //#if MC >= 1.12.2
            //$$         val stack = ItemStack(nbt)
            //#else
            //$$         val stack = ItemStack.fromNbt(nbt)
            //#endif
            //$$         ShowItem(stack)
            //$$     }
            //$$
            //$$     HoverEvent.Action.SHOW_ENTITY -> ShowEntity(MCEntityContent.convertFromVanilla(value))
            //$$
            //$$     else -> null
            //$$ }
            //#endif
        }

        @JvmStatic
        public fun convertToVanilla(event: MCHoverEvent): HoverEvent {
            //#if MC >= 1.21.5
            //$$ return when (event) {
            //$$     is ShowText -> HoverEvent.ShowText(MCSimpleTextHolder(event.value).asVanilla())
            //$$     is ShowItem -> HoverEvent.ShowItem(event.value)
            //$$     is ShowEntity -> HoverEvent.ShowEntity(event.createVanillaWrapper())
            //$$ }
            //#elseif MC >= 1.16.5
            return when (event) {
                is ShowText -> HoverEvent(HoverEvent.Action.SHOW_TEXT, MCSimpleTextHolder(event.value).asVanilla())
                is ShowItem -> HoverEvent(HoverEvent.Action.SHOW_ITEM, event.createVanillaWrapper())
                is ShowEntity -> HoverEvent(HoverEvent.Action.SHOW_ENTITY, event.createVanillaWrapper())
            }
            //#else
            //$$ val action = when (event) {
            //$$     is ShowText -> HoverEvent.Action.SHOW_TEXT
            //$$     is ShowItem -> HoverEvent.Action.SHOW_ITEM
            //$$     is ShowEntity -> HoverEvent.Action.SHOW_ENTITY
            //$$ }
            //$$
            //$$ val value = when (event) {
            //$$     is ShowText -> MCSimpleTextHolder(event.value).asVanilla()
            //$$     is ShowItem -> MCSimpleTextHolder(event.value.serializeNBT().toString()).asVanilla()
            //$$     is ShowEntity -> event.createVanillaWrapper()
            //$$ }
            //$$
            //$$ return HoverEvent(action, value)
            //#endif
        }

        //#if FABRIC && MC <= 1.12.2
        //$$ private fun ItemStack.serializeNBT(): NbtCompound {
        //$$     val value = NbtCompound()
        //$$     toNbt(value)
        //$$     return value
        //$$ }
        //#endif
    }
}
