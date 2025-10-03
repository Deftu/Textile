package dev.deftu.textile.minecraft

import dev.deftu.textile.Text
import net.minecraft.text.HoverEvent
import java.util.Optional
import java.util.UUID

//#if MC >= 1.16.5
import net.minecraft.entity.EntityType
//#else
//$$ import net.minecraft.util.text.ITextComponent as VanillaText
//$$ import net.minecraft.nbt.JsonToNBT
//#endif

public data class EntityContent(
    //#if MC >= 1.16.5
    public val type: EntityType<*>,
    //#else
    //$$ public val type: String?,
    //#endif
    public val uuid: UUID,
    public val name: Optional<Text>
) {
    public companion object {
        @JvmStatic
        public fun wrap(
            //#if MC >= 1.16.5
            content: HoverEvent.EntityContent
            //#else
            //$$ content: VanillaText
            //#endif
        ): EntityContent {
            //#if MC >= 1.16.5
            return EntityContent(
                content.entityType,
                content.uuid,
                //#if MC >= 1.20.4
                content.name.map(MCText::wrap),
                //#else
                //$$ Optional.ofNullable(content.name).map(MCText::wrap)
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
            //$$ val name: Optional<Text> = Optional.ofNullable(nbt.getString("name")).map(Text::literal)
            //$$ return EntityContent(type, id, name)
            //#endif
        }
    }
}
