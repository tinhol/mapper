package org.tinhol.mapper.api

interface Target<T> {
    fun getContent(): T
    fun setValue(name: String, value: Any?)
}