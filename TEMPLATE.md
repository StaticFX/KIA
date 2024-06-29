# KIA - Kotlin Inventory API

[![Dokka](https://img.shields.io/badge/JavaDoc-Online-green)](https://staticfx.github.io/KIA/)
![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/staticfx/kia/publish-package.yml)
![GitHub Release](https://img.shields.io/github/v/release/staticfx/kia)

# Prerequisites

- KIA needs to be installed on the server as its dependent plugin. So KIA can hook into the events
- KIA needs to run on a PaperMC server, because some paper specific functions are used.

### Add to your code base

<details>
<summary>Maven</summary>

- ```xml 
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>

  <dependency>
    <groupId>de.staticred.kia</groupId>
    <artifactId>kia</artifactId>
    <version>$VERSION</version>
    <scope>provided</scope>
  </dependency>
    ```
</details>

<details>
<summary>Gradle Groovy:</summary>

- ```groovy
  	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
  
  compileOnly "de.staticred.kia:kia:$VERSION"
    ```

</details>

<details>
<summary>Gradle kts</summary>

- ```kotlin
  repositories { 
    maven { setUrl("https://jitpack.io") }
  }
  
  api("de.staticred.kia:kia:$VERSION")
    ```
</details>

## TLDR 

To get a quick look on how to use KIA, please visit the [wiki](https://github.com/StaticFX/KIA/wiki).

## Introduction

KIA is an API programed using the papermc api to make inventory management and its creation easier.
Using NBT KIA is able to handle inventories without naming issues or similar.

## Key features
### Easily build inventories using the build in DSL
```kotlin
val inventory = kInventory(sender, 5.rows, InventoryType.CHEST) {
    setItem(row = 1, slot = 4, kItem(Material.DIAMOND_PICKAXE) {
        setDisplayName(Component.text("Some cool item").color(TextColor.color(255, 0, 0)).decorate(TextDecoration.BOLD))
        enchant(Enchantment.DIG_SPEED, 5)
    })
}
```
### Easier to use listeners
```kotlin
setItem(1, 4, kItem(Material.DIAMOND_PICKAXE) {
    onClick { kItem: KItem, player: Player -> player.sendMessage("Cool you just clicked ${kItem.slot}") }
})
```
### Animations
```kotlin
openingAnimation = endlessAnimation(100, TimeUnit.MILLISECONDS) {
    onAnimationFrame {
        row.shift(ShiftDirection.LEFT, 1, true)
        this@mainPage.setRow(1, row)
    }
}
```
This animation will play everytime the inventory is opened.

### Paging inventories
```kotlin
val pageInventory = kPageInventory(sender, 5.rows) {
    looping = true
    title = Component.text("Paging Inventory")

    mainPage {
        this.title = Component.text("This is the main page")
        header = defaultHeader
    }

    addPage {
        this.title = Component.text("Page 2")
        header = defaultHeader
    }

    addPage {
        this.title = Component.text("Page 3")
        header = defaultHeader
    }
}
```
With support for page headers to make navigating an ease!
```kotlin
val defaultHeader = kPageController {
    nextBtn = kItem(Material.PAPER, 1) {
        setDisplayName(Component.text("Next Page ->"))
    }
    previousBtn = kItem(Material.PAPER, 1) {
        setDisplayName(Component.text("<- Previous Page"))
    }
    placeholderItem = kItem(Material.BLACK_STAINED_GLASS_PANE, 1) {
        setDisplayName(Component.text("'"))
    }
    builder = { nextBtn: KItem, previousBtn: KItem, placeholder: KItem -> run {
        kRow {
            setItem(0..1, placeholder!!)
            setItem(2, previousBtn!!)
            setItem(3..5, placeholder)
            setItem(6, nextBtn!!)
            setItem(7..8, placeholder)
        }
    }}
}
```

For more examples and on how to use KIA, please head over to the [wiki!](https://github.com/StaticFX/KIA/wiki)

## Other Features
- NBT System for Inventory Matching (Gets rid of inventory title issues)
- Different helper functions to build the inventories easier
- Optional row based system to easier build and manage your inventories
