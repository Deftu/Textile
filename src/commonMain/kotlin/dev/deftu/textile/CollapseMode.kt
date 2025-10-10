package dev.deftu.textile

/**
 * How [Text.collapseToString] should write style codes into a plain string.
 *
 * Think of it as: "how aggressive should we be about turning styles on/off as we build the string?".
 *
 * **TL;DR**
 * - Use [AUTO]. It picks the right thing for you.
 * - [SCOPED] = each piece cleans up after itself (no style leaks).
 * - [DELTA]  = only change when needed (styles keep flowing until changed).
 *
 * ## Example (same styled content, different outputs)
 * Given three pieces: `red = ("Hello")`, `bold & underlined = ("World")`, `green = ("!")`
 *
 * - **SCOPED** → `§cHello §r§l§nWorld§r §a!§r`  (open, write, reset every time)
 * - **DELTA**  → `§cHello §l§nWorld §a!`        (only emit when something changes)
 */
public enum class CollapseMode {
    /**
     * Pick automatically based on the styles you use.
     *
     * If any style provides a "close" code (a *right* closer, like a reset),
     * act like [SCOPED]. Otherwise, act like [DELTA].
     *
     * This lets platforms opt into safer output without the caller caring.
     */
    AUTO,

    /**
     * Self-contained spans (no bleed).
     *
     * For each formatted piece, write its left codes **before** the text and its right closers
     * **after** the text. Neighboring pieces never inherit styles from each other.
     */
    SCOPED,

    /**
     * Minimal, flowing styles (carry forward until changed).
     *
     * Only write codes when the **effective** style changes. Do **not** emit right closers after each piece.
     */
    DELTA
}
