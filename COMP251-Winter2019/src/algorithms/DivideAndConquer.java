package algorithms;

/**
 * Algorithms covered in lectures 1-4 and Assignment 1.
 */
public class DivideAndConquer {

    /**
     * Wrapper for the method below.
     *
     * @param arr
     * @return the sorted array
     */
    public static int[] mergesort(int[] arr) {
        return mergesort(arr, 0, arr.length-1);
    }

    /**
     * The classic Mergesort algorithm. Implemented for int arrays only
     * for the sake of simplicity.
     *
     * @param arr
     * @param left
     * @param right
     * @return the sorted array
     */
    private static int[] mergesort(int[] arr, int left, int right) {
        if (arr.length == 0) return arr;
        if (left < right) {
            int middle = (left + right) / 2;

            // Recursively divide the array in half
            int[] arr1 = mergesort(arr, left, middle);
            int[] arr2 = mergesort(arr, middle+1, right);

            return merge(arr1, arr2);
        } else {
            // Return an array with the one element in the base case
            return new int[]{ arr[left] };
        }
    }

    /**
     * Merge helper method for the Mergesort method above.
     *
     * @param arr1
     * @param arr2
     * @return the sorted, merged array
     */
    public static int[] merge(int[] arr1, int[] arr2) {
        int[] mergedArray = new int[arr1.length + arr2.length];

        int i = 0, j = 0, index = 0;

        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] < arr2[j]) {
                mergedArray[index] = arr1[i];
                i++;
            } else {
                mergedArray[index] = arr2[j];
                j++;
            }
            index++;
        }
        while (i < arr1.length) {
            mergedArray[index] = arr1[i];
            i++;
            index++;
        }
        while (j < arr2.length) {
            mergedArray[index] = arr2[j];
            j++;
            index++;
        }

        return mergedArray;
    }

    public static int gcd(int a, int b) {
        int remainder = a % b;

        if (remainder == 0) return b;
        else return gcd(b, remainder);
    }

    /**
     * Given a sorted array and a target value k, return the index of k in
     * the array or return -1 if it is not in the array.
     *
     * @param nums
     * @param k
     * @return index of k in nums
     */
    public static int binarySearch(int[] nums, int k) {
        int low = 0, high = nums.length-1;
        int mid = (high - low)/2 + low; // Avoid overflow

        while (low < high) {
            if (nums[mid] == k) {
                return mid;
            } else if (nums[mid] < k) {
                low = mid+1;
            } else {
                high = mid-1;
            }
            mid = (high - low)/2 + low;
        }

        return -1;
    }
}
