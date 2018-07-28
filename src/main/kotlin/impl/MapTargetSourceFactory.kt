package impl

import api.Source
import api.SourceFactory
import api.Target
import api.TargetFactory

class MapTargetSourceFactory : TargetFactory, SourceFactory<MutableMap<String, Any?>> {
    override fun create(param: MutableMap<String, Any?>): Source {
        return MapTargetSource(param)
    }

    override fun create(): Target {
        return MapTargetSource(mutableMapOf())
    }
}