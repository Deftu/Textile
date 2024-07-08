package dev.deftu.textile.impl

import net.minecraft.client.resource.language.I18n

public class TranslatableMutableTextHolder(
    key: String,
    vararg args: Any
) : SimpleMutableTextHolder(I18n.translate(key, *args))
