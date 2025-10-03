package dev.deftu.textile.minecraft

import java.net.URI
import net.minecraft.text.ClickEvent as VanillaClickEvent

public object ClickEvents {
    @JvmStatic
    public fun wrap(event: VanillaClickEvent): ClickEvent {
        //#if MC >= 1.21.5
        //$$ return when (event) {
        //$$     is VanillaClickEvent.OpenUrl -> ClickEvent.OpenUrl(event.uri)
        //$$     is VanillaClickEvent.OpenFile -> ClickEvent.OpenFile(event.path)
        //$$     is VanillaClickEvent.RunCommand -> ClickEvent.RunCommand(event.command)
        //$$     is VanillaClickEvent.SuggestCommand -> ClickEvent.SuggestCommand(event.command)
        //$$     is VanillaClickEvent.ChangePage -> ClickEvent.ChangePage(event.page)
        //$$     is VanillaClickEvent.CopyToClipboard -> ClickEvent.CopyToClipboard(event.value)
            //#if MC >= 1.21.6
            //$$ is VanillaClickEvent.ShowDialog -> ClickEvent.ShowDialog(event.dialog)
            //$$ is VanillaClickEvent.Custom -> ClickEvent.Custom(event.id, event.payload)
            //#endif
        //$$     else -> throw IllegalArgumentException("Unknown ClickEvent type: ${event::class.java}")
        //$$ }
        //#else
        return when (event.action) {
            VanillaClickEvent.Action.OPEN_URL -> ClickEvent.OpenUrl(URI.create(event.value))
            VanillaClickEvent.Action.OPEN_FILE -> ClickEvent.OpenFile(event.value)
            VanillaClickEvent.Action.RUN_COMMAND -> ClickEvent.RunCommand(event.value)
            VanillaClickEvent.Action.SUGGEST_COMMAND -> ClickEvent.SuggestCommand(event.value)
            VanillaClickEvent.Action.CHANGE_PAGE -> ClickEvent.ChangePage(event.value.toIntOrNull() ?: 0)
            //#if MC >= 1.16.5
            VanillaClickEvent.Action.COPY_TO_CLIPBOARD -> ClickEvent.CopyToClipboard(event.value)
            //#endif
            else -> throw IllegalArgumentException("Unknown ClickEvent action: ${event.action}")
        }
        //#endif
    }

    @JvmStatic
    public fun convert(event: ClickEvent): VanillaClickEvent {
        //#if MC >= 1.21.5
        //$$ return when (event) {
        //$$     is ClickEvent.OpenUrl -> VanillaClickEvent.OpenUrl(event.uri)
        //$$     is ClickEvent.OpenFile -> VanillaClickEvent.OpenFile(event.path)
        //$$     is ClickEvent.RunCommand -> VanillaClickEvent.RunCommand(event.command)
        //$$     is ClickEvent.SuggestCommand -> VanillaClickEvent.SuggestCommand(event.command)
        //$$     is ClickEvent.ChangePage -> VanillaClickEvent.ChangePage(event.page)
        //$$     is ClickEvent.CopyToClipboard -> VanillaClickEvent.CopyToClipboard(event.value)
            //#if MC >= 1.21.6
            //$$ is ClickEvent.ShowDialog -> VanillaClickEvent.ShowDialog(event.dialog)
            //$$ is ClickEvent.Custom -> VanillaClickEvent.Custom(event.identifier, event.payload)
            //#endif
        //$$ }
        //#else
        return when (event) {
            is ClickEvent.OpenUrl -> VanillaClickEvent(VanillaClickEvent.Action.OPEN_URL, event.uri.toString())
            is ClickEvent.OpenFile -> VanillaClickEvent(VanillaClickEvent.Action.OPEN_FILE, event.path)
            is ClickEvent.RunCommand -> VanillaClickEvent(VanillaClickEvent.Action.RUN_COMMAND, event.command)
            is ClickEvent.SuggestCommand -> VanillaClickEvent(VanillaClickEvent.Action.SUGGEST_COMMAND, event.command)
            is ClickEvent.ChangePage -> VanillaClickEvent(VanillaClickEvent.Action.CHANGE_PAGE, event.page.toString())
            //#if MC >= 1.16.5
            is ClickEvent.CopyToClipboard -> VanillaClickEvent(VanillaClickEvent.Action.COPY_TO_CLIPBOARD, event.value)
            //#endif
        }
        //#endif
    }
}
