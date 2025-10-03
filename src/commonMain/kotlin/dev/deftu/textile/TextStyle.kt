package dev.deftu.textile

import kotlin.jvm.JvmField
import kotlin.jvm.JvmOverloads

public data class TextStyle(public val propertiesByName: Map<PropertyKey<*>, Property<*>>) {
    public companion object {
        @JvmField public val EMPTY: TextStyle = TextStyle(emptyMap())
    }

    public data class PropertyKey<T>(val name: String, val sortIndex: Int? = null) : Comparable<PropertyKey<*>> {
        override fun compareTo(other: PropertyKey<*>): Int {
            return when {
                this.sortIndex != null && other.sortIndex != null -> this.sortIndex.compareTo(other.sortIndex)
                this.sortIndex != null -> -1
                other.sortIndex != null -> 1
                else -> this.name.compareTo(other.name)
            }
        }

        override fun toString(): String {
            return name
        }
    }

    public data class Property<T> @JvmOverloads public constructor(
        public val key: PropertyKey<T>,
        public val value: T,
        /** A string which can be applied to the left-hand side of the text when applying formatting to it manually. */
        public val left: String? = null,
        /** A string which can be applied to the right-hand side of the text when applying formatting to it manually. */
        public val right: String? = null
    )

    public val properties: List<Property<*>>
        get() = propertiesByName.values.sortedBy(Property<*>::key)

    public operator fun <T> get(key: PropertyKey<T>): Property<T>? {
        @Suppress("UNCHECKED_CAST")
        return propertiesByName[key] as? Property<T>
    }

    public fun withProperties(vararg props: Property<*>): TextStyle {
        if (props.isEmpty()) {
            return this
        }

        return TextStyle(propertiesByName + props.associateBy(Property<*>::key))
    }

    public fun inherited(parent: TextStyle): TextStyle {
        val newProperties = parent.propertiesByName + propertiesByName
        return if (newProperties.size == propertiesByName.size) this else TextStyle(newProperties)
    }

    override fun toString(): String {
        return buildString {
            append("Style[ ")

            properties.forEachIndexed { index, property ->
                if (index > 0) append(", ")
                append(property.key.name)
                append("=")
                append(property.value)
            }

            append(" ]")
        }
    }
}
