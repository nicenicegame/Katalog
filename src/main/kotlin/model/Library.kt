package model

data class Library(
    val alias: String,
    val group: String,
    val name: String,
    val version: Version
) {
    companion object {
        fun create(alias: String, artifactId: String): Library {
            val (group, name, version) = artifactId.split(":")
            return Library(
                alias = alias,
                group = group,
                name = name,
                version = VersionNumber(version)
            )
        }

        fun create(alias: String, artifactId: String, ref: VersionRef): Library {
            val (group, name) = artifactId.split(":")
            return Library(
                alias = alias,
                group = group,
                name = name,
                version = ref
            )
        }
    }

    val artifactId: String
        get() = when (version) {
            is VersionNumber -> "$group:$name:${version.value}"
            is VersionRef -> "$group:$name:${version.number}"
        }

    override fun toString(): String {
        return when (version) {
            is VersionNumber -> "$alias = { group = \"$group\", name = \"$name\", version = \"${version.value}\" }"
            is VersionRef -> "$alias = { group = \"$group\", name = \"$name\", version.ref = \"${version.alias}\" }"
        }
    }
}

external interface LibraryJSO {

}