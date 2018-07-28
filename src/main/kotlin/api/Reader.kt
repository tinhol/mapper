package api

interface Reader<in T: ReadCommand> {
    fun read(readCommand: T): List<Source>
}