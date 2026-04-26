import java.util.HashMap;
import java.util.Map;

/**
 * Optimized solution for finding the longest subarray with sum equal to k using HashMap.
 * @author Coffee Coders
 */
public class LongestSubarrayHashMap {
    /**
     * Finds the length of the longest subarray with sum equal to k using prefix sums and HashMap.
     *
     * @param nums the input array of integers
     * @param k the target sum
     * @return the length of the longest subarray with sum k
     */
    public int longestSubarray(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        int prefixSum = 0;
        int maxLength = 0;
        for (int i = 0; i < nums.length; i++) {
            prefixSum += nums[i];
            if (prefixSum == k) {
                maxLength = i + 1;
            }
            if (map.containsKey(prefixSum - k)) {
                int prevIndex = map.get(prefixSum - k);
                maxLength = Math.max(maxLength, i - prevIndex);
            }
            if (!map.containsKey(prefixSum)) {
                map.put(prefixSum, i);
            }
        }
        return maxLength;
    }
}
