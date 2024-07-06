package pl.edu.mimuw;

import java.util.*;

public class FilterableList<T> extends ArrayList<T> {

    public FilterableList() {
        super();
    }

    public FilterableList(Collection<? extends T> col) {
        super(col);
    }

    public FilterableList<T> filter(Filter<T> filter) {

        FilterableList<T> result = new FilterableList<T>();

        for (T elem : this) {
            if (filter.test(elem))
                result.add(elem);
        }

        return result;
    }

    public List<T> toList() {
        return new ArrayList<T>(this);
    }

    public FilterableList<T> madd(T t) {
        this.add(t);
        return this;
    }
}
