package hw_2;

public class FilterImpl<T extends Number> implements Filter<T> {
    @Override
    public T apply(T o) {
        if (o instanceof Integer) {
            return (T) Integer.valueOf(o.intValue() * 2);
        } else if (o instanceof Double) {
            return (T) Double.valueOf(o.doubleValue() * 2);
        } else if (o instanceof Long) {
            return (T) Long.valueOf(o.longValue() * 2);
        } else if (o instanceof Float) {
            return (T) Float.valueOf(o.floatValue() * 2);
        }
            throw new UnsupportedOperationException("Unsupported type");
    }
}

