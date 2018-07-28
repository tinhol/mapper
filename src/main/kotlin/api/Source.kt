package api

interface Source {
    fun getValue(name: String) : Any?
}