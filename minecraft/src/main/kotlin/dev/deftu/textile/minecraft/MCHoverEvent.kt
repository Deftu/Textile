package dev.deftu.textile.minecraft

import net.minecraft.text.HoverEvent

@Suppress("DataClassPrivateConstructor")
public data class MCHoverEvent private constructor(val action: HoverAction, val value: String) {

    public enum class HoverAction {

        SHOW_TEXT,
        SHOW_ITEM,
        SHOW_ENTITY
    }

    public companion object {

        //#if MC <= 1.12.2
        //$$ @JvmField
        //$$ @Suppress("EnumValuesSoftDeprecate")
        //$$ public val VANILLA_MAPPED_HOVER_ACTIONS: Map<HoverAction, HoverEvent.Action> = noInline {
        //$$     MCHoverEvent.HoverAction.values().associateWith { action ->
        //$$         when (action) {
        //$$             HoverAction.SHOW_TEXT -> HoverEvent.Action.SHOW_TEXT
        //$$             HoverAction.SHOW_ITEM -> HoverEvent.Action.SHOW_ITEM
        //$$             HoverAction.SHOW_ENTITY -> HoverEvent.Action.SHOW_ENTITY
        //$$         }
        //$$     }
        //$$ }
        //#endif

        @JvmStatic
        public fun convertFromVanilla(event: HoverEvent): MCHoverEvent? {
            //#if MC >= 1.16.5
            return when (event.action) {
                HoverEvent.Action.SHOW_TEXT -> {
                    val value = event.getValue(HoverEvent.Action.SHOW_TEXT)?.let { MCTextHolder.convertFromVanilla(it) }?.asString()
                        ?: return null
                    MCHoverEvent(HoverAction.SHOW_TEXT, value)
                }

                else -> TODO("Not yet implemented")
            }
            //#else
            //$$ val action = VANILLA_MAPPED_HOVER_ACTIONS.entries.firstOrNull { it.value == event.action }?.key
            //$$     ?: throw IllegalArgumentException("Invalid hover action")
            //$$ val value = MCTextHolder.convertFromVanilla(event.value).asString()
            //$$ return MCHoverEvent(action, value)
            //#endif
        }

        @JvmStatic
        public fun convertToVanilla(event: MCHoverEvent): HoverEvent {
            //#if MC >= 1.16.5
            return when (event.action) {
                HoverAction.SHOW_TEXT -> HoverEvent(HoverEvent.Action.SHOW_TEXT, MCSimpleTextHolder(event.value).asVanilla())
                else -> TODO("Not yet implemented")
            }
            //#else
            //$$ val action = VANILLA_MAPPED_HOVER_ACTIONS[event.action] ?: throw IllegalArgumentException("Invalid hover action")
            //$$ return HoverEvent(action, MCSimpleTextHolder(event.value).asVanilla())
            //#endif
        }

        //#if MC <= 1.12.2
        //$$ private inline fun <T> noInline(init: () -> T): T = init()
        //#endif

    }

}
