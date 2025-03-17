package dev.deftu.textile.minecraft

import net.minecraft.text.ClickEvent

@Suppress("DataClassPrivateConstructor")
public data class MCClickEvent private constructor(val action: ClickAction, val value: String) {

    public enum class ClickAction {

        OPEN_URL,
        OPEN_FILE,
        RUN_COMMAND,
        SUGGEST_COMMAND,
        CHANGE_PAGE
    }

    @Suppress("EnumValuesSoftDeprecate")
    public companion object {

        @JvmField
        public val VANILLA_MAPPED_CLICK_ACTIONS: Map<ClickAction, ClickEvent.Action> = noInline {
            ClickAction.values().associateWith { action ->
                when (action) {
                    ClickAction.OPEN_URL -> ClickEvent.Action.OPEN_URL
                    ClickAction.OPEN_FILE -> ClickEvent.Action.OPEN_FILE
                    ClickAction.RUN_COMMAND -> ClickEvent.Action.RUN_COMMAND
                    ClickAction.SUGGEST_COMMAND -> ClickEvent.Action.SUGGEST_COMMAND
                    ClickAction.CHANGE_PAGE -> ClickEvent.Action.CHANGE_PAGE
                }
            }
        }

        @JvmStatic
        public fun convertFromVanilla(event: ClickEvent): MCClickEvent? {
            val action = VANILLA_MAPPED_CLICK_ACTIONS.entries.firstOrNull { it.value == event.action }?.key
                ?: return null
            return MCClickEvent(action, event.value)
        }

        @JvmStatic
        public fun convertToVanilla(event: MCClickEvent): ClickEvent? {
            val mapped = VANILLA_MAPPED_CLICK_ACTIONS[event.action]
                ?: throw IllegalArgumentException("Invalid click action")
            return ClickEvent(mapped, event.value)
        }

        @JvmStatic
        public fun openUrl(value: String): MCClickEvent {
            return MCClickEvent(ClickAction.OPEN_URL, value)
        }

        @JvmStatic
        public fun openFile(value: String): MCClickEvent {
            return MCClickEvent(ClickAction.OPEN_FILE, value)
        }

        @JvmStatic
        public fun runCommand(value: String): MCClickEvent {
            return MCClickEvent(ClickAction.RUN_COMMAND, value)
        }

        @JvmStatic
        public fun suggestCommand(value: String): MCClickEvent {
            return MCClickEvent(ClickAction.SUGGEST_COMMAND, value)
        }

        @JvmStatic
        public fun changePage(value: String): MCClickEvent {
            return MCClickEvent(ClickAction.CHANGE_PAGE, value)
        }

        private inline fun <T> noInline(init: () -> T): T = init()

    }

}
