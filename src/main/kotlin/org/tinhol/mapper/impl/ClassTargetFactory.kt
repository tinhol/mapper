package org.tinhol.mapper.impl

import org.tinhol.mapper.api.Target
import org.tinhol.mapper.api.TargetFactory

class ClassTargetFactory(val clazz: Class<out Any>) : TargetFactory {
    override fun create(): Target {
        return ClassTarget(clazz.newInstance())
    }
}