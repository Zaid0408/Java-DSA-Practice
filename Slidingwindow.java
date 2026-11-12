import java.util.HashMap;

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
}
