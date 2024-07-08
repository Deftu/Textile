package dev.deftu.textile.impl

import net.minecraft.client.resource.language.I18n

public open class TranslatableTextHolder(
    key: String,
    vararg args: Any
): SimpleTextHolder(I18n.translate(key, *args))
