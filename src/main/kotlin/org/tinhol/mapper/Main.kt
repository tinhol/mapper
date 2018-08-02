package org.tinhol.mapper

import org.tinhol.mapper.api.*
import org.tinhol.mapper.api.Target
import org.tinhol.mapper.impl.Mapper

class Main <in T: ReadCommand, S>(
        val reader: Reader<T>,
        val mapping: List<Op>,
        val targetFactory: TargetFactory<S>
) {
    fun process(readCommand: T): List<Target<S>> {
        val mapper = Mapper(targetFactory)
        return reader.read(readCommand)
                .map { source -> mapper.map(source, mapping) }

    }
}