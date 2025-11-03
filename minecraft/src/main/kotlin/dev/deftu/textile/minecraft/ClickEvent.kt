package dev.deftu.textile.minecraft

import net.minecraft.network.chat.ClickEvent as VanillaClickEvent
import java.net.URI

//#if MC >= 1.21.6
import net.minecraft.server.dialog.Dialog
import net.minecraft.nbt.Tag
import net.minecraft.core.Holder
import net.minecraft.resources.ResourceLocation
import java.util.Optional
//#endif

public sealed interface ClickEvent {
    public data class OpenUrl(val uri: URI) : ClickEvent {
        override fun asVanilla(): VanillaClickEvent {
            //#if MC >= 1.21.5
            return VanillaClickEvent.OpenUrl(uri)
            //#else
            //$$ return VanillaClickEvent(VanillaClickEvent.Action.OPEN_URL, uri.toString())
            //#endif
        }
    }

    public data class OpenFile(val path: String) : ClickEvent {
        override fun asVanilla(): VanillaClickEvent {
            //#if MC >= 1.21.5
            return VanillaClickEvent.OpenFile(path)
            //#else
            //$$ return VanillaClickEvent(VanillaClickEvent.Action.OPEN_FILE, path)
            //#endif
        }
    }

    public data class RunCommand(val command: String) : ClickEvent {
        override fun asVanilla(): VanillaClickEvent {
            //#if MC >= 1.21.5
            return VanillaClickEvent.RunCommand(command)
            //#else
            //$$ return VanillaClickEvent(VanillaClickEvent.Action.RUN_COMMAND, command)
            //#endif
        }
    }

    public data class SuggestCommand(val command: String) : ClickEvent {
        override fun asVanilla(): VanillaClickEvent {
            //#if MC >= 1.21.5
            return VanillaClickEvent.SuggestCommand(command)
            //#else
            //$$ return VanillaClickEvent(VanillaClickEvent.Action.SUGGEST_COMMAND, command)
            //#endif
        }
    }

    public data class ChangePage(val page: Int) : ClickEvent {
        override fun asVanilla(): VanillaClickEvent {
            //#if MC >= 1.21.5
            return VanillaClickEvent.ChangePage(page)
            //#else
            //$$ return VanillaClickEvent(VanillaClickEvent.Action.CHANGE_PAGE, page.toString())
            //#endif
        }
    }

    //#if MC >= 1.16.5
    public data class CopyToClipboard(val value: String) : ClickEvent {
        override fun asVanilla(): VanillaClickEvent {
            //#if MC >= 1.21.5
            return VanillaClickEvent.CopyToClipboard(value)
            //#else
            //$$ return VanillaClickEvent(VanillaClickEvent.Action.COPY_TO_CLIPBOARD, value)
            //#endif
        }
    }
    //#endif

    //#if MC >= 1.21.6
    public data class ShowDialog(val dialog: Holder<Dialog>) : ClickEvent {
        override fun asVanilla(): VanillaClickEvent {
            return VanillaClickEvent.ShowDialog(dialog)
        }
    }

    public data class Custom(val identifier: ResourceLocation, val payload: Optional<Tag>) : ClickEvent {
        override fun asVanilla(): VanillaClickEvent {
            return VanillaClickEvent.Custom(identifier, payload)
        }
    }
    //#endif

    public fun asVanilla(): VanillaClickEvent
}
