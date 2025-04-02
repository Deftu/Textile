package dev.deftu.textile.minecraft

//#if MC >= 1.21.5
//$$ import java.net.URI
//#endif

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

        //#if MC <= 1.21.4
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
        //#endif

        @JvmStatic
        public fun convertFromVanilla(event: ClickEvent): MCClickEvent? {
            //#if MC >= 1.21.5
            //$$ return when (event) {
            //$$     is ClickEvent.OpenUrl -> MCClickEvent(ClickAction.OPEN_URL, event.uri.toString())
            //$$     is ClickEvent.OpenFile -> MCClickEvent(ClickAction.OPEN_FILE, event.path)
            //$$     is ClickEvent.RunCommand -> MCClickEvent(ClickAction.RUN_COMMAND, event.command)
            //$$     is ClickEvent.SuggestCommand -> MCClickEvent(ClickAction.SUGGEST_COMMAND, event.command)
            //$$     is ClickEvent.ChangePage -> MCClickEvent(ClickAction.CHANGE_PAGE, event.page.toString())
            //$$     else -> null
            //$$}
            //#else
            val action = VANILLA_MAPPED_CLICK_ACTIONS.entries.firstOrNull { it.value == event.action }?.key
                ?: return null
            return MCClickEvent(action, event.value)
            //#endif
        }

        @JvmStatic
        public fun convertToVanilla(event: MCClickEvent): ClickEvent? {
            //#if MC >= 1.21.5
            //$$ return when (event.action) {
            //$$     ClickAction.OPEN_URL -> ClickEvent.OpenUrl(URI.create(event.value))
            //$$     ClickAction.OPEN_FILE -> ClickEvent.OpenFile(event.value)
            //$$     ClickAction.RUN_COMMAND -> ClickEvent.RunCommand(event.value)
            //$$     ClickAction.SUGGEST_COMMAND -> ClickEvent.SuggestCommand(event.value)
            //$$     ClickAction.CHANGE_PAGE -> ClickEvent.ChangePage(event.value.toIntOrNull() ?: 0)
            //$$     else -> null
            //$$ }
            //#else
            val action = VANILLA_MAPPED_CLICK_ACTIONS[event.action]
                ?: throw IllegalArgumentException("Invalid click action")
            return ClickEvent(action, event.value)
            //#endif
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
