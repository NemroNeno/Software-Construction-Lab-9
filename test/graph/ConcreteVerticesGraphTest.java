package graph;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.Set;

public class ConcreteVerticesGraphTest {

    @Test
    public void testAddVertex() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        assertTrue(graph.add("A"));
        assertTrue(graph.add("B"));
        assertFalse(graph.add("A"));
    }

    @Test
    public void testSetEdge() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        
        assertEquals(0, graph.set("A", "B", 5));
        assertEquals(5, (int) graph.targets("A").get("B"));
        assertEquals(0, (int) graph.targets("B").getOrDefault("A", 0));
        
        assertEquals(5, graph.set("A", "B", 10));
        assertEquals(10, (int) graph.targets("A").get("B"));
    }

    @Test
    public void testRemoveVertex() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);

        assertTrue(graph.remove("A"));
        assertFalse(graph.vertices().contains("A"));
    }

    @Test
    public void testSourcesTargets() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.set("A", "B", 4);
        graph.set("B", "C", 6);
        
        assertEquals(Map.of("A", 4), graph.sources("B"));
        assertEquals(Map.of("B", 6), graph.sources("C"));
    }

    @Test
    public void testToString() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.set("A", "B", 4);
        assertTrue(graph.toString().contains("A --(4)--> B"));
    }
}
