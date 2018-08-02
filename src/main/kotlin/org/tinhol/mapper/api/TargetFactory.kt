package org.tinhol.mapper.api

interface TargetFactory<T> {
    fun create() : Target<T>
}