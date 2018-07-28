package impl

import api.Target

class ClassTarget(val obj: Any) : Target {
    override fun setValue(name: String, value: Any?) {
        val declaredField = obj.javaClass.getDeclaredField(name)
        declaredField.set(obj, value)
    }

    override fun toString(): String {
        return "ClassTarget(obj=$obj)"
    }
}