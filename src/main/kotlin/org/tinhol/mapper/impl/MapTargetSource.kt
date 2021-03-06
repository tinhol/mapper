package org.tinhol.mapper.impl

import org.tinhol.mapper.api.Source
import org.tinhol.mapper.api.Target

class MapTargetSource(private val storage: MutableMap<String, Any?>) : Target<MutableMap<String, Any?>>, Source {
    override fun getContent(): MutableMap<String, Any?> {
        return storage
    }

    override fun setValue(name: String, value: Any?) {
        storage[name] = value
    }

    override fun getValue(name: String): Any? {
        return storage[name]
    }

    override fun toString(): String {
        return "org.tinhol.mapper.MapTargetSource(storage=$storage)"
    }
}