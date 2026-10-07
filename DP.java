import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.lang.model.util.Elements;
/*
Absolutely. The **jist** is:

there is a trick, and once you understand it, tabulation becomes much less about memorizing loop directions.

The key idea is:

You don't decide the tabulation loops first. 
You start from the memoization state definition and ask: "What states does this state depend on?" 
Then you fill the DP table in an order that guarantees those states are already computed.

### When converting Memoization → Tabulation

**1. Look at what your recursive function depends on.**

If:

```java
dp[i] depends on dp[i-1]
```

→ loop `i` **forward**.

If:

```java
dp[i] depends on dp[i+1]
```

→ loop `i` **backward**.

For 2D, apply the same logic to `j`.

---

**2. The final answer comes directly from your original recursive call.**

If:

```java
return help(1, n);
```

then:

```java
return dp[1][n];
```

If:

```java
return help(0, n-1);
```

then:

```java
return dp[0][n-1];
```

**Don't guess the answer cell — copy the starting state of the recursion.**

---

**3. DP size depends on the indices you access.**

If you access up to index `n`:

```java
new int[n + 1]
```

If you access `n + 1`:

```java
new int[n + 2]
```

For example, if you have:

```java
dp[k + 1][j]
```

and `k` can become `n`, you need `n + 2` space.

---

### The one-line mental rule

> **Memoization tells you the states; the dependencies tell you the loop direction; the initial recursive call tells you the answer cell; the largest index tells you the DP size.**

That's basically all you need to remember.


*/

