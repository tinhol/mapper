package org.tinhol.mapper.impl

import org.tinhol.mapper.api.Source
import org.tinhol.mapper.api.SourceFactory
import org.tinhol.mapper.api.Target
import org.tinhol.mapper.api.TargetFactory

class MapTargetSourceFactory : TargetFactory, SourceFactory<MutableMap<String, Any?>> {
    override fun create(param: MutableMap<String, Any?>): Source {
        return MapTargetSource(param)
    }

    override fun create(): Target {
        return MapTargetSource(mutableMapOf())
    }
}