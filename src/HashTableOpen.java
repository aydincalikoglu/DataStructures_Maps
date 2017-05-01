import java.util.*;

/**
 * Hash table implementation using open addressing.
 */
public class HashTableOpen<K, V> extends AbstractMap<K, V>
        implements HashMap<K, V> {
    // Data Fields

    private Entry<K, V>[] table;
    private static final int START_CAPACITY = 101;
    private double LOAD_THRESHOLD = 0.75;
    private int numKeys;
    private int numDeletes;
    private final Entry<K, V> DELETED =
            new Entry<K, V>(null, null);


    // Constructor
    public HashTableOpen() {
        table = new Entry[START_CAPACITY];
    }

    /** Contains key-value pairs for a hash table. */
    public static class Entry<K, V> implements Map.Entry<K, V> {

        /** The key */
        private K key;
        /** The value */
        private V value;

        /**
         * Creates a new key-value pair.
         * @param key The key
         * @param value The value
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Retrieves the key.
         * @return The key
         */
        @Override
        public K getKey() {
            return key;
        }

        /**
         * Retrieves the value.
         * @return The value
         */
        @Override
        public V getValue() {
            return value;
        }

        /**
         * Sets the value.
         * @param val The new value
         * @return The old value
         */
        @Override
        public V setValue(V val) {
            V oldVal = value;
            value = val;
            return oldVal;
        }

// Insert solution to programming exercise 3, section 4, chapter 7 here
    }

    /** Returns true if empty */
    @Override
    public boolean isEmpty() {
        return numKeys == 0;
    }
    /**
     * Finds either the target key or the first empty slot in the
     * search chain using linear probing.
     * @pre The table is not full.
     * @param key The key of the target object
     * @return The position of the target or the first empty slot if
     *         the target is not in the table.
     */
    private int find(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length; // Make it positive.
        }
        while ((table[index] != null)
                //&& (!key.equals(table[index].key))
                ) {
            index++;
            if (index >= table.length) {
                index = 0;
            }
        }
        return index;
    }



    /**
     * Finds either the target key or the first empty slot in the
     * search chain using linear probing.
     * @pre The table is not full.
     * @param key The key of the target object
     * @return The position of the target or the first empty slot if
     *         the target is not in the table.
     */
    private int getfind(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length; // Make it positive.
        }
        while ((table[index] != null)
            && (!key.equals(table[index].key))
                ) {
            index++;
            if (index >= table.length) {
                index = 0;
            }
        }
        return index;
    }

    /**
     * Method get for class HashtableOpen.
     * @param key The key being sought
     * @return the value associated with this key if found;
     *         otherwise, null
     */
    @Override
    public V get(Object key) {
        int index = getfind(key);
        if (table[index] != null) {
            return table[index].value;
        } else {
            return null; // key not found.
        }
    }

    /**
     * Method put for class HashtableOpen.
     * This key-value pair is inserted in the
     *       table and numKeys is incremented. If the key is already
     *       in the table, its value is changed to the argument
     *       value and numKeys is not changed. If the LOAD_THRESHOLD
     *       is exceeded, the table is expanded.
     * @param key The key of item being inserted
     * @param value The value for this key
     * @return Old value associated with this key if found;
     *         otherwise, null
     */
    @Override
    public V put(K key, V value) {
        int index = find(key);
        if (table[index] == null) {
            table[index] = new Entry<K, V>(key, value);
            numKeys++;

            double loadFactor =
                    (double) (numKeys + numDeletes) / table.length;
            if (loadFactor > LOAD_THRESHOLD) {
                rehash();
            }
            return null;
        }

        V oldVal = table[index].value;
        table[index].value = value;
        return oldVal;
    }

    /**
     * Expands table size when loadFactor exceeds LOAD_THRESHOLD
     * @post The size of table is doubled and is an odd integer.
     *       Each nondeleted entry from the original table is
     *       reinserted into the expanded table.
     *       The value of numKeys is reset to the number of items
     *       actually inserted; numDeletes is reset to 0.
     */
    private void rehash() {
        Entry<K, V>[] oldTable = table;
        table = new Entry[2 * oldTable.length + 1];
        numKeys = 0;
        numDeletes = 0;
        for (int i = 0; i < oldTable.length; i++) {
            if ((oldTable[i] != null) && (oldTable[i] != DELETED)) {
                put(oldTable[i].key, oldTable[i].value);
            }
        }
    }
    /**
     * Returns a set view of the entries in the Map
     * @return a Set view of the entries in the Map
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    /** Inner class to implement the set view. */
    private class EntrySet extends AbstractSet<Map.Entry<K, V>> {

        /** Return the size of the set. */
        @Override
        public int size() {
            return numKeys;
        }

        /** Return an iterator over the set. */
        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new hashOpenIterator<K,V>();
        }
    }

    /**
     * @param key Key for accessing value
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * This implementation iterates over searching for an
     * entry with the specified key.  If such an entry is found, its value is
     * obtained with its <tt>getValue</tt> operation, the entry is removed
     * from the collection (and the backing map) with the iterator's
     * <tt>remove</tt> operation, and the saved value is returned.  If the
     * iteration terminates without finding such an entry, <tt>null</tt> is
     * returned.  Note that this implementation requires linear time in the
     * size of the map; many implementations will override this method.
     * Note that this implementation throws an
     * <tt>UnsupportedOperationException</tt> if the <tt>entrySet</tt>
     * iterator does not support the <tt>remove</tt> method and this map
     * contains a mapping for the specified key.
     */
    @Override
    public V remove(Object key) {
        int index = getfind(key);
        if (table[index] != null) {
            V tempValue=table[index].value;
            table[index]=DELETED;
            numDeletes++;
            return tempValue;
        } else {
            return null; // key not found.
        }
    }

    public Iterator<Map.Entry<K, V>> iterator() {
        return new hashOpenIterator<K,V>();
    }

    private class hashOpenIterator<K,V> implements Iterator
    {
        int i=0;
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            while ( i<table.length && (table[i]==null || (table[i] == DELETED)))i++;
            return i<table.length;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Entry<K,V> next() {
            if ((table[i] != null) && (table[i] != DELETED)) {
                return (Entry<K, V>) table[i++];
            }
            return null;
        }
    }

}
