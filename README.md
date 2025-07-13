# Textile

A Kotlin-first, minimal and powerful text interface library. Enabling clean abstraction of text handling
across various platforms.

---

[![CurseForge Badge](https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/cozy/available/curseforge_64h.png)](https://www.curseforge.com/minecraft/mc-mods/textile)
[![Modrinth Badge](https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/cozy/available/modrinth_64h.png)](https://modrinth.com/mod/textile-lib)  
[![Discord Badge](https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/cozy/social/discord-singular_64h.png)](https://s.deftu.dev/discord)
[![GitHub Badge](https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/cozy/social/github-singular_64h.png)](https://github.com/Deftu/Textile)  
[![Ko-Fi Badge](https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/cozy/donate/kofi-singular_64h.png)](https://ko-fi.com/Deftu)

---

## Usage

### Repository


<details>
    <summary>Groovy (.gradle)</summary>

```gradle
maven {
    name = "Deftu Releases"
    url = "https://maven.deftu.dev/releases"
}
```
</details>

<details>
    <summary>Kotlin (.gradle.kts)</summary>

```kotlin
maven(url = "https://maven.deftu.dev/releases") {
    name = "Deftu Releases"
}
```
</details>

### Dependency

![Repository badge](https://maven.deftu.dev/api/badge/latest/releases/dev/deftu/textile?color=C33F3F&name=Textile)

<details>
    <summary>Groovy (.gradle)</summary>

```gradle
implementation "dev.deftu:textile:<version>"
```

</details>

<details>
    <summary>Kotlin (.gradle.kts)</summary>

```gradle
implementation("dev.deftu:textile:<version>")
```

</details>

### Minecraft Dependency

<details>
    <summary>Minecraft versions & mod loaders</summary>

- 1.21.7 NeoForge    (1.21.7-neoforge)
- 1.21.7 Fabric      (1.21.7-fabric)
- 1.21.6 NeoForge    (1.21.6-neoforge)
- 1.21.6 Fabric      (1.21.6-fabric)
- 1.21.5 NeoForge    (1.21.5-neoforge)
- 1.21.5 Fabric      (1.21.5-fabric)
- 1.21.4 NeoForge    (1.21.4-neoforge)
- 1.21.4 Fabric      (1.21.4-fabric)
- 1.21.1 NeoForge    (1.21.1-neoforge)
- 1.21.1 Fabric      (1.21.1-fabric)
- 1.20.6 NeoForge    (1.20.6-neoforge)
- 1.20.6 Fabric      (1.20.6-fabric)
- 1.20.4 NeoForge    (1.20.4-neoforge)
- 1.20.4 Forge       (1.20.4-forge)
- 1.20.4 Fabric      (1.20.4-fabric)
- 1.20.1 Forge       (1.20.1-forge)
- 1.20.1 Fabric      (1.20.1-fabric)
- 1.19.4 Forge       (1.19.4-forge)
- 1.19.4 Fabric      (1.19.4-fabric)
- 1.19.2 Forge       (1.19.2-forge)
- 1.19.2 Fabric      (1.19.2-fabric)
- 1.18.2 Forge       (1.18.2-forge)
- 1.18.2 Fabric      (1.18.2-fabric)
- 1.17.1 Forge       (1.17.1-forge)
- 1.17.1 Fabric      (1.17.1-fabric)
- 1.16.5 Forge       (1.16.5-forge)
- 1.16.5 Fabric      (1.16.5-fabric)
- 1.12.2 Forge       (1.12.2-forge)
- 1.12.2 Fabric      (1.12.2-fabric)
- 1.8.9  Forge       (1.8.9-forge)
- 1.8.9  Fabric      (1.8.9-fabric)

</details>

<details>
    <summary>Groovy (.gradle)</summary>

```gradle
modImplementation "dev.deftu:textile-<minecraft version>-<loader>:<version>"
```

</details>

<details>
    <summary>Kotlin (.gradle.kts)</summary>

```gradle
modImplementation("dev.deftu:textile-<minecraft version>-<loader>:<version>")
```

</details>

## Examples

### Basic Usage

```kt
import dev.deftu.textile.TextHolder
import dev.deftu.textile.SimpleTextHolder

fun main() {
    // Very simple literal text implementation bundled with Textile
    val firstText = SimpleTextHolder("Hello, World!")
    printText(firstText)
    
    // You can extend TextHolder to create custom text holders
    // Including ones which support translations!
    val secondText = MyCustomTextHolder(TranslationKeys.MY_TRANSLATION_KEY)
    printText(secondText) // Prints the translation of MY_TRANSLATION_KEY, as provided by you
}

fun printText(text: TextHolder<*, *>) {
    println(text.asString())
}
```

### Mutability

Now, Textile's `TextHolder` is immutable by default, allowing you to control how you
handle the text state in your application. Should you want to mutate existing text
without creating duplicates or children of the original text, you can use
`MutableTextHolder`.

```kt
import dev.deftu.textile.MutableTextHolder
import dev.deftu.textile.SimpleTextHolder
import dev.deftu.textile.SimpleMutableTextHolder

fun main() {
    // MutableTextHolder allows you to mutate the text without creating a duplicate/copy of the original text
    val mutableText = SimpleMutableTextHolder("Hello, World!")
    
    // Mutate the text by adding a child
    mutableText.append(SimpleTextHolder(" How are you?"))
    
    // Print the mutated text
    println(mutableText.asString()) // Outputs: Hello, World! How are you?
}
```

---

## Minecraft Integration

Textile provides a default module for Minecraft, which includes various text holder implementations
tailored for Minecraft's text system. This module supports most major Minecraft versions and loaders.

These can be automatically converted to and from Minecraft's own text component system, allowing you to
easily integrate Textile text holders into your Minecraft mods.

Not only does this let you use Textile’s rich text API, but it also makes integrating with Minecraft's
text system trivial across several Minecraft versions.

The text holders implemented for the Minecraft version are identical to the base ones provided by Textile,
but with `MC` prefixed on them, as well as 2 additional ones for translations. These additional text holders
include utilities for Minecraft's click and hover events, as well as converting to Minecraft's text component
system.

### How do I create a `MCTextHolder` from a vanilla component?

`MCTextHolder` gives you a static `convertFromVanilla` method, which takes a vanilla component and outputs
an identical `MCTextHolder` instance to the original component.

The opposite can be done with `MCTextHolder#convertToVanilla` (static) or `MCTextHolder#asVanilla`,
which takes **ANY** `TextHolder` and outputs a vanilla component with a structure identical to it.
Keep in mind that click and hover events can ONLY be carried over from `MCTextHolder`, you'll need
to either extend `MCTextHolder` or write your own conversion logic if you want to carry over click
and hover events from other text holders.

### How do I build out my own `MCTextHolder`?

The easiest way to do so is with literal text through `MCSimpleTextHolder`.

You can find an example/test on how to do this in [TestMod.kt](./minecraft/src/testMod/kotlin/com/test/TestMod.kt).

---

[![BisectHosting](https://www.bisecthosting.com/partners/custom-banners/8fb6621b-811a-473b-9087-c8c42b50e74c.png)](https://bisecthosting.com/deftu)

---

**This project is licensed under [LGPL-3.0][lgpl]**\
**&copy; 2024 Deftu**
