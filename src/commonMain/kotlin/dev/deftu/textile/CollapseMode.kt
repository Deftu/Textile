package dev.deftu.textile

/**
 * Controls how [Text.collapseToString] emits formatting styles.
 *
 * You should usually use [CollapseMode.AUTO] unless you have a specific reason not to.
 */
public enum class CollapseMode {
    /**
     * Heuristic: choose behavior based on the styles present.
     *
     * If any property supplies a *right* closer (e.g., a Minecraft reset), behave like [SCOPED].
     * Otherwise, behave like [DELTA].
     */
    AUTO,

    /**
     * Self-contained spans. No style bleed.
     *
     * For each visually formatted node, emit its left openers **before** content and its right closers
     * **after** content. Adjacent segments never inherit styles from each other.
     */
    SCOPED,

    /**
     * Minimal, flowing styles (inherit across siblings until changed).
     *
     * Emit formatting only when the **effective** style changes. Do not emit *right* closers after each node.
     */
    DELTA
}
