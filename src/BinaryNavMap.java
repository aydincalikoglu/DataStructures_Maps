import java.util.*;

public class BinaryNavMap<K extends Comparable<K>,V extends Comparable<V>> extends AbstractMap<K,V>
        implements NavigableMap<K,V>, Cloneable, java.io.Serializable
{

    private Node<K,V> root=null;
    int size=0;

    private static final class Node<K, V> extends AbstractMap.SimpleEntry<K, V>
    {
         /** The left child node. */
         Node<K, V> left=null;
         /** The right child node. */
         Node<K, V> right=null;
         /** The parent node. */
         Node<K, V> parent;

         Node(K key, V value,Node<K,V> parent)
         {
             super(key, value);
             this.parent=parent;
         }
         
    }

    @Override
    public V put(K key, V value) {

        root=add(root,key,value,null);
        if (addReturn==true)
        {
            size++;
            return value;
        }
        else
            return null;
    }


    /** Return value from the public add method. */
    protected boolean addReturn;
    /** Return value from the public delete method. */
    protected Node<K,V> deleteReturn;


    /**
     * The data field addReturn is set true if the item is added
     * @param locRoot The local root of the subtree
     * @param key The object to be inserted
     * @param value The object to be inserted
     * @return The new local root that now contains the
     *         inserted item
     */
    private Node<K,V> add(Node<K,V> locRoot, K key,V value,Node<K,V> parent) {
        if (locRoot == null) {
            addReturn = true;
            return new Node<K,V>(key,value,parent);
        } else if (key.compareTo(locRoot.getKey()) == 0) {
            addReturn = false;
            return locRoot;
        } else if (key.compareTo(locRoot.getKey()) < 0) {
            locRoot.left = add(locRoot.left, key,value,locRoot);
            return locRoot;
        } else {
            locRoot.right = add(locRoot.right, key,value,locRoot);
            return locRoot;
        }
    }

    /**
     * @param key The object to be inserted
     * @throws ClassCastException   {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * This implementation iterates over <tt>entrySet()</tt> searching
     * for an entry with the specified key.  If such an entry is found,
     * the entry's value is returned.  If the iteration terminates without
     * finding such an entry, <tt>null</tt> is returned.  Note that this
     * implementation requires linear time in the size of the map; many
     * implementations will override this method.
     */
    @Override
    public V get(Object key) {
        return find(root,(K) key);
    }

    /**
     * This implementation returns <tt>entrySet().size()</tt>.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @param key The object to be inserted
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * This implementation iterates over <tt>entrySet()</tt> searching for an
     * entry with the specified key.  If such an entry is found, its value is
     * obtained with its <tt>getValue</tt> operation, the entry is removed
     * from the collection (and the backing map) with the iterator's
     * <tt>remove</tt> operation, and the saved value is returned.  If the
     * iteration terminates without finding such an entry, <tt>null</tt> is
     * returned.  Note that this implementation requires linear time in the
     * size of the map; many implementations will override this method.
     * 
     * Note that this implementation throws an
     * <tt>UnsupportedOperationException</tt> if the <tt>entrySet</tt>
     * iterator does not support the <tt>remove</tt> method and this map
     * contains a mapping for the specified key.
     */
    @Override
    public V remove(Object key) {
        delete(root,(K) key);
        return deleteReturn.getValue();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    private class EntrySet extends AbstractSet<Entry<K, V>> {

        /** Return the size of the set. */
        @Override
        public int size() {
            return size;
        }

        /** Return an iterator over the set. */
        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new InOrderIterator<>(root) ;
        }
    }


    /**
     * Returns a key-value mapping associated with the greatest key
     * .strictly less than the given key, or {@code null} if there is
     * no such key
     *
     * @param key The object to be inserted
     * @return an entry with the greatest key less than {@code key},
     * or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public Entry<K, V> lowerEntry(K key) {
        if (key==null)
            throw new NullPointerException();
        if (root!=null)
            return lowerEntry(root,null,key);
        return null;
    }


    private Entry<K, V>lowerEntry(Node<K,V> localRoot,Node<K,V> findNode,K key)
    {
        if (localRoot==null)
            return findNode;
        if (key.compareTo(localRoot.getKey())<=0)
                return lowerEntry(localRoot.left,findNode,key);
        else
        {
            return lowerEntry(localRoot.right,localRoot,key);
        }
    }

    /**
     * Returns the greatest key strictly less than the given key, or
     * {@code null} if there is no such key.
     *
     * @param key The object to be inserted
     * @return the greatest key less than {@code key},
     * or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public K lowerKey(K key) {
        if (key==null)
            throw new NullPointerException();
        if (root!=null)
            return lowerEntry(root,null,key).getKey();
        return null;
    }

    /**
     * Returns a key-value mapping associated with the greatest key
     * less than or equal to the given key, or {@code null} if there
     * is no such key.
     *
     * @param key The object to be inserted
     * @return an entry with the greatest key less than or equal to
     * {@code key}, or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public Entry<K, V> floorEntry(K key) {
        if (key==null)
            throw new NullPointerException();

        if (root!=null)
            return floorEntry(root,null,key);
        return null;
    }


    private Entry<K, V>floorEntry(Node<K,V> localRoot,Node<K,V> findNode,K key)
    {
        if (localRoot==null)
            return findNode;
        if (key.compareTo(localRoot.getKey())==0)
        {
            return localRoot;
        }
        if (key.compareTo(localRoot.getKey())<0)
            return floorEntry(localRoot.left,findNode,key);
        return floorEntry(localRoot.right,localRoot,key);
    }

    /**
     * Returns the greatest key less than or equal to the given key,
     * or {@code null} if there is no such key.
     *
     * @param key The object to be inserted
     * @return the greatest key less than or equal to {@code key},
     * or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public K floorKey(K key) {
        if (key==null)
            throw new NullPointerException();
        if (root!=null)
            return floorEntry(root,null,key).getKey();
        return null;
    }

    /**
     * Returns a key-value mapping associated with the least key
     * greater than or equal to the given key, or {@code null} if
     * there is no such key.
     *
     * @param key The object to be inserted
     * @return an entry with the least key greater than or equal to
     * {@code key}, or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public Entry<K, V> ceilingEntry(K key) {
        if (key==null)
            throw new NullPointerException();

        if (root!=null)
            return ceilingEntry(root,null,key,0);
        return null;
    }




    private Entry<K, V>ceilingEntry(Node<K,V> localRoot,Node<K,V> parent,K key,int rigLeft)
    {
        if (localRoot==null){
            if (rigLeft==0)
                return parent;
            else
            {
                if (parent.parent!=null)
                    return parent.parent;
                return null;
            }
        }
        if (key.compareTo(localRoot.getKey())==0)
            return localRoot;
        if (key.compareTo(localRoot.getKey())<0)
            return ceilingEntry(localRoot.left,localRoot,key,0);
        else
            return ceilingEntry(localRoot.right,localRoot,key,1);
    }

    /**
     * Returns the least key greater than or equal to the given key,
     * or {@code null} if there is no such key.
     *
     * @param key The object to be inserted
     * @return the least key greater than or equal to {@code key},
     * or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public K ceilingKey(K key) {
        if (key==null)
            throw new NullPointerException();
        if (root!=null)
            return ceilingEntry(root,null,key,0).getKey();
        return null;
    }

    /**
     * Returns a key-value mapping associated with the least key
     * strictly greater than the given key, or {@code null} if there
     * is no such key.
     *
     * @param key The object to be inserted
     * @return an entry with the least key greater than {@code key},
     * or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public Entry<K, V> higherEntry(K key) {
        if (key==null)
            throw new NullPointerException();
        if (root!=null)
            return higherEntry(root,null,key,0);
        return null;
    }


    private Entry<K, V>higherEntry(Node<K,V> localRoot,Node<K,V> parent,K key,int rigLeft)
    {
        if (localRoot==null){
            if (rigLeft==0)
                return parent;
            else
            {
                if (parent.parent!=null)
                    return parent.parent;
                return null;
            }
        }
        if (key.compareTo(localRoot.getKey())==0)
            if (rigLeft==0)
                return localRoot.parent;
            else
                return localRoot.right;
        if (key.compareTo(localRoot.getKey())<0)
            return higherEntry(localRoot.left,localRoot,key,0);
        else
            return higherEntry(localRoot.right,localRoot,key,1);
    }
    /**
     * Returns the least key strictly greater than the given key, or
     * {@code null} if there is no such key.
     *
     * @param key The object to be inserted
     * @return the least key greater than {@code key},
     * or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public K higherKey(K key) {
        if (key==null)
            throw new NullPointerException();
        if (root!=null)
            return higherEntry(root,null,key,0).getKey();
        return null;
    }

    /**
     * Returns a key-value mapping associated with the least
     * key in this map, or {@code null} if the map is empty.
     *
     * @return an entry with the least key,
     * or {@code null} if this map is empty
     */
    @Override
    public Entry<K, V> firstEntry() {
        return (new InOrderIterator<>(root).next);
    }

    /**
     * Returns a key-value mapping associated with the greatest
     * key in this map, or {@code null} if the map is empty.
     *
     * @return an entry with the greatest key,
     * or {@code null} if this map is empty
     */
    @Override
    public Entry<K, V> lastEntry() {
        Node<K,V> cur=root;
        while (cur.right!=null)
            cur=cur.right;
        return cur;
    }

    /**
     * Removes and returns a key-value mapping associated with
     * the least key in this map, or {@code null} if the map is empty.
     *
     * @return the removed first entry of this map,
     * or {@code null} if this map is empty
     */
    @Override
    public Entry<K, V> pollFirstEntry() {
        Node<K,V> cur=root;
        if (size==1)
            root=null;
        else {
            while (cur.left != null)
                cur = cur.left;
            if (cur.parent != null)
                cur.parent.right = cur.left;
            else
                root=cur.right;
        }
        size--;
        return cur;

    }

    /**
     * Removes and returns a key-value mapping associated with
     * the greatest key in this map, or {@code null} if the map is empty.
     *
     * @return the removed last entry of this map,
     * or {@code null} if this map is empty
     */
    @Override
    public Entry<K, V> pollLastEntry() {
        Node<K,V> cur=root;
        if (size==1)
            root=null;
        while (cur.right!=null)
            cur=cur.right;
        if (cur.parent!=null)
            cur.parent.right=cur.left;
        size--;
        return cur;
    }


    /**
     * Returns a reverse order view of the mappings contained in this map.
     * The descending map is backed by this map, so changes to the map are
     * reflected in the descending map, and vice-versa.  If either map is
     * modified while an iteration over a collection view of either map
     * is in progress (except through the iterator's own {@code remove}
     * operation), the results of the iteration are undefined.
     * 
     * The returned map has an ordering equivalent to
     * <tt>{@link Collections#reverseOrder(Comparator) Collections.reverseOrder}(comparator())</tt>.
     * The expression {@code m.descendingMap().descendingMap()} returns a
     * view of {@code m} essentially equivalent to {@code m}.
     *
     * @return a reverse order view of this map
     */
    @Override
    public NavigableMap<K, V> descendingMap() {
        NavigableMap<K,V> ns=new TreeMap<K, V>();
        ns.putAll(this);
        return ns.descendingMap();
    }



    /**
     * Returns a {@link NavigableSet} view of the keys contained in this map.
     * The set's iterator returns the keys in ascending order.
     * The set is backed by the map, so changes to the map are reflected in
     * the set, and vice-versa.  If the map is modified while an iteration
     * over the set is in progress (except through the iterator's own {@code
     * remove} operation), the results of the iteration are undefined.  The
     * set supports element removal, which removes the corresponding mapping
     * from the map, via the {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear} operations.
     * It does not support the {@code add} or {@code addAll} operations.
     *
     * @return a navigable set view of the keys in this map
     */
    @Override
    public NavigableSet<K> navigableKeySet() {
        NavigableMap<K,V> ns=new TreeMap<K, V>();
        ns.putAll(this);
        return ns.navigableKeySet();
    }

    /**
     * Returns a reverse order {@link NavigableSet} view of the keys contained in this map.
     * The set's iterator returns the keys in descending order.
     * The set is backed by the map, so changes to the map are reflected in
     * the set, and vice-versa.  If the map is modified while an iteration
     * over the set is in progress (except through the iterator's own {@code
     * remove} operation), the results of the iteration are undefined.  The
     * set supports element removal, which removes the corresponding mapping
     * from the map, via the {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear} operations.
     * It does not support the {@code add} or {@code addAll} operations.
     *
     * @return a reverse order navigable set view of the keys in this map
     */
    @Override
    public NavigableSet<K> descendingKeySet() {
        NavigableMap<K,V> ns=new TreeMap<K, V>();
        ns.putAll(this);
        return ns.descendingKeySet();
    }

    /**
     * Returns a view of the portion of this map whose keys range from
     * {@code fromKey} to {@code toKey}.  If {@code fromKey} and
     * {@code toKey} are equal, the returned map is empty unless
     * {@code fromInclusive} and {@code toInclusive} are both true.  The
     * returned map is backed by this map, so changes in the returned map are
     * reflected in this map, and vice-versa.  The returned map supports all
     * optional map operations that this map supports.
     * 
     * The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside of its range, or to construct a
     * submap either of whose endpoints lie outside its range.
     *
     * @param fromKey       low endpoint of the keys in the returned map
     * @param fromInclusive {@code true} if the low endpoint
     *                      is to be included in the returned view
     * @param toKey         high endpoint of the keys in the returned map
     * @param toInclusive   {@code true} if the high endpoint
     *                      is to be included in the returned view
     * @return a view of the portion of this map whose keys range from
     * {@code fromKey} to {@code toKey}
     * @throws ClassCastException       if {@code fromKey} and {@code toKey}
     *                                  cannot be compared to one another using this map's comparator
     *                                  (or, if the map has no comparator, using natural ordering).
     *                                  Implementations may, but are not required to, throw this
     *                                  exception if {@code fromKey} or {@code toKey}
     *                                  cannot be compared to keys currently in the map.
     * @throws NullPointerException     if {@code fromKey} or {@code toKey}
     *                                  is null and this map does not permit null keys
     * @throws IllegalArgumentException if {@code fromKey} is greater than
     *                                  {@code toKey}; or if this map itself has a restricted
     *                                  range, and {@code fromKey} or {@code toKey} lies
     *                                  outside the bounds of the range
     */
    @Override
    public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        NavigableMap<K,V> ns= new TreeMap<>();
        ns.putAll(this);
        return ns.subMap(fromKey,fromInclusive,toKey,toInclusive);
    }

    /**
     * Returns a view of the portion of this map whose keys are less than (or
     * equal to, if {@code inclusive} is true) {@code toKey}.  The returned
     * map is backed by this map, so changes in the returned map are reflected
     * in this map, and vice-versa.  The returned map supports all optional
     * map operations that this map supports.
     * 
     * The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside its range.
     *
     * @param toKey     high endpoint of the keys in the returned map
     * @param inclusive {@code true} if the high endpoint
     *                  is to be included in the returned view
     * @return a view of the portion of this map whose keys are less than
     * (or equal to, if {@code inclusive} is true) {@code toKey}
     * @throws ClassCastException       if {@code toKey} is not compatible
     *                                  with this map's comparator (or, if the map has no comparator,
     *                                  if {@code toKey} does not implement {@link Comparable}).
     *                                  Implementations may, but are not required to, throw this
     *                                  exception if {@code toKey} cannot be compared to keys
     *                                  currently in the map.
     * @throws NullPointerException     if {@code toKey} is null
     *                                  and this map does not permit null keys
     * @throws IllegalArgumentException if this map itself has a
     *                                  restricted range, and {@code toKey} lies outside the
     *                                  bounds of the range
     */
    @Override
    public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
        NavigableMap<K,V> ns=new TreeMap<K, V>();
        ns.putAll(this);
        return ns.headMap(toKey,inclusive);
    }

    /**
     * Returns a view of the portion of this map whose keys are greater than (or
     * equal to, if {@code inclusive} is true) {@code fromKey}.  The returned
     * map is backed by this map, so changes in the returned map are reflected
     * in this map, and vice-versa.  The returned map supports all optional
     * map operations that this map supports.
     *
     * The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside its range.
     *
     * @param fromKey   low endpoint of the keys in the returned map
     * @param inclusive {@code true} if the low endpoint
     *                  is to be included in the returned view
     * @return a view of the portion of this map whose keys are greater than
     * (or equal to, if {@code inclusive} is true) {@code fromKey}
     * @throws ClassCastException       if {@code fromKey} is not compatible
     *                                  with this map's comparator (or, if the map has no comparator,
     *                                  if {@code fromKey} does not implement {@link Comparable}).
     *                                  Implementations may, but are not required to, throw this
     *                                  exception if {@code fromKey} cannot be compared to keys
     *                                  currently in the map.
     * @throws NullPointerException     if {@code fromKey} is null
     *                                  and this map does not permit null keys
     * @throws IllegalArgumentException if this map itself has a
     *                                  restricted range, and {@code fromKey} lies outside the
     *                                  bounds of the range
     */
    @Override
    public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
        NavigableMap<K,V> ns=new TreeMap<K, V>();
        ns.putAll(this);
        return ns.tailMap(fromKey,inclusive);
    }

    /**
     * Returns the comparator used to order the keys in this map, or
     * {@code null} if this map uses the {@linkplain Comparable
     * natural ordering} of its keys.
     *
     * @return the comparator used to order the keys in this map,
     * or {@code null} if this map uses the natural ordering
     * of its keys
     */
    @Override
    public Comparator<? super K> comparator() {
        return null;
    }

    /**
     * Equivalent to {@code subMap(fromKey, true, toKey, false)}.
     *
     * @param fromKey low endpoint of the keys in the returned map
     * @param toKey high endpoint of the keys in the returned map
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        NavigableMap<K,V> ns=new TreeMap<K, V>();
        ns.putAll(this);
        return ns.subMap(fromKey,toKey);
    }

    /**
     * Equivalent to {@code headMap(toKey, false)}.
     *
     * @param toKey high endpoint of the keys in the returned map
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public SortedMap<K, V> headMap(K toKey) {
        NavigableMap<K,V> ns=new TreeMap<K, V>();
        ns.putAll(this);
        return ns.headMap(toKey);
    }

    /**
     * {@inheritDoc}
     * 
     * Equivalent to {@code tailMap(fromKey, true)}.
     *
     * @param fromKey low endpoint of the keys in the returned map
     * @throws ClassCastException       {@inheritDoc}
     * @throws NullPointerException     {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        NavigableMap<K,V> ns=new TreeMap<K, V>();
        ns.putAll(this);
        return ns.tailMap(fromKey);
    }

    /**
     * Returns the first (lowest) key currently in this map.
     *
     * @return the first (lowest) key currently in this map
     * @throws NoSuchElementException if this map is empty
     */
    @Override
    public K firstKey() {
        return this.firstEntry().getKey();
    }

    /**
     * Returns the last (highest) key currently in this map.
     *
     * @return the last (highest) key currently in this map
     * @throws NoSuchElementException if this map is empty
     */
    @Override
    public K lastKey() {
        return lastEntry().getKey();
    }




    /**
     * Give an iterator traverse the tree according to Level order
     * @return levelOrderIterator
     */
    public Iterator Iterator() {
        return new myInOrIterator<>();
    }


    /**
     * InOrderTravers Iterator
     * Returns an iterator over elements of type {@code T}.
     * @return an Iterator.
     */
    private class myInOrIterator<K,V> implements Iterator {

        Stack<Node> nodes = new Stack<>();
        /**
         * Constructor
         */
        public myInOrIterator() {
            nodes.push(root);
        }
        /**
         * Override Method HasNext if there is a value
         *
         * @return boolean value
         */
        @Override
        public boolean hasNext() {
            if (root!=null)
                if (nodes.size() > 0)
                    return true;
            return false;
        }

        /**
         * Give Next item
         */
        @Override
        public Node<K,V> next() {
            return nodes.pop();
        }
    }


    public class InOrderIterator<K,V> implements Iterator {
        private Node next;
        public InOrderIterator(Node root){
            next = root;
            if(next == null)
                return;
            while (next.left != null)
                next = next.left;
        }

        public boolean hasNext(){
            return next != null;
        }

        public Node next(){
            if(!hasNext())
                throw new NoSuchElementException();
            Node temp = next;
            if(next.right != null){
                next = next.right;
                while (next.left != null)
                    next = next.left;
                return temp;
            }else
                while(true)
                {
                    if(next.parent == null){
                        next = null;
                    return temp;
                }
                if(next.parent.left == next){
                    next = next.parent;
                    return temp;
                }
                next = next.parent;
            }
        }
    }




    /**
     * Recursive delete method The item is not in the tree;
     * @param localRoot The root of the current subtree
     * @param item The item to be deleted
     * @return The modified local root that does not contain
     *         the item
     */
    private Node<K,V> delete(Node<K,V> localRoot, K item) {
        if (localRoot == null) {
            deleteReturn = null;
            return localRoot;
        }
        int compResult = item.compareTo(localRoot.getKey());
        if (compResult < 0) {
            localRoot.left = delete(localRoot.left, item);
            return localRoot;
        } else if (compResult > 0) {
            localRoot.right = delete(localRoot.right, item);
            return localRoot;
        } else {
            deleteReturn = localRoot;
            if (localRoot.left == null) {
                return localRoot.right;
            } else if (localRoot.right == null) {
                return localRoot.left;
            } else {
                if (localRoot.left.right == null) {
                    localRoot= localRoot.left;
                    return localRoot;
                } else {
                    localRoot = findLargestChild(localRoot.left);
                    return localRoot;
                }
            }
        }
    }



    /**
     * Find the node that is the
     * inorder predecessor and replace it
     * with its left child (if any).
     * @post The inorder predecessor is removed from the tree.
     * @param parent The parent of possible inorder
     *        predecessor (ip)
     * @return The data in the ip
     */
    private Node<K,V> findLargestChild(Node<K,V> parent) {
        // If the right child has no right child, it is
        // the inorder predecessor.
        if (parent.right.right == null) {
            Node<K,V> returnValue = parent.right;
            parent.right = parent.right.left;
            return returnValue;
        } else {
            return findLargestChild(parent.right);
        }
    }



    /**
     * Recursive find method.
     * @param localRoot Root
     * @param target The object being sought
     * @return The object, if found, otherwise null
     */
    private V find(Node<K,V> localRoot, K target) {
        if (localRoot == null) {
            return null;
        }
        int compResult = target.compareTo(localRoot.getKey());
        if (compResult == 0) {
            return localRoot.getValue();
        } else if (compResult < 0) {
            return find(localRoot.left, target);
        } else {
            return find(localRoot.right, target);
        }
    }


}


