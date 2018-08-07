package org.tinhol.mapper.impl

import org.tinhol.mapper.api.Target

class ClassTarget<T : Any>(val obj: T) : Target<T> {
    override fun getContent(): T {
        return obj
    }

    override fun setValue(name: String, value: Any?) {
        fun getHierarchy(cls: Class<*>): List<Class<*>> {
            return if (cls.superclass != null) {
                listOf(cls) + getHierarchy(cls.superclass)
            } else {
                listOf(cls)
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