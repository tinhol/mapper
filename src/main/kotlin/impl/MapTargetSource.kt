package impl

import api.Source
import api.Target

class MapTargetSource(val storage: MutableMap<String, Any?>) : Target, Source {
    override fun setValue(name: String, value: Any?) {
        storage[name] = value
    }

    override fun getValue(name: String): Any? {
        return storage[name]
    }

    override fun toString(): String {
        return "impl.MapTargetSource(storage=$storage)"
    }
}