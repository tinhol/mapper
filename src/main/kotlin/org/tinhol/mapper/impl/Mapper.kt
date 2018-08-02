package org.tinhol.mapper.impl

import org.tinhol.mapper.api.*
import org.tinhol.mapper.api.Target

open class Mapper<T>(val targetFactory: TargetFactory<T>) {
    fun map(sources: Iterable<Source>, mapping: List<Op>): Iterable<Target<T>> {
        return sources.map { source ->
            map(source, mapping)
        }
    }

    open fun map(source: Source, mapping: List<Op>): Target<T> {
        val mappingTarget = targetFactory.create()
        mapping.forEach({ entry ->
            when (entry) {
                is Trivial -> {
                    mappingTarget.setValue(entry.name, source.getValue(entry.name))
                }
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