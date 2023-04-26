package model

data class Library(
    val alias: String,
    val group: String,
    val name: String,
    val version: Version
) {
    companion object {
        fun create(alias: String, artifactId: String): Library {
            val (group, name, version) = artifactId.split(":", limit = 3)
            return Library(
                alias = alias,
                group = group,
                name = name,
                version = VersionNumber(version)
            )
        }
    }

    val artifactId: String
        get() = when (version) {
            is VersionNumber -> "$group:$name:${version.value}"
            is VersionRef -> "$group:$name:${version.number}"
        }
}