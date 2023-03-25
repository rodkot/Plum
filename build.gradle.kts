import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "ru.nsu.ccfit"
version = "1.0-SNAPSHOT"

repositories {
    google()
    maven("https://jitpack.io")
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("br.com.devsrsouza.compose.icons.jetbrains:feather:1.0.0")
                implementation("br.com.devsrsouza.compose.icons.jetbrains:simple-icons:1.0.0")
                implementation("dev.shreyaspatil:capturable:1.0.3")
                implementation("com.raedapps:rasmview:1.2.1")
                implementation("com.godaddy.android.colorpicker:compose-color-picker-jvm:0.7.0")
                implementation("com.github.SmartToolFactory:Compose-Colorful-Sliders:1.1.0")
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Plum"
            packageVersion = "1.0.0"
        }
    }
}
