import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DP {
    // Tabulation or memoization used
    // tabulation is bottom up approach used in loops mainly.
    // Memoization is top down approach used in recursion mostly
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
     * Given a rod of n inches and an array of prices of all pieces of size smaller than n. 
     * Determine the maximum value obtainable by cutting the rod up and selling the pieces.
     * length=[1,2,3,4,5,6,7,8]
     * price=[1,5,8,9,10,17,17,20]
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
    /* 
     * Coin Change
     * You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money.
     * Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.
     * Input: coins = [1,2,5], amount = 11 Output: 3  Explanation: 11 = 5 + 5 + 1
     * Input: coins = [2], amount = 3 Output: -1
     *
    */
    public int coinChange(int[] coins, int amount) {
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
                    dp[i][j]= dp[i-1][j] + dp[i][j-coins[i-1]];
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
    public int coinChange2(int[] coins, int amount) {
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
     * Longest Common Subsequence
     * Given two strings text1 and text2, return the length of their longest common subsequence. If there is no common subsequence, return 0.
     * A subsequence of a string is a new string generated from the original string with some characters (can be none) deleted without changing the relative order of the remaining characters.
     * For example, "ace" is a subsequence of "abcde".
     * Example 1:
     * Input: text1 = "abcde", text2 = "ace"  Output: 3  Explanation: The longest common subsequence is "ace" and its length is 3.
     * Example 3: 
     * Input: text1 = "abc", text2 = "def" Output: 0 Explanation: There is no such common subsequence, so the result is 0.
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
            int ans1= longestCommonSubsequence(text1,text2,dp1,n-1,m);
            int ans2= longestCommonSubsequence(text1,text2,dp1,n,m-1);
            return dp1[n][m]= Math.max(ans1,ans2); // valid statement will return dp1[n][m]
        }
    }
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
    public static int longestCommonSubstring(String text1, String text2)
    {
        // similar logic as longest common subsequence 
        int dp[][]=new int[text1.length()+1][text2.length()+1];
        for(int i=1;i<dp.length;i++){
            for(int j=1;j<dp[0].length;j++){
                if(text1.charAt(i-1)==text2.charAt(j-1)){
                    dp[i][j]=dp[i-1][j-1]+1;    // if both the last characters of text1 and text2 are same 
                }
                else{ // if uncommon characters then substring breaks then set it to 0
                    dp[i][j]=0;
                }
            }
        }
        return dp[text1.length()][text2.length()];
    }
    public static int longestIncreasingSubsequence(int[] arr)
    {   // same to same as longest common subsequence logic 
        HashSet<Integer> hs= new HashSet<>();
        for(int i=0;i<arr.length;i++){
            hs.add(arr[i]);
        }
        int arr2[]=new int[hs.size()];
        int k=0;
        for(int x:hs){
            arr2[k++]=x;
        }
        Arrays.sort(arr2);
        int dp[][]=new int[arr2.length+1][arr.length+1];
        for(int i=1;i<dp.length;i++){
            for(int j=1;j<dp[0].length;j++){
                if(arr[j-1]==arr2[i-1])
                {
                    dp[i][j]=1+dp[i-1][j-1];
                }
                else
                {
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return dp[hs.size()][arr.length];
    }
    public static int longestPalindromicSubsequence(String s) {
    // DP Approach
    // 1)Reverse The string;
    // 2)Find the lowest common subsequence of given string and reverse of the string.
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
    public static int EditDistance(String s1, String s2){
        int dp[][]=new int[s1.length()+1][s2.length()+1];
        // initialization step
        for(int i=0;i<dp.length;i++){
            for(int j=0;j<dp[0].length;j++){
                if(i==0)
                    dp[i][j]=j; // if string 1 is empty then all the characters of string 2 to be inserted
                else if(j==0)
                    dp[i][j]=i; // if string 2 is empty then all the characters of string 1 to be deleted
            }
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
    public static int CountingTrees(int n){
        // given a number find the total number of bst's possible
        // exactly same to same logic as catalan number
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
    public static void main(String[] args) {
        int W=7;
        int val[]={15,14,10,45,30};
        int weight[]={2,5,1,3, 4};
        int dp[][]=new int[val.length+1][W+1];
        for(int i=0;i<dp.length;i++){
            for(int j=0;j<dp[0].length;j++){
                dp[i][j]=-1;
            }
        }
        //System.out.println(knapsack(val, weight, W, val.length, dp));
        //System.out.println(knapsack(val, weight, W, val.length));
        // for(int i=0;i<dp.length;i++){
        //     for(int j=0;j<dp[0].length;j++){
        //         System.out.print(dp[i][j]+" ");
        //     }
        //     System.out.println();
        // }

        int[] length={1,2,3,4,5,6,7,8};
        int[] price={1,5,8,9,10,17,17,20};
        //System.out.println(RodCutting(price, length, 8, length.length));

        String str1="abcde",str2="ace";
        int dp1[][]=new int[str1.length()+1][str2.length()+1];
        for(int i=0;i<dp1.length;i++){
            for(int j=0;j<dp1[0].length;j++){
                dp1[i][j]=-1; // initialize all the array with -1 if we get length of lcs replace the -1 with that value
            }
        }
        //System.out.println(longestCommonSubsequence(str1, str2, dp1,str1.length(), str2.length()));
        //System.out.println(longestCommonSubsequence2(str1, str2));
        //System.out.println(longestCommonSubstring("abce", str2));
        int array[]={50,3,10,7,40,80};
        System.out.println(longestIncreasingSubsequence(array));

        
    }
}
