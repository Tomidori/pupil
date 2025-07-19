# pupil

**pupil** is a Kotlin library providing extension functions and a DSL
for [polymer-virtual-entity](https://github.com/Patbox/polymer). With pupil, you can easily create and manage
`ElementHolder` instances.

## Gradle Setup

### Groovy

```groovy
repositories {
    maven { url 'https://maven.nucleoid.xyz' }
    maven { url 'https://api.modrinth.com/maven' }
}

dependencies {
    modImplementation 'eu.pb4:polymer-virtual-entity:POLYMER_VERSION'
    modImplementation 'maven.modrinth:pupil:PUPIL_VERSION'
}
```

### Kotlin DSL

```kotlin
repositories {
    maven("https://maven.nucleoid.xyz")
    maven("https://api.modrinth.com/maven")
}

dependencies {
    modImplementation("eu.pb4:polymer-virtual-entity:POLYMER_VERSION")
    modImplementation("maven.modrinth:pupil:PUPIL_VERSION")
}
```

## Usage

Here is a simple example.

```kotlin
import net.minecraft.item.Items
import org.tomidori.pupil.elements.*
import org.tomidori.pupil.holders.*
import org.tomidori.pupil.math.*

fun create() = elementHolder {
    itemDisplayElement {
        onTick {
            transformation {
                rotateLocalYDegrees(ticks * 3.0f)
            }

            startInterpolation(1)
        }

        item = Items.TNT.defaultStack
        teleportDuration = 1
    }
}
```