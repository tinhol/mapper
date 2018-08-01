package org.tinhol.mapper.api

sealed class Op
data class Trivial(val name: String) : Op()
data class Simple(val from: String, val to: String) : Op()
data class Transform(val from: String, val to: String, val transform : Transformation) : Op()
data class Value(val value: Any, val to: String) : Op()