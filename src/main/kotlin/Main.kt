import api.*
import api.Target
import impl.Mapper

class Main <in T: ReadCommand>(
        val reader: Reader<T>,
        val mapping: List<Op>,
        val targetFactory: TargetFactory
) {
    fun process(readCommand: T): Iterable<Target> {
        val mapper = Mapper(targetFactory)
        return reader.read(readCommand)
                .map { source -> mapper.map(source, mapping) }

    }
}