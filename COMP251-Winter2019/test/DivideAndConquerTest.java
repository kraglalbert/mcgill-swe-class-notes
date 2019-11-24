import org.junit.Assert;
import org.junit.Test;

public class DivideAndConquerTest {

    @Test
    public void mergeTest() {
        Assert.assertArrayEquals(new int[]{1,2,3,4}, algorithms.DivideAndConquer.merge(new int[]{2,3}, new int[]{1,4}));
        Assert.assertArrayEquals(new int[]{1,2,3,4}, algorithms.DivideAndConquer.merge(new int[]{3}, new int[]{1,2,4}));
        Assert.assertArrayEquals(new int[]{-1,0,3,5,7,8,10}, algorithms.DivideAndConquer.merge(new int[]{-1,0,10}, new int[]{3,5,7,8}));
    }

    @Test
    public void mergesortTest() {
        Assert.assertArrayEquals(new int[]{}, algorithms.DivideAndConquer.mergesort(new int[]{}));
        Assert.assertArrayEquals(new int[]{1,2,3,4,5}, algorithms.DivideAndConquer.mergesort(new int[]{5,4,3,2,1}));
        Assert.assertArrayEquals(new int[]{-1,0,1,3,4,5,8,10}, algorithms.DivideAndConquer.mergesort(new int[]{1,4,3,5,10,-1,0,8}));
        Assert.assertArrayEquals(new int[]{-50,-45,-13,-10,-2,-1,0}, algorithms.DivideAndConquer.mergesort(new int[]{-10,-13,-1,-2,-50,-45,0}));
        Assert.assertArrayEquals(new int[]{-225,-10,-2,0,0,0,1,10,22,37,47,80,141}, algorithms.DivideAndConquer.mergesort(new int[]{10,80,22,47,37,1,0,-10,-225,-2,141,0,0}));
        Assert.assertArrayEquals(new int[]{0,1,1,2,2,2}, algorithms.DivideAndConquer.mergesort(new int[]{2,2,2,1,1,0}));
    }

    @Test
    public void gcdTest() {
        Assert.assertEquals(15, algorithms.DivideAndConquer.gcd(240, 45));
        Assert.assertEquals(4, algorithms.DivideAndConquer.gcd(164, 144));
        Assert.assertEquals(2, algorithms.DivideAndConquer.gcd(1024, 326));
        Assert.assertEquals(16, algorithms.DivideAndConquer.gcd(1024, 560));
        Assert.assertEquals(111, algorithms.DivideAndConquer.gcd(333, 222));
        Assert.assertEquals(1, algorithms.DivideAndConquer.gcd(333, 124));
        Assert.assertEquals(100, algorithms.DivideAndConquer.gcd(100, 100));
        Assert.assertEquals(3, algorithms.DivideAndConquer.gcd(321, 123));
    }

    @Test
    public void binarySearchTest() {
        Assert.assertEquals(-1, algorithms.DivideAndConquer.binarySearch(new int[]{},3));
        Assert.assertEquals(3, algorithms.DivideAndConquer.binarySearch(new int[]{1,2,3,4,5},4));
        Assert.assertEquals(-1, algorithms.DivideAndConquer.binarySearch(new int[]{0,10,20,30,40,50,60,70,80,90,100},99));
        Assert.assertEquals(0, algorithms.DivideAndConquer.binarySearch(new int[]{0,10,20,30,40,50,60,70,80,90,100},0));
        Assert.assertEquals(2, algorithms.DivideAndConquer.binarySearch(new int[]{-14,-3,-1,4,9,11,23,47,92},-1));
        Assert.assertEquals(13, algorithms.DivideAndConquer.binarySearch(new int[]{-123,-100,34,45,124,564,599,601,602,669,699,704,798,1023,1139},1023));
    }
}
