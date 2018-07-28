package api

open class ReadCommand(
        open val path: String? = null,
        open val repeat: Boolean = true) {
}