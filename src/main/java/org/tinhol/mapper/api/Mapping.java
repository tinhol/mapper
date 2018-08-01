package org.tinhol.mapper.api;

public final class Mapping {

    private Mapping() {
    }

    public static Trivial trivial(String name) {
        return new Trivial(name);
    }

    public static Simple simple(String from, String to) {
        return new Simple(from, to);
    }

    public static Transform transform(String from, String to, Transformation transformation) {
        return new Transform(from, to, transformation);
    }

    public static Value value(Object value, String to) {
        return new Value(value, to);
    }
}
