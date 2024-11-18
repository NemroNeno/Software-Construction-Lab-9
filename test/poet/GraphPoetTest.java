/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy for poem():
    //      num bridge words - 0, 1, n
    //      multiple bridges weight - same, different
    //      case-sensitivity
    //      multiple lines
    //      edge cases like empty or simple input strings
    //      correct handling of input files
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // tests
    
    // covers multiple lines
    @Test
    public void testMultipleLines() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/poet/poem1.txt"));
        String poem = poet.poem("Seek to explore new and exciting synergies!");
        
        assertEquals("test multiple lines", poem, "Seek to explore strange new life and exciting synergies!");
    }
    
    // covers num bridge - 0
    @Test
    public void testNoBridge() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/poet/poem4.txt"));
        String poem = poet.poem("no bridge");
        
        assertEquals("expected no bridge words", poem, "no bridge");
    }
    
    // covers num bridge - 1
    @Test
    public void testOneBridge() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/poet/poem4.txt"));
        String poem = poet.poem("one bridge");
        
        assertEquals("expected one bridge word", poem, "one simple bridge");
    }
    
    // covers num bridge - n
    //        bridge - same weight
    //        case insensitive
    @Test
    public void testMultipleBridgesSameWeightCaseInsensitive() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/poet/poem5.txt"));
        String poem = poet.poem("MULTIPLE bridges");
        
        assertTrue("expected multiple bridge words with same weight", 
                   poem.equals("MULTIPLE same bridges") || poem.equals("MULTIPLE weight bridges"));
    }
    
    // covers multiple bridges - different weight
    @Test
    public void testMultipleBridgesDifferentWeight() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/poet/poem2.txt"));
        String poem = poet.poem("multiple bridges");
        
        assertEquals("expected multiple bridge words with different weight",
                     poem, "multiple same bridges");
    }
    
    // covers correct handling of input files
    @Test
    public void testCorrectFileHandling() throws IOException {
        try {
            new GraphPoet(new File("test/poet/nonexistent-file.txt"));
            fail("Expected IOException for nonexistent file");
        } catch (IOException e) {
            // expected exception
        }
        
        // Valid file case
        GraphPoet poet = new GraphPoet(new File("test/poet/poem1.txt"));
        String poem = poet.poem("test input");
        assertNotNull("Expected valid graph construction and poem generation", poem);
    }
    
    // covers correct generation of poems with and without bridge words

    
    // covers edge cases: empty or simple input strings
    @Test
    public void testEdgeCases() throws IOException {
        GraphPoet poet = new GraphPoet(new File("test/poet/poem6.txt"));

        // Empty input string
        String emptyPoem = poet.poem("");
        assertEquals("Expected empty output for empty input", "", emptyPoem);

//        // Single word input string
//        String singleWordPoem = poet.poem("hello");
//        assertEquals("Expected same word for single input", "hello", singleWordPoem);
//
//        // Input string with only spaces
//        String spacesPoem = poet.poem("     ");
//        assertEquals("Expected same spaces for space input", "     ", spacesPoem);
    }
}
