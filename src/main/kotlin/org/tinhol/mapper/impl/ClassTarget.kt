package org.tinhol.mapper.impl

import org.tinhol.mapper.api.Target

class ClassTarget(val obj: Any) : Target {
    override fun setValue(name: String, value: Any?) {
        fun getHierarchy(cls: Class<in Any>): List<Class<in Any>> {
            if (cls.superclass != null) {
                return listOf(cls) + getHierarchy(cls.superclass)
            } else {
                return listOf(cls)
            }
        }

        getHierarchy(obj.javaClass).forEach({ cls ->
            try {
                val declaredField = cls.getDeclaredField(name)
                declaredField.set(obj, value)
                return
            } catch (e: Exception) {
                //ignore exception
            }
        })
    }

    override fun toString(): String {
        return "ClassTarget(obj=$obj)"
    }
}