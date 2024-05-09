# KIA - Kotlin Inventory API

[![Dokka](https://img.shields.io/badge/JavaDoc-Online-green)](https://staticfx.github.io/KIA/)
![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/staticfx/kia/workflow.yml)
![GitHub Release](https://img.shields.io/github/v/release/staticfx/kia)

Maven:
```
<dependency>
  <groupId>de.staticred.kia</groupId>
  <artifactId>kia</artifactId>
  <version>1.0.25</version>
</dependency>
```



KIA is an API programed using the papermc api to make inventory management and its creation easier.
KIA allows to set event listeners directly on the Inventory Items instead of manually listening to them.
In order to keep track of the Items KIA uses the NBT data of an item to set a UUID and match it with the corresponding item.

## Other Features
 - NBT System for Inventory Matching (Gets rid of inventory title issues)
 - Supports inventory rows
 - Inventory animations
