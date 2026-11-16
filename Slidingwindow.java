import java.util.HashMap;

import javax.xml.stream.events.Characters;

public class Slidingwindow {
    // Constant Window:

    // leetcode 1423

    /*
    * Problem: Given an array of card points, we need to pick exactly k cards 
    * from either the absolute beginning or the absolute end of the row in 
    * multiple steps to maximize the total sum of the cards chosen.
    */

    /*
    * Approach: Instead of choosing k cards from the edges, we invert the logic 
    * by realizing that picking k cards from the ends leaves behind a single 
    * contiguous subarray of size (n - k) in the center. To maximize our score, 
    * we find the minimum possible sum of this middle subarray using a sliding 
    * window of size (n - k) and subtract it from the total sum of all cards.
    */

    public int maxScore(int[] cardPoints, int k) {
        int sum=0,n=cardPoints.length;
        for(int card:cardPoints)
            sum += card;
        if(n==k)
            return sum;
        
        int window=n-k;
        int currentWindowSum = 0;
        
        // 1. Find the sum of the first window of size (n - k)
        for (int i = 0; i < window; i++) {
            currentWindowSum += cardPoints[i];
        }
        int minSum = currentWindowSum;
        // 2. Slide the window across the rest of the array
        for (int i = window; i < n; i++) {
            currentWindowSum += cardPoints[i]-cardPoints[i-window];
            minSum = Math.min(minSum, currentWindowSum);
        }
        return sum-minSum;
        // minSum is the minimum sum of the middle subarray of size (n - k), and sum is the total sum of all cards.
        // hence it is important to subtract from here
    }

    // Longest and Smallest Window Problems:

    // leetcode 3 : Longest Substring Without Repeating Characters

    /*
    Given a string s, find the length of the longest substring without repeating characters.
    Substring here means order matters and should be continous. 
    For example, "abc" is a substring of "abcde", but "ace" is not. not to be confused with subsequence.
    */

    /*
    Intuition : Store an array to increment count of all characters in the current window. 
    If any character count exceeds 1, we have a duplicate and we need to shrink the window from the left until all characters are unique again. 
    Keep track of the maximum length of the window during this process.
    */

    public int lengthOfLongestSubstring(String s) {
        int charSum[]=new int[128];
        int l=0,ans=0;
        for(int i=0;i<s.length();i++)
        {
            char ch=s.charAt(i);
            charSum[ch]++; // append the character to the current window regardless of whether it is a duplicate or not
            while(charSum[ch]>1) // if the character count exceeds 1, we have a duplicate and need to shrink the window
            {
                charSum[s.charAt(l)]--; // l here is the left pointer of the window, we decrement the count of the character at the left pointer
                l++; // move the left pointer to the right to shrink the window
            }
            ans=Math.max(ans,i-l+1); // Max window length is the current index minus the left pointer plus 1 
        }
        return ans;
    }

    // Leetcode 1004. Max Consecutive Ones III
    /*
    Given a binary array nums and an integer k, 
    return the maximum number of consecutive 1's in the array if you can flip at most k 0's.
    */
    public int longestOnes(int[] nums, int k) {
        int left = 0,right = 0;
        int zeroCount = 0;
        int maxLen = 0;
        while (right < nums.length) {
            // If we encounter a zero, increment our zero count
            if (nums[right] == 0) {
                zeroCount++;
            }
            // If zeros exceed k, shrink the window from the left
            while (zeroCount > k) {
                if (nums[left] == 0) {
                    zeroCount--;
                }
                left++;
            }
            // Update the maximum length of valid window found so far
            maxLen = Math.max(maxLen, right - left + 1);
            right++;
        }
        return maxLen;
    }

    // leetcode 903 Fruits into baskets (question is same as longest substring with at most 2 unique characters but is framed in a bad way)
    // the problem is basically Find the length of the longest sub array such that it has only two unique elements

    /* Approach:
    We initialize two pointers: start and end to define our sliding window.

A HashMap stores the count of each fruit type in the current window.

Expand the window by moving end ➡️ and adding fruits to the map.

If the map has more than 2 fruit types, shrink the window from start until we're back to only two types 🍎🍌.

Keep updating the maximum length (maxLen) at each valid window.
     */
    public int totalFruit(int[] fruits) {
        HashMap<Integer,Integer> hm =new HashMap<>();
        int maxLen=0;
        int left=0,right=0;
        while(right<fruits.length)
        {
            hm.put(fruits[right],hm.getOrDefault(fruits[right],0)+1);
            while(hm.size()>=3)
            {
                hm.put(fruits[left],hm.get(fruits[left])-1);
                if(hm.get(fruits[left])==0)
                    hm.remove(fruits[left]);
                left++;
            }
            maxLen=Math.max(maxLen,right-left+1);
            right++;
        }
        return maxLen;
    }

