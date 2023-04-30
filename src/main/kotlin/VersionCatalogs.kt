import model.Bundle
import model.Library
import model.Plugin
import model.VersionRef

interface VersionCatalogs {
    val versions: List<VersionRef>
    val libraries: List<Library>
    val plugins: List<Plugin>
    val bundles: List<Bundle>

    companion object {
        val EMPTY = object : VersionCatalogs {
            override val versions = emptyList<VersionRef>()
            override val libraries = emptyList<Library>()
            override val plugins = emptyList<Plugin>()
            override val bundles = emptyList<Bundle>()
        }

        fun create(
            versions: List<VersionRef>,
            libraries: List<Library>,
            plugins: List<Plugin>,
            bundles: List<Bundle>
        ): VersionCatalogs {
            return object : VersionCatalogs {
                override val versions = versions
                override val libraries = libraries
                override val plugins = plugins
                override val bundles = bundles
            }
        }
    }
}

fun VersionCatalogs.generateTOML(): String {
    return buildString {
        if (versions.isNotEmpty()) {
            appendLine("[versions]")
            versions.forEach { appendLine(it.toString()) }
            appendLine()
        }
        if (libraries.isNotEmpty()) {
            appendLine("[libraries]")
            libraries.forEach { appendLine(it.toString()) }
            appendLine()
        }
        if (plugins.isNotEmpty()) {
            appendLine("[plugins]")
            plugins.forEach { appendLine(it.toString()) }
            appendLine()
        }
        if (bundles.isNotEmpty()) {
            appendLine("[bundles]")
            bundles.forEach { appendLine(it.toString()) }
            appendLine()
        }
    }
}