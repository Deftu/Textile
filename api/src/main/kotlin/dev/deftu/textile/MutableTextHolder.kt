package dev.deftu.textile

public interface MutableTextHolder<T : MutableTextHolder<T, F>, F : TextFormat> : TextHolder<T, F> {

    /**
     * Sets the contents of this text holder.
     *
     * @since 0.4.0
     * @author Deftu
     */
    public fun set(text: TextHolder<*, *>): T

    /**
     * Sets the contents of this text holder.
     *
     * @since 0.6.0
     * @author Deftu
     */
    public fun set(text: String): T

    /**
     * Appends the given text holder to this text holder, making it a child.
     *
     * @since 0.4.0
     * @author Deftu
     */
    public fun append(text: TextHolder<*, *>): T

    /**
     * Applies formatting to this text holder.
     *
     * @since 0.4.0
     * @author Deftu
     */
    public fun format(vararg formatting: F): T

    /**
     * Replaces a given key with a value.
     *
     * @since 0.4.0
     * @author Deftu
     */
    public fun replace(key: String, value: Any): T

    /**
     * Replaces the first occurrence of a given key with a value.
     *
     * @since 0.4.0
     * @author Deftu
     */
    public fun replaceFirst(key: String, value: Any): T

    /**
     * Replaces the last occurrence of a given key with a value.
     *
     * @since 0.4.0
     * @author Deftu
     */
    public fun replaceLast(key: String, value: Any): T

}
