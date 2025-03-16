package dev.deftu.textile.minecraft

import net.minecraft.client.resource.language.I18n

public class MCTranslatableMutableTextHolder(
    key: String,
    vararg args: Any
) : MCSimpleMutableTextHolder(I18n.translate(key, *args))