// https://leetcode.com/discuss/post/1000929/solved-all-dynamic-programming-dp-proble-8m82/ Check more problems here
public class DP {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
           this.left = left;
            this.right = right;
       }
   }
    // Tabulation or memoization used
    // Tabulation is a ‘bottom-up’ approach where we start from the base case and reach the final answer that we want. Tabulation helps in optimizing the solution by preventing additional stack space used during recursion.
    // Memoization is Known as the “top-down” dynamic programming, usually the problem is solved in the direction of the main problem to the base cases.
    // Space Optimization : only in some cases like house robber and climbing stairs where you just need the previous two values and not anything before 
    // remember the approach for the below given seven problems they are needed to solve most dp sums
    public static int fibonacci(int n, int f[])
    {// Memoization
        if(n<=1)
            return n;
        if(f[n]!=0)
            return f[n];
        f[n]=fibonacci(n-1,f)+fibonacci(n-2,f);
        return f[n];
    }
    static Map<Integer, Integer> ans = new HashMap<>();
    public static int climbStairs(int n) // fibonacci variation of climbing stairs
    {//Memoization
        if(n==1 || n==0 || n==2)
            return n;
        if(!ans.containsKey(n)){
            ans.put(n,climbStairs((n-1)) + climbStairs((n-2)));
        }
        return ans.get(n);
    }
    public static int climbStairsTabulation(int n)
    { // Tabulation
        int ans[]=new int[n+1];
        ans[0]=1;
        ans[1]=1;
        ans[2]=2;
        for(int i=3;i<=n;i++){
            ans[i]=ans[i-1]+ans[i-2];
        }
        return ans[n];
    }
    // lc 70 Climbing stair using Space optimization
    public int climbStairs2(int n) {
        if(n==1 || n==0 || n==2)
            return n;
        int prev=2;
        int prev2=1;
        int cur=0;
        for(int i=3;i<=n;i++)
        {
            cur= prev2+prev;
            prev2=prev;
            prev=cur;
        }
        return cur;
    }


    // Frog Jump : Frog can take either one or two jumps but the jump cost is such that if going from idx i to j the cost is abs(height[i]-height[j])
    // memoization : top down approach , here start from the end 
    // understand the recursive soln below and then this becomes easy 
    private int solve(int ind, int[] height, int[] dp) {
        if (ind == 0) return 0;

        // Return memoized result if already computed
        if (dp[ind] != -1) return dp[ind];

        // Initialize jumpTwo with a large value
        int jumpTwo = Integer.MAX_VALUE;

        // Compute cost when jumping from previous stone (ind - 1)
        int jumpOne = solve(ind - 1, height, dp) + Math.abs(height[ind] - height[ind - 1]);

        // Compute cost when jumping from two stones back (ind - 2) if possible
        if (ind > 1) {
            jumpTwo = solve(ind - 2, height, dp) + Math.abs(height[ind] - height[ind - 2]);
        }

        // Memoize and return the minimum of the two choices
        dp[ind] = Math.min(jumpOne, jumpTwo);
        return dp[ind];
    }


    public int frogJump(int[] height) {
        // Handle empty input
        if (height == null || height.length == 0) return 0;

        // Prepare dp with -1 indicating uncomputed states
        int n = height.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);

        // Start from the last index
        return solve(n - 1, height, dp);
    }
    // recursive soln for frog jump:
    public int recur(int i, int[] heights)
    {
        if(i==0)
            return 0;
        
        int left=recur(i-1,heights) + Math.abs(heights[i]-heights[i-1]);
        int right=-1;
        if(i>1)
            right=recur(i-2,heights) + Math.abs(heights[i]-heights[i-2]);

        return right==-1?left:Math.min(right,left);
    }

    /*
    Frog Jump with K steps : Same as above problem but isntead of taking either one or two jumps we can take any number of jumps in range(1,k)
    */
    public int frogJump(int[] heights, int k) {
        int s=heights.length;
        int dp[]=new int[s];
        Arrays.fill(dp,-1);
        return solve(s-1,heights,dp,k);
    }
    private int solve(int i, int[] height, int[] dp,int k) {
        if (i == 0) return 0;

        // Return memoized result if already computed
        if (dp[i] != -1) return dp[i];
        int minEnergy = Integer.MAX_VALUE;
        for(int j=i-1;j>=Math.max(0,i - k);j--) // compute of all jumps from i-1 to i-k
        {
            int jump= solve(j,height,dp,k) + Math.abs(height[i]-height[j]);
            minEnergy= Math.min(minEnergy,jump);
        }
        // Memoize and return the minimum of the two choices
        
        return dp[i]=minEnergy;
    }
    /*
     * The knapsack problem is a classic optimization problem where we need to choose a subset of items with weights and values to include in a knapsack with a limited capacity. 
     * The goal is to maximize the total value of the items while keeping the total weight within the capacity limit.
     * We are given a set of items, each with a weight and a value, and a knapsack with a capacity W. 
     * We need to determine the optimal subset of items to include in the knapsack to achieve the maximum value. 
     * The problem has a constraint that each item can either be included or excluded, but not partially included.
     */
    public static int knapsack(int[] val, int[] wt, int cap, int n)
    {   // Tabulation approach
        // remember knapsack pattern
        int dp[][]=new int[n+1][cap+1];
        for(int i=0;i<=n;i++)
        {
            dp[i][0]=0;
        }
        for(int j=0;j<=cap;j++)
        {
            dp[0][j]=0;
        }
        for(int i=1;i<=n;i++){ // i is the number of the items for which we can find max profit for
            for(int j=1;j<=cap;j++) // j means the actual weight of the knapsack from 1 to max capacity of the knapsack
            {// dp[i][j] stores the maximum profit of i items present in the knapsack and j is the capacity of the knapsack at a particular point
                if(wt[i-1]<=j){ // valid condition
                    // include the weight into knapsack
                    int ans1=val[i-1]+ dp[i-1][j-wt[i-1]];
                    // exclude the weight from knapsack
                    int ans2= dp[i-1][j];
                    dp[i][j]=Math.max(ans1,ans2);
                }
                else{ // not valid
                    dp[i][j]=dp[i-1][j];
                }
            }
        }
        return dp[n][cap];
        // dp[n][cap] consists the maximum profit for n items in the knapsack with capacity cap (items could be n or less weight could be capacity or less)

        /*
         * 0-1 Knapsack: You can either take an item or leave it. 
         * Each item can only be chosen once, and you can't choose an item more than once. 
         * The goal is to maximize the total value of the items you choose while keeping the total weight within a given limit.
         */
        
    }
    /* 
    * 0-1 knapsack problem both memoization and tabulation approach:-
    * It initializes a 2D table dp to store the maximum values for sub-problems. 
    * The table is filled by iterating over items and capacity, considering two possibilities: including or excluding the current item. 
    * If the item's weight is within the capacity, it calculates the maximum value by including or excluding the item. 
    * If not, it excludes the item. 
    * The maximum value is stored in the table and returned as the solution.
    */
    public static int knapsack(int[] val, int[] wt, int cap, int n, int dp[][]){
        //memoization approach
        if(cap==0 || n==0)
            return 0;
        if(dp[n][cap]!=-1)
        {
            return dp[n][cap];
        }
        if(wt[n-1]<=cap)
        {
            // include the weight into knapsack
            int ans1= val[n-1]+knapsack(wt, val, cap-wt[n-1], n-1,dp);
            // exclude the weight from knapsack
            int ans2= knapsack(wt, val, cap, n-1,dp);
            dp[n][cap] = Math.max(ans1, ans2);
            return dp[n][cap];
        }
        else{ // not valid
            dp[n][cap]=knapsack(wt, val, cap, n-1,dp);
            return dp[n][cap];
        }
        // dp[n][cap] consists the maximum profit for n items in the knapsack with capacity cap (items could be n or less weight could be capacity or less)


    }
    /*
     * Target sum subset problem variation of 0-1 knapsack
     * value in dp[i][j] - boolean array represents whether the sum j is possible with the first i elements
     * yes then store true else false.
     * ans -> n-items -> subset_sum= target ? True : False
     * similar to 0-1 knapsack, the weight of knapsack is target sum. and value is considered same as weight as well. So instead of adding weight we add value only
     */
    public static boolean Targetsumsubset(int[] val, int target, int n){
        // tasbulation approach
        boolean dp[][]=new boolean[n+1][target+1];
        for(int i=0;i<dp.length;i++)
        {
            dp[i][0]=true;
        }
        // i is items j is target sum i.e. weight importatnt remember this 
        int V=0;
        for(int i=1;i<dp.length;i++)
        {
            for(int j=1;j<dp[0].length;j++)
            {
                V=val[i-1];
                if(V<=j && dp[i-1][j-V]==true) // include condition 
                {
                    dp[i][j]=true;

                }
                /*
                 * Include works beacause V<=j means we can add the value into knapsack. So remaining weight is j-V
                 * now if dp[i-1][j-V] is true then it means that whatever items we have had so far ,
                 * those items and their sum is less =j-V and if we add V the sum will become j which is the max capacity at this particular point
                 * similar logic used in 0-1 knapsack problem
                 */
                else if(dp[i-1][j]==true){ // exclude condition or not valid condition
                    dp[i][j]=true;
                }
                /*
                here V> j hence we exclude adding it to the knaspack
                 * If we don't add the value to knapsack then we add the previous value and if it is true then we add the value
                 */
            }
        }
        return dp[n][target];
    }
    public static int Unboundedknapsack(int[] val, int[] wt, int cap, int n){
        // tabulation approach
        int dp[][]=new int[n+1][cap+1];
        for(int i=0;i<=n;i++)
        {
            dp[i][0]=0;
        }
        for(int j=0;j<=cap;j++)
        {
            dp[0][j]=0;
        }
        for(int i=1;i<=n;i++){ // i is the number of the items for which we can find max profit for
            for(int j=1;j<=cap;j++) // j means the actual weight of the knapsack from 1 to max capacity of the knapsack
            { // dp[i][j] stores the maximum profit of i items present in the knapsack and j is the capacity of the knapsack at a particular point
                if(wt[i-1]<=j){ // valid condition
                    // include the weight into knapsack
                    int ans1=val[i-1]+ dp[i][j-wt[i-1]]; // i instead of i-1 as we can include this item for the profit calculation
                    // exclude the weight from knapsack
                    int ans2= dp[i-1][j];
                    dp[i][j]=Math.max(ans1,ans2);
                }
                else{ // not valid
                    dp[i][j]=dp[i-1][j];
                }
            }
        }
        return dp[n][cap];
        // dp[n][cap] consists the maximum profit for n items in the knapsack with capacity cap (items could be n or less weight could be capacity or less)
        /*
         * You can take an item any number of times. 
         * There's no limit to how many times you can choose an item, so you can choose the same item multiple times if it helps you maximize the total value. 
         * The goal is to maximize the total value of the items you choose while keeping the total weight within a given limit.
         */
    }
    /*
     * Given a rod of n inches and an array of prices of all pieces. 
     * Determine the maximum value obtainable by cutting the rod up and selling the pieces.
     * length=[1,2,3,4,5,6,7,8] - this may or may not be gven so consider i as length of rod i
     * price=[1,5,8,9,10,17,17,20]
     * n is just length of prices array
     * rod length=8 ans=22
     */
    public static int RodCutting(int[] prices, int[] len, int rodLength, int n){ // same to same as unbounded knapsack
        // tabulation approach
        int dp[][]=new int[n+1][rodLength+1];
        for(int i=0;i<=n;i++)
        {
            dp[i][0]=0;
        }
        for(int j=0;j<=rodLength;j++)
        {
            dp[0][j]=0;
        }
        for(int i=1;i<=n;i++){ // i is the number of the items for which we can find max profit for
            for(int j=1;j<=rodLength;j++) // j means the actual length of the rod from 1 to max rod length 
            { // trying to find max profit at a particular length j with i items and storing the profit of the same at dp[i][j]
                if(len[i-1]<=j){ // valid condition 
                    // include the rod length into the knapsack
                    int ans1=prices[i-1]+ dp[i][j-len[i-1]];
                    // exclude the rod length into the knapsack
                    int ans2= dp[i-1][j];
                    dp[i][j]=Math.max(ans1,ans2);
                }
                else{ // not valid
                    dp[i][j]=dp[i-1][j];
                }
            }
        }
        return dp[n][rodLength];
    }
    // same rod cutting in striver 
    // this below is the correct code from striver
    /*
    Rows (i) represent the length of the piece you are allowed to cut (from 1 up to n inches).
    Columns (j) represent the total remaining length of the rod you are trying to cut down.
    price[i-1] gets the value of the current piece because arrays start at index 0 while our loop starts at 1.
    if (j >= i) checks if the current piece length i actually fits inside the remaining rod length j.
    dp[i][j-i] means you take the piece, earn its price, and stay on row i to allow reusing the same piece length.
    dp[i-1][j] is the backup choice where you completely skip the current piece size and copy the best value from the row above.
    */
    public int RodCutting(int price[], int n) {
        int dp[][]=new int[n+1][n+1];
        for(int i=1;i<=n;i++){ 
            for(int j=1;j<=n;j++){
                int ans=0;
                // if statement means Check if the rod capacity 'j' can accommodate piece length 'i'
                if(j>=i) // question mentions 1 based indexing hence we can take i 
                    ans=price[i-1] + dp[i][j-i];
                dp[i][j]=Math.max(dp[i-1][j],ans);
            }
        }
        return dp[n][n];
    }
    /* 
     * Coin Change 2 lc 518

     *
    */
    public int coinChange2(int[] coins, int amount) {
        // dp[i][j] stores the no of ways in which i coins can make the sum j
        int dp[][]=new int[coins.length+1][amount+1];
        for(int i=0;i<=coins.length;i++)
        {
            dp[i][0]=1;
        }
        // 1 because since there is always 1 way to make 0 amount with 0 coins also because dp[i][j] stores the no of ways 
        for(int j=1;j<=amount;j++)
        {
            dp[0][j]=0;
        }
        // 0 because there is always 0 way to make any amount with 0 coins
        for(int i=1;i<=coins.length;i++)
        {
            for(int j=1;j<=amount;j++)
            {
                if(coins[i-1]<=j)
                {
                    dp[i][j]= dp[i-1][j]+ dp[i][j-coins[i-1]];
                    // adding both cuz we need number of ways so it means we can include the current coin and make sum and exclude coin and make the sum
                    // dp[i][j-coins[i-1]] include the current coin to make the sum j
                    // dp[i-1][j] exclude the current coin to make the sum j
                    // hence total ways is sum of both
                }
                else{
                    dp[i][j]=dp[i-1][j];
                }
            }
        }
        
        return dp[coins.length][amount];
    }
    // Easier knowing soln below this is a bit advanced: space optimization soln 
    // lc 322 Coin change 1
    public int coinChange1(int[] coins, int amount) {
        int dp[]=new int[amount+1];
        Arrays.fill(dp,amount+1);
        dp[0]=0;
        for(int coin:coins)
        {
            for(int j=coin;j<=amount;j++)
            {
                dp[j]= Math.min(dp[j] ,1 + dp[j-coin]); 
            }
        }
        
        return dp[amount]!=amount+1 ? dp[amount]: -1 ;
    }

    /*
 * ==================== DP ON STRINGS / LCS ====================
 *
 * Recognize this pattern when the problem involves TWO strings/sequences
 * and asks about their relationship while preserving character order.
 *
 * Strong hints:
 * - Longest Common Subsequence
 * - Common characters/elements while maintaining order
 * - Number of ways to form one string from another
 * - Make two strings equal using deletions/insertions
 * - Shortest Common Supersequence
 * - Palindromic subsequence -> compare string with its reverse
 * - String transformation problems that compare prefixes of two strings
 *
 * Main mental question:
 * "What is the relationship between the first i characters of string 1
 *  and the first j characters of string 2?"
 *
 * Common 2D state:
 * dp[i][j] = answer for first i characters of string1
 *            and first j characters of string2.
 *
 * Typical LCS transition:
 * If characters match:
 *     use both -> 1 + dp[i-1][j-1]
 *
 * If characters differ:
 *     skip one side -> max(dp[i-1][j], dp[i][j-1])
 *
 * BUT DO NOT blindly apply the LCS recurrence.
 * First identify what dp[i][j] is counting:
 *
 * LCS              -> maximum common subsequence length
 * Distinct Subseq. -> number of ways
 * Common Substring -> contiguous match, mismatch becomes 0
 * Edit Distance    -> minimum transformation cost
 * SCS              -> use LCS table to reconstruct shortest supersequence
 *
 * Important distinction:
 * SUBSEQUENCE -> characters can be skipped.
 * SUBSTRING   -> characters must remain contiguous.
 *
 * KEY DIFFERENCE:
 * When TWO strings are being compared and the problem talks about
 * commonality, matching, forming, transforming, or preserving order,
 * consider a 2D string DP / LCS-family pattern.
 * ================================================================
 */

    // DP on Strings Pattern.
    /*
     * Longest Common Subsequence
     * Given two strings text1 and text2, return the length of their longest common subsequence. If there is no common subsequence, return 0.
     * A subsequence of a string is a new string generated from the original string with some characters (can be none) deleted without changing the relative order of the remaining characters.
     * For example, "ace" is a subsequence of "abcde".
     * Example 1:
     * Input: text1 = "abcde", text2 = "ace"  Output: 3  Explanation: The longest common subsequence is "ace" and its length is 3.
     * Example 3: 
     * Input: text1 = "abc", text2 = "def" Output: 0 Explanation: There is no such common subsequence, so the result is 0.
     */
    // The important idea is that we compare characters from the end of both strings.
    /*
    If text1[n-1] == text2[m-1]
Both characters can be part of the LCS.
So take them → 1 + LCS(n-1, m-1).
If they are different:
We cannot take both.
Either ignore the current character of text1, or ignore the current character of text2.
Take the better of those two possibilities.
Your n and m represent lengths, not indices. 
Suppose:

text1 = "abc"
text2 = "axc"

At this point, both last characters are c. Since they match, there is no reason to throw c away.So:
LCS("abc", "axc") = 1 + LCS("ab", "ax") That's why: 1 + longestCommonSubsequence(..., n-1, m-1)
    */
    public static int longestCommonSubsequence(String text1, String text2, int dp1[][],int n, int m) {
        // memoization 
        /* 
         * dp[i][j] : if text1 length is i and text2 length is j then the longest common subsequence between both text1 and text2 will be dp[i][j]
         * final answer stored in dp[n][m] where n is text1 final length and m is text2 final length
         */
        if(n==0 || m==0)
            return 0;
        if(dp1[n][m]!=-1)
            return dp1[n][m];
        if(text1.charAt(n-1)==text2.charAt(m-1))
            return  dp1[n][m]= 1 + longestCommonSubsequence(text1,text2,dp1,n-1,m-1); 
        else{
            int ans1= longestCommonSubsequence(text1,text2,dp1,n-1,m); // ignore the current character of text1 and move on
            int ans2= longestCommonSubsequence(text1,text2,dp1,n,m-1); // ignore the current character of text2 and move on
            return dp1[n][m]= Math.max(ans1,ans2); // valid statement will return dp1[n][m]
        }
        /*
        int n=text1.length(),m=text2.length();
        int dp[][]=new int[n+1][m+1];
        for(int i=0;i<=n;i++)
        {
            Arrays.fill(dp[i],-1);
        }
        return helper(text1,text2,n,m,dp);
        */
    }
    /*
 * LCS pattern:
 * dp[i][j] = LCS length between first i chars of text1 and first j chars of text2.
 * If last chars match -> take both: 1 + dp[i-1][j-1].
 * If they don't match -> skip one character and take max(dp[i-1][j], dp[i][j-1]).
 * Base case: if either string is empty, LCS = 0.
 * Memoization stores repeated (i,j) states; tabulation fills them bottom-up.
 * Final answer = dp[text1.length()][text2.length()].
 */
    public static int longestCommonSubsequence2(String text1, String text2)
    {   // remember LCS pattern for dp
        // tabulation approach
        /* 
         * dp[i][j] : if text1 length is i and text2 length is j then the longest common subsequence between both text1 and text2 will be dp[i][j]
         * final answer stored in dp[n][m] where n is text1 final length and m is text2 final length
         */
        int dp[][]=new int[text1.length()+1][text2.length()+1];
        for(int i=0;i<dp.length;i++){
            dp[i][0]=0;
        }
        // because if text1 is 0 length the lcs will be 0
        for(int j=0;j<dp[0].length;j++){
            dp[0][j]=0;
        } // beacuse if text2 is 0 length the lcs will be 0
        // above loops not needed just for understanding the logic
        // similar logic as memoization approach
        for(int i=1;i<dp.length;i++){
            for(int j=1;j<dp[0].length;j++){
                if(text1.charAt(i-1)==text2.charAt(j-1)){
                    dp[i][j]=dp[i-1][j-1]+1;    // if both the last characters of text1 and text2 are same 
                }
                else{
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return dp[text1.length()][text2.length()];
        
    }
    // lc 583. Delete Operation for Two Strings
    // Given two strings word1 and word2, return the minimum number of steps required to make word1 and word2 the same.
    // Find the LCS of the words and then return the length of the word1 - LCS + length of word2 - LCS
    // length of the word1 - LCS : number of ops required to remove characters from word1
    // length of the word2 - LCS : number of ops required to remove characters from word2
    // Hence summation of both is the answer 
    /*
    Instead of directly thinking: "Which characters should I delete?"
Think: Which characters should I keep?" If we can find the Longest Common Subsequence, those characters already exist in both strings in the correct order.
So we can keep the LCS and delete everything else.
    */
    public int minDistance(String word1, String word2) {
        int dp[][]=new int[word1.length()+1][word2.length()+1];
        for(int i=1;i<dp.length;i++){
            for(int j=1;j<dp[0].length;j++){
                if(word1.charAt(i-1)==word2.charAt(j-1)){
                    dp[i][j]=dp[i-1][j-1]+1;    // if both the last characters of word1 and word2 are same 
                }
                else{
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return word1.length()- dp[word1.length()][word2.length()] + word2.length()- dp[word1.length()][word2.length()];
    }
    // same logic as above 
    //Given two strings str1 and str2, find the minimum number of insertions and deletions in string str1 required to transform str1 into str2.
    // Insertion and deletion of characters can take place at any position in the string.
    public int minOperations(String str1, String str2) {
        int dp[][]=new int[str1.length()+1][str2.length()+1];
        for(int i=1;i<dp.length;i++){
            for(int j=1;j<dp[0].length;j++){
                if(str1.charAt(i-1)==str2.charAt(j-1)){
                    dp[i][j]=dp[i-1][j-1]+1;    // if both the last characters of str1 and str2 are same 
                }
                else{
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return str1.length()- dp[str1.length()][str2.length()] + str2.length()- dp[str1.length()][str2.length()];
    }
    // lc 1092. Shortest Common Supersequence
    // find the Longest Common Subsequence (LCS), The LCS represents characters that appear in both strings in the same order.
    // Then, I construct the Shortest Common Supersequence by:
    // Starting from the end of both strings and working backwards
    // If the characters from both strings match, I include it only once
    // If they differ, I include characters from both strings
    // Finally, I add any remaining characters from either string
    // Reverse the constructed string to get the final answer.
    /*
    You need to create the shortest string that contains both str1 and str2 as subsequences. For example:

str1 = "abac"
str2 = "cab"

A valid answer could be: "cabac" because both strings can be obtained from it by deleting some characters.
    The key is: The characters common to both strings should be written only once. And what represents the characters common to both strings while preserving their order?
    LCS.
    Find LCS
   ↓
Use LCS to know which characters can be shared
   ↓
For matching characters → add once
For different characters → add the appropriate character from one string
   ↓
Add remaining characters
   ↓
Reverse

For phase 2 will constructing the shortest supersequence back 
That is, you're standing at the last characters of both strings. Think of this as walking backward through the two strings.

Characters are the same → add once
Characters are different → add the appropriate character from one string

If: dp[i-1][j] > dp[i][j-1] then ignoring the current character of str1 gives a better LCS.

So you append the current str1 character and move: ans.append(str1.charAt(i-1)); i--;

Otherwise:
ans.append(str2.charAt(j-1));
j--;

The important intuition is: The LCS table tells you which side to move toward while constructing the shortest supersequence.
Why do you append the character even though you're moving toward an LCS?

This is an important distinction. The LCS table is only helping you decide which character should be handled first.

The final SCS needs:

all LCS characters once
all non-LCS characters from str1
all non-LCS characters from str2

So when characters differ, you append the character from the side you're moving from.
    */
   /*
 * LC 1092: Build the shortest string containing both strings as subsequences.
 * First calculate the LCS table because LCS characters can be shared only once.
 * Traverse from the end: if characters match, add once and move both pointers.
 * If they differ, use the LCS table to decide which character to add first.
 * After one string ends, append all remaining characters from the other string.
 * Reconstruction is done backwards, so reverse the StringBuilder at the end.
 */

   /*
   How to arrive at Tabulation from Memoization

The recursive state would be something like:
f(i,j)

meaning: What is the required LCS information for the first i characters and first j characters?
The changing variables are: i and j Therefore:

2 changing variables
        ↓
2D DP

The dependencies are:

dp[i-1][j]
dp[i][j-1]
dp[i-1][j-1]

Therefore those smaller states must already exist. So fill:

i = 1 → n
j = 1 → m

Then, once the LCS table exists, walk backward through it to construct the actual SCS. That distinction is important: DP table is filled forward. Answer reconstruction is done backward.
   */
    public String shortestCommonSupersequence(String str1, String str2) {
        int n=str1.length(),m=str2.length();
        int dp[][]=new int[n+1][m+1];
        // dp[i][j] = LCS length between the first i characters of str1 and first j characters of str2.
        for(int i=1;i<=n;i++)
        {
            for(int j=1;j<=m;j++)
            {
                if(str1.charAt(i-1)==str2.charAt(j-1))
                    dp[i][j]=dp[i-1][j-1]+1;
                else
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
            }
        } // standard lcs till here 
        StringBuilder ans=new StringBuilder();
        int i=n,j=m;
        while(i>0 && j>0)
        {
            if(str1.charAt(i-1)==str2.charAt(j-1))
            {
                ans.append(str1.charAt(i-1));
                i--;j--;
            }
            else if(dp[i-1][j]>dp[i][j-1])
            {
                ans.append(str1.charAt(i-1));
                i--;
            }
            else
            {
                ans.append(str2.charAt(j-1));
                j--;
            }
        }
        while(i>0){
            ans.append(str1.charAt(i-1));
            i--;
        }
        while(j>0){
            ans.append(str2.charAt(j-1));
            j--;
        }

        return ans.reverse().toString();
        /*
        Why reverse? You're constructing the answer from the end toward the beginning. So if the actual answer should be:
        abcde
        you've built:
        edcba hence reverser the answer
        */
    }
    // lc 115. Distinct Subsequences Recursive soln : will exceed time limit
    // find the number of distinct subsequences of t in s 
    // Notice that we're not asking whether t exists.We're asking: How many different ways can I form t from s?
    // ex: Input: s = "babgbag", t = "bag"
        // Output: 5
        // Explanation:
        // As shown below, there are 5 ways you can generate "bag" from s.
        // ba g
        // ba    g
        //     bag
        //   b  ag
        // b    ag
    // f(i-1,j-1,s,t)+ f(i-1,j,s,t); represents the number of ways to generate t[0..j-1] from s[0..i-1] including the char at s[i]
    // it also means that suppose s[i]==t[j]== 'g' this means taking this current g into consideration for the subsequence and then continuing on to find b and a
    // but if we dont consider this subsequence and want to find anothe g we use f(i-1,j,s,t) 

    
    /*
    Why is the LCS-style pattern used here? This problem looks similar to LCS because we're comparing two strings character by character.
But there is an important difference:

LCS asks: What's the maximum length of a common subsequence?

LC 115 asks: How many different subsequences can form t?So the state structure resembles LCS:

i = position in s
j = position in t

but the value stored in DP is different.

For LCS: dp[i][j] = maximum length
For this problem: dp[i][j] = number of ways

That's why you can think of this as an LCS-family/string-DP pattern, but don't blindly use the LCS recurrence.
    */

    /*
    When characters match You have:
if(s.charAt(i)==t.charAt(j))
    return f(i-1,j-1,s,t)+ f(i-1,j,s,t);

This is the heart of the problem. Suppose:

s[i] == t[j] There are two possibilities.

Possibility 1 — Take s[i] and Use this character to match t[j].Therefore both move:
i → i-1
j → j-1

That's:f(i-1,j-1)

Possibility 2 — Don't take s[i]
Even though the characters match, you don't have to use this particular occurrence.
Maybe another occurrence of the same character later/earlier can form another valid subsequence.So:

i → i-1
j stays

That's: f(i-1,j)

Therefore: take + don't take becomes:

f(i-1,j-1) + f(i-1,j)

This is why your comment about the second call is correct:
We ignore this occurrence of the matching character and continue looking for another possible way to construct t.
When characters don't match You have:

return f(i-1,j,s,t);

There is no choice. If:
s[i] != t[j]
then s[i] cannot help match t[j].

So simply ignore s[i]:
i → i-1
j stays
    */
    public int numDistinct(String s, String t) {
        int n=s.length(),m=t.length();
        return f(n-1,m-1,s,t);
    }
    private int f(int i, int j,String s, String t)
    { // f(i,j,s,t) means Number of ways to form t[0...j] using s[0...i].
        if(j<0)
            return 1; // means one subseqence possible
        /*
        We have successfully matched the entire target t. There is exactly one successful way from here:
        Do nothing. So return 1.
        This 1 is extremely important in counting DP. You're basically saying:
        "One valid construction has been completed."
        */
        if(i<0)
            return 0; // no more traversal possible
        /*
        We've run out of characters in s. But t still hasn't been completely matched.Therefore:
        No possible subsequence. Return 0.
        */
        if(s.charAt(i)==t.charAt(j))
            return f(i-1,j-1,s,t)+ f(i-1,j,s,t); // remember this is number of ways so we use the take + not take formula used in other dp problems 

        return f(i-1,j,s,t); // characters did not match go to the next index in S string while keeping the T string intact
    }
    // Memoization this also gives TLE use Tabulation instead , this is because of recursion and is here for learning purpose
    private int f(int i, int j,String s, String t, int dp[][])
    {
        if(j<0)
            return 1; // means one subseqence possible
        if(i<0)
            return 0; // no more traversal possible
        if(dp[i][j]!=0)
            return dp[i][j];
        if(s.charAt(i)==t.charAt(j))
            return dp[i][j]= f(i-1,j-1,s,t,dp)+ f(i-1,j,s,t,dp);

        return dp[i][j]= f(i-1,j,s,t,dp); // characters did not match go to the next index in S string while keeping the T string intact
    }
    // Tabulation : Similar to LCS
    /*
    Your memoization state is:
f(i,j) where i and j are indices.

But your tabulation uses: dp[i][j] where i and j represent lengths.
This is the usual +1 shift. Think:

recursive index i
       ↓
tabulation length i+1

Your recursive base cases:

j < 0 return 1
i < 0 return 0

become tabulation initialization:

dp[i][0] = 1

because:no matter how many characters you take from s, an Empty t can be formed from any prefix of s in exactly one way: choose nothing.

Then: dp[0][j] = 0 for all j > 0, because: You cannot form a non-empty target from an empty source.

Your transitions:
match:
f(i-1,j-1) + f(i-1,j) become:

dp[i][j] = dp[i-1][j-1] + dp[i-1][j]

And mismatch:
f(i-1,j) becomes:

dp[i][j] = dp[i-1][j]
Why fill top → bottom?

Current state only depends on the previous row:

dp[i-1][j-1]
dp[i-1][j]

So row i-1 must be calculated before row i.

Therefore:

i = 1 → n
j = 1 → m
    */
   /*
 * LC 115: Count distinct subsequences of s that form t.
 * dp[i][j] = number of ways to form first j chars of t using first i chars of s.
 * If chars match, either take s[i-1] or skip it -> dp[i-1][j-1] + dp[i-1][j].
 * If chars don't match, s[i-1] cannot help -> dp[i][j] = dp[i-1][j].
 * Empty target has 1 way: choose nothing, so dp[i][0] = 1.
 * Empty source cannot form non-empty target, so dp[0][j] = 0.
 */
    public int numDistinct2(String s, String t) {
        int n=s.length(),m=t.length();
        int dp[][]=new int[n+1][m+1];
        for(int i=0;i<=n;i++)
            dp[i][0]=1; // for String t if length is 0 that neans there is atleast one subsequence found (check recusrion logic )
        for(int i=1;i<=n;i++)
            {
                for(int j=1;j<=m;j++)
                {
                    if(s.charAt(i-1)==t.charAt(j-1))
                        dp[i][j]=dp[i-1][j-1]+ dp[i-1][j];
                    else
                        dp[i][j]=dp[i-1][j];
                }
            }
        return dp[n][m];
    }
    /*
    Longest Common Substring Characters must be continuous/adjacent.  For example:
"abcde"
"abfde"
Common substrings include:
"ab"
"de"

but "ade" is not a substring because the characters aren't continuous.

Why does this use an LCS-like pattern? Because we're still comparing characters at positions i and j.

So we can use the same basic 2D state: dp[i][j]

But the meaning changes: dp[i][j] = length of the common substring that ends exactly at text1[i-1] and text2[j-1].
That "ends exactly here" is the key.
    */
   /*
 * Longest Common Substring: characters must be contiguous.
 * dp[i][j] = length of common substring ending exactly at text1[i-1], text2[j-1].
 * If chars match -> extend diagonal: dp[i-1][j-1] + 1.
 * If chars differ -> substring breaks, so dp[i][j] = 0.
 * Unlike LCS, do NOT take max(top, left) because skipping breaks continuity.
 * The longest substring can end anywhere, so maintain the maximum DP value.
 */
    public static int longestCommonSubstring(String text1, String text2)
    {
        // similar logic as longest common subsequence 
        int dp[][]=new int[text1.length()+1][text2.length()+1];
        int maxi=-1;
        for(int i=1;i<dp.length;i++){
            for(int j=1;j<dp[0].length;j++){
                if(text1.charAt(i-1)==text2.charAt(j-1)){
                    dp[i][j]=dp[i-1][j-1]+1;    // if both the last characters of text1 and text2 are same 
                    maxi=Math.max(maxi,dp[i][j]);
                }
                else{ // if uncommon characters then substring breaks then set it to 0
                    dp[i][j]=0;
                } // this is the main diff betwee this and lcs dp[i][j]=0; is done only because we are looking for substring and not subsequence, a substring is continuous and in the same order as the orignal word
            }
        }
        return maxi;
        //return dp[text1.length()][text2.length()]; // answer not necesaarily is stored here , change this to return a variable called maxi which stores the maximum 
    }

    /*
 * Longest Palindromic Subsequence:
 * A palindrome reads the same forwards and backwards.
 * Reverse the string and find LCS(original, reversed).
 * The common subsequence represents characters that can form a palindrome.
 * Therefore LPS length = LCS(s, reverse(s)).
 * Use the standard LCS DP: match -> diagonal + 1, mismatch -> max(top,left).
 * Final answer = dp[s.length()][reverse.length()].
 */
/*
 * Intuition:
 * Convert LPS into LCS by comparing the string with its reverse.
 * dp[i][j] stores the best common subsequence between the two prefixes.
 * Matching characters can be included -> dp[i-1][j-1] + 1.
 * Mismatching characters require skipping one side -> max(top,left).
 * The LCS with the reversed string gives the longest palindromic subsequence.
 */
/*
 * Memoization -> Tabulation:
 * Recursive state has two variables: LCS(i,j), so use a 2D dp table.
 * Base case i=0 or j=0 gives 0 -> initialize first row/column to 0.
 * Match dependency is (i-1,j-1) -> dp[i-1][j-1] + 1.
 * Mismatch dependencies are (i-1,j) and (i,j-1) -> max(top,left).
 * Since current state depends on smaller i/j values, fill top-to-bottom, left-to-right.
 */
    public static int longestPalindromicSubsequence(String s) {
    // DP Approach
    // 1)Reverse The string;
    // 2)Find the longest common subsequence of given string and reverse of the string.
        StringBuilder ss=new StringBuilder(s);
        String rev=ss.reverse().toString();
        int dp[][]=new int[s.length()+1][rev.length()+1];
        for(int i=0;i<dp.length;i++){
            dp[i][0]=0;
        }
        for(int j=0;j<dp[0].length;j++){
            dp[0][j]=0;

        }
        for(int i=1;i<dp.length;i++){
            for(int j=1;j<dp[0].length;j++){
                if(s.charAt(i-1)==rev.charAt(j-1)){
                    dp[i][j]=dp[i-1][j-1]+1;    // if both the last characters of s and rev are same 
                }
                else{
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return dp[s.length()][rev.length()];
    }
    // lc 1312. Minimum Insertion Steps to Make a String Palindrome
    // exactly same as above but we need to return the no of operations to make the string palindrome
    // Minimum Insertion Steps to Make a String Palindrome is basically length of the string - longest pallindromic subsequence of the string
    // Intuition is to find the longest pallindromic subsequence , keep it handy
    // for the remaining characters which are not pallindrome/characters not included in the LPS  add the remaining characters to make the string pallindrome ex:
    // given string : abcaa , let us consider lps is aaa remaing chars are b,c 
    // a b c a      a : to make this a pallindrome we should add c,b after the second a to make it a pallindrome to make it a b c a c b a
    // let us consider lps is aca remaing chars are b,a 
    // a b   c    a a : to make this a pallindrome we should add a between b and c and b between c and a to make it a pallindrome to make it a b a c a  b a
    // hence formula is easier to understand
    /*
 * LC 1312: Minimum insertions to make s a palindrome.
 * First find the Longest Palindromic Subsequence (LPS).
 * LPS = LCS(s, reverse(s)) because a palindrome reads the same backwards.
 * Characters belonging to the LPS are already arranged as a palindrome.
 * The remaining characters must be matched using insertions.
 * Therefore minimum insertions = s.length() - LPS.
 * We only need the LPS length, so there is no need to reconstruct the palindrome.
 * Why does n - LPS work? Think of the LPS as the part you don't need to fix.
If the string contains:

LPS = "aba"

those three characters can remain.
The characters outside that palindromic subsequence need corresponding characters inserted to make the entire string symmetric.
 */
/*
 * Memoization -> Tabulation:
 * This problem uses the exact LCS state: LCS(i,j) between s and reverse(s).
 * Two changing variables -> 2D dp table.
 * Empty string base cases -> first row and column are 0.
 * Match -> diagonal + 1; mismatch -> max(top,left).
 * Fill table top-to-bottom and left-to-right because dependencies are smaller states.
 * After LCS is calculated, return s.length() - dp[n][n].
 */
    public int minInsertions(String s) {
        StringBuilder ss=new StringBuilder(s);
        String rev=ss.reverse().toString();
        int dp[][]=new int[s.length()+1][rev.length()+1];
        for(int i=1;i<dp.length;i++){
            for(int j=1;j<dp[0].length;j++){
                if(s.charAt(i-1)==rev.charAt(j-1)){
                    dp[i][j]=dp[i-1][j-1]+1;    // if both the last characters of s and rev are same 
                }
                else{
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return s.length()-dp[s.length()][rev.length()];
    }
    /*
    dp[i][j-1] = ADD / INSERT

Suppose we're at:

word1 = "ab"
word2 = "abc"

We're trying to convert:

"ab" → "abc"

The last character c needs to be inserted.

Before inserting c, we had:

"ab" → "ab"

dp[i-1][j] = REMOVE

Suppose:

word1 = "abc"
word2 = "ac"

We want:

"abc" → "ac"

We can remove b.

Before removing b, we solve:

"ab" → "ac"
    */
   /*
 * Edit Distance:
 * Find minimum insert, delete, and replace operations to convert s1 into s2.
 * dp[i][j] = minimum operations to convert first i chars of s1 into first j chars of s2.
 * If current chars match, no operation is needed -> dp[i-1][j-1].
 * If they differ, choose minimum of insert, delete, and replace.
 * This resembles LCS because both compare two string prefixes, but the DP meaning is different.
 * 
 * Your state is:
dp[i][j]
Convert first i characters of s1 into first j characters of s2.
 */

    /*
    Case 1 — Characters are equal
if(s1.charAt(i-1)==s2.charAt(j-1))
    dp[i][j]=dp[i-1][j-1];

Suppose:
s1 = "abc"
s2 = "adc"

At the current position:
c == c

You don't need an operation for c. So just solve the smaller problem: first i-1 characters → first j-1 characters
Therefore:dp[i][j] = dp[i-1][j-1]

Case 2 — Characters differ
Suppose: s1[i-1] != s2[j-1] , You have exactly three choices.

Choice 1 — Insert

You want the current target character s2[j-1]. So imagine inserting it into s1.

After that insertion, the target character has been handled, but the source prefix hasn't been consumed. Therefore:

dp[i][j-1] + 1

That's your:
int add=dp[i][j-1] + 1;

Choice 2 — Remove

Remove the current character from s1.So: dp[i-1][j] + 1
Your code: int rem=dp[i-1][j] + 1;
The source becomes one character shorter.

Choice 3 — Replace. Replace s1[i-1] with s2[j-1].
Now both current characters have been dealt with. So:

dp[i-1][j-1] + 1

Your code: int replace=dp[i-1][j-1] + 1;

Then:

dp[i][j]=Math.min(add,Math.min(rem,replace));

Because the question asks for the minimum number of operations.
    */
    public static int EditDistance(String s1, String s2){
        // This may look similar to LCS but it is not it is actually: String transformation DP. LCS maybe a subset of this 
        int dp[][]=new int[s1.length()+1][s2.length()+1];
        // initialization step
        for (int i = 0; i <= dp.length; i++) {
            dp[i][0] = i;// if string 2 is empty then all the characters of string 1 to be deleted
        }
        for (int j = 0; j <= dp[0].length; j++) {
            dp[0][j] = j; // if string 1 is empty then all the characters of string 2 to be inserted
        }
        
        for(int i=1;i<dp.length;i++){
            for(int j=1;j<dp[0].length;j++){
                if(s1.charAt(i-1)==s2.charAt(j-1))
                    dp[i][j]=dp[i-1][j-1];
                else{
                    int add=dp[i][j-1] + 1; // no of operations to add the remaining characters
                    int rem=dp[i-1][j] + 1; // no of operations to remove the remaining characters
                    int replace=dp[i-1][j-1] + 1; // no of operations to replace the remaining characters
                    dp[i][j]=Math.min(add,Math.min(rem,replace));
                    // dp[i][j] = minimum operations required to convert first i characters of word1 into first j characters of word2
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }
    /*
     * String Conversion Problem : Given two strings str1 and str2 
     * to convert str 1 intto str2 and print the number of insertion operations and delete operations.
     * for delete operations take longest common subsequence between the two strings and subtract it from the length of str1
     * this will give total number of delete operations store it in x var
     * for insert operations subtract length of str2 with longest common subsequence between the two strings and store it in y var
     */



    /*
     * catalan Number : 1,1,2,5,14,42,132,429,....
     * remember pattern for dp
     */
    public static int cattyNum(int n, int dp[])
    {   // memoization
        if(n<=1)
            return 1;
        if(dp[n]!=0)
            return dp[n];
        for(int i=0;i<n;i++){
            dp[n]+=cattyNum(i,dp)*cattyNum(n-i-1,dp);
        }
        return dp[n];
    }
    public static int cattyNum2(int n)
    {   // formula is C(n)= C(0)*C(n-1) + C(1)*C(n-2) + C(2)*C(n-3)+....+ C(n-1)*C(0)
        //dp[i] means ith catalan number in 1,1,2,5,14,42,132,429,....
        int dp[]=new int[n+1];
        dp[0]=1;
        dp[1]=1;
        for(int i=2;i<=n;i++){
            for(int j=0;j<i;j++){
                dp[i]+=dp[j]*dp[i-j-1];
            }
        }
        return dp[n];
    }
    
    public static int minPartition(int arr[]){
        int sum=0;
        for(int a:arr)
            sum+=a;
        int W=sum/2;
        int dp[][]=new int[arr.length+1][W+1];
        for(int i=1;i<=arr.length;i++){
            for(int j=1;j<=W;j++){
                if(arr[i-1]<=j)
                    dp[i][j]=Math.max(arr[i-1]+dp[i-1][j-arr[i-1]],dp[i-1][j]);
                                    //include part                     exclude part
                else
                    dp[i][j]=dp[i-1][j];
            }
        }
        int sum1=dp[arr.length][W];
        int sum2=sum-sum1;
        return Math.abs(sum1-sum2);
    }
    // House Robber Problem Leetcode 198

    // lc 198 House Robber
    // tabulation approach
    public int robMemo(int[] nums) {
        int n=nums.length;
        int dp[]=new int[n];
        dp[0]=nums[0];
        if (n == 1) {
            return dp[0];
        }
        dp[1] = Math.max(nums[0], nums[1]);

        for(int i=2;i<n;i++)
        {
            dp[i]= Math.max(dp[i-1],(dp[i-2]+nums[i]));
        }

        return dp[n-1];
    }

    // Space Optimization 
    public static int rob(int[] nums) {
        int rob1=0,rob2=0;
        // Adding and changing values of rob1 and rob2
        // nums:[rob1,rob2,n,n+1,...]
        // rob1+ num means you can choose the rob nth house but cannot rob house rob2. 
        //or u can choose not to rob house n and then try to rob the houses before that
        for(int num: nums)
        {
            int temp=Math.max(rob1+num,rob2);
            rob1=rob2;
            rob2=temp;
        }
        return rob2;
        

    }
    // House robber 2 Leetcode 213
    // do the house robber on two sub arrays
    // one being from 0 to n-1 position other being 1 to n position 
    // return max of the both helper functions
    public int rob2(int[] nums) {
        if(nums.length==1)
            return nums[0];
        int num1[]=new int[nums.length-1];
        int num2[]=new int[nums.length-1];
        for(int i=0;i<nums.length-1;i++)
            num1[i]=nums[i];
        for(int i=1;i<nums.length;i++)
            num2[i-1]=nums[i];
        return Math.max(helper(num1),helper(num2));
    }
    
    public int helper(int[] nums)
    {
        int rob1=0,rob2=0;
        for(int num: nums)
        {
            int temp=Math.max(rob1+num,rob2);
            rob1=rob2;
            rob2=temp;
        }
        return rob2;
    }

    // ninja training

    public int ninjaTraining(int[][] matrix) {
        int n=matrix.length;
        int[][] dp = new int[n][4];
        for (int[] row : dp) {
            Arrays.fill(row, -1); // Initialize the dp array with -1
        }
        // Start the recursive calculation from the last day with no previous activity
        return f(n - 1, 3, matrix, dp);
    }
    public int f(int day, int last, int[][] points, int[][] dp) {
        // If the result for this day and last activity is already calculated, return it
        if (dp[day][last] != -1) return dp[day][last];

        // Base case: When we reach the first day (day == 0)
        if (day == 0) {
            int maxi = 0;
            // Calculate the maximum points for the first day by choosing an activity
            // different from the last one
            for (int i = 0; i <= 2; i++) {
                if (i != last)
                    maxi = Math.max(maxi, points[0][i]);
            }
            // Store the result in dp array and return it
            return dp[day][last] = maxi;
        }

        int maxi = 0;
        // Iterate through the activities for the current day
        for (int i = 0; i <= 2; i++) {
            if (i != last) {
                // Calculate the points for the current activity and add it to the
                // maximum points obtained so far (recursively calculated)
                int activity = points[day][i] + f(day - 1, i, points, dp);
                maxi = Math.max(maxi, activity);
            }
        }

        // Store the result in dp array and return it
        return dp[day][last] = maxi;
    }

    public int ninjaTrainingTabulation(int[][] matrix) {
        int n=matrix.length;
        int[][] dp = new int[n][4];

        for (int last = 0; last < 4; last++) {
            int maxi = 0;
            for (int i = 0; i < 3; i++) {
                if (i != last) {
                    maxi = Math.max(maxi, matrix[0][i]);
                }
            }
            dp[0][last] = maxi;
        }// updating when day==0 

        for(int ind=1;ind<n;ind++)
        {
            for (int last=0; last<4; last++) {
                int maxi = 0;
                for (int i=0; i<3; i++) {
                    if (i != last) {
                        int activity = matrix[ind][i] + dp[ind - 1][i];
                        maxi = Math.max(maxi, activity);
                    }
                }
                dp[ind][last] = maxi;
            }
        }
        return dp[n-1][3];
    }

    // leetcode 62 Unique paths
    // Bottom up tabulation approach
    public int uniquePaths(int m, int n) {
        int dp[][]=new int[m][n];
        for(int i=0;i<m;i++)
            dp[i][0]=1;
        
        for(int i=0;i<n;i++)
            dp[0][i]=1;

        for(int i=1;i<m;i++)
        {
            for(int j=1;j<n;j++)
            {
                dp[i][j]=dp[i-1][j]+ dp[i][j-1];
            }
        } 
        return dp[m-1][n-1];  
    }

    // leectdoe 63 Unique paths 2
    // same approach as above ut here an obstacle is present , skip obstacles and compute the unique path
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m=obstacleGrid.length;
        int n=obstacleGrid[0].length;
        int dp[][]=new int[m][n];
        if(obstacleGrid[m-1][n-1]==1 || obstacleGrid[0][0]==1)
            return 0;

        dp[0][0]=1;
        for(int i=0;i<m;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(obstacleGrid[i][j]==1)
                {
                    dp[i][j] =0;
                    continue;
                }
                if(i==0 &&  j==0) continue;
                int x=(i>0)?dp[i-1][j]:0; 
                int y=(j>0)?dp[i][j-1]:0; 
                dp[i][j]=x+y;
            }
        } 
        return dp[m-1][n-1];  
    }
    // leetcode 64 Minimum path sum
    // same apprach as unique path 1 
    // tabulation
    public int minPathSum(int[][] grid) {
        int n=grid.length;
        int m=grid[0].length;
        int dp[][]=new int[n][m];
        dp[0][0]=grid[0][0];
        for(int i=1;i<n;i++)
            dp[i][0]=dp[i-1][0]+grid[i][0];
        for(int i=1;i<m;i++)
            dp[0][i]=dp[0][i-1]+grid[0][i];
        
        for(int i=1;i<n;i++)
        {
            for(int j=1;j<m;j++)
            {
                int min = Math.min(dp[i-1][j], dp[i][j-1]);
                dp[i][j]= grid[i][j]+min;
            }
        }
        return dp[n-1][m-1];
    }

    // lc 120 Triangle

    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] dp = new int[n][n];
        boolean[][] visited = new boolean[n][n]; // this is not needed
        for(int i=0;i<n;i++)
            {
                Arrays.fill(dp[i],Integer.MAX_VALUE);// // max value is initialized and not -1 as the traingle can contain negative values 
            }
        return Triangle(0, 0, triangle, dp,visited);
    }
    private int Triangle(int row,int col, List<List<Integer>> triangle, int[][] dp,boolean[][] visited)
    {
        if(row==triangle.size()-1)
        {
            return triangle.get(row).get(col);
        }
        if(visited[row][col]) // not needed instead you can use the second if below
        {
            return dp[row][col];
        }
        if(dp[row][col]!=Integer.MAX_VALUE) 
            {
                return dp[row][col];
            }

        int down= triangle.get(row).get(col) + Triangle(row+1,col,triangle,dp, visited);
        int diagnol= triangle.get(row).get(col) + Triangle(row+1,col+1,triangle,dp, visited);

        dp[row][col]= Math.min(down,diagnol);
        visited[row][col] = true;

        return dp[row][col];
    }

    //leetcode 139 word break
    /*
    Is there a combination of words in wordDict that can be used to recreate the original string s.
    Notice that I mentioned the word combination, this does not mean that you will need all the words in wordDict to recreate s (Using some words from wordDict as long as they perfectly recreate the string s; is a valid solution or word break).
    Also note that you can use the words in wordDict more than once.

Take this example for reference:

Input: "bb", ["a","b","bbb","bbbb"]
Expected: true

Here, word "b" from wordDict can be used to perfectly to recreate the string s = "b" + "b" , which is why the expected output is True. Note that we did not use all the words in the dictionary and still found a valid word break.

Now, let's take another example:
Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
Output: false


    */
    public boolean wordBreak(String s, List<String> wordDict) {
        // dp memoization 
        // check for each letter in S keep appending and check of they are in the dictionary or not 
        // if we get our first true reset the substring we check upon and start from there
        // substring start is i-word.length() where i is current index in the string s and word is a word in the dict 
        // make the dp array position i true when s.substring(start,i) is the same as word . 
        // we reach the end of string and array. Check the last value of the array, then return it.

        boolean arr[]=new boolean[s.length()+1];
        arr[0]=true;
        for(int i=1;i<=s.length();i++)
        {
            for(String word:wordDict)
            {
                int start=i-word.length();
                if(start>=0 && s.substring(start,i).equals(word) && arr[start])
                { // arr[start] should be true 
                    arr[i]=true;
                    break; // break is necessary to go to the next word as the word in the dict was found in the string s so go back and c heck for new word 
                }
            }
        }

        return arr[s.length()]; 
    }
    public static void main(String[] args) {
    }

    // DP on subsequences
    // This is similar to knapscak problem of weights and 0-1 knapsack
    // addition of values mean knapsack with weight
    // choosing any one is 0-1 knapsack
    // Check if any subset of an array sums up to a given target    
    public boolean isSubsetSum(int[] arr, int target) {
        // return rec(arr,target,arr.length-1); 

        int dp[][]=new int[arr.length][target+1];
        for (int[] row : dp) {
            java.util.Arrays.fill(row, -1);
        }
        return subsetSumDp(arr.length - 1, target, arr, dp);
    }
    public boolean rec(int[] arr, int target, int ind) // recusion approach
    {        
        if(target==0)
            return true;
        if(ind==0)
            return arr[0]==target;

        boolean take=false;
        boolean no=rec(arr,target,ind-1);; // dont consider it 

        if(target>=arr[ind])
            take=rec(arr,target-arr[ind],ind-1); // consider the element as part of the subset
        
        return take || no; // OR because we have to know if subset we consider sums upt to target or not
    }
    public boolean subsetSumDp(int ind, int target, int[] arr, int[][] dp) { // Memoization
        if (target == 0) return true;
        if (ind == 0) return arr[0] == target;

        if(dp[ind][target]!=-1)
            return dp[ind][target]==1;

        boolean notTake = subsetSumDp(ind - 1, target, arr, dp);
        boolean take = false;
        if (target >= arr[ind])
            take = subsetSumDp(ind - 1, target-arr[ind], arr, dp);
        dp[ind][target] = take ? 1 : 0;
        return take || notTake;
    }
    public boolean subsetSumTabulation(int ind, int target, int[] arr, boolean[][] dp)
    { // THIS IS SIMILAR TO KNAPSACK PROBLEM
        for(int i=0;i<=ind;i++)
        {
            dp[i][0]=true;
        }
        if (arr[0]<=target) {
            dp[0][arr[0]] = true;
        }
        for(int i=1;i<=ind;i++)
        {
            for(int j=1;j<=target;j++)
            {
                boolean take=false;
                boolean notTake=dp[i-1][j];
                if(j>=arr[i])
                    take=dp[i-1][j-arr[i]];
                dp[i][j]=take || notTake;
            }
        }
        return dp[ind][target];
    }
    // lc 416 Patition equal subset sum // knapsack logic 
    public boolean canPartition(int[] nums) {
        int xuc=0;
        for(int num: nums)
            xuc+=num;
        if(xuc%2==1)
            return false;

        xuc=xuc/2;
        boolean dp[][]= new boolean[nums.length+1][xuc+1];
        for(int i=0;i<=nums.length;i++) 
            dp[i][0]=true;
        for(int i=1;i<=nums.length;i++)
        {
            for(int j=1;j<=xuc;j++)
            {
                boolean take=false;
                boolean no=dp[i-1][j];

                if(j>=nums[i-1])
                    take=dp[i-1][j-nums[i-1]];
                dp[i][j]=take || no;
            }
        }

        return dp[nums.length][xuc];

    }

    // Count subsets with sum k
    public int perfectSum(int[] arr, int K) {
        int n=arr.length;
        int dp[][]=new int[n+1][K+1];
        dp[0][0]=1;
        // dp[i][j] = number of subsets using the first i elements whose sum is exactly j
        for(int i=1;i<=n;i++)
        {
            for(int j=0;j<=K;j++)
            {
                int take=0;
                if(j>=arr[i-1])
                {
                    take =dp[i-1][j-arr[i-1]];
                    // "If I take the current number, how many ways were there to make the remaining sum using the elements before it?"
                }
                dp[i][j]=take+dp[i-1][j];
            }
        }
        return dp[n][K];

    }
    // Count partitions with given difference
    // Same as the above problem with logic change
    // Given an array arr of n integers and an integer diff, count the number of ways to partition the array into two subsets S1 and S2 such that:

    // ∣S1−S2∣ = diff and S1 ≥ S2
    // Where |S1| and |S2| are sum of Subsets S1 and S2 respectively.

    /*
    Instead of checking of difference use the following intuition below 
        set |S1|+ set |S2|= total sum of all elements in arr
        set |S1|- set |S2|= diff (which we need to find)

        Substitute S1 with total - S2 
        total - S2 - S2 = diff
        total-diff= 2*s2
        S2 = (total-diff)/2 ie Summation of all elements in set S2= (total-diff)/2
        (total-diff)/2 use this as new target and problem is now similar to Count Subsets with sum k
    */
    // This is exactly the same as LC 494 Target Sum no code changes
    public int countPartitions(int n, int diff, int[] arr) {
        int tot=0;
        for(int a:arr)
            tot=tot+a;
        // return 0 if tot-diff  <0 or (tot-diff)%2==1 
        if(tot-diff<0 || (tot-diff)%2==1) return 0;
        int target=(tot-diff)/2;
        
        int dp[][]=new int[n+1][target+1];
        dp[0][0]=1;
        for(int i=1;i<=n;i++)
        {
            for(int j=0;j<=target;j++)
            {
                int take=0;
                if(j>=arr[i-1])
                {
                    take =dp[i-1][j-arr[i-1]];
                }
                dp[i][j]=take+dp[i-1][j];
            }
        }
        return dp[n][target];
    }
    // lc 322 Coin change 1 
    private int helper(int[] coins, int i, int amount, int[][] dp) {
        if (amount == 0) {
            return 0;
        }
        // dp[i][amount] = minimum coins needed to make `amount` using the first `i` coin types
        if (i == 0) {
            return amount + 1;
        }
        if (dp[i][amount] != -1) {
            return dp[i][amount];
        }
        int take = amount + 1;
        if (amount >= coins[i - 1]) {
            take = 1 + helper(
                coins,
                i,
                amount - coins[i - 1],
                dp
            );
        }

        int notTake = helper(
            coins,
            i - 1,
            amount,
            dp
        );

        return dp[i][amount] = Math.min(take, notTake);
    }
    // impo disticnton here 
    // we do not change the value of i in recursion / tabulation because  We have: coins = [1, 2, 5]
    // Suppose we take 5. We now have:
    // 11
    // ↓ take 5
    // 6
    // Can we take another 5? Yes! So when we solve the remaining 6, we must still have access to the 5. Therefore: helper(i, j - coins[i - 1])
    /*
                    dp[i][j]
                       |
              ┌────────┴────────┐
            TAKE              NOT TAKE
             |                    |
          use coin             don't use coin
             |                    |
     1 + dp[i][j-coin]       dp[i-1][j]
    */
    public int coinChange(int[] coins, int amount) {
        int n=coins.length;
        int dp[][]=new int[n+1][amount+1];
        // dp[i][j] = minimum number of coins required to make amount j using the first i coins
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], amount + 1);
        }
        // We're using amount + 1 as a fake infinity. As if ex is 11 and amount is 10, we can't make 11 using 10 coins.
        for(int i=0;i<=n;i++) dp[i][0]=0; 
        for(int i=1;i<=n;i++)
        {
            for(int j=1;j<=amount;j++)
            {
                int take =amount+1;
                if(j>=coins[i-1])
                    take=1+dp[i][j-coins[i-1]] ;// If i take the current coin then add one to :  how many ways were there to make the remaining sum using the coins before it
                dp[i][j]=Math.min(take,dp[i-1][j]);
            }
        }
        return dp[n][amount]>amount? -1:dp[n][amount];
    }

    // DP on stocks 
    // Remeber that sopace optimization is important 

    /*
    LC 121
One transaction
→ Track minimum buying price
        ↓
LC 122
Unlimited transactions
→ State = (day, buy)
        ↓
LC 123
At most 2 transactions
→ State = (day, buy, cap)
        ↓
LC 188
At most K transactions
→ Same state, cap = K
        ↓
LC 309
Unlimited transactions + cooldown
→ Same (day, buy) state
→ Selling jumps to i + 2
    */


    // lc 121 best time to buy and sell stock
    // Space optimization

    /*
    Simple explanation You can make only one transaction: Buy once → Sell once.
    You want the maximum profit: selling price - buying price . The important restriction is that you must buy before you sell.
    solution is based on a very simple observation: For every day, if I want to sell today, I should have bought at the cheapest price seen before today.
    So you maintain two things:  minimum → cheapest stock price seen so far, profit → maximum profit found so far
    */
    /*
1. Only ONE transaction is allowed: buy once and sell once.
2. For every day, assume I sell today.
3. To maximize today's profit, I need the minimum price seen before today.
4. Keep `minimum` = cheapest price seen so far.
5. Calculate today's profit = current price - minimum.
6. Keep the maximum profit found so far.
7. Since only minimum price and maximum profit matter, space can be optimized to O(1).
*/
    public int maxProfit(int[] prices) {
        int minimum=prices[0];
        int cost=0,profit=0;

        for(int i=1;i<prices.length;i++)
        {
            cost=prices[i]-minimum; // cost of seeling a particular stock on the ith day
            profit=(Math.max(profit,cost)); // saving max profit
            minimum=Math.min(minimum,prices[i]); // saving minimum price of the stocke so that we can have max profit
        }
        return profit;
    }

    // lc 122 Best time to buy and sell stock 2
    // difference between 121 and 122 is that here we can buy and sell multiple times
    // But we can only hold one stock at a time but we have unlimited transactions A transaction means: Buy + Sell
    // We cannot buy other stocks unless we esell them 

    // recursion time complexity O(2^n) space complexity O(n)
    // This will have overallaping sub problems and hence time complexity will be exponential

    /*
    At every day, there are only two situations:

buy == 1 → you are allowed to buy
buy == 0 → you currently have a stock, so your meaningful choice is to sell

From either state, you have two choices.

When buy == 1
You can Buy today:You pay the price: -prices[i] and move to the state where you cannot buy.

Or:
Don't buy today
Stay in the same state and move to tomorrow. So you're asking: "Is it better to buy today or wait?"

When buy == 0

You can:

Sell today: You receive: +prices[i] and return to the state where you can buy again.
Or:
Don't sell todayKeep holding and move to tomorrow.

So you're asking:"Is it better to sell today or keep holding?" That's the entire DP. The state is basically: Which day am I on + am I currently allowed to buy or not?
    */

    /*
1. Unlimited transactions are allowed, but only one stock can be held at a time.
2. State = current day + whether I am allowed to buy.
3. buy = 1 means I can buy; buy = 0 means I currently hold a stock and can sell.
4. At every state, I have two choices: take the action or skip the day.
5. Buying costs prices[i], while selling adds prices[i].
6. Selling returns me to the buy state, allowing another transaction.
7. Memoization stores (day, buy); tabulation builds the same states iteratively.
*/
    
    public int profit(int i, int buy,int prices[])
    {
        if(i==prices.length)
            return 0;
        int profit=0;

        if(buy==1){ // 1 here means you are allowed to buy today
            int buyIt=profit(i+1,0,prices)-prices[i]; // i have the choice to buy today at day i hence it is -ve as i am buying
            int notBuyIt=profit(i+1,1,prices); // i dont want to buy on this day
            profit=Math.max(buyIt,notBuyIt); // to consider max profit if i buy on this day or not 
        }
        else{ // 0 means you cannot buy
            int sellIt=profit(i+1,1,prices)+prices[i]; // I have a choice to sell today so i will sell hence it is +ve as prices[i]  will get aded
            int notSellIt=profit(i+1,0,prices); // I dont want to sell on this day
            profit=Math.max(sellIt,notSellIt); // to consider max profit if i sell on this day or not
        }
        return profit;
    }
    // Memoization Approach
    public int profit(int i, int buy,int prices[], int dp[][])
    {
        if(i==prices.length)
            return 0;
        int profit=0;
        if(dp[i][buy]!=-1)
            return dp[i][buy];
        if(buy==1){ // 1 here means you are allowed to buy today
            int buyIt=profit(i+1,0,prices,dp)-prices[i]; // i have the choice to buy today at day i hence it is -ve as i am buying
            int notBuyIt=profit(i+1,1,prices,dp); // i dont want to buy on this day
            profit=Math.max(buyIt,notBuyIt); // to consider max profit if i buy on this day or not 
            dp[i][buy]=profit;
        }
        else{ // 0 means you cannot buy
            int sellIt=profit(i+1,1,prices,dp)+prices[i]; // I have a choice to sell today so i will sell hence it is +ve as prices[i]  will get aded
            int notSellIt=profit(i+1,0,prices,dp); // I dont want to sell on this day
            profit=Math.max(sellIt,notSellIt); // to consider max profit if i sell on this day or not
            dp[i][buy]=profit;
        }
        return dp[i][buy]=profit;
    }
    // Tabulation Approach 
    // Remeber tabulation is used to remove auxillary stack space occupied by memoization

    /*
            HOLDING                 NOT HOLDING
                |                         |
            dp[i][0]                    dp[i][1]
                |                         |
       ┌────────┴───────┐       ┌─────────┴─────────┐
       ↓                ↓       ↓                   ↓
  keep holding      buy today  stay out         sell today
       ↓                ↓       ↓                   ↓
 dp[i-1][0]    dp[i-1][1]-price  dp[i-1][1]  dp[i-1][0]+price
    */
    public int profit(int prices[])
    {
        int dp[][]=new int[prices.length][2];
        for(int i=0;i<prices.length;i++)
        {
            if(i==0)
            {
                dp[0][0]=-prices[0];  // -proces[i] because i currently HAVE THAT STOCK AND NOT ALLOWED TO BUY
                dp[0][1]=0; // dont have anything i have bought
            }
            else
            {
                dp[i][0]=Math.max(dp[i-1][0],dp[i-1][1]-prices[i]); // dp[i][0] means max profit from day i if we have held the stock  
                // dp[i-1][0] You already had a stock and don't sell today. dp[i-1][1]-prices[i]  You were not holding a stock yesterday and buy today.
                dp[i][1]=Math.max(dp[i-1][1],dp[i-1][0]+prices[i]); // dp[i][1] means max profit from day i When i am allowed to buy
                // dp[i-1][1] Dont sell today. dp[i-1][0]+prices[i] Sell today
            }
        }
        return dp[prices.length-1][1]; // returning dp[prices.length-1][1] as 1 means there is nothing to buy no  and i have no stock with me on the last day
    }
    // Space optimization
    public int profitSpaceOptimized(int[] prices) {
        int buy = -prices[0];
        int notBuy = 0;
    
        for (int i = 1; i < prices.length; i++) {
            int newBuy = Math.max(buy, notBuy - prices[i]);
            int newNotBuy = Math.max(notBuy, buy + prices[i]);
    
            buy = newBuy;
            notBuy = newNotBuy;
        }
    
        return notBuy;
    }

    // LC 123 Best time to buy and sell stock 3
    // diff between this and LC 122 is that here we have a limt on the transactions we can make 
    // in the previous problem we can make any number of transactions (A transaction is nothing but buying and selling that stock) 
    // In this we can only do upto 2 transactions where cap represents the number of transactions remaining. So max value of cap is 2 atmost
    // reccusrive soln
    // SC : O(N) and TC : O(2^N) Gives TLE

    /*
    The only new thing is what happens to cap. You only decrease cap when you sell.Why? Because:

Buy alone isn't a completed transaction.
Buy + Sell = one completed transaction.

Therefore:

SELL → cap - 1

Your recursion stops when:
cap == 0
because no more transactions are available.
    */

/*
1. Same stock DP as LC 122, but now there is a transaction limit.
2. State = day + buy/sell state + transactions remaining.
3. buy = 1 means I can buy; buy = 0 means I am holding and can sell.
4. A transaction is completed only when I SELL, so cap decreases on selling.
5. Buying does not decrease cap because the transaction is not completed yet.
6. For every state, choose between taking the action or skipping the day.
7. For tabulation, process day from n-1 toward 0 because states depend on future days.
*/
    public int profit(int i, int buy,int prices[],int cap)
    {
        if(cap==0)
            return 0;
        if(i==prices.length)
            return 0;
        int profit=0;

        if(buy==1){ // 1 here means you are allowed to buy today
            int buyIt=profit(i+1,0,prices,cap)-prices[i]; // i have the choice to buy today at day i hence it is -ve as i am buying
            int notBuyIt=profit(i+1,1,prices,cap); // i dont want to buy on this day
            profit=Math.max(buyIt,notBuyIt); // to consider max profit if i buy on this day or not 
        }
        else{ // 0 means you cannot buy
            int sellIt=profit(i+1,1,prices,cap-1)+prices[i]; // I have a choice to sell today so i will sell hence it is +ve as prices[i]  will get aded
            int notSellIt=profit(i+1,0,prices,cap); // I dont want to sell on this day
            profit=Math.max(sellIt,notSellIt); // to consider max profit if i sell on this day or not
        }
        return profit;
    }
    // Acceptable soln
    public int profit(int i, int buy,int prices[],int cap, int dp[][][])
    {
        if(cap==0)
            return 0;
        if(i==prices.length)
            return 0;
        if(dp[i][buy][cap]!=-1)
            return dp[i][buy][cap];
        int profit=0;

        if(buy==1){ // 1 here means you are allowed to buy today
            int buyIt=profit(i+1,0,prices,cap,dp)-prices[i]; // i have the choice to buy today at day i hence it is -ve as i am buying
            int notBuyIt=profit(i+1,1,prices,cap,dp); // i dont want to buy on this day
            profit=Math.max(buyIt,notBuyIt); // to consider max profit if i buy on this day or not 
        }
        else{ // 0 means you cannot buy
            int sellIt=profit(i+1,1,prices,cap-1,dp)+prices[i]; // I have a choice to sell today so i will sell hence it is +ve as prices[i]  will get aded
            int notSellIt=profit(i+1,0,prices,cap,dp); // I dont want to sell on this day
            profit=Math.max(sellIt,notSellIt); // to consider max profit if i sell on this day or not
        }
        return dp[i][buy][cap]=profit;
    }
    // Space optimization
    // For tabulation problems here onwards it is important to remember one thing 
    // in the given example below i goes from n-1 to 0 whereas in memoization it goes from 0 to n-1
    // to figure this out check the memoization code and get the changing variables 
    // if the changing variables go from i=0 to n-1 so in tabulation it will be the other way around remember this point
    // i is the only variable changing in memoization hence taking the opposite way
    // This is seen in LIS and best time to buy sell stock problems 

    public int maxProfitSpaceOptimized(int prices[])
    {
        int n = prices.length;
        int dp[][][] = new int[n + 1][2][3];
        for (int i = n - 1; i >= 0; i--) {
            for (int buy = 0; buy < 2; buy++) {
                for (int cap = 1; cap < 3; cap++) {
                    if (buy == 1) {
                        // Buy OR skip
                        dp[i][buy][cap] = Math.max(dp[i + 1][0][cap] - prices[i],dp[i + 1][1][cap] );

                    } else {
                        // Sell OR skip
                        dp[i][buy][cap] = Math.max(dp[i + 1][1][cap - 1] + prices[i],dp[i + 1][0][cap]);
                    }
                }
            }
        }
        return dp[0][1][2];
    }
    // lc 188 Best time to buy and sell stock 4
    // diff between this and above problem is instead of having 2 transactions at a time we can have atmost k transactions 
    // code is exact same as the above but now it is just adding a k to the dp array

/*
1. LC 188 is the generalized version of LC 123.
2. Instead of at most 2 transactions, we can make at most K transactions.
3. State = day + buy state + transactions remaining.
4. buy = 1 means I can buy; buy = 0 means I currently hold a stock.
5. Decrease cap only when selling because Buy + Sell completes one transaction.
6. The transitions are exactly the same as LC 123.
7. Only the transaction dimension changes from 2 to K.
*/
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
       // dp[i][buy][cap]
       // i   = current day
       // buy = 1 -> can buy, 0 -> can sell
       // cap = number of transactions remaining
       int dp[][][] = new int[n + 1][2][k+1];
       for (int i = n - 1; i >= 0; i--) {
           for (int buy = 0; buy < 2; buy++) {
               for (int cap = 1; cap <=k; cap++) {
                   if (buy == 1) {
                       // Buy OR skip
                       dp[i][buy][cap] = Math.max(dp[i + 1][0][cap] - prices[i],dp[i + 1][1][cap] );

                   } else {
                       // Sell OR skip
                       dp[i][buy][cap] = Math.max(dp[i + 1][1][cap - 1] + prices[i],dp[i + 1][0][cap]);
                   }
               }
           }
       }
       return dp[0][1][k];
   }

   // lc 309 Best time to buy and sell with cooldown
   // here cooldown means you cannot buy on the next day after selling
   // Buy,..Sell,cooldown day(cannot buy immidiately after selling), buy.. etc
   // same as best time to buyb and sell 2 as this problem has unlimiited tracsactions

/*
1. This is the unlimited-transactions stock DP with one extra cooldown rule.
2. State = day + whether I am allowed to buy.
3. If I can buy: either buy today or skip today.
4. If I hold a stock: either sell today or keep holding.
5. After selling today, tomorrow is forced to be a cooldown day.
6. Therefore selling moves from i to i+2 instead of i+1.
7. Tabulation must be built from right to left because states depend on future days.
*/

   public int profitWithCooldown(int i, int buy,int prices[], int dp[][])
    {
        if(i>=prices.length)
            return 0;
        int profit=0;
        if(dp[i][buy]!=-1 && i<prices.length-1)
            return dp[i][buy];
        if(buy==1){ // 1 here means you are allowed to buy today
            int buyIt=profit(i+1,0,prices,dp)-prices[i]; // i have the choice to buy today at day i hence it is -ve as i am buying
            int notBuyIt=profit(i+1,1,prices,dp); // i dont want to buy on this day
            profit=Math.max(buyIt,notBuyIt); // to consider max profit if i buy on this day or not 
            dp[i][buy]=profit;
        }
        else{ // 0 means you cannot buy
            int sellIt=profit(i+2,1,prices,dp)+prices[i]; // I have a choice to sell today so i will sell hence it is +ve as prices[i]  will get aded
            // i+2 because i cannot buy on the next day, this is the cooldown part when we sell we cannot buy the next day
            int notSellIt=profit(i+1,0,prices,dp); // I dont want to sell on this day
            profit=Math.max(sellIt,notSellIt); // to consider max profit if i sell on this day or not
            dp[i][buy]=profit;
        }
        return dp[i][buy]=profit;
    }
    // tabluation
    /*
    Your state is:

dp[i][buy]

buy = 1 → we are allowed to buy
buy = 0 → we are holding stock, so we can sell

When buy == 1:

Buy → dp[i+1][0] - prices[i]
Skip → dp[i+1][1]

When buy == 0:

Sell → dp[i+2][1] + prices[i] ← cooldown!
Skip → dp[i+1][0]
    
    */
    public int maxProfitWithCooldown(int[] prices) {
        int dp[][]=new int[prices.length+2][2];
        for(int i=prices.length-1;i>=0;i--)
        {
            for(int buy=0;buy<=1;buy++)
            {
                if(buy==1)
                {
                    dp[i][buy]=Math.max(dp[i+1][1],dp[i+1][0]-prices[i]);
                }
                else
                {
                    dp[i][buy]=Math.max(dp[i+1][0],dp[i+2][1]+prices[i]); 
                }
            }
        }
        return dp[0][1];
    }
    /*
 * ==================== LIS / SUBSEQUENCE DP ====================
 *
 * Recognize this pattern when the problem asks for:
 * - Longest / maximum / minimum subsequence under some condition.
 * - A sequence must maintain the original order, but elements can be skipped.
 * - We are selecting elements one-by-one and deciding TAKE vs SKIP.
 * - A valid next element depends on the previously selected element.
 * - Common conditions: increasing, decreasing, divisible, compatible,
 *   valid chain, previous element < current element, etc.
 *
 * Main mental question:
 * "If I choose this element, what previous element/state does it depend on?"
 *
 * Common 2D state:
 * dp(i, prev) = best subsequence starting at i given previous selected index.
 *
 * Common optimized 1D state:
 * dp[i] = best subsequence ending specifically at index i.
 *
 * Typical transition:
 * If current element can follow prev:
 *     take = 1 + dp[prev]
 * Otherwise skip / consider another previous element.
 *
 * Variations can ask for:
 * - Length of the subsequence
 * - Number of LIS
 * - Print/reconstruct the LIS
 * - Longest chain based on a custom comparison
 *
 * KEY DIFFERENCE:
 * Subsequence = order matters, but elements between chosen elements
 * can be skipped.
 * ================================================================
 */


    // Longest Increasing Subsequence pattern
    // Subsequence where all the elements follow the sequence in the origanl arrasy but are in increasing order
    // EX: [1,3,2,4,5] has a LIS as 1,3,4,5 or 1,2,4,5 are in increasing order and ans is 4.
    // Given an array, find the length of the longest subsequence where:
    // Elements must remain in their original order.
    // Every next element must be strictly greater than the previous selected element

    /*
    There are two ways you've represented the LIS state:
Recursive / Memoization / 2D Tabulation Think:

"I'm at index i. What did I select previously?"
state = (i, prev)

You are making a future decision:
TAKE / SKIP


1D DP Think:
"What is the longest increasing subsequence that ENDS at i?"
state = i

You are looking backward:
Can I attach arr[i] to a previous increasing subsequence?
    */

    /*
1. At every index, decide whether to TAKE or SKIP the current element.
2. State = current index i + index of previously selected element prev.
3. TAKE is possible if there is no previous element or arr[i] > arr[prev].
4. If I TAKE arr[i], it becomes the new prev for the next state.
5. If I SKIP arr[i], prev remains unchanged.
6. Return the maximum length between TAKE and SKIP.
7. Recursion explores all subsequences, giving O(2^n) time.
*/

    // recusrive Tc O(2^n) because we are doing take or not take hence 2^n and SC O(n)  
    public int LIS(int[] arr, int i, int prev) {
        if(i==arr.length)
            return 0;

        int len1=0,len2=0;
        if(prev==-1 || arr[i]>arr[prev]) // prev==-1 means first element so take the first element , arr[i]>arr[prev] means if the current element is greater than the previous element take it to make the LIS
        {
            len1=1+LIS(arr,i+1,i); // take condition , since we consider arr[i] we have to pass i as the prev ind and increment i to be passed as the new index 
            // Why does prev become i? Because the element I just selected becomes my new previous element.
        }
        len2=LIS(arr,i+1,prev);// not take condition 
        return Math.max(len1,len2);
    }
    public int LIS(int[] arr) {
        int dp[][]=new int[arr.length][arr.length+1];
        for(int i=0;i<arr.length;i++)
        {
            Arrays.fill(dp[i],-1);
        }

        return LISM(arr,0,-1,dp);
    }
    // memoization
    // Tc O(n*n) and SC O(n*n)
    // Overlapping Sub problems hence we need to convert into memoization, The problem with recursion is that you repeatedly reach the same (i, prev) state.
    // here we need to take care of cordinate shift , this basically means we store the data ofr dp[i][prev]at dp[i][prev+1] this is done to include prev=-1 case as well

    // This is impo for this LIS problem as we need to take care of prev=-1 case
    // hence answer is always at dp[i][prev+1] and not dp[i][prev]

    /*
        The important prev = -1 problem ,Your prev can be:
-1, 0, 1, 2, ...
But arrays cannot use index -1.So you perform a coordinate shift:

actual prev     stored index

-1              0
 0              1
 1              2
 2              3
...

Hence: dp[i][prev + 1]. This is an extremely important LIS detail. The answer starts at:
i = 0
prev = -1
so:
dp[0][-1 + 1]
=
dp[0][0]
    */

/*
1. Same TAKE/SKIP intuition as recursion, but cache repeated states.
2. State = (i, prev), representing the best LIS from i with previous index prev.
3. TAKE if prev == -1 or arr[i] > arr[prev].
4. TAKE makes i the new prev; SKIP keeps prev unchanged.
5. prev can be -1, so use coordinate shifting: store it at prev + 1.
6. Therefore dp[i][prev + 1] represents the actual state (i, prev).
*/
    public int LISM(int[] arr, int i, int prev, int dp[][]) {
        if(i==arr.length)
            return 0;

        if(dp[i][prev+1]!=-1)
            return dp[i][prev+1];

        int len1=0,len2=0;
        if(prev==-1 || arr[i]>arr[prev]) // prev==-1 means first element so take the first elemen , arr[i]>arr[prev] means if the current element is greater than the previous element take it to make the LIS
        {
            len1=1+LISM(arr,i+1,i,dp); // take condition , since we consider arr[i] we have to pass i as the prev ind and increment i to be passed as the new index 
        }
        len2=LISM(arr,i+1,prev,dp);// not take condition 
        return dp[i][prev+1]=Math.max(len1,len2);
    }
    // Tabulation Tc O(n*n) and SC O(n*n)
    /*
1. Tabulation uses the exact same TAKE/SKIP state as memoization.
2. State = (ind, prev), and the answer depends on ind + 1.
3. Since future index states are required, build ind from n-1 down to 0.
4. TAKE gives 1 + answer after selecting arr[ind].
5. SKIP keeps prev unchanged and moves to the next index.
6. Coordinate shifting is still needed because prev can be -1.
7. Time is O(n²) because we evaluate every (ind, prev) state.
*/
    public int LIST(int arr[])
    {
        int dp[][]=new int[arr.length][arr.length+1];
        for(int ind=arr.length-1;ind>=0;ind--)
        {
            for(int prev=ind-1;prev>=-1;prev--)
            {
                int len1=0,len2=0;
                if(prev==-1 || arr[ind]>arr[prev])
                {
                    len1=1+dp[ind+1][ind];
                }
                len2=dp[ind+1][prev];
                dp[ind][prev]=Math.max(len1,len2);
            }
        }
        return dp[0][-1+1]; // dp[0][-1] is the LIS
    }
    
    // space optimization
    // lc 300 Length of LIS

    /*
    Instead of asking: "What is the LIS from index i given some previous index?"
    your 1D solution asks: "What is the longest increasing subsequence that ENDS at index i?"

    dp[i] means Length of the longest increasing subsequence whose last element is arr[i].
    Initially: Arrays.fill(dp, 1) Why is every value at least 1?
    Because every individual element can form a subsequence of length 1.

    Now consider: arr[i]
    Look at every previous element:
    arr[0] ... arr[i-1]

    If: arr[prev] < arr[i] then arr[i] can be attached to the increasing subsequence ending at prev. That gives:
        1 + dp[prev] : The 1 represents the current element arr[i].

So you're essentially asking:
"Among all increasing subsequences that I can extend with arr[i], which one is the longest?" Hence:
dp[i] = max(dp[i], 1 + dp[prev])

Finally, why don't you return simply dp[n-1]? Because the LIS doesn't necessarily end at the last element. ex:
For: [10, 9, 2, 5, 3, 7, 101, 18] the LIS could end at 101, or 18, etc.
Therefore you maintain: maxi - to find the largest dp[i] across all ending positions.
     */

/*
1. Define dp[i] as the LIS length ending specifically at arr[i].
2. Every element can start a subsequence, so initialize every dp[i] to 1.
3. For every previous index prev < i, check if arr[prev] < arr[i].
4. If increasing, arr[i] can extend the subsequence ending at prev.
5. Therefore dp[i] = max(dp[i], 1 + dp[prev]).
6. The LIS can end anywhere, so take the maximum dp[i] over the entire array.
7. Time is O(n²) and space is O(n), making this simpler than the 2D DP.
*/
    public int LIS(int arr[],int n)
    {
        int dp[]=new int[n];
        int maxi=-1;
        Arrays.fill(dp,1);
        for(int ind=0;ind<n;ind++)
        {
            for(int prev=0;prev<ind;prev++)
            {
                if(arr[ind]>arr[prev] && 1+dp[prev]>dp[ind])
                {
                    dp[ind]=1+dp[prev];
                }
            }
            maxi=Math.max(maxi,dp[ind]);
        }

        return maxi;
    }

    /*
    For example: 1 → 3 → 4 → 5 Suppose dp[5] = 4. You need to know: Where did 5 come from?
That's exactly what your: hash[i] stores.

You initialize: hash[i] = i meaning:
"Currently, I consider i to be the starting point of its own chain." 
When you discover that nums[prev] can extend the LIS ending at prev:
nums[i] > nums[prev]

and it produces a longer sequence:
dp[i] < 1 + dp[prev]

you do: hash[i] = prev;

Meaning: "The LIS ending at i came from prev."

So imagine: 1 ← 3 ← 4 ← 5 Your hash array stores these links.

Finding where the LIS ends - 

You maintain:
lastIndex

Whenever you find a larger dp[i], you remember that index. So:
lastIndex = endpoint of the longest LIS ending at i. Then you walk backward:

lastIndex
   ↓
hash[lastIndex]
   ↓
hash[previous]
   ↓
...

until: hash[lastIndex] == lastIndex

which means you've reached the beginning. But you've reconstructed the sequence backwards, so:
Collections.reverse(ans); puts it back into the original increasing order.
    */

/*
1. First use normal LIS DP: dp[i] = LIS length ending at i.
2. hash[i] stores the previous index from which the LIS ending at i came.
3. Whenever prev can extend i and gives a longer LIS, update dp[i] and hash[i].
4. lastIndex stores the endpoint of the overall longest LIS.
5. Start from lastIndex and follow hash[] backward to reconstruct the sequence.
6. hash[i] = i means we reached the starting element of the chain.
7. Reverse the reconstructed elements because we collected them from end to start.
*/

    public List<Integer> printLIS(int[] nums) {
        int n=nums.length;
        int dp[]=new int[n];
        int hash[]=new int[n];
        Arrays.sort(nums);
        Arrays.fill(dp,1);
        int maxi=1-1;
        int lastIndex=0;
        for (int i = 0; i < n; i++) {
            hash[i]=i;
        }

        for(int i=0;i<n;i++)
        {
            for(int prev=0;prev<i;prev++)
            {
                if(nums[i]>nums[prev] && dp[i]<1+dp[prev])
                {
                    dp[i]=dp[prev]+1;
                    hash[i]=prev;
                }
            }
            if(dp[i]>maxi)
            {
                maxi=dp[i];
                lastIndex=i;
            }
        }
        List<Integer> ans = new ArrayList<>();
        while (hash[lastIndex] != lastIndex) {
            ans.add(nums[lastIndex]);
            lastIndex = hash[lastIndex];
        }
        ans.add(nums[lastIndex]);
        Collections.reverse(ans);

        return ans;

    }

    // lc 368 Largest Divisible Subset

    // Same logic as printing the LIS 
    // Given an array, find the largest subset where: For every pair of elements, one must divide the other. Example: [1, 2, 4, 8] works because:
    // 2 % 1 = 0
    // 4 % 2 = 0
    // 8 % 4 = 0

    // So the answer length is 4.

    /*
    irst, you sort the numbers.

That is extremely important because after sorting, if:
prev < i: then:  nums[prev] <= nums[i]
So you only need to check: nums[i] % nums[prev] == 0. You don't need to separately check the opposite direction.

Now think exactly like LIS , dp[i] means: Largest divisible subset ending at nums[i].

For every previous number: nums[prev] , ask:

"Can I append nums[i] to the divisible subset ending at prev?"

Your condition:

nums[i] % nums[prev] == 0

answers that.

If yes: dp[i] = 1 + dp[prev]

And just like Print LIS, you use: hash[i] = prev; to remember the actual chain.

So conceptually:

LIS:
Can prev → i be connected?
        ↓
arr[i] > arr[prev]

Divisible subset:
Can prev → i be connected?
        ↓
arr[i] % arr[prev] == 0
Everything else is basically the same.
    */

    /*
1. Sort the array so previous elements are <= current elements.
2. dp[i] = largest divisible subset length ending at nums[i].
3. For every prev < i, check whether nums[i] is divisible by nums[prev].
4. If divisible, nums[i] can extend the subset ending at prev.
5. Update dp[i] and hash[i] when this produces a longer subset.
6. Track lastIndex of the largest dp value to find the final chain.
7. Follow hash[] backward and reverse to reconstruct the subset.
*/

    public List<Integer> largestDivisibleSubset(int[] nums) {
        int n=nums.length;
        int dp[]=new int[n];
        int hash[]=new int[n];
        Arrays.sort(nums);
        Arrays.fill(dp,1);
        int maxi=1-1;
        int lastIndex=0;
        for (int i = 0; i < n; i++) {
            hash[i]=i;
        }

        for(int i=0;i<n;i++)
        {
            for(int prev=0;prev<i;prev++)
            {
                if(nums[i]%nums[prev]==0 && dp[i]<1+dp[prev])
                {
                    dp[i]=dp[prev]+1;
                    hash[i]=prev;
                }
            }
            if(dp[i]>maxi)
            {
                maxi=dp[i];
                lastIndex=i;
            }
        }
        List<Integer> ans = new ArrayList<>();
        while (hash[lastIndex] != lastIndex) {
            ans.add(nums[lastIndex]);
            lastIndex = hash[lastIndex];
        }
        ans.add(nums[lastIndex]);
        Collections.reverse(ans);

        return ans;

    }

    // lc 1048 Longest String Chain

    // Longest string chain is a chain of string predecessors
    // ex ab is the predesessor of abc and abc is the predecessor of abcd, the difference is justv an addition of a extra char
    // find the longest string chain of such predecessors
    // Input: words = ["a","b","ba","bca","bda","bdca"]
    // Output: 4
    // Explanation: One of the longest word chains is ["a","ba","bda","bdca"].

    /*
    Instead of asking: "Is arr[prev] < arr[i]?" we ask: "Can words[prev] be the predecessor of words[i]?"

That's what your: compareStrings(words[i], words[prev]) checks.

Why sort by length?Your code starts with:
Arrays.sort(words, Comparator.comparingInt(String::length)); This ensures shorter words come first.

Why? Because a predecessor must be exactly one character shorter.

Therefore when you're at: words[i], you only need to look backward at possible shorter words.
This gives you the same directional structure as LIS: prev → i

dp[i] = longest valid string chain ending at words[i].

For every previous word: words[prev] , you ask: "Can this word be the predecessor of words[i]?"

If yes:
dp[i] = 1 + dp[prev]

So the DP structure hasn't changed at all. Only the definition of "can prev connect to i?" has changed.
    */

/*
1. Sort words by length so every possible predecessor appears before its word.
2. dp[i] = longest valid string chain ending at words[i].
3. For every prev < i, check if words[prev] is a valid predecessor of words[i].
4. A predecessor must have exactly one fewer character.
5. compareStrings() checks whether the shorter word can be obtained by removing one character.
6. If valid, extend the chain: dp[i] = 1 + dp[prev].
7. Track the maximum dp[i] because the longest chain can end at any word.
*/

    public int longestStrChain(String[] words) {
        Arrays.sort(words, Comparator.comparingInt(String::length));
        int n=words.length;
        int dp[]=new int[n];
        Arrays.fill(dp,1);
        int maxi=1;
        for(int i=1;i<n;i++)
        {
            for(int prev=0;prev<i;prev++)
            {
                if(compareStrings(words[i],words[prev]) && dp[i]<1+dp[prev])
                {
                    dp[i]=dp[prev]+1;
                }
            }
            if(dp[i]>maxi)
            {
                maxi=dp[i];
            }
        }
        
        return maxi;

    }
    public boolean compareStrings(String s1,String s2)
    {
        // Try to match every character of the shorter word inside the longer word while allowing exactly one extra character in the longer word.
        if(s1.length()!=s2.length()+1) // because the longer word must contain exactly one additional character.
            return false;

        int p1=0,p2=0;
        while(p1<s1.length())
        {
            if(p2<s2.length() && s1.charAt(p1)==s2.charAt(p2))
            {
                p1++;p2++;
            }
            else{
                p1++;
            }
        }
        if(p1==s1.length() && p2==s2.length())
            return true;
        
        return false;
    }
    // lc 673 Number of Longest Increasing Subsequences 
    /*
    dp[i] means: Length of the longest increasing subsequence ending at i.
    You also need: cnt[i] which means: Number of longest increasing subsequences of length dp[i] that end at i.
    Initially:
dp[i] = 1
cnt[i] = 1
Why? Every element by itself is an LIS of length 1.

Case 1 — We found a longer LIS Your code checks:

dp[i] < 1 + dp[prev] That means: "The sequence ending at prev, followed by nums[i], is better than anything I previously had for i."

So: dp[i] = dp[prev] + 1;

cnt[i] = cnt[prev]; Why don't we add?
Because we've discovered a new best length.
The old sequences at i are no longer relevant because we're only counting sequences having the new maximum length.

So we replace the count.

Case 2 — We found another way to achieve the same longest length

Your second condition: dp[i] == 1 + dp[prev] That means:
"I already have an LIS of this length ending at i, but prev gives me another way to create the same length."
Therefore:

cnt[i] += cnt[prev];

Now we add because we've discovered additional LISs of the same optimal length.

Finally After calculating every dp[i], you know the overall LIS length:
maxi

But the LIS can end at different positions. So you go through every index:
if(dp[i] == maxi)
    ans += cnt[i];

Meaning: "If an LIS ends here and has the global maximum length, add all LISs ending here."
    */
    public int findNumberOfLIS(int[] nums) {
        int n=nums.length;
        int dp[]=new int[n];
        int cnt[]=new int[n];
        Arrays.fill(dp,1);
        Arrays.fill(cnt,1);
        int maxi=1;
        for(int i=0;i<n;i++)
        {
            for(int prev=0;prev<i;prev++)
            {
                if(nums[i]>nums[prev]  && dp[i]<1+dp[prev])
                {
                    dp[i]=dp[prev]+1;
                    cnt[i]=cnt[prev];
                }
                else if(nums[i]>nums[prev]  && dp[i]==1+dp[prev])
                {
                    cnt[i] += cnt[prev];
                }
            }
            if(dp[i]>maxi)
            {
                maxi=dp[i];
            }
        }
        int ans=0;
        for(int i=0;i<n;i++)
        {
            if(dp[i]==maxi)
                ans+= cnt[i];
        }
        
        return ans;
    }

    /*
 * ==================== MCM / PARTITION DP ====================
 *
 * Recognize this pattern when the problem asks you to:
 * - Partition / split an array, string, or range into parts.
 * - Decide where to place cuts.
 * - Try every possible partition point k.
 * - Combine answers from the left and right portions.
 * - Find minimum/maximum cost after performing a sequence of operations.
 *
 * Strong hint:
 * "Choose a partition point / split point / last operation."
 *
 * Main mental question:
 * "If I solve the range from i to j, where should I make the
 * final partition?"
 *
 * Common state:
 * dp[i][j] = best answer for the range from i to j.
 *
 * Try every possible partition:
 *
 * for k = i ... j:
 *     left  = dp[i][k]
 *     right = dp[k+1][j]
 *     current = left + right + cost of combining/performing operation
 *
 * Then take:
 *     minimum OR maximum
 *
 * Typical clues:
 * - Matrix Chain Multiplication
 * - Burst Balloons
 * - Boolean Parenthesization
 * - Palindrome Partitioning
 * - Minimum cost to cut a stick
 * - Problems asking for optimal way to split/parenthesize/partition
 *
 * KEY IDEA:
 * Unlike LIS where the decision is usually TAKE/SKIP,
 * partition DP asks:
 *
 * "Where should I split this range?"
 *
 * If the answer depends on solving BOTH sides of a chosen split,
 * strongly consider interval / partition DP.
 * ================================================================
 */

    // Patition DP

    // Whenevr there is more than one way to solve a question use partition dp
    // ex in matrix chain multiplication we can use partition dp to find the best way to multiply the matrices
    // given matrices A,B,C we have a few ways to multiply them ie (AB)C, A(BC), AC(B) etc, and we have to find the best way to multiply them
    // in such scenarios use partition dp

    // Rules for poartition DP

    // 1. Start witjh the entire block / array
    // 2. try all partitions -> run a loop to try all partitions 
    // 3. return the best possible partition 

    // f(i,j) -> means the best possible way to multiply the matrices from i to j
    // f(i,j) = min{f(i,k)+f(k+1,j)} for all k such that i<k<=j-1 : this is trying all possible partitions

    /*
        f(i,j){
            if(i==j) return 0;

            for(int k=i;k<=j-1;k++)
                {
                    steps=+ a[i-1]*a[k]*a[j] + f(i,k) + f(k+1,j) ; // this step can change depending on the problem
                    if(steps<minSteps)
                        minSteps=steps;
                }
            return minSteps;
        }
    */
    // exponential time complexity in recusion
    // the input is given as nums=[10,20,10,40] which means 3 matrices
    // Matrix A is of 10x20 , Matrix B is of 20x10 and Matrix c is of 10x40


    /*
 * ==================== MCM / PARTITION DP ====================
 *
 * Recognize this pattern when:
 * - We have a range / interval.
 * - We need to split or partition that range.
 * - We can choose different partition points k.
 * - Choosing k creates a LEFT and RIGHT subproblem.
 * - We need the minimum / maximum total cost.
 *
 * Main question:
 * "Where should I split this range?"
 *
 * General structure:
 *
 *          i ----------- j
 *                |
 *                k
 *                |
 *          --------|--------
 *          LEFT    RIGHT
 *
 * Try every possible k:
 *
 * answer(i,j) =
 *     best over all k {
 *         answer(left)
 *         + answer(right)
 *         + cost of choosing k
 *     }
 *
 * MCM:
 * - Split a chain of matrices.
 * - Cost of combining = nums[i-1] * nums[k] * nums[j].
 *
 * Minimum Cost to Cut Stick:
 * - Split a stick by choosing which cut to perform.
 * - Cost of current cut = current stick length.
 *
 * KEY RECOGNITION:
 * "I have a range, I choose a partition point,
 *  solve both sides, and combine their answers."
 *
 *                ↓
 *
 *          MCM / PARTITION DP
 * =============================================================
 */
    public int matrixMultiplication(int[] nums) {
        int dp[][]=new int[nums.length][nums.length];
        for(int i=0;i<nums.length;i++)
        {
            for(int j=0;j<nums.length;j++)
            {
                dp[i][j]=-1;
            }
        }
        return func(nums,1,nums.length-1,dp); // Find the minimum cost to multiply all matrices from matrix i through matrix j.
    }
    public int func(int nums[],int i,int j)
    {
        if(i==j) return 0; // there is only one matrix. You don't need to multiply anything. hence cost is 0
        int mini=Integer.MAX_VALUE;
        for(int k=i;k<j;k++)
        {
            int steps=nums[i-1]*nums[k]*nums[j] + func(nums,i,k) + func(nums,k+1,j);
            mini=Math.min(mini,steps);
        }
        return mini;
    }
    // memoization 
    // check changing variables 
    public int func(int nums[],int i,int j,int dp[][])
    {

        if(i==j) return 0; // there is only one matrix. You don't need to multiply anything. hence cost is 0
        if(dp[i][j]!=-1) return dp[i][j];
        int mini=Integer.MAX_VALUE;
        for(int k=i;k<j;k++)
        {
            int steps=nums[i-1]*nums[k]*nums[j] + func(nums,i,k,dp) + func(nums,k+1,j,dp);
            // func(nums,i,k) Solve the matrices on the left. func(nums,k+1,j) Solve the matrices on the right. nums[i-1] * nums[k] * nums[j] Combine them
            mini=Math.min(mini,steps);
        }
        return dp[i][j]=mini;
    }

    // tabulation
    /*
 * MCM — MEMOIZATION → TABULATION
 *
 * 1. Find changing variables in recursion: i and j.
 * 2. Therefore create a 2D table: dp[i][j].
 * 3. Convert base case: i == j returns 0 → dp[i][i] = 0.
 * 4. Convert recursive calls: func(i,k) → dp[i][k].
 * 5. Convert recursive calls: func(k+1,j) → dp[k+1][j].
 * 6. Keep the same partition loop over every possible k.
 * 7. Check dependencies before deciding the loop direction.
 * 8. dp[i][j] depends on smaller intervals, so calculate those first.
 * 9. Finally, the original recursive call func(1,n-1) becomes dp[1][n-1].
 */
    public int matrixMultiplicationTabulation(int[] nums) {
        int n=nums.length;
        int dp[][]=new int[n][n];
        for(int i=n-1;i>=0;i--)
        {
            for(int j=i+1;j<n;j++) // since we need range here i to j hence j starts from i+1 
            {
                else
                {
                    int mini=Integer.MAX_VALUE;
                    for(int k=i;k<j;k++) // recurrence
                    {
                        int steps=nums[i-1]*nums[k]*nums[j] + dp[i][k] + dp[k+1][j];
                        mini=Math.min(mini,steps);
                    }
                    dp[i][j]=mini;
                }
            }
        }
        return dp[1][n-1]; // final answer stored here as i goes from n-1 to 1
    }

    // lc 1547 Minimum cost to cut the stick
    /*
    For example: n = 7, cuts = [1,3,4,5]
You need to perform all these cuts.

The important part is:
The cost of making a cut equals the length of the stick that exists at that moment.

So the order of cuts matters. For example, making a cut while the stick is length 7 costs 7.
After cutting it, you now have smaller sticks.

 * LC 1547 — MINIMUM COST TO CUT A STICK
 *
 * We must perform all required cuts on a stick of length n.
 * The cost of each cut equals the length of the stick being cut.
 * Therefore, the order in which we perform cuts affects the total cost.
 * For a range of remaining cuts i...j, choose one cut k to perform first.
 * That cut divides the current stick into a LEFT and RIGHT part.
 * Solve the remaining cuts on both sides independently.
 * Add the current stick length as the cost of performing cut k.
 * Try every possible k and choose the minimum total cost.
 * This is Partition DP because we repeatedly choose where to partition.
 
    */
    // recursion

    public int minCost(int n, int[] cuts) {
        int dp[][]=new int[cuts.length+1][cuts.length+1];
        for(int i=0;i<=cuts.length;i++)
        {
            for(int j=0;j<=cuts.length;j++)
            {
                dp[i][j]=-1;
            }
        }
        Arrays.sort(cuts);
        List<Integer> l=new ArrayList<>();
        l.add(0);
        for(int cut:cuts)
            l.add(cut);

        l.add(n); // remember to add boundaries to the list important while solving
        // you first add: 0 and n to the cuts list. This is important because now every group of cuts has a clear left and right boundary.
        return help(1,cuts.length,l,dp);// Find the minimum cost to perform all cuts from index i to j.
    }
    public int help(int i,int j,List<Integer> l)
    {
        if(i>j) return 0;
        int mini=Integer.MAX_VALUE;
        for(int k=i;k<=j;k++)
        {
            int cut=l.get(j+1) -l.get(i-1) + help(i,k-1,l) + help(k+1,j,l); 
            // cut divides the remaining work into:  i ... k-1 and: k+1 ... j
            // l.get(j+1) -l.get(i-1) is the length of the cut we are making. If k is performed first, the current stick has length: l[j+1] - l[i-1]
            // current cut cost[l.get(j+1) -l.get(i-1)] + best left cost(i ... k-1) + best right cost(k+1 ... j)
            mini=Math.min(mini,cut);
        }

        return mini;
    }
    public int help(int i,int j,List<Integer> l,int dp[][])
    {
        if(i>j) return 0;
        if(dp[i][j]!=-1) return dp[i][j];
        int mini=Integer.MAX_VALUE;
        for(int k=i;k<=j;k++)
        {
            int cut=l.get(j+1) -l.get(i-1) + help(i,k-1,l) + help(k+1,j,l);
            mini=Math.min(mini,cut);
        }

        return dp[i][j]=mini;
    }

    // Tabulation
    /*
 * CUT STICK — MEMOIZATION → TABULATION
 *
 * 1. Recursive state is help(i,j), so DP needs two dimensions.
 * 2. Create dp[i][j] to represent the minimum cost for cuts i...j.
 * 3. Base case i > j returns 0; Java's default zero handles these states.
 * 4. help(i,k-1) becomes dp[i][k-1].
 * 5. help(k+1,j) becomes dp[k+1][j].
 * 6. Keep the same loop over every possible first cut k.
 * 7. Inspect dependencies to determine table filling order.
 * 8. Both dependencies are smaller ranges, so calculate them first.
 * 9. Original help(1,cuts.length) becomes dp[1][cuts.length].
 */

    public int minCostTabulation(int n, int[] cuts) {
        Arrays.sort(cuts);// sort the cuts
        List<Integer> l=new ArrayList<>();
        l.add(0);
        for(int cut:cuts)
            l.add(cut);

        l.add(n);
        int dp[][]=new int[cuts.length+2][cuts.length+2];
        for(int i=cuts.length;i>=1;i--)
        {
            for(int j=1;j<=cuts.length;j++)
            {
                if(i>j) continue;
                else{
                    int mini=Integer.MAX_VALUE;
                    for(int k=i;k<=j;k++)
                    {
                        int cut=l.get(j+1) -l.get(i-1) + dp[i][k-1] + dp[k+1][j];
                        mini=Math.min(mini,cut);
                    }
                    dp[i][j]=mini;
                }
            }
        }
        return dp[1][cuts.length];
    }

    // lc 312 Burst Ballons : Same pattern as above 
    /*
    1. What is the problem and why is Partition DP used? You have balloons with values, and when you burst balloon k, you earn:

left balloon × current balloon × right balloon
The difficult part is that bursting a balloon changes its neighbors. So thinking:

"Which balloon should I burst first?"

makes the neighboring values difficult to track. The key idea is to reverse the thinking:

Which balloon should I burst LAST in this range? If k is the last balloon burst between i and j, then at that moment:
arr[i-1]     arr[k]     arr[j+1]
     ↓          ↓           ↓
   left       last        right

The balloons between i and j have already disappeared. Therefore the coins gained from the final balloon are simply:
arr[i-1] * arr[k] * arr[j+1]

And everything else has already been solved independently:
[i ... k-1]       [k+1 ... j]

That gives the Partition DP structure:
LEFT + LAST OPERATION + RIGHT
 *
 * We need to burst all balloons and maximize the total coins earned.
 * Bursting a balloon changes its neighbors, making "burst first" difficult.
 * Instead, think about which balloon is burst LAST in a given range.
 * If k is last, all balloons between i and j are already removed.
 * Therefore k's neighbors are simply arr[i-1] and arr[j+1].
 * Coins earned from the final balloon = arr[i-1] * arr[k] * arr[j+1].
 * The remaining balloons form two independent subproblems: left and right.
 * Try every k as the last balloon and take the maximum total coins.
 * This is Partition DP because choosing k partitions the range into two parts.
 * 
              choose k as LAST
                    ↓
        ┌───────────┴───────────┐
        ↓                       ↓
     LEFT SIDE              RIGHT SIDE
     i...k-1                 k+1...j
        ↓                       ↓
     best coins              best coins
        └───────────┬───────────┘
                    ↓
              final burst
                    ↓
        arr[i-1] * arr[k] * arr[j+1]

 * Main trick: choose the LAST balloon to burst, not the first.
 * Padding the array with 1 at both ends gives fixed boundaries.
 * dp[i][j] represents the maximum coins from bursting range i...j.
 * k represents the balloon chosen as the final balloon in this range.
 * Final burst cost = arr[i-1] * arr[k] * arr[j+1].
 * Then combine left range i...k-1 and right range k+1...j.
 * This is a MAX partition DP, so use Math.max over all k.
 * Be careful that the main method must actually call the memoized helper.
    */
    public int maxCoins(int[] nums) {

        int n = nums.length;
    
        int arr[] = new int[n + 2];
    
        arr[0] = 1;
        arr[n + 1] = 1;
    
        for (int i = 0; i < n; i++) {
            arr[i + 1] = nums[i];
        }
        int dp[][] = new int[n + 2][n + 2];

        for (int i = 0; i < n + 2; i++) {
            Arrays.fill(dp[i], -1);
        }
        return help(1, n, arr);
    }
    
    public int help(int i, int j, int arr[]) {
    
        if (i > j)
            return 0;
    
        int maxi = 0;
    
        for (int k = i; k <= j; k++) {
    
            int coins = arr[i - 1] * arr[k] * arr[j + 1]
                      + help(i, k - 1, arr)
                      + help(k + 1, j, arr);
    
            maxi = Math.max(maxi, coins);
        }
    
        return maxi;
    }
    // memoization 
    public int help(int i, int j, int arr[], int dp[][]) { // What is the maximum number of coins I can collect by bursting all balloons from i to j?

        if (i > j)
            return 0;
    
        if (dp[i][j] != -1)
            return dp[i][j];
    
        int maxi = 0;
    
        for (int k = i; k <= j; k++) {
    
            int coins = arr[i - 1] * arr[k] * arr[j + 1]
                      + help(i, k - 1, arr, dp)
                      + help(k + 1, j, arr, dp);
    
            maxi = Math.max(maxi, coins);
        }
    
        return dp[i][j] = maxi;
    }
    /*
 * BURST BALLOONS — MEMOIZATION → TABULATION
 *
 * Recursive state help(i,j) has two changing variables, so use dp[i][j].
 * Base case i > j returns 0, which is already Java's default table value.
 * Convert help(i,k-1) into dp[i][k-1].
 * Convert help(k+1,j) into dp[k+1][j].
 * Keep the same loop over k because every k can be the last balloon.
 * dp[i][j] depends only on smaller intervals.
 * Therefore calculate smaller intervals before calculating larger intervals.
 * Use Math.max because we want the maximum number of coins.
 * The original answer help(1,n) becomes dp[1][n].
 */

    public int maxCoinsTabulation(int[] nums) {

        int n = nums.length;
    
        int arr[] = new int[n + 2];
    
        arr[0] = 1;
        arr[n + 1] = 1;
    
        for (int i = 0; i < n; i++) {
            arr[i + 1] = nums[i];
        }
    
        int dp[][] = new int[n + 2][n + 2];
    
        for (int i = n; i >= 1; i--) {
    
            for (int j = i; j <= n; j++) {
    
                int maxi = 0;
    
                for (int k = i; k <= j; k++) {
    
                    int coins = arr[i - 1] * arr[k] * arr[j + 1]
                              + dp[i][k - 1]
                              + dp[k + 1][j];
    
                    maxi = Math.max(maxi, coins);
                }
    
                dp[i][j] = maxi;
            }
        }
    
        return dp[1][n];
    }

    // Pallindrome partioning 2 : Same pattern as above
    /*
 * PALINDROME PARTITIONING — PARTITION DP
 *
 * you want to partition a string into pieces such that every piece is a palindrome, using the minimum number of cuts.For example:
"ababbbabbababa"

You want to find the best partition:
"aba" | "bbb" | "abba" | "baba"

where every piece is a palindrome.The important decision is:

Starting from position i, where should I make my next partition?You try every possible ending position j:
i -------- j
If: s[i...j]
is a palindrome, you can make that partition and solve the remaining suffix:

s[j+1 ... n-1]

So this is partition DP, although unlike MCM it uses a 1D state:
dp[i]

 * We need to divide a string into palindrome substrings using minimum cuts.
 * Starting from index i, try every possible ending index j.
 * If s[i...j] is a palindrome, it can be one valid partition.
 * After choosing that palindrome, the remaining problem starts at j+1.
 * Therefore dp[i] can represent the minimum cuts needed from index i onward.
 * Try every valid palindrome starting at i and choose the minimum result.
 * Unlike MCM, this uses 1D DP because only the next starting index matters.
 * The partition decision is the ending point j of the current palindrome.
 * This is still Partition DP: choose a valid partition, then solve the remainder.
 */
    // recursion
    public int minCut(String s) {
        int dp[]=new int[s.length()];
        Arrays.fill(dp,-1);

        return cutMemoization(s,0,dp);
    }
    // recursion has overlapping sub problems 
    public int cutRecursion(String s,int i) {
        if(i==s.length())
            return 0;

        int mini=Integer.MAX_VALUE;
        // String we consider will always be from i to j
        for(int j=i;j<s.length();j++)
        {
            if(isPalindrome(s,i,j))
            {
                int cut=1+cutRecursion(s,j+1);
                mini=Math.min(mini,cut);
            }
        }
        return mini;

    }
    public int cutMemoization(String s,int i,int dp[]) {
        if(i==s.length())
            return 0;
        if(dp[i]!=-1) return dp[i];
        int mini=Integer.MAX_VALUE;
        for(int j=i;j<s.length();j++)
        {
            if(isPalindrome(s,i,j))
            {
                if (j == s.length() - 1){
                    mini = Math.min(mini, 0);
                }
                else{
                    int cut=1+cutMemoization(s,j+1,dp);
                    mini=Math.min(mini,cut);
                }
            }
        }
        return dp[i]=mini;
    }

    public boolean isPalindrome(String s,int i,int j)
    {
        while(i<j)
        {
            if(s.charAt(i)!=s.charAt(j))
                return false;
            i++;
            j--;
        }
        return true;
    }

    // lc 1043. Partition Array for Maximum Sum

    // recursion 

    public int maxSumAfterPartitioning(int[] arr, int k) {
        int n=arr.length;
        int dp[]=new int[n+1];
        Arrays.fill(dp,-1);
        return hell(arr,0,k,dp);
    }
    // recursion with exponenetial time complexity
    public int hell(int arr[],int i,int k)
    {
        if(i==arr.length)
            return 0;
        if(i+k>arr.length)
            return 0;
        int maxi=0;
        int sum=0;
        int len=0;
        int mani=Integer.MIN_VALUE;
        for(int j=i;j<Math.min(arr.length,(i+k));j++)
        {
            len++;
            mani=Math.max(mani,arr[j]);
            sum+=len*mani + hell(arr,j+1,k);
            maxi=Math.max(maxi,sum);
        }
        return maxi;
    }
    // memoization TC : O(N)* O(k)
    // SC : O(N) (dp array) * O(N) -> Auxillary stack space
    public int hell(int arr[],int i,int k, int dp[])
    {
        if(i==arr.length)
            return 0;
        if(dp[i]!=-1)
            return dp[i];
        int maxi=0;
        int mani=Integer.MIN_VALUE;
        for(int j=i;j<Math.min(arr.length,(i+k));j++)
        {
            int len=j-i+1;
            mani=Math.max(mani,arr[j]);
            int sum =len*mani + hell(arr,j+1,k,dp);
            maxi=Math.max(maxi,sum);
        }
        return dp[i]=maxi;
    }
}
