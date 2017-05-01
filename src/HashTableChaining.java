import java.util.*;

/**
 * Created by syucer on 4/24/2017.
 */
public class HashTableChaining<K, V> implements HashMap<K, V> {
    /** The table */
    //Do not forget you can use more class and methods to do this homework,
    // this project gives you an initial classes an methods to see easily
    //....
    //.... other class members

    public HashTableChaining() {

        table = new HashTableOpen[CAPACITY];

    }

    /** The table */
    private HashTableOpen<K,V>[] table;
    /** The number of keys */
    private int numKeys;
    /** The capacity */
    private static final int CAPACITY = 101;
    /** The maximum load factor */
    private static final double LOAD_THRESHOLD = 3.0;


    @Override
    public V get(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length;
        }
        return table[index].get(key);
    }

    @Override
    public V put(K key, V value) {

        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length;
        }
        if (table[index]==null)
            table[index]=new HashTableOpen<K,V>();
        V put = table[index].put(key, value);

        numKeys++;
        if (numKeys > (LOAD_THRESHOLD * table.length)) {
            rehash();
        }
        return put;
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
        HashTableOpen<K,V>[] oldTable = table;
        table = new HashTableOpen[size()*2+1];
        numKeys = 0;

        for (int i = 0; i < oldTable.length; i++) {

            if ((oldTable[i] != null) && (oldTable[i].size() > 0)) {
                Set<Map.Entry<K, V>> hashOpenSet = oldTable[i].entrySet();
                Iterator<Map.Entry<K, V>> it = hashOpenSet.iterator();
                while (it.hasNext()) {
                    Map.Entry<K,V> next=it.next();
                    put(next.getKey(), next.getValue());
                }
            }
        }
    }

    @Override
    public V remove(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length;
        }
        numKeys--;
        return table[index].remove(key);
    }

    @Override
    public int size() {
        return numKeys;
    }

    @Override
    public boolean isEmpty() {
        return numKeys == 0;
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("{ ");
        for (int i=0;i<table.length;i++)
        {
            if (table[i]!=null) {
                Iterator<Map.Entry<K, V>> it = table[i].iterator();

                while (it.hasNext()) {
                    Map.Entry<K, V> curEntry = it.next();
                    if (curEntry != null) {
                        sb.append(curEntry.getKey().toString() + "=" + curEntry.getValue().toString() + ", ");
                    }
                }
            }
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("}");
        return sb.toString();
    }
}
