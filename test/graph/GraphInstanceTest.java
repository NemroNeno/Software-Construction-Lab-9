/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
	public abstract Graph<String> emptyInstance();



    // Test: A new graph should have no vertices
    // Test that a new graph has no vertices
    @Test
    public void testInitialVerticesEmpty() {
    	Graph <String> graph = emptyInstance();
        assertTrue(graph.vertices().isEmpty());
    }

    // Test adding vertices and check if they exist in the graph
    @Test
    public void testAddVertex() {
    	Graph <String> graph = emptyInstance();
        assertTrue(graph.add("A"));
        assertTrue(graph.vertices().contains("A"));
        assertTrue(graph.add("B"));
        assertTrue(graph.vertices().contains("B"));
    }

    // Test adding a duplicate vertex and check no duplication occurs
    @Test
    public void testAddDuplicateVertex() {
    	Graph <String> graph = emptyInstance();
        assertTrue(graph.add("A"));
        assertFalse(graph.add("A"));
        assertEquals(1, graph.vertices().size());
    }

    // Test adding edges and check if they exist with correct weights
    @Test
    public void testSetEdge() {
    	Graph <String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        assertEquals(0, graph.set("A", "B", 5));
        assertEquals(5, graph.set("A", "B", 5));

        Map<String, Integer> targets = graph.targets("A");
        assertEquals(1, targets.size());
        assertEquals(5, (int) targets.get("B"));

        assertEquals(5, graph.set("A", "B", 3));
        assertEquals(3, (int) graph.targets("A").get("B"));

        assertEquals(3, graph.set("A", "B", 0));
        assertFalse(graph.targets("A").containsKey("B"));
    }

    // Test removing a vertex and verifying it no longer exists
    @Test
    public void testRemoveVertex() {
    	Graph <String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        
        assertTrue(graph.remove("A"));
        assertFalse(graph.vertices().contains("A"));
        assertTrue(graph.vertices().contains("B"));
        assertTrue(graph.targets("A").isEmpty());

        assertFalse(graph.remove("A"));
    }

    // Test sources and targets for directed edges
    @Test
    public void testSourcesAndTargets() {
    	Graph <String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        
        graph.set("A", "B", 4);
        graph.set("C", "B", 7);

        Map<String, Integer> sources = graph.sources("B");
        assertEquals(2, sources.size());
        assertEquals(4, (int) sources.get("A"));
        assertEquals(7, (int) sources.get("C"));

        Map<String, Integer> targets = graph.targets("A");
        assertEquals(1, targets.size());
        assertEquals(4, (int) targets.get("B"));
    }
}
