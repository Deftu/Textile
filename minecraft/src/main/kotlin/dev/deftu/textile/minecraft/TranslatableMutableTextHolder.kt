package dev.deftu.textile.minecraft

import dev.deftu.textile.SimpleMutableTextHolder
import net.minecraft.client.resource.language.I18n

public class TranslatableMutableTextHolder(
    key: String,
    vararg args: Any
) : SimpleMutableTextHolder(I18n.translate(key, *args))
