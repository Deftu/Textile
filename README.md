# Textile

Kotlin-first text interfacing library. Minimal, fast and portable. No magic, just build text, style it, and ship it.

---

[![CurseForge Badge](https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/cozy/available/curseforge_64h.png)](https://www.curseforge.com/minecraft/mc-mods/textile)
[![Modrinth Badge](https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/cozy/available/modrinth_64h.png)](https://modrinth.com/mod/textile-lib)  
[![Discord Badge](https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/cozy/social/discord-singular_64h.png)](https://s.deftu.dev/discord)
[![GitHub Badge](https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/cozy/social/github-singular_64h.png)](https://github.com/Deftu/Textile)  
[![Ko-Fi Badge](https://raw.githubusercontent.com/intergrav/devins-badges/v2/assets/cozy/donate/kofi-singular_64h.png)](https://ko-fi.com/Deftu)

---

## Installation

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

- 1.21.9 NeoForge    (1.21.9-neoforge)
- 1.21.9 Fabric      (1.21.9-fabric)
- 1.21.8 NeoForge    (1.21.8-neoforge)
- 1.21.8 Fabric      (1.21.8-fabric)
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
// f.ex "dev.deftu:textile-1.19.4-fabric:1.0.0"
modImplementation "dev.deftu:textile-<minecraft.version>-<loader>:<version>"
```

</details>

<details>
    <summary>Kotlin (.gradle.kts)</summary>

```gradle
// f.ex "dev.deftu:textile-1.19.4-fabric:1.0.0"
modImplementation("dev.deftu:textile-<minecraft.version>-<loader>:<version>")
```

</details>

## API TL;DR

Textile revolves around three primary concepts:
- `Text`: Node with `content`, `style` and potentially `siblings`.
- `TextStyle`: Map of properties that define how your text looks and is outputted when converted to a string.
- `StringVisitor`: Visit leaf strings, whether they be given to you raw or styled.

The following examples make use of the Minecraft module.

### Building text

```kotlin
import dev.deftu.textile.Text
import dev.deftu.textile.minecraft.MCTextStyle
import dev.deftu.textile.minecraft.TextColors

val message = Text.literal("Hello, ")
    .append(Text.literal("world").setStyle(MCTextStyle.color(TextColors.RED).bold()))
    .append(Text.literal("!"))
```

### Visiting strings (in render order)

```kotlin
import dev.deftu.textile.TextStyle

message.visit({ content, style ->
    println("$content | $style")
    // Returns Unit from println, which is effectively a "keep going" signal
}, TextStyle.EMPTY)
```

### Collapsing into a single string value / into legacy codes

```kotlin
val content = message.collapseToString()
```

### Converting your `Text` into a Minecraft component using the Minecraft module

```kotlin
import dev.deftu.textile.minecraft.MCText

val component = MCText.convert(message) // Textile -> Vanilla Component
// val back = MCText.wrap(component) // Vanilla Component -> Textile
```

---

[![BisectHosting](https://www.bisecthosting.com/partners/custom-banners/8fb6621b-811a-473b-9087-c8c42b50e74c.png)](https://bisecthosting.com/deftu)

---

**This project is licensed under [LGPL-3.0][lgpl]**\
**&copy; 2025 Deftu**
