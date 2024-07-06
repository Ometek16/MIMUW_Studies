package pl.edu.mimuw;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TestMultiSet {

    @Test
    public void shouldAddAndSize() {
        // given
        MultiSet<String> multiSet = new MultiSet<>();

        // when
        multiSet.add("apple");
        multiSet.add("banana");
        multiSet.add("apple");
        multiSet.add("orange");

        // then
        assertEquals(4, multiSet.size());
    }

    @Test
    public void testRemove() {
        // given
        MultiSet<String> multiSet = new MultiSet<>();

        // when
        multiSet.add("apple");
        multiSet.add("banana");
        multiSet.add("apple");
        multiSet.add("orange");

        // then
        assertTrue(multiSet.remove("apple"));
        assertFalse(multiSet.remove("pear"));
        assertEquals(3, multiSet.size());
    }

    @Test
    public void testClearAndIsEmpty() {
        // given
        MultiSet<String> multiSet = new MultiSet<>();

        // when
        multiSet.add("apple");
        multiSet.add("banana");
        multiSet.clear();

        // then
        assertTrue(multiSet.isEmpty());
        assertEquals(0, multiSet.size());
    }

    @Test
    public void testContains() {
        // given
        MultiSet<String> multiSet = new MultiSet<>();

        // when
        multiSet.add("apple");
        multiSet.add("banana");
        multiSet.add("apple");
        multiSet.add("orange");

        // then
        assertTrue(multiSet.contains("apple"));
        assertFalse(multiSet.contains("pear"));
    }

    @Test
    public void testContainsAll() {
        // given
        MultiSet<String> multiSet = new MultiSet<>();
        List<String> list = new ArrayList<>();

        // when
        multiSet.add("apple");
        multiSet.add("banana");
        multiSet.add("apple");
        multiSet.add("orange");

        list.add("apple");
        list.add("banana");

        // then
        assertTrue(multiSet.containsAll(list));
    }

    @Test
    public void testAddAll() {
        // given
        MultiSet<String> multiSet = new MultiSet<>();
        List<String> list = new ArrayList<>();

        // when
        list.add("apple");
        list.add("banana");
        list.add("apple");
        list.add("orange");

        // then
        assertTrue(multiSet.addAll(list));
        assertEquals(4, multiSet.size());
    }

    @Test
    public void testRemoveAll() {
        // given
        MultiSet<String> multiSet = new MultiSet<>();
        List<String> list = new ArrayList<>();

        // when
        multiSet.add("apple");
        multiSet.add("banana");
        multiSet.add("apple");
        multiSet.add("orange");

        list.add("apple");
        list.add("banana");
        list.add("pear");

        // then
        assertTrue(multiSet.removeAll(list));
        assertEquals(2, multiSet.size());
    }

    @Test
    public void testRetainAll() {
        // given
        MultiSet<String> multiSet = new MultiSet<>();
        List<String> list = new ArrayList<>();

        // when
        multiSet.add("apple");
        multiSet.add("banana");
        multiSet.add("apple");
        multiSet.add("orange");

        list.add("apple");
        list.add("orange");
        list.add("pear");

        // then
        assertTrue(multiSet.retainAll(list));
        assertEquals(3, multiSet.size());
    }

    @Test
    public void testToArrayNoSize() {
        // given
        MultiSet<String> multiSet = new MultiSet<>();

        // when
        multiSet.add("apple");
        multiSet.add("banana");
        multiSet.add("apple");
        multiSet.add("orange");

        String[] array = multiSet.toArray(new String[0]);

        assertEquals("banana", array[0]);
        assertEquals("orange", array[1]);
        assertEquals("apple", array[2]);
        assertEquals("apple", array[3]);
    }

    public void testToArrayWithSize() {
        // given
        MultiSet<String> multiSet = new MultiSet<>();

        // when
        multiSet.add("apple");
        multiSet.add("banana");
        multiSet.add("apple");
        multiSet.add("orange");

        String[] array = multiSet.toArray(new String[10]);

        assertEquals("banana", array[0]);
        assertEquals("orange", array[1]);
        assertEquals("apple", array[2]);
        assertEquals("apple", array[3]);
        for (int i = 4; i < array.length; i++) {
            assertNull(array[i]);
        }
    }

    @Test
    public void testToArrayObject() {
        // given
        MultiSet<String> multiSet = new MultiSet<>();

        // when
        multiSet.add("apple");
        multiSet.add("banana");
        multiSet.add("apple");
        multiSet.add("orange");

        Object[] array = multiSet.toArray();

        assertEquals("banana", array[0]);
        assertEquals("orange", array[1]);
        assertEquals("apple", array[2]);
        assertEquals("apple", array[3]);
    }
}
