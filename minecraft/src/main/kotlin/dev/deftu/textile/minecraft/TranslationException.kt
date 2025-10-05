package dev.deftu.textile.minecraft

public class TranslationException(message: String) : Exception(message) {
    public constructor(content: TranslatableTextContent, index: Int) : this("Invalid index $index requested for $content")
}
