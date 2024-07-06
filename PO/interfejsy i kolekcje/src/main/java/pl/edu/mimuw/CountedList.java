package pl.edu.mimuw;

import java.util.*;

public class CountedList<T> extends ArrayList<T> {
    private HashMap<Integer, Integer> indexCounter = new HashMap<>();
    private HashMap<T, Integer> objectCounter = new HashMap<>();

    @Override
    public T get(int index) {
        if (!indexCounter.containsKey(index))
            indexCounter.put(index, 1);
        else
            indexCounter.put(index, indexCounter.get(index) + 1);

        T object = super.get(index);

        if (!objectCounter.containsKey(object))
            objectCounter.put(object, 1);
        else
            objectCounter.put(object, objectCounter.get(object) + 1);

        return super.get(index);
    }

    public int getIndexAccessCount(int index) {
        if (!indexCounter.containsKey(index))
            return 0;
        return indexCounter.get(index);
    }

    public int getElementAccessCount(T object) {
        if (!objectCounter.containsKey(object))
            return 0;
        return objectCounter.get(object);
    }

    public int getMostOftenAccessedIndex() {
        return Collections.max(
                indexCounter.entrySet(),
                Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    public T getMostOftenAccessedElement() {
        return Collections.max(
                objectCounter.entrySet(),
                Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

}
