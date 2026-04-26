/**
 * Brute force solution for finding the longest subarray with sum equal to k.
 * @author Coffee Coders
 */
public class LongestSubarrayBruteForce {
    /**
     * Finds the length of the longest subarray with sum equal to k using brute force.
     *
     * @param nums the input array of integers
     * @param k the target sum
     * @return the length of the longest subarray with sum k
     */
    public int longestSubarrayBruteForce(int[] nums, int k) {
        int maxLength = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (sum == k) {
                    maxLength = Math.max(maxLength, j - i + 1);
                }
            }
        }
        return maxLength;
    }
}
