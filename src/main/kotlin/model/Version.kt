package model

sealed class Version

data class VersionNumber(val value: String) : Version()

data class VersionRef(
    val alias: String,
    val number: String
) : Version() {
    override fun toString(): String {
        return "$alias = \"$number\""
    }
}