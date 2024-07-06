package pl.edu.mimuw;

import java.util.*;

public class MultiSet<T> implements Collection<T> {

    private HashMap<T, Integer> map;

    public MultiSet() {
        map = new HashMap<>();
    }

    @Override
    public int size() {
        int count = 0;
        for (int value : map.values()) {
            count += value;
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public Iterator<T> iterator() {
        return new MultiSetIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        int index = 0;
        for (T element : this) {
            array[index++] = element;
        }
        return array;
    }

    @Override
    public boolean add(T element) {
        if (map.containsKey(element)) {
            map.put(element, map.get(element) + 1);
        } else {
            map.put(element, 1);
        }
        return true;
    }

    private class MultiSetIterator implements Iterator<T> {

        private final Iterator<T> mapIterator;
        private T currentElement;
        private int remainingOccurrences;

        public MultiSetIterator() {
            mapIterator = map.keySet().iterator();
            currentElement = null;
            remainingOccurrences = 0;
        }

        @Override
        public boolean hasNext() {
            return remainingOccurrences > 0 || mapIterator.hasNext();
        }

        @Override
        public T next() {
            if (remainingOccurrences == 0) {
                currentElement = mapIterator.next();
                remainingOccurrences = map.get(currentElement);
            }
            remainingOccurrences--;
            return currentElement;
        }
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size()) {
            T[] result = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size());
            a = result;
        } else if (a.length > size()) {
            a[size()] = null;
        }

        int index = 0;
        for (var element : this) {
            a[index++] = (T) element;
        }
        return a;
    }

    @Override
    public boolean remove(Object o) {
        if (map.containsKey(o)) {
            int occurrences = map.get(o);
            if (occurrences > 1) {
                map.put((T) o, occurrences - 1);
            } else {
                map.remove(o);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (var element : c) {
            if (!map.containsKey(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T element : c) {
            modified |= add(element);
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object element : c) {
            modified |= remove(element);
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Set<Object> elementsToRemove = new HashSet<>(map.keySet());
        elementsToRemove.removeAll(c);
        return removeAll(elementsToRemove);
    }

    @Override
    public void clear() {
        map.clear();
    }

    public int getCount(Object o) {
        if (!this.map.containsKey(o))
            return 0;
        return this.map.get(o);
    }

    public void setCount(T t, int i) {
        if (i == 0)
            this.remove(t);
        else
            this.map.put(t, i);
    }
}
