package model

data class Plugin(
    val alias: String,
    val id: String,
    val version: Version
) {
    fun from(artifactId: String): Plugin {
        val (id, version) = artifactId.split(":", limit = 2)
        return Plugin(
            alias = id,
            id = id,
            version = VersionNumber(version)
        )
    }
}