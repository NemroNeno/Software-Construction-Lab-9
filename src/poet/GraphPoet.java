package poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>This class is initialized with a text corpus to derive a word affinity graph. 
 * The graph captures word connections based on their adjacency in the text.
 * 
 * Vertices represent words, which are case-insensitive non-space strings.
 * Edges indicate how frequently one word follows another, with weights reflecting the count of such occurrences.
 * 
 * Given an input string, the class generates a poem by inserting bridge words between adjacent pairs of words.
 * A bridge word is inserted if it forms the highest-weight two-edge path between the words.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction Function:
    //   AF(graph) = Represents a poetry generator using word relationships.
    //
    // Representation Invariant:
    //   - Graph vertices are non-empty, case-insensitive strings with no spaces or newlines.
    //
    // Safety from Representation Exposure:
    //   - The `graph` field is private and final.

    // Ensures that the representation invariant holds
    private void checkRep() {
        for (String vertex : graph.vertices()) {
            String cleaned = vertex.toLowerCase().trim().replaceAll("\\s+", "");
            
            assert vertex.equals(cleaned); // Ensures no spaces or newlines
            assert !vertex.isEmpty();      // Ensures non-empty strings
        }
    }

    /**
     * Constructs a GraphPoet using a given corpus file.
     *
     * @param corpus the file containing text to derive the word affinity graph
     * @throws IOException if the file cannot be read
     */
    public GraphPoet(File corpus) throws IOException {
        List<String> words = new ArrayList<>();
        
        // Reading the file and extracting words
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(corpus)))) {
            while (scanner.hasNext()) {
                words.add(scanner.next().toLowerCase()); // Convert words to lowercase
            }
        }

        // Add words as vertices to the graph
        for (String word : words) {
            if (!graph.vertices().contains(word)) {
                graph.add(word);
            }
        }

        // Establish edges based on word adjacencies in the text
        for (int i = 0; i < words.size() - 1; i++) {
            String word1 = words.get(i);
            String word2 = words.get(i + 1);
            
            Map<String, Integer> targets = graph.targets(word1);
            
            // Increment edge weight if it exists, otherwise create a new edge
            graph.set(word1, word2, targets.getOrDefault(word2, 0) + 1);
        }
        checkRep();
    }

    /**
     * Generates a poem by inserting bridge words between adjacent words in the input.
     *
     * @param input the input string
     * @return a new poem based on the input
     */
    public String poem(String input) {
        List<String> words = Arrays.asList(input.trim().split("\\s+"));
        List<String> poemWords = new ArrayList<>();

        for (int i = 0; i < words.size() - 1; i++) {
            String src = words.get(i).toLowerCase();
            String trg = words.get(i + 1).toLowerCase();
            
            // Attempt to find a bridge word
            String bridge = findBridgeWord(src, trg);
            
            // Add the current word and the bridge word (if any) to the poem
            poemWords.add(words.get(i));
            if (!bridge.isEmpty()) {
                poemWords.add(bridge);
            }
        }

        // Add the last word from the input
        poemWords.add(words.get(words.size() - 1));
        checkRep();
        return String.join(" ", poemWords);
    }

    /**
     * Finds the best bridge word between two words.
     * 
     * @param word1 the source word
     * @param word2 the target word
     * @return the bridge word with the highest weight, or an empty string if none found
     */
    private String findBridgeWord(String word1, String word2) {
        Map<String, Integer> srcTargets = graph.targets(word1);
        Map<String, Integer> trgSources = graph.sources(word2);
        
        Set<String> commonWords = new HashSet<>(srcTargets.keySet());
        commonWords.retainAll(trgSources.keySet());

        if (commonWords.isEmpty()) {
            return "";
        }

        // Find the bridge word with the highest combined weight
        String bestBridge = "";
        int maxWeight = 0;
        for (String bridge : commonWords) {
            int weight = srcTargets.get(bridge) + trgSources.get(bridge);
            if (weight > maxWeight) {
                maxWeight = weight;
                bestBridge = bridge;
            }
        }
        return bestBridge;
    }

    @Override
    public String toString() {
        return graph.toString();
    }
}
