package edu.cmu.cs.cs214.rec02;

import org.junit.Before;
import org.junit.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static org.junit.Assert.*;


/**
 * TODO: 
 * 1. The {@link LinkedIntQueue} has no bugs. We've provided you with some example test cases.
 * Write your own unit tests to test against IntQueue interface with specification testing method 
 * using mQueue = new LinkedIntQueue();
 * 
 * 2. 
 * Comment `mQueue = new LinkedIntQueue();` and uncomment `mQueue = new ArrayIntQueue();`
 * Use your test cases from part 1 to test ArrayIntQueue and find bugs in the {@link ArrayIntQueue} class
 * Write more unit tests to test the implementation of ArrayIntQueue, with structural testing method
 * Aim to achieve 100% line coverage for ArrayIntQueue
 *
 * @author Alex Lockwood, George Guo, Terry Li
 */
public class IntQueueTest {
    private IntQueue mQueue;
    private List<Integer> testList;

    @Before
    public void setUp() {
        mQueue = new LinkedIntQueue(); 
        testList = new ArrayList<>(List.of(1, 2, 3));
    }

 
    @Test
    public void testNewQueueIsEmpty() {
        assertTrue("daraalal hooson baina", mQueue.isEmpty());
        assertEquals("daraalliin hemjee 0", 0, mQueue.size());
    }

    @Test
    public void testNotEmpty() {
        mQueue.enqueue(1);
        assertFalse("element nemsnii daraa daraalal hooson bish", mQueue.isEmpty());
    }

    // Peek Tests
    @Test
    public void testPeekEmptyQueue() {
        assertNull("hooson daraallyg null gej butsaah ystoi", mQueue.peek());
    }

    @Test
    public void testPeekNoEmptyQueue() {
        mQueue.enqueue(5);
        assertEquals("Peek ehnii elementiig butsaah yostoi", Integer.valueOf(5), mQueue.peek());
        assertEquals("Peek ni elementiig ustgah yosgui", 1, mQueue.size());
    }

    // daraalald oruulah Tests
    @Test
    public void testEnqueue() {
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testEnqueueMultipleElements() {
        for (int i = 0; i < 5; i++) {
            assertTrue(mQueue.enqueue(i));
            assertEquals("hemjee ni daraalal burt nemegdeh ystoi", i + 1, mQueue.size());
        }
        assertEquals("Ehnii element urd tald uldeh ystoi", Integer.valueOf(0), mQueue.peek());
    }

    // Dequeue Tests
    @Test
    public void testDequeue() {
        for (Integer value : testList) {
            mQueue.enqueue(value);
        }
        for (Integer expected : testList) {
            assertEquals("Elementuudiig FIFO daraallaar hasah heregtei", expected, mQueue.dequeue());
        }
        assertTrue("odoo daraalal hooson baih ystoi", mQueue.isEmpty());
    }

    // File Input Test
    @Test
    public void testContent() throws IOException {
        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");
            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                mQueue.enqueue(input);
            }
            for (Integer result : correctResult) {
                assertEquals(mQueue.dequeue(), result);
            }
        }
    }

    // Additional Edge Cases
    @Test
    public void testEnqueueDequeueAlternating() {
        for (int i = 0; i < 10; i++) {
            assertTrue(mQueue.enqueue(i));
            assertEquals("hemjee ni daraallyn daraa 1 baih yostoi", 1, mQueue.size());
            assertEquals("daraalald oruulsan elementiig daraalalgui bolgoh ystoi", Integer.valueOf(i), mQueue.dequeue());
            assertTrue("Daraalal duussany daraa daraalal hooson baih yostoi", mQueue.isEmpty());
        }
    }

    @Test
    public void testWraparound() {
        // daraalald element nemey
        for (int i = 0; i < 8; i++) {
            mQueue.enqueue(i);
        }
        
        // taliig ni ustrgay
        for (int i = 0; i < 4; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }
        
        // elementuud nemey
        for (int i = 8; i < 12; i++) {
            mQueue.enqueue(i);
        }
        
        // Buh elementuudiig shalgay
        for (int i = 4; i < 12; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }
    }
}