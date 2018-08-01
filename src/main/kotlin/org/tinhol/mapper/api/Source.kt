package org.tinhol.mapper.api

interface Source {
    fun getValue(name: String) : Any?
}