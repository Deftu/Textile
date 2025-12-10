package dev.deftu.textile.ansi

public enum class ColorLevel {
    /** Do not emit ANSI codes */
    NONE,
    /**
     * 16-color (standard) ANSI. Maps to basic foreground colors.
     */
    COLOR_16,
    /**
     * 256-color ANSI (`38;5;N` / `48;5;N`).
     */
    COLOR_256,
    /**
     * Truecolor ANSI (`38;2;r;g;b` / `48;2;r;g;b`).
     */
    TRUECOLOR,
}
