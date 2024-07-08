package dev.deftu.textile

public interface MutableTextHolder : TextHolder {

    /**
     * Sets the contents of this text holder.
     *
     * @since 0.4.0
     * @author Deftu
     */
    public fun set(textHolder: TextHolder): MutableTextHolder

    /**
     * Appends the given text holder to this text holder, making it a child.
     *
     * @since 0.4.0
     * @author Deftu
     */
    public fun append(textHolder: TextHolder): MutableTextHolder

    /**
     * Applies formatting to this text holder.
     *
     * @since 0.4.0
     * @author Deftu
     */
    public fun format(vararg formatting: TextFormat): TextHolder

    /**
     * Replaces a given key with a value.
     *
     * @since 0.4.0
     * @author Deftu
     */
    public fun replace(key: String, value: Any): TextHolder

    /**
     * Replaces the first occurrence of a given key with a value.
     *
     * @since 0.4.0
     * @author Deftu
     */
    public fun replaceFirst(key: String, value: Any): TextHolder

    /**
     * Replaces the last occurrence of a given key with a value.
     *
     * @since 0.4.0
     * @author Deftu
     */
    public fun replaceLast(key: String, value: Any): TextHolder

}
