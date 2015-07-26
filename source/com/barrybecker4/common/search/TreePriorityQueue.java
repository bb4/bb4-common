package com.barrybecker4.common.search;

import java.util.*;

/**
 * Use a priority queue implementation that employs a TreeMap under the covers to
 * allow for updating priorities. The update is done by removing the node, updating the priority
 * then re-adding the node. The open hashmap is used to find a node quickly.
 * @author Barry Becker
 */
public class TreePriorityQueue<S, T> implements UpdatablePriorityQueue<S, T>  {

    private TreeSet<Node<S, T>> pq;
    private HashMap<S, Node<S, T>> open;

    public TreePriorityQueue() {
        pq = new TreeSet<>();
        open = new HashMap<>();
    }

    /** @return the node with the lowest priority */
    public Node<S, T> pop() {
        Node<S, T> n = pq.first();
        this.remove(n);
        return n;
    }

    /**
     * Find the node with given state, and update its priority
     * @param node node
     * @return true if the node was found and updated
     */
    public boolean addOrUpdate(Node<S, T> node) {
        boolean updated = false;
        if (open.containsKey(node.getState())) {
            Node<S, T> current = open.get(node.getState());
            if (node.getEstimatedFutureCost() < current.getEstimatedFutureCost()) {
                this.remove(current);
                updated = true;
            }
        }
        this.add(node);
        return updated;
    }

    public boolean add(Node<S, T> node) {
        open.put(node.getState(), node);
        return pq.add(node);
    }

    private boolean remove(Node<S, T> node) {
        open.remove(node.getState());
        return pq.remove(node);
    }

    public int size() {
        return pq.size();
    }

    public boolean isEmpty() {
        return pq.isEmpty();
    }
}
