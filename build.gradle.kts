fun kotlinw(target: String): String =
    "org.jetbrains.kotlin-wrappers:kotlin-$target"

val kotlinWrappersVersion = "1.0.0-pre.542"

plugins {
    kotlin("js") version "1.8.20"
}

group = "me.nicenicegame"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(enforcedPlatform(kotlinw("wrappers-bom:$kotlinWrappersVersion")))
    implementation(kotlinw("emotion"))
    implementation(kotlinw("react"))
    implementation(kotlinw("react-dom"))
    implementation(kotlinw("mui"))
    implementation(kotlinw("mui-icons"))
    implementation(kotlinw("extensions"))
    implementation(npm("highlight.js", "11.7.0"))
}

kotlin {
    js {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
}