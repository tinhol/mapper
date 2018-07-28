package api

interface SourceFactory<T> {
    fun create(param: T) : Source
}