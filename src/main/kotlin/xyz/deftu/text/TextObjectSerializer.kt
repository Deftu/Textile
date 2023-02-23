package xyz.deftu.text

interface TextObjectSerializer<T : Text> {
    fun serialize(obj: Any): T
}
