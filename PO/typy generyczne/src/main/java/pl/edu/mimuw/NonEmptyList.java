package pl.edu.mimuw;

import java.util.Iterator;

public class NonEmptyList<T> implements Iterable<T> {
    private T head;
    private final NonEmptyList<T> tail;

    public NonEmptyList(T head) {
        this.head = head;
        this.tail = null;
    }

    private NonEmptyList(T head, NonEmptyList<T> tail) {
        this.head = head;
        this.tail = tail;
    }

    public T getHead() {
        return this.head;
    }

    public NonEmptyList<T> prepend(T element) {
        return new NonEmptyList<T>(element, this);
    }

    public NonEmptyList<T> reverse() {
        var reverseList = new NonEmptyList<>(this.head);
        if (this.tail == null)
            return reverseList;

        for (var elem : this.tail) {
            reverseList = reverseList.prepend(elem);
        }
        return reverseList;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private NonEmptyList<T> currentNode = NonEmptyList.this;

            @Override
            public boolean hasNext() {
                return this.currentNode != null;
            }

            @Override
            public T next() {
                var currentHead = this.currentNode.head;
                this.currentNode = this.currentNode.tail;
                return currentHead;
            }
        };
    }
}
