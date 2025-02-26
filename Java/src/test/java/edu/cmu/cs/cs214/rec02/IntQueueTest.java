package edu.cmu.cs.cs214.rec02;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class IntQueueTest {
    private IntQueue mQueue;
    private List<Integer> testList;

    @Before
    public void setUp() {
        mQueue = new ArrayIntQueue();
        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    public void testNewQueueIsEmpty() {
        assertTrue("shine jagsaalt hooson baigaa", mQueue.isEmpty());
        assertEquals("urt ni 0", 0, mQueue.size());
    }

    @Test
    public void testNotEmpty() {
        mQueue.enqueue(1);
        assertFalse("hoooson bish", mQueue.isEmpty());
        // assertTrue("hoooson bish", mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        assertNull("null butsaana", mQueue.peek());
    }

    @Test
    public void testPeekNoEmptyQueue() {
        mQueue.enqueue(5);
        assertEquals("ehnii element", Integer.valueOf(5), mQueue.peek());
        assertEquals("Peek ni elyemyentiig arilgah yosgui", 1, mQueue.size());
    }

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
            assertEquals("element nemeh burt size ni usnu", i + 1, mQueue.size());
        }
        assertEquals("ehnii element urdaa uldene", Integer.valueOf(0), mQueue.peek());
    }

    @Test
    public void testDequeue() {
        for (Integer value : testList) {
            mQueue.enqueue(value);
        }
        for (Integer expected : testList) {
            assertEquals("FIFO daraalalaar hasagdan", expected, mQueue.dequeue());
        }
        assertTrue("daraalal hoosno bolno", mQueue.isEmpty());
    }

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

    @Test
    public void testEnqueueDequeueAlternating() {
        for (int i = 0; i < 10; i++) {
            assertTrue(mQueue.enqueue(i));
            assertEquals("size 1 bolno", 1, mQueue.size());
            assertEquals("daraalliin element daraalald orson elementtai tohiroh ystoi", Integer.valueOf(i),
                    mQueue.dequeue());
            assertTrue("hassanii daraa hooson bolno", mQueue.isEmpty());
        }
    }

    @Test
    public void testWraparound() {
        for (int i = 0; i < 8; i++) {
            mQueue.enqueue(i);
        }

        for (int i = 0; i < 4; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }

        for (int i = 8; i < 12; i++) {
            mQueue.enqueue(i);
        }

        for (int i = 4; i < 12; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }
    }

    @Test
    public void testClearFullQueue() {
        for (int i = 0; i < 10; i++) {
            mQueue.enqueue(i);
        }
        assertEquals(10, mQueue.size());

        mQueue.clear();
        assertTrue(mQueue.isEmpty());
        assertEquals(0, mQueue.size());
        assertNull(mQueue.peek());
    }

    @Test
    public void testResizeWithWraparound() {
        for (int i = 0; i < 10; i++) {
            mQueue.enqueue(i);
        }

        for (int i = 0; i < 5; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }

        for (int i = 10; i < 15; i++) {
            mQueue.enqueue(i);
        }

        for (int i = 5; i < 15; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }
    }

    @Test
    public void testFullQueueOperations() {
        for (int i = 0; i < 10; i++) {
            assertTrue(mQueue.enqueue(i));
        }

        assertTrue(mQueue.enqueue(10));
        assertEquals(11, mQueue.size());

        for (int i = 0; i < 11; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }
    }

    @Test
    public void testEdgeCases() {
        assertNull(mQueue.peek());
        assertNull(mQueue.dequeue());
        assertTrue(mQueue.isEmpty());

        assertTrue(mQueue.enqueue(Integer.MAX_VALUE));
        assertTrue(mQueue.enqueue(Integer.MIN_VALUE));
        assertEquals(Integer.valueOf(Integer.MAX_VALUE), mQueue.dequeue());
        assertEquals(Integer.valueOf(Integer.MIN_VALUE), mQueue.dequeue());
    }

    @Test
    public void testDequeueUntilEmpty() {
        for (int i = 0; i < 5; i++) {
            mQueue.enqueue(i);
        }

        for (int i = 0; i < 5; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }

        assertNull(mQueue.dequeue());
        assertTrue(mQueue.isEmpty());
        assertEquals(0, mQueue.size());
    }

    @Test
    public void testMultipleResizes() {
        for (int i = 0; i < 25; i++) {
            assertTrue(mQueue.enqueue(i));
        }

        assertEquals(25, mQueue.size());

        for (int i = 0; i < 25; i++) {
            assertEquals(Integer.valueOf(i), mQueue.dequeue());
        }
    }
}