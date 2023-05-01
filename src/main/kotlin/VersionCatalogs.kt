import model.Bundle
import model.Library
import model.Plugin
import model.VersionRef

data class VersionCatalogs(
    val versions: List<VersionRef>,
    val libraries: List<Library>,
    val plugins: List<Plugin>,
    val bundles: List<Bundle>
) {
    companion object {
        val EMPTY = VersionCatalogs(
            versions = emptyList(),
            libraries = emptyList(),
            plugins = emptyList(),
            bundles = emptyList(),
        )
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