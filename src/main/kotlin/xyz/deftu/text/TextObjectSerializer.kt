package xyz.deftu.text

public interface TextObjectSerializer<T : Text> {
    public fun serialize(obj: Any): T
}
