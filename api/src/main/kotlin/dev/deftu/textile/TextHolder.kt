package dev.deftu.textile

import java.util.Optional

/**
 * Represents a class which holds, formats, and manipulates text.
 *
 * @since 0.4.0
 * @author Deftu
 */
public interface TextHolder<T : TextHolder<T, F>, F : TextFormat> {

    /**
     * All children of this text holder, appended in the order they were added.
     *
     * @since 0.4.0
     * @author Deftu
     */
    public val children: List<TextHolder<*, *>>

    /**
     * The formatting applied to this text holder.
     *
     * @since 0.4.0
     * @author Deftu
     */
    public val formatting: Set<F>

    public fun <T> visit(visitor: TextHolderVisitor<T>): Optional<out T>

    /**
     * Creates an exact copy of this text holder.
     *
     * @since 0.4.0
     * @author Deftu
     */
    public fun copy(): T

    /**
     * Converts this text holder to a string, including it's formatting and children.
     *
     * @since 0.4.0
     * @author Deftu
     */
    public fun asString(): String

    /**
     * Converts this text holder to a string, without it's formatting, but including it's children.
     *
     * @since 0.4.0
     * @author Deftu
     */
    public fun asUnformattedString(): String

    /**
     * Converts this text holder to a string, without it's children, still including formatting.
     *
     * @since 0.4.0
     * @author Deftu
     */
    public fun asExclusiveString(): String

    /**
     * Returns a recreation of the text holder with the given formatting applied to it.
     *
     * @since 0.4.0
     * @author Deftu
     */
    public fun withFormatting(vararg formatting: F): T

}
