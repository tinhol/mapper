package org.tinhol.mapper

import org.tinhol.mapper.api.*
import org.tinhol.mapper.api.Target
import org.tinhol.mapper.impl.Mapper

class Main <in T: ReadCommand, S>(
    private val reader: Reader<T>,
    private val mapping: List<Op>,
    private val targetFactory: TargetFactory<S>
) {
    fun process(readCommand: T): List<Target<S>> {
        val mapper = Mapper(targetFactory)
        return reader.read(readCommand)
                .map { source -> mapper.map(source, mapping) }

    }
}