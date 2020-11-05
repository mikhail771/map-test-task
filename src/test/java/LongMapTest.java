import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LongMapTest {
    private static final String TEST_STRING_ONE = "First test string";
    private static final String TEST_STRING_TWO = "Second test string";
    private static final String TEST_STRING_THREE = "Third test string";
    private static final String NOT_EXISTED_VALUE_IN_MAP = "Not existed value";

    LongMap<String> map;

    @Before
    public void initialize() {
        map = new LongMapImpl();
    }

    @Test
    public void putAndGetOk() {
        fillMap();
        Assert.assertEquals("Test failed! Expected result is " + TEST_STRING_ONE
                + " but actual is " + map.get(1), TEST_STRING_ONE, map.get(1));
        Assert.assertEquals("Test failed! Expected result is " + TEST_STRING_TWO
                + " but actual is " + map.get(2), TEST_STRING_TWO, map.get(2));
        Assert.assertEquals("Test failed! Expected result is " + TEST_STRING_THREE
                + " but actual is " + map.get(3), TEST_STRING_THREE, map.get(3));
    }

    @Test
    public void rewriteValuesOk() {
        fillMap(100);
        fillMap();
        Assert.assertEquals("Test failed! Expected result is " + TEST_STRING_ONE
                + " but actual is " + map.get(1), TEST_STRING_ONE, map.get(1));
        Assert.assertEquals("Test failed! Expected result is " + TEST_STRING_TWO
                + " but actual is " + map.get(2), TEST_STRING_TWO, map.get(2));
        Assert.assertEquals("Test failed! Expected result is " + TEST_STRING_THREE
                + " but actual is " + map.get(3), TEST_STRING_THREE, map.get(3));
    }

    @Test
    public void getByNonExistedKey() {
        Assert.assertNull("Test failed! If element with such key" +
                "doesn't exist method get() should return null.", map.get(99));
    }

    @Test
    public void removeByExistedKey() {
        map.put(1, TEST_STRING_ONE);
        String expected = TEST_STRING_ONE;
        String actual = map.remove(1);
        Assert.assertEquals("Test failed! Expected result on delete is "
            + expected + " but actual is " + actual, expected, actual);
        Assert.assertNull(map.get(1));
    }

    @Test
    public void removeByNotExistedKey() {
        Assert.assertNull("Test failed! Removing by "
                + "not existed key must return null, but returned "
                + map.remove(1), map.remove(1));
    }

    @Test
    public void checkSizeAfterAdding() {
        long count = 10;
        Assert.assertEquals(0, map.size());
        fillMap(count);
        long expected = count;
        long actual = map.size();
        Assert.assertEquals("Test failed! After adding expected size of map "
                + "must be " + expected + " but was " + actual, expected, actual);
    }

    @Test
    public void checkSizeAfterRemoving() {
        fillMap(10);
        map.remove(2);
        long expected = 9;
        long actual = map.size();
        Assert.assertEquals("Test failed! After removing expected size of map "
                + "must be " + expected + " but was " + actual, expected, actual);
    }

    @Test
    public void isMapEmpty() {
        Assert.assertTrue("Test failed! The new map must be empty!",
                map.isEmpty());
    }

    @Test
    public void isMapNotEmpty() {
        fillMap();
        Assert.assertFalse("Test failed! Map with elements couldn't be empty",
                map.isEmpty());
    }

    @Test
    public void isMapEmptyAfterClear() {
        fillMap();
        map.clear();
        Assert.assertTrue("Test failed! Map must be empty "
                + "after calling clear() method!", map.isEmpty());
    }

    @Test
    public void checkSizeAfterClear() {
        fillMap();
        map.clear();
        long expected = 0;
        long actual = map.size();
        Assert.assertEquals("Test failed! Size of map must be 0 "
                + "after clear() method "
                + "but was " + actual, expected, actual);
    }

    @Test
    public void isKeyContainsInMapTrue() {
        fillMap();
        Assert.assertTrue("Test failed! If key contains in map, method "
                + "containsKey() must return true!", map.containsKey(1));
    }

    @Test
    public void isKeyContainsInMapFalse() {
        fillMap();
        Assert.assertFalse("Test failed! If key doesn't contains in map, method "
                + "containsKey() must return false!", map.containsKey(4));
    }

    @Test
    public void isValueContainsInMapTrue() {
        fillMap();
        Assert.assertTrue("Test failed! If value contains in map, method "
                + "containsValue() must return true!", map.containsValue(TEST_STRING_ONE));
    }

    @Test
    public void isValueContainsInMapFalse() {
        fillMap();
        Assert.assertFalse("Test failed! If value doesn't contains in map, method "
                + "containsValue() must return false!", map.containsValue(NOT_EXISTED_VALUE_IN_MAP));
    }

    @Test
    public void getAllKeysInMap() {
        fillMap(10);
        long[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        long[] actual = map.keys();
        Assert.assertArrayEquals("Test failed! The actual array of keys "
                + "does not match the expected", expected, actual);
    }

    @Test
    public void getAllValuesInMap() {
        fillMap();
        Object[] expected = {
                TEST_STRING_ONE,
                TEST_STRING_TWO,
                TEST_STRING_THREE
        };
        Object[] actual = map.values();
        Assert.assertArrayEquals("Test failed! The actual array of values "
                + "does not match the expected", expected, actual);
    }

    @After
    public void clearData() {
        map = null;
    }

    private void fillMap() {
        map.put(1, TEST_STRING_ONE);
        map.put(2, TEST_STRING_TWO);
        map.put(3, TEST_STRING_THREE);
    }

    private void fillMap(long count) {
        for (int i = 0; i < count; i++) {
            map.put(i, "Text" + i);
        }
    }
}
