package com.barrybecker4.common.search;

import com.barrybecker4.common.search.slidingpuzzle.Board;
import com.barrybecker4.common.search.slidingpuzzle.Solver;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Barry Becker
 */
public class TreePriorityQueueTest extends UpdatablePriorityQueueTest {


    public UpdatablePriorityQueue<StubState, StubTransition> createQueue() {
        return new TreePriorityQueue<>();
    }

}
