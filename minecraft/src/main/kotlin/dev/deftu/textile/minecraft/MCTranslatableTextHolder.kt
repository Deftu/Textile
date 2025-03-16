package dev.deftu.textile.minecraft

import net.minecraft.client.resource.language.I18n

public open class MCTranslatableTextHolder(
    key: String,
    vararg args: Any
): MCSimpleTextHolder(I18n.translate(key, *args))