    // leetcode 76
    /*
    Minimum Window Substring
You are given two strings: s and t. 
Your goal is to find the shortest substring in s that contains all the characters found in t (including duplicates). If no such substring exists, you return an empty string "".

Sliding Window Approach: We expand a window from right to left using two pointers (left and right).
Tracking Counts: We use frequency arrays (have and need) to ensure every required character from t is satisfied. 
Once all characters are matched, we try to shrink the window from the left to find the minimum possible length while still keeping all required characters.
    */
    public String minWindow(String s, String t) {
        int[] have = new int[128], need = new int[128];
        for (char ch : t.toCharArray()) need[ch]++; // Count required chars

        int left = 0, matched = 0, minLen = Integer.MAX_VALUE, start = 0;

        for (int right = 0; right < s.length(); right++) {
            char ch = s.charAt(right); 
            have[ch]++; // Expand window to the right
            if (need[ch] > 0 && have[ch] <= need[ch]) 
                matched++; // Track fulfilled needs
            while (matched == t.length()) { // When all chars are found, shrink window

                if (right - left + 1 < minLen) { 
                    minLen = right - left + 1; start = left; 
                }
                have[s.charAt(left)]--; // Remove leftmost char and update match count if needed

                if (need[s.charAt(left)] > 0 && have[s.charAt(left)] < need[s.charAt(left)]) 
                    matched--;
                left++; // Shrink from left
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
    }


    // Counting Sub Arrays / Sub strings problems 

    // 1358. Number of Substrings Containing All Three Characters
    // Given a string s consisting only of characters a, b and c.
    // Return the number of substrings containing at least one occurrence of all these characters a, b and c.

    // Brute force Gives TLE
    /* INTUITION:
 * Fix a starting index 'i' and expand a substring forward using index 'j'.
 * Track the presence of 'a', 'b', and 'c' using a small frequency/presence 
 * array of size 3 (since only these three characters exist).
 * 
 * KEY OPTIMIZATION (EARLY BREAK):
 * The moment our substring s[i...j] contains all three characters (sum == 3),
 * we don't need to keep extending 'j' one by one. Why? Because adding any 
 * remaining characters to the right of 'j' will still keep the substring valid.
 * 
 * Therefore, every substring starting at 'i' and ending at 'j', 'j+1', ..., 'n-1'
 * is guaranteed to be valid. That gives us (n - j) valid substrings for this 'i'
 * in one shot, allowing us to break out of the inner loop immediately.
 * 
 * COMPLEXITY:
 * - Time:  O(N^2) in the worst case (e.g., "aaaa...bc", where the valid condition 
 *          isn't met until the very end of the string for almost every 'i'). 
 *          This causes Time Limit Exceeded (TLE) on large inputs.
 * - Space: O(1) as the tracking array always has a fixed size of 3.
 */
    public int numberOfSubstrings(String s) {
        int n=s.length();
        int cnt=0;
        for(int i=0;i<n;i++)
        {
            int hash[]=new int[3];
            for(int j=i;j<n;j++)
            {
                hash[s.charAt(j)-'a']=1;
                if(hash[0]+hash[1]+hash[2]==3)
                {
                    cnt=cnt + (n-j);
                    break; 
                    // why ? Because we dont need to count the remaining substrings when the needed characters are already present
                    // example string is abcaa : so when abc is counted as correct no matter how many letters you add the substring is valid 
                    // hence just add the remainig number of characters to the cnt 
                }
            }
        }
        return cnt;
    }
    // Optimal soln

    /* INTUITION:
 * Instead of fixing the START of a substring and looking forward, we fix the 
 * END of a substring at the current index 'i' and look backward.
 * 
 * HOW IT WORKS:
 * 1. Maintain an array 'hash' of size 3 initialized to {-1, -1, -1} to store 
 *    the most recent (last seen) index of 'a', 'b', and 'c' as we scan left to right.
 * 2. Once all three characters have been seen at least once (none are -1), 
 *    we can form valid substrings ending at the current index 'i'.
 * 3. To find the SHORTEST valid substring ending at 'i', we must include the 
 *    earliest among the three last-seen positions. Let this index be:
 *    minIdx = Math.min(hash[0], Math.min(hash[1], hash[2]))
 *    The shortest valid window is s[minIdx ... i].
 * 4. Since s[minIdx ... i] already contains all three characters, prepending 
 *    ANY character before 'minIdx' (from index 0 up to minIdx - 1) will also 
 *    produce a valid substring ending at 'i'.
 * 5. Thus, all starting indices from 0 to 'minIdx' form valid substrings ending 
 *    at 'i'. The total count of such starting positions is (minIdx + 1).
 * 
 * COMPLEXITY:
 * - Time:  O(N) — we traverse the string in a single pass, doing O(1) work per step.
 * - Space: O(1) — uses only a fixed-size array of 3 integers.
 */
    public int numberOfSubstringsOpt(String s) {
        int n=s.length();
        int cnt=0;
        int hash[]=new int[]{-1,-1,-1};
        for(int i=0;i<n;i++)
        {
            hash[s.charAt(i)-'a']=i;
            if(hash[0]!=-1 && hash[1]!=-1 && hash[2]!=-1)
            {
                cnt=cnt + 1 + Math.min(hash[0],Math.min(hash[1],hash[2]));
            }
        }
        return cnt;
    }
}