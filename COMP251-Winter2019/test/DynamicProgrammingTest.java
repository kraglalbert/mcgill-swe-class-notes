import org.junit.Assert;
import org.junit.Test;

public class DynamicProgrammingTest {

    @Test
    public void binomSlowTest() {
        Assert.assertEquals(15, algorithms.DynamicProgramming.binomSlow(6,4));
        Assert.assertEquals(126, algorithms.DynamicProgramming.binomSlow(9,4));
    }

    @Test
    public void binomTest() {
        Assert.assertEquals(0, algorithms.DynamicProgramming.binom(3,4));
        Assert.assertEquals(1, algorithms.DynamicProgramming.binom(11,11));
        Assert.assertEquals(21, algorithms.DynamicProgramming.binom(21,1));
        Assert.assertEquals(21, algorithms.DynamicProgramming.binom(21,20));
        Assert.assertEquals(15, algorithms.DynamicProgramming.binom(6,4));
        Assert.assertEquals(126, algorithms.DynamicProgramming.binom(9,4));
        Assert.assertEquals(14307150, algorithms.DynamicProgramming.binom(30,9));
    }

    @Test
    public void longestIncreasingSubsequenceTest() {
        Assert.assertEquals(0, algorithms.DynamicProgramming.longestIncreasingSubsequence(new int[]{}));
        Assert.assertEquals(4, algorithms.DynamicProgramming.longestIncreasingSubsequence(new int[]{10,9,2,5,3,7,101,18}));
        Assert.assertEquals(8, algorithms.DynamicProgramming.longestIncreasingSubsequence(new int[]{3,2,5,1,6,8,13,9,10,11,15}));
        Assert.assertEquals(6, algorithms.DynamicProgramming.longestIncreasingSubsequence(new int[]{100,123,13,98,67,58,94,130,420,96,11,2,42,341,555,312,324,321,302}));
        Assert.assertEquals(2, algorithms.DynamicProgramming.longestIncreasingSubsequence(new int[]{1,1,1,1,1,1,1,1,1,2}));
        Assert.assertEquals(1, algorithms.DynamicProgramming.longestIncreasingSubsequence(new int[]{2,1,1,1,1,1,1,1,1,1}));
    }

    @Test
    public void editDistanceTest() {
        Assert.assertEquals(0, algorithms.DynamicProgramming.editDistance("",""));
        Assert.assertEquals(4, algorithms.DynamicProgramming.editDistance("","word"));
        Assert.assertEquals(3, algorithms.DynamicProgramming.editDistance("horse","ros"));
        Assert.assertEquals(5, algorithms.DynamicProgramming.editDistance("fundamental","elemental"));
        Assert.assertEquals(1, algorithms.DynamicProgramming.editDistance("nice","rice"));
        Assert.assertEquals(5, algorithms.DynamicProgramming.editDistance("earth","saturn"));
        Assert.assertEquals(16, algorithms.DynamicProgramming.editDistance("pizzadeliveryperson","bellhop"));
        Assert.assertEquals(9, algorithms.DynamicProgramming.editDistance("autocorrect","spellcheck"));
        Assert.assertEquals(4, algorithms.DynamicProgramming.editDistance("hi","hello"));
        Assert.assertEquals(4, algorithms.DynamicProgramming.editDistance("hello","world"));
    }
}
