package pl.edu.mimuw;

import java.util.function.*;

public class Container<T extends Comparable<T>> {
    private T value;

    public Container(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    // ? Ko-wariancja (mozna tylko czytać)
    public void pullValueFrom(Container<? extends T> other) {
        this.value = other.value;
    }

    // ? Kontra-wariancja (mozna tylko pisac)
    public void pushValueTo(Container<? super T> other) {
        other.value = this.value;
    }

    // ? Musi być ten sam typ, bo wpp In-wariancja (nie dałoby się nic)
    public void swapValuesWith(Container<T> other) {
        T tmp = this.value;
        this.value = other.value;
        other.value = tmp;
    }

    public <U extends Comparable<U>> Container<U> map(Function<T, U> mapper) {
        return new Container<U>(mapper.apply(this.value));
    }

    public <U extends Comparable<U>> Container<U> flatMap(Function<T, Container<U>> mapper) {
        return mapper.apply(this.value);
    }

    public <U extends Comparable<U>, V extends Comparable<V>> Container<V> merge(Container<U> other,
            BiFunction<U, T, V> f) {
        return new Container<V>(f.apply(other.value, this.value));
    }

    public int compareTo(Container<T> other) {
        return this.value.compareTo(other.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
