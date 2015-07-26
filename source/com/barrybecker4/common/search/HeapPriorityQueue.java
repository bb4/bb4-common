package com.barrybecker4.common.search;

import java.util.*;

/**
 * A priority queue implementation based on a binary heap.
 * It is pretty much the same as PriorityQueue provided by java
 * with two modifications
 *  - It allows updating the priority of nodes (needed by A*)
 *  - Does not implement Queue interface since that has more methods than needed
 * @author Josh Bloch, Doug Lea, modified by Barry Becker as indicated above.
 */
public class HeapPriorityQueue<S, T> implements UpdatablePriorityQueue<S, T> {

    private static final int DEFAULT_INITIAL_CAPACITY = 256;

    /**
     * Priority queue represented as a balanced binary heap: the two
     * children of queue[n] are queue[2*n+1] and queue[2*(n+1)].  The
     * priority queue is ordered by comparator, or by the elements'
     * natural ordering, if comparator is null: For each node n in the
     * heap and each descendant d of n, n <= d.  The element with the
     * lowest value is in queue[0], assuming the queue is nonempty.
     */
    private transient Object[] queue;

    /** allows for quick lookup of a nodes position in the heap. Required for updating priority of nodes */
    private Map<Node<S, T>, Integer> indexMap;

    /**
     * The number of elements in the priority queue.
     */
    private int size = 0;

    /**
     * The comparator, or null if priority queue uses elements'
     * natural ordering.
     */
    private final Comparator<? super Node<S, T>> comparator;

    /**
     * Creates a {@code PriorityQueue} with the default initial
     * capacity (11) that orders its elements according to their
     * {@linkplain Comparable natural ordering}.
     */
    public HeapPriorityQueue() {
        this(DEFAULT_INITIAL_CAPACITY, null);
    }

    /**
     * Creates a {@code PriorityQueue} with the specified initial
     * capacity that orders its elements according to their
     * {@linkplain Comparable natural ordering}.
     *
     * @param initialCapacity the initial capacity for this priority queue
     * @throws IllegalArgumentException if {@code initialCapacity} is less
     *         than 1
     */
    public HeapPriorityQueue(int initialCapacity) {
        this(initialCapacity, null);
    }

    /**
     * Creates a {@code PriorityQueue} with the specified initial capacity
     * that orders its elements according to the specified comparator.
     *
     * @param  initialCapacity the initial capacity for this priority queue
     * @param  comparator the comparator that will be used to order this
     *         priority queue.  If {@code null}, the {@linkplain Comparable
     *         natural ordering} of the elements will be used.
     * @throws IllegalArgumentException if {@code initialCapacity} is
     *         less than 1
     */
    public HeapPriorityQueue(int initialCapacity,
                             Comparator<? super Node<S, T>> comparator) {
        // Note: This restriction of at least one is not actually needed,
        // but continues for 1.5 compatibility
        if (initialCapacity < 1)
            throw new IllegalArgumentException();
        this.queue = new Object[initialCapacity];
        this.indexMap = new HashMap<>(DEFAULT_INITIAL_CAPACITY);
        this.comparator = comparator;
    }

    /**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * Increases the capacity of the array.
     * @param minCapacity the desired minimum capacity
     */
    private void grow(int minCapacity) {
        int oldCapacity = queue.length;
        // Double size if small; else grow by 50%
        int newCapacity = oldCapacity + ((oldCapacity < 4096) ?
                (oldCapacity + 2) :
                (oldCapacity >> 1));
        // overflow-conscious code
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        queue = Arrays.copyOf(queue, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    @Override
    public Node<S, T> pop() {
        Node<S, T> minNode = this.peek();
        this.removeAt(0);
        return minNode;
    }

    @Override
    public boolean addOrUpdate(Node<S, T> node) {
        if (indexMap.containsKey(node)) {
            int index = indexMap.get(node);
            this.siftUp(index, node);
            return true;
        }
        else {
            add(node);
            return false;
        }
    }

    /**
     * Removes the ith element from queue.
     * First remove the last put where the removed one was.
     * Then shift it up.
     */
    private void removeAt(int i) {
        assert i >= 0 && i < size;
        int s = --size;
        Node<S, T> removed = (Node<S, T>) queue[i];
        queue[i] = queue[s];
        queue[s] = null;
        if (s != i) { // removing other than last
            Node<S, T> node = (Node<S, T>) queue[i];
            siftDown(i, node);
        }
        indexMap.remove(removed);
    }

    /**
     * Inserts the specified element into this priority queue.
     *
     * @return {@code true} (as specified by {@link Collection#add})
     * @throws ClassCastException if the specified element cannot be
     *         compared with elements currently in this priority queue
     *         according to the priority queue's ordering
     * @throws NullPointerException if the specified element is null
     */
    public boolean add(Node<S, T> e) {
        return offer(e);
    }

    /**
     * Inserts the specified element into this priority queue.
     *
     * @return {@code true} if the specified node is added
     * @throws ClassCastException if the specified element cannot be
     *         compared with elements currently in this priority queue
     *         according to the priority queue's ordering
     * @throws NullPointerException if the specified element is null
     */
    public boolean offer(Node<S, T> node) {
        if (node == null)
            throw new NullPointerException();
        int i = size;
        if (i >= queue.length)
            grow(i + 1);
        size = i + 1;
        queue[i] = node;
        indexMap.put(node, i);
        if (i != 0) {
            siftUp(i, node);
        }
        return true;
    }

    public Node<S, T> peek() {
        if (size == 0) return null;
        return (Node<S, T>) queue[0];
    }

    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes all of the elements from this priority queue.
     * The queue will be empty after this call returns.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            indexMap.remove(queue[i]);
            queue[i] = null;
        }
        size = 0;
    }

