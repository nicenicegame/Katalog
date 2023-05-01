package model

data class Plugin(
    val alias: String,
    val id: String,
    val version: Version
) {
    companion object {
        fun create(alias: String, pluginId: String): Plugin {
            val (id, version) = pluginId.split(":")
            return Plugin(
                alias = alias,
                id = id,
                version = VersionNumber(version)
            )
        }

        fun create(alias: String, pluginId: String, ref: VersionRef): Plugin {
            val (id) = pluginId.split(":")
            return Plugin(
                alias = alias,
                id = id,
                version = ref
            )
        }
    }

    val pluginId: String
        get() = when (version) {
            is VersionNumber -> "$id:${version.value}"
            is VersionRef -> "$id:${version.number}"
        }

    override fun toString(): String {
        return when (version) {
            is VersionNumber -> "$alias = { id = \"$id\", version = \"${version.value}\" }"
            is VersionRef -> "$alias = { id = \"$id\", version.ref = \"${version.alias}\" }"
        }
    }
}