package org.tinhol.mapper.impl

import org.tinhol.mapper.api.Target
import org.tinhol.mapper.api.TargetFactory

class ClassTargetFactory<T : Any>(val clazz: Class<T>) : TargetFactory<T> {
    override fun create(): Target<T> {
        return ClassTarget(clazz.getDeclaredConstructor().newInstance() as T)
    }
}