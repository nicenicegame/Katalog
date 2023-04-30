package model

data class Bundle(
    val alias: String,
    val group: List<Library>
) {
    override fun toString(): String {
        return "$alias = [${group.joinToString { "\"${it.alias}\"" }}]"
    }
}