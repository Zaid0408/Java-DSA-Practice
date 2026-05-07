import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class recursion {

    public static int fibo(int n)
    {
        if(n==0 || n==1)
            return n;
        return fibo(n-1)+fibo(n+1);
    }
    public static Boolean SortedArray(int arr[],int i)
    {
        if(arr.length==1)
        {
            return true;
        }
        if(arr[i]>arr[i+1])
        {
            return false;
        }
        return SortedArray(arr, i+1);
    }
    public static int firstOccurence(int arr[],int key,int i){
        if(i==arr.length)
            return -1;
   
        if(arr[i]==key)
            return i;
        return firstOccurence(arr, key, i+1);
    }

    public static void reverse(String str)
    {
        if(str.length()==0)
            return ;
        String res= str.substring(1);
        reverse(res);
        System.out.println(str.charAt(0));// correct this
    }
    public static void replacePI(String s) //s="pippxxpipxpxpi"
    {
        if(s.length()==0)
            return;
        
        if(s.charAt(0)=='p' && s.charAt(1)=='i')
        {
            System.out.print("3.14");
            replacePI(s.substring(2));
        }
        else
        {
            System.out.print(s.charAt(0));
            replacePI(s.substring(1));
        }
    }
    public static void removeDup(String s,int i, StringBuilder newStr,Boolean map[]) // remove duplicate in string
    {
        if(s.length()==i){
            System.out.println(newStr);
            return ;
        }
        char ch= s.charAt(i);
        if(map[ch-'a']==true)
            removeDup(s, i+1, newStr, map);
        else{
            map[ch-'a']=true;
            removeDup(s, i, newStr.append(ch), map);
        }
        
       
    }
    public static void permutation(String s, String ans) //Permutations of a string
    {
        if(s.length()==0)
        {
            System.out.println(ans);
            return;
        }
        for (int i = 0; i < s.length(); i++)
        {
            char ch= s.charAt(i);
            String ros=s.substring(0,i)+ s.substring(i+1);
    
            permutation(ros,(ans+ch));
        }
        
    }
    public static void SubstringsOf(String s,String ans) // all possible substrings of a string
    {
        if(s.length()==0)
        {
            System.out.println(ans);
            return;
        }
        char ch=s.charAt(0);
        String ros=s.substring(1);
    
        SubstringsOf(ros,ans);
        SubstringsOf(ros,(ans+ch));
    }
    

    // leetcode 50 Power of N

    public double myPow(double x, int n) {
        long N=n;// to handle interger overflow
        if(N<0)
        {
            x=1/x;
            N=-1*N;
        }

        return Power(x,N);
    }
    private double Power(double x, long N)
    {
        /**
        Key Idea (O(log n))
If n is even:
x^n = (x^(n/2)) * (x^(n/2))
If n is odd:
x^n = x * (x^(n/2)) * (x^(n/2))
         */
        if(N==0) return 1;

        if(N==1) return x;

        double half = Power(x, N/2);
        if(N%2==0)
        {
            return half*half;
        }
        else
        {
            return half*half*x;
        }
    }

    // letcodee 1922 Count Good Numbers
    private static long MOD= 1000000007;
    public int countGoodNumbers(long n) {
        // must use long to prevent interg overflow
        long even = (n + 1) / 2;
        long odd = n / 2;
        // Mathematically derived formula to calculate the number of good numbers
        long res = (long)(Power(5, even) * Power(4, odd)) % MOD; // use the same power function as above
        return (int) res;
    }

    // reverse a stack using recursion

    public void reverseStack(Stack<Integer> st) {
        if(st.isEmpty())
            return;

        int top=st.pop();
        reverseStack(st);
        InsertStack2(st,top);

    }
    public void InsertStack2(Stack<Integer> st, int num)
    {
        if(st.isEmpty())
        {
            st.push(num);
            return ;
        }
        int top=st.pop();
        InsertStack2(st,num);
        st.push(top);
    }

    // recursively sort stack in descending order same pattern as above

    public void sortStack(Stack<Integer> st) {
        if(st.isEmpty())
            return;

        int top=st.pop();
        sortStack(st);
        InsertStack(st,top);
    }
    public void InsertStack(Stack<Integer> st, int num)
    {
        if(st.isEmpty() || st.peek()<=num)
        {
            st.push(num);
            return ;
        }
        int top=st.pop();
        InsertStack(st,num);
        st.push(top);
    }

    public static int PowerOfN(int x, int n)// x^n in log n time
    {
        if(n==0 )
            return 1;
        else if(n==1)
            return x;
        else{
            if(n%2==0)
                return PowerOfN(x, n/2)*PowerOfN(x, n/2); // incorrect assign a variable to it and then multiply otehrwise tales a lot of time 
            else
                return x* PowerOfN(x, n/2)*PowerOfN(x, n/2);
        }

    }
    public static int TillingProblem(int n) 
    {
        if(n==1 || n==0)
            return 1;// can be done by tabulation
        return TillingProblem(n-1)+TillingProblem(n-2);
    }
    public static int Pairingfriends(int n) // can be done by tabulation
    {
        if(n==1 || n==2) //explanation in book
            return n;
        return Pairingfriends(n-1)+ (n-1)*Pairingfriends(n-2);
    }
    public static void BinaryString(int n, int lastplace,String str){
        // if(lastplace==0){
        //     //str.append("0");
        //     BinaryString(n-1, 0, str.append("0"));
        //     BinaryString(n-1, 1, str.append("1"));

        // }
        // else {
        //     BinaryString(n-1, 0, str.append("0"));
        // }

        //Optimized:
        if(n==0){//base
            System.out.println(str);
            return;
        }
        BinaryString(n-1, 0, str+"0");//main work
        if(lastplace==0){
            BinaryString(n-1, 1, str+"1");
        }
    }
    public static void ToH(int n,char src,char dest,char temp)
    {
        if(n==1)
            System.out.println("Move disk "+n+" from "+src+" to "+dest);
        else{
            ToH(n-1,src,temp,dest);
            System.out.println("Move disk "+n+" from "+src+" to "+dest);
            ToH(n-1,temp,dest,src);
        }
    }
    public static int knapsack(int[] wt, int[] val, int cap, int n)
    { // 0-1 knapsack problem . 0-1 means either have to include or to exclude the weight cannot add 
        if(cap==0 || n==0)
            return 0;
        
        if(wt[n-1]<=cap)
        {
            // include the weight into knapsack
            int ans1= val[n-1]+knapsack(wt, val, cap-wt[n-1], n-1);
            // exclude the weight from knapsack
            int ans2= knapsack(wt, val, cap, n-1);
            return Math.max(ans1, ans2);
        }
        else{
            return knapsack(wt, val, cap, n-1);
        }
    }
    char ch1,ch2;
    public int longestCommonSubsequence(String text1, String text2) {
        
        if(text1.length()==0 || text2.length()==0)
            return 0;
        
        ch1=text1.charAt(text1.length()-1);
        ch2=text2.charAt(text2.length()-1);
        if(ch1==ch2)
            return  1 + longestCommonSubsequence(text1.substring(0,text1.length()-1), text2.substring(0,text2.length()-1));
        else{
            int ans1= longestCommonSubsequence(text1.substring(0,text1.length()-1), text2);
            int ans2= longestCommonSubsequence(text1,text2.substring(0,text2.length()-1));
            return Math.max(ans1,ans2);
        }

    }

    // Generate Binary Strings Without Consecutive 1s 

    /*
    Given an integer n, return all binary strings of length n that do not contain consecutive 1s. Return the result in lexicographically increasing order.
A binary string is a string consisting only of characters '0' and '1'.

Example 1

Input: n = 3
Output: ["000", "001", "010", "100", "101"] in lexicographic order
Explanation: All strings are of length 3 and do not contain consecutive 1s.

Example 2

Input: n = 2
Output: ["00", "01", "10"]
    */

    List<String> ans=new ArrayList<>();
    public List<String> generateBinaryStrings(int n) {
        StringBuilder s=new StringBuilder();
        helper(n,'0', s); //  assume prev char is 0
        return ans;
    }
    public void helper(int n,char prev,StringBuilder s)
    {
        if(s.length()==n) // string of length n reached add to list
        {
            ans.add(s.toString());
            return;
        }
        s.append('0');
        helper(n,'0',s); // when 000 gets added in the string it comes back here then: 
        s.deleteCharAt(s.length()-1); // last character gets removed making it 00 then adding 1 making it 001 and calling recursion again 

        if (prev != '1') {
            s.append('1');
            helper(n,'1',s); // since we append 1 here we pass the prev appended char as 1
            s.deleteCharAt(s.length() - 1);
        }
        

    }

    // leetcode 22 Generate Parentheses same pattern as above 

    public List<String> generateParenthesis(int n) {
        StringBuilder s=new StringBuilder();
        helper(n,0,0, s);
        return ans;
    }
    public void helper(int n, int i, int j,StringBuilder s )
    {
        /* 
        Three rules: 
        1. Only add open parenthesis when i < n
        2. Only add closed parenthesis when j<i
        3. return the string when open == close ==n
         */
        if(i==j && i==n)
        {
            ans.add(s.toString());
            return;
        }
        if(i<n)
        {
            s.append('(');
            helper(n,i+1,j,s);
            s.deleteCharAt(s.length()-1); // same deletion logic as previous problem 
        }
        if(j<i)
        {
            s.append(')');
            helper(n,i,j+1,s);
            s.deleteCharAt(s.length()-1);
        }

    }
// leetcode 17 Letter Combinations of a Phone Number

    // example 1:
    // Input: digits = "23"
    // Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]

    // example 2:
    // Input: digits = "2"
    // Output: ["a","b","c"]

    List<String> ans3 = new ArrayList<>();
    public List<String> letterCombinations(String digits) {
        if (digits.length() == 0) return ans;

        String[] map = {"","","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
        helper(0, digits, new StringBuilder(), map);
        return ans3;
    }

    private void helper(int i, String digits, StringBuilder sb, String[] map) {
        if (i == digits.length()) {
            ans3.add(sb.toString());
            return;
        }
        String letters = map[digits.charAt(i) - '0'];
        // supose digits.charAt(i) was '9' then letters will be "wxyz"
        for (char ch : letters.toCharArray()) {
            sb.append(ch);                  // choose
            helper(i + 1, digits, sb, map); // explore
            sb.deleteCharAt(sb.length() - 1);   // backtrack
        }
    }

    static List<List<Integer>> ans1= new ArrayList<>();
    public static List<List<Integer>> powerSet(int[] nums) {
        // get all subsets of nums not to be confused with sub arrays
        helper(0, nums, new ArrayList<>());
        return ans1;
    }
    private static void helper(int index, int[] nums, List<Integer> current) {
        ans1.add(new ArrayList<>(current));
        // two choices :
        /*
        1. Add to the subset 
        2. Dont add to the subset
        []
       /  \
     [1]   []
    /  \   / \
 [1,2] [1] [2] []

        */
        for (int i = index; i < nums.length; i++) {
            // include nums[i]
            current.add(nums[i]);
            // move forward
            helper(i + 1, nums, current);
            // backtrack or remove nums[i]
            current.remove(current.size() - 1);
        }
    }

    // leetcode 78 Subsets similar to the above 

    public List<List<Integer>> subsets(int[] nums) {
        List<Integer> subset=new ArrayList<>();
        List<List<Integer>> temp=new ArrayList<>();
        Subsets(nums,0, subset,temp);
        return temp;
    }
    public void Subsets(int nums[],int i,List<Integer> subset,List<List<Integer>> temp)
    {
        if(i==nums.length)
        {
            temp.add(new ArrayList<>(subset));
            return;
        }
        // Two choices
        // First include current element while making subset
        subset.add(nums[i]);
        Subsets(nums,i+1, subset,temp); 
        // Second not to include the current element while making subset
        subset.remove(subset.size()-1);
        Subsets(nums,i+1, subset,temp);


    }

    // leetcode 90 Subsets II same problem as above but no duplicates allowed
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        helper( nums,0, new ArrayList<>());
        return ans1;
    }
    private static void helper( int[] nums, int i,List<Integer> curr)
    {
        if(i==nums.length)
        {
            ans1.add(new ArrayList<>(curr));
            return;
        }
        // include current nums[i]
        curr.add(nums[i]);
        helper(nums,i+1,curr);
        // remove the current num from the subset to generate new 
        curr.remove(curr.size()-1);
        // before generating new patterns by not considering nums[i] we must skip the duplicates in the nums array so that no duplicates will get added while append to the curr list
        while(i+1<nums.length && nums[i]==nums[i+1]) // why i+1<nums.length  because we need to validate the condition after &&
        {
            i++;
        }

        helper(nums,i+1,curr);
    }

    /*
    Count all subsequences with sum K

Given an array nums and an integer k.Return the number of non-empty subsequences of nums such that the sum of all elements in the subsequence is equal to k.

Example 1
Input : nums = [4, 9, 2, 5, 1] , k = 10
Output : 2
Explanation : The possible subsets with sum k are [9, 1] , [4, 5, 1].

Example 2

Input : nums = [4, 2, 10, 5, 1, 3] , k = 5
Output : 3
Explanation : The possible subsets with sum k are [4, 1] , [2, 3] , [5].

    */
    int ans2=0;
    public int countSubsequenceWithTargetSum(int[] nums, int k) {

        helper(nums,0,0,k);
        return ans2;
    }
    public void helper(int[] nums,int i,int s,int k)
    {
        if(i==nums.length)
        {
            if(s==k)
            {
                ans2+=1;
            }
            return;
        }

        s+=nums[i];
        helper(nums,i+1,s,k);

        s-=nums[i];
        helper(nums,i+1,s,k);
    }
    public static void main(String[] args) {
        // System.out.println(PowerOfN(5, 9));
        //permutation("ZAID", "");
        // SubstringsOf("ZAID", "");
        // Boolean map[]=new Boolean[26];
        // for(int i=0;i<map.length;i++)
        // {
        //     map[i]=false;
        // }
       //System.out.println(Pairingfriends(7));
        //removeDup("zzzaaiiiidddd",0,new StringBuilder(""),map);
        //replacePI("pippxxpipxpxpi");
        //BinaryString(4, 0, "");
        //ToH(3, 'A','B','C');
        // int W=7;
        // int val[]={15,14,10,45,30};
        // int weight[]={2,5,1,3, 4};
        //System.out.println(knapsack(weight, val, W, val.length));

        int nums[]={1,2,3,4};
        powerSet(nums);
        for(int i=0;i<ans1.size();i++)
        {
            for(int j=0;j<ans1.get(i).size();j++)
            {
                System.out.print(ans1.get(i).get(j)+" ");
            }
            System.out.println();
        }
    }
}