    /**
     * Inserts item x at position k, maintaining heap invariant by
     * promoting x up the tree until it is greater than or equal to
     * its parent, or is the root.
     *
     * To simplify and speed up coercions and comparisons. the
     * Comparable and Comparator versions are separated into different
     * methods that are otherwise identical. (Similarly for siftDown.)
     *
     * @param k the position to fill
     * @param x the item to insert
     */
    private void siftUp(int k, Node<S, T> x) {
        if (comparator != null)
            siftUpUsingComparator(k, x);
        else
            siftUpComparable(k, x);
    }

    private void siftUpComparable(int k, Node<S, T> key) {
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            Object element = queue[parent];
            if (key.compareTo((Node<S, T>) element) >= 0)
                break;
            queue[k] = element;
            indexMap.put((Node<S, T>) element, k);
            k = parent;
        }
        queue[k] = key;
        indexMap.put(key, k);
    }

    private void siftUpUsingComparator(int k, Node<S, T> x) {
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            Object e = queue[parent];
            if (comparator.compare(x, (Node<S, T>) e) >= 0)
                break;
            queue[k] = e;
            indexMap.put((Node<S, T>) e, k);
            k = parent;
        }
        queue[k] = x;
        indexMap.put(x, k);
    }

    /**
     * Inserts item x at position k, maintaining heap invariant by
     * demoting x down the tree repeatedly until it is less than or
     * equal to its children or is a leaf.
     *
     * @param k the position to fill
     * @param x the item to insert
     */
    private void siftDown(int k, Node<S, T> x) {
        if (comparator != null)
            siftDownUsingComparator(k, x);
        else
            siftDownComparable(k, x);
    }

    private void siftDownComparable(int k, Node<S, T> key) {
        int half = size >>> 1;        // loop while a non-leaf
        while (k < half) {
            int child = (k << 1) + 1; // assume left child is least
            Object c = queue[child];
            int right = child + 1;
            if (right < size &&
                    ((Comparable<? super Node<S, T>>) c).compareTo((Node<S, T>) queue[right]) > 0)
                c = queue[child = right];
            if (key.compareTo((Node<S, T>) c) <= 0)
                break;
            queue[k] = c;
            indexMap.put((Node<S, T>) c, k);
            k = child;
        }
        queue[k] = key;
        indexMap.put(key, k);
    }

    private void siftDownUsingComparator(int k, Node<S, T> x) {
        int half = size >>> 1;
        while (k < half) {
            int child = (k << 1) + 1;
            Object c = queue[child];
            int right = child + 1;
            if (right < size &&
                    comparator.compare((Node<S, T>) c, (Node<S, T>) queue[right]) > 0)
                c = queue[child = right];
            if (comparator.compare(x, (Node<S, T>) c) <= 0)
                break;
            queue[k] = c;
            indexMap.put((Node<S, T>) c, k);
            k = child;
        }
        queue[k] = x;
        indexMap.put(x, k);
    }

    /**
     * Returns the comparator used to order the elements in this
     * queue, or {@code null} if this queue is sorted according to
     * the {@linkplain Comparable natural ordering} of its elements.
     *
     * @return the comparator used to order this queue, or
     *         {@code null} if this queue is sorted according to the
     *         natural ordering of its elements
     */
    public Comparator<? super Node<S, T>> comparator() {
        return comparator;
    }
}
