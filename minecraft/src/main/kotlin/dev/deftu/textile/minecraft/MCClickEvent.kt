package dev.deftu.textile.minecraft

import net.minecraft.text.ClickEvent
import java.net.URI

//#if MC >= 1.21.6
//$$ import net.minecraft.dialog.type.Dialog
//$$ import net.minecraft.nbt.NbtElement
//$$ import net.minecraft.registry.entry.RegistryEntry
//$$ import net.minecraft.util.Identifier
//$$ import java.util.Optional
//#endif

public sealed class MCClickEvent private constructor() {

    public abstract fun asVanilla(): ClickEvent

    public data class OpenUrl(val uri: URI) : MCClickEvent() {

        override fun asVanilla(): ClickEvent {
            //#if MC >= 1.21.5
            //$$ return ClickEvent.OpenUrl(uri)
            //#else
            return ClickEvent(ClickEvent.Action.OPEN_URL, uri.toString())
            //#endif
        }

    }

    public data class OpenFile(val path: String) : MCClickEvent() {

        override fun asVanilla(): ClickEvent {
            //#if MC >= 1.21.5
            //$$ return ClickEvent.OpenFile(path)
            //#else
            return ClickEvent(ClickEvent.Action.OPEN_FILE, path)
            //#endif
        }

    }

    public data class RunCommand(val command: String) : MCClickEvent() {

        override fun asVanilla(): ClickEvent {
            //#if MC >= 1.21.5
            //$$ return ClickEvent.RunCommand(command)
            //#else
            return ClickEvent(ClickEvent.Action.RUN_COMMAND, command)
            //#endif
        }

    }

    public data class SuggestCommand(val command: String) : MCClickEvent() {

        override fun asVanilla(): ClickEvent {
            //#if MC >= 1.21.5
            //$$ return ClickEvent.SuggestCommand(command)
            //#else
            return ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command)
            //#endif
        }

    }

    public data class ChangePage(val page: Int) : MCClickEvent() {

        override fun asVanilla(): ClickEvent {
            //#if MC >= 1.21.5
            //$$ return ClickEvent.ChangePage(page)
            //#else
            return ClickEvent(ClickEvent.Action.CHANGE_PAGE, page.toString())
            //#endif
        }

    }

    //#if MC >= 1.16.5
    public data class CopyToClipboard(val value: String) : MCClickEvent() {

        override fun asVanilla(): ClickEvent {
            //#if MC >= 1.21.5
            //$$ return ClickEvent.CopyToClipboard(value)
            //#else
            return ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, value)
            //#endif
        }

    }
    //#endif

    //#if MC >= 1.21.6
    //$$ public data class ShowDialog(val dialogEntry: RegistryEntry<Dialog>) : MCClickEvent() {
    //$$
    //$$     override fun asVanilla(): ClickEvent {
    //$$         return ClickEvent.ShowDialog(dialogEntry)
    //$$     }
    //$$
    //$$ }
    //$$
    //$$ public data class Custom(val identifier: Identifier, val nbt: Optional<NbtElement>) : MCClickEvent() {
    //$$
    //$$     override fun asVanilla(): ClickEvent {
    //$$         return ClickEvent.Custom(identifier, nbt)
    //$$     }
    //$$
    //$$ }
    //#endif

    public companion object {

        @JvmStatic
        public fun convertFromVanilla(event: ClickEvent): MCClickEvent? {
            //#if MC >= 1.21.5
            //$$ return when (event) {
            //$$     is ClickEvent.OpenUrl -> OpenUrl(event.uri)
            //$$     is ClickEvent.OpenFile -> OpenFile(event.path)
            //$$     is ClickEvent.RunCommand -> RunCommand(event.command)
            //$$     is ClickEvent.SuggestCommand -> SuggestCommand(event.command)
            //$$     is ClickEvent.ChangePage -> ChangePage(event.page)
            //$$     else -> null
            //$$}
            //#else
            return when (event.action) {
                ClickEvent.Action.OPEN_URL -> OpenUrl(URI.create(event.value))
                ClickEvent.Action.OPEN_FILE -> OpenFile(event.value)
                ClickEvent.Action.RUN_COMMAND -> RunCommand(event.value)
                ClickEvent.Action.SUGGEST_COMMAND -> SuggestCommand(event.value)
                ClickEvent.Action.CHANGE_PAGE -> ChangePage(event.value.toIntOrNull() ?: 0)
                else -> null
            }
            //#endif
        }

        @JvmStatic
        public fun convertToVanilla(event: MCClickEvent): ClickEvent? {
            // //#if MC >= 1.21.5
            // //$$ return when (event.action) {
            // //$$     ClickAction.OPEN_URL -> ClickEvent.OpenUrl(URI.create(event.value))
            // //$$     ClickAction.OPEN_FILE -> ClickEvent.OpenFile(event.value)
            // //$$     ClickAction.RUN_COMMAND -> ClickEvent.RunCommand(event.value)
            // //$$     ClickAction.SUGGEST_COMMAND -> ClickEvent.SuggestCommand(event.value)
            // //$$     ClickAction.CHANGE_PAGE -> ClickEvent.ChangePage(event.value.toIntOrNull() ?: 0)
            // //$$     else -> null
            // //$$ }
            // //#else
            // val action = VANILLA_MAPPED_CLICK_ACTIONS[event.action]
            //     ?: throw IllegalArgumentException("Invalid click action")
            // return ClickEvent(action, event.value)
            // //#endif

            //#if MC >= 1.21.5
            //$$ return when (event) {
            //$$     is OpenUrl -> ClickEvent.OpenUrl(event.uri)
            //$$     is OpenFile -> ClickEvent.OpenFile(event.path)
            //$$     is RunCommand -> ClickEvent.RunCommand(event.command)
            //$$     is SuggestCommand -> ClickEvent.SuggestCommand(event.command)
            //$$     is ChangePage -> ClickEvent.ChangePage(event.page)
            //$$     else -> null
            //$$ }
            //#else
            return when (event) {
                is OpenUrl -> ClickEvent(ClickEvent.Action.OPEN_URL, event.uri.toString())
                is OpenFile -> ClickEvent(ClickEvent.Action.OPEN_FILE, event.path)
                is RunCommand -> ClickEvent(ClickEvent.Action.RUN_COMMAND, event.command)
                is SuggestCommand -> ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, event.command)
                is ChangePage -> ClickEvent(ClickEvent.Action.CHANGE_PAGE, event.page.toString())
                else -> null
            }
            //#endif
        }

        private inline fun <T> noInline(init: () -> T): T = init()

    }

}
