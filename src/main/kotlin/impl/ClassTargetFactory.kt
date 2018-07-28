package impl

import api.Target
import api.TargetFactory

class ClassTargetFactory(val clazz: Class<out Any>) : TargetFactory {
    override fun create(): Target {
        return ClassTarget(clazz.newInstance())
    }
}