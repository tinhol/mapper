package impl

import api.*
import api.Target

open class Mapper(val targetFactory: TargetFactory) {
    fun map(sources: Iterable<Source>, mapping: List<Op>): Iterable<Target> {
        return sources.map { source ->
            map(source, mapping)
        }
    }

    open fun map(source: Source, mapping: List<Op>): Target {
        val mappingTarget = targetFactory.create()
        mapping.forEach({ entry ->
            when (entry) {
                is Simple -> {
                    mappingTarget.setValue(entry.to, source.getValue(entry.from))
                }
                is Transform -> {
                    mappingTarget.setValue(entry.to, applyTransform(source.getValue(entry.from), entry.transform))
                }
                is Value -> {
                    mappingTarget.setValue(entry.to, entry.value)
                }
            }
        })
        return mappingTarget
    }

    open fun applyTransform(value: Any?, transform: Transformation): Any? = transform.transform(value)
}