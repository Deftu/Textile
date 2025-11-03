package dev.deftu.textile.minecraft

import dev.deftu.textile.Text
import net.minecraft.world.entity.Entity
import net.minecraft.network.chat.HoverEvent
import java.util.Optional
import java.util.UUID

//#if MC >= 1.16.5
import net.minecraft.world.entity.EntityType
//#else
//$$ import net.minecraft.util.text.ITextComponent as VanillaText
//$$ import net.minecraft.nbt.JsonToNBT
//#endif

public data class EntityTooltipInfo(
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
            content: HoverEvent.EntityTooltipInfo
            //#else
            //$$ content: VanillaText
            //#endif
        ): EntityTooltipInfo {
            //#if MC >= 1.16.5
            return EntityTooltipInfo(
                content.type,
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
            //$$ return EntityTooltipInfo(type, id, name)
            //#endif
        }

        @JvmStatic
        public fun of(entity: Entity): EntityTooltipInfo {
            return EntityTooltipInfo(
                //#if MC >= 1.16.5
                entity.type,
                //#else
                //$$ entity.entityId.toString(),
                //#endif
                entity.uuid,
                //#if MC >= 1.16.5
                Optional.ofNullable(entity.name).map(MCText::wrap)
                //#else
                //$$ Optional.ofNullable(entity.name).map(Text::literal)
                //#endif
            )
        }
    }
}
