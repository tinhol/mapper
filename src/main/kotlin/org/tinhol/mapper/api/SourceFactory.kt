package org.tinhol.mapper.api

interface SourceFactory<T> {
    fun create(param: T) : Source
}