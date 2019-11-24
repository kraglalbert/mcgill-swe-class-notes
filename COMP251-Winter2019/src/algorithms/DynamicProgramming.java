package algorithms;

import java.util.Arrays;

/**
 * Algorithms covered in lectures 5-7 and Assignment 2.
 */
public class DynamicProgramming {

    /**
     * The naive recursive implementation for calculating a binomial coefficient.
     *
     * @param n
     * @param k
     * @return n choose k
     */
    public static int binomSlow(int n, int k) {
        if (n == k || k == 0) return 1;
        else if (n < k) return 0;
        else {
            return binomSlow(n-1, k) + binomSlow(n-1, k-1);
        }
    }

    /**
     * The top-down DP implementation for calculating a binomial coefficient.
     *
     * @param n
     * @param k
     * @return n choose k
     */
    public static long binom(int n, int k) {
        long[][] dp = new long[n+1][k+1];

        // The helper method does all of the heavy lifting
        return binomHelper(n, k, dp);
    }

    /**
     * Helper method for the binomial method above.
     *
     * @param n
     * @param k
     * @param dp
     * @return n choose k
     */
    private static long binomHelper(int n, int k, long[][] dp) {
        if (n == k || k == 0) return 1;
        else if (n < k) return 0;
        else if (dp[n][k] != 0) return dp[n][k];
        else {
            dp[n][k] = binomHelper(n-1, k, dp) + binomHelper(n-1, k-1, dp);
            return dp[n][k];
        }
    }

    public static int cutRod() {
        return 0;
    }

    /**
     * Asked in Assignment 2, Question 2.
     *
     * @return
     */
    public static int coinChange() {
        return 0;
    }

    /**
     * Asked in Assignment 2, Question 3.
     *
     * @param nums
     * @return longest increasing subsequence (non-contiguous) in nums
     */
    public static int longestIncreasingSubsequence(int[] nums) {
        if (nums.length == 0) return 0;

        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);

        int longest = 1;

        for (int i = 0; i < nums.length; i++) {
            int curVal = nums[i];
            int curLongest = 1;

            for (int j = 0; j < i; j++) {
                if (curVal > nums[j]) {
                    curLongest = Math.max(curLongest, dp[j] + 1);
                }
            }

            dp[i] = curLongest;
            longest = Math.max(dp[i], longest);
        }

        return longest;
    }

    /**
     * Asked in Assignment 2, Question 4.
     *
     * @param s1
     * @param s2
     * @return minimum number of edits to change s1 into s2
     */
    public static int editDistance(String s1, String s2) {
        int rowLen = s1.length() + 1;
        int colLen = s2.length() + 1;

        int[][] dp = new int[rowLen][colLen];

        // Fill base cases of the DP array
        for (int i = 0; i < rowLen; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j < colLen; j++) {
            dp[0][j] = j;
        }

        // Fill the rest of the dp array
        for (int k = 1; k < rowLen; k++) {
            for (int n = 1; n < colLen; n++) {
                // If the characters are the same, the cost is 0 because no edit is needed
                int replaceValue = (s1.charAt(k-1) == s2.charAt(n-1)) ? 0 : 1;

                dp[k][n] = Math.min(replaceValue + dp[k-1][n-1], Math.min(1 + dp[k-1][n], 1 + dp[k][n-1]));
            }
        }

        return dp[rowLen-1][colLen-1];
    }
}
