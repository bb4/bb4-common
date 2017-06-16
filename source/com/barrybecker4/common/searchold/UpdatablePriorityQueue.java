package com.barrybecker4.common.searchold;

/**
 * @author Barry Becker
 */
public interface UpdatablePriorityQueue<S, T> {


    /** @return the node with the lowest priority */
    Node<S, T> pop();

    /**
     * Find the node with given state, and update its priority
     * If the node is not currently in the heap, it is added.
     * @param node node
     * @return true if the node was found and updated
     */
    boolean addOrUpdate(Node<S, T> node);

    boolean add(Node<S, T> node);

    int size();

    boolean isEmpty();
}
