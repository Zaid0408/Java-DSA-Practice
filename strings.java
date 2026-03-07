import java.util.*;
import java.util.stream.Collectors;
class strings {
    Scanner sc = new Scanner(System.in);
    // BAD - String concatenation
    // String sb1 = "";
    // sb1 = sb1 + ch;  // Creates a NEW string object every single time!

    // GOOD - StringBuilder
    // StringBuilder sb = new StringBuilder();
    // sb.append(ch);  // Modifies the SAME object in place


// **Why string concatenation is slow:**

// Strings in Java are **immutable** — they cannot be changed once created. So every `+` operation:
// 1. Creates a brand new String object in memory
// 2. Copies all existing characters into it
// 3. Adds the new character
// 4. Discards the old String (garbage collection)

    public static float ShortestPath(String s)
    {
        int x=0,y=0;
        for(int i=0;i<s.length();i++)
        {
            if(s.charAt(i)=='N'){y++;}
            else if(s.charAt(i)=='S'){y--;}
            else if(s.charAt(i)=='E'){x++;}
            else if(s.charAt(i)=='W'){x--;}
            else{
                System.out.println("Invalid String\n");
                return 0;
            }
        }
        int X2=x*x;
        int Y2=y*y;
        
        return (float)Math.sqrt(Y2+X2);
    }
    public static String compress1(String str)
    {
        StringBuilder sb= new StringBuilder("");
        int count=0;
        for(int i=0;i<str.length()-1;i++)
        {
            char ch=str.charAt(i);
            count=1;
            while(ch==str.charAt(i+1) && i<str.length()-1){count++;i++;}
            sb.append(ch);
            sb.append(count);
        }
        if(sb.toString().compareTo(str)>0)
        {
            return str;
        }
        return sb.toString();
        
    }
    public static String compress2(String str)
    {
        StringBuilder sb= new StringBuilder("");
        for(int i=0;i<str.length();i++)
        {
            //aaabbcccdd
            Integer count=1;
            if(i<str.length()-1)
            {
                while(str.charAt(i)==str.charAt(i+1) && i<str.length()-1){
                    count++;
                    i++;
                }
            }
            sb.append(str.charAt(i));
            if(count>1){sb.append(count);}
                
        }
        return sb.toString();
    }

    // letcode 1021 Remove Outermost Parentheses
    // whenever you encounter a first '(' remove it and when ct of '(' == ct of ')' then remove ')' again if you encounter '(' then you remove it. 
    // as it is given balanced parentheses already so after removing ')' 
    // you will always find next element is '(' and it will be again first for next consecutive sequence..
    public String removeOuterParentheses(String s) {
        String ans="";
        int c1=0;
        for(int i=0;i<s.length();i++)
        {
            char ch=s.charAt(i);
            if(ch=='(')
            {
                if(c1>0)
                {
                    ans=ans+ch;
                }
                c1++;
            }
            else if(ch==')')
            {
                c1--;
                if(c1>0)
                {
                    ans=ans+ch;
                }
            }
        }
        return ans;
    }
    // leetcode 151 Reverse Words in a String
    public String reverseWords(String s) {
        String m="",c="";
        for(int i=s.length()-1;i>=0;i--)
        {
            if(s.charAt(i)!=' ')
            {
                c=c+s.charAt(i); // append the word 
            }
            else
            {
                if(c.length()>0){ // if space encountered then reverse the word
                    c=rev(c);
                    System.out.println(c);
                    m= m+c+" ";
                    c="";// store the reverse in m and reset c
                }
            }
        }
        if(c.length() > 0) { // check for last word in the string as the else statement does not account for that 
            c = rev(c);
            m = m + c;
        } else {
            m = m.trim();   // Remove trailing space from last word added
        }
        
        return m.trim();
    }
    public String rev(String k) // function to reverse word used above
    {
        String l="";
        for(int i=k.length()-1;i>=0;i--)
        {
            l=l+k.charAt(i);
        }
        return l;
    }
    // leetcode 1903 Largest Odd Number in String
    public String largestOddNumber(String num) { 
        
        for(int i=num.length()-1;i>=0;i--)
        {
            char ch = num.charAt(i);
            if((ch-'0')%2 !=0){
                return num.substring(0, i + 1);
            }
        }
        
        return "";
        
    }
    // Leetcode 205 Isomorphic Strings : Two strings are isomorphic if the characters in s can be replaced to get t.
    public boolean isIsomorphic(String s, String t) {
        if(s.length()!=t.length())
            return false;

        int a[]=new int[200];
        int b[]=new int[200];
        for(int i=0;i<s.length();i++)
        {
            if(a[s.charAt(i)] != b[t.charAt(i)])
                return false;

            a[s.charAt(i)]= i+1;     
            b[t.charAt(i)]= i+1;        
        }

        return true;
    }
    // leetcode 796 Rotate String brute force fails at test case : s ="defdefdefabcabc" and goal = "defdefabcabcdef"
    // Rotate String means 

    public boolean rotateString(String s, String goal) { 
        if(s.length()!=goal.length())
            return false;
        char ch=s.charAt(0);
        int idx=-1;
        for(int i=0;i<goal.length();i++)
        {
            if(ch==goal.charAt(i)){
                idx=i;
                break;
            }
        }
        if(idx==-1)
            return false;
        for(int i=0,j=idx;i<s.length() && j<goal.length();i++)
        {
            if(s.charAt(i)!=goal.charAt(j))
                return false;
            j=(j+1)%goal.length();
        }

        return true;
    }
    // Leetcode 796 Rotate String optimized better than above 
    public boolean rotateStringOptimized(String s, String goal) {
        if (s.length() != goal.length()) {
            return false;
        }
        return (s + s).contains(goal);
    }
    // leetcode 242 Valid Anagram
    public boolean isAnagram(String s, String t) {
        int [] count = new int[26];

        for(int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            int index = (int)c - (int)'a';
            count[index]++;
        }
        for(int i=0; i<t.length(); i++) {
            char c = t.charAt(i);
            int index = (int)c - (int)'a';
            count[index]--;
        }
        for (int i=0; i<count.length; i++) {
            if (count[i] != 0) return false;
        }
        return true;
    }
    // leetcode 451 Sort Characters By Frequency
    // very slow code 
    // Time Complexity: O(n + k log k)

    // O(n) to build the frequency map
    // O(k log k) for stream sorting where k = unique characters
    // O(n) to build the result string
    // Since k ≤ 26 (lowercase) or 128 (ASCII), sort is nearly O(1) in practice

    // Space Complexity: O(n + k)

    // O(k) for the HashMap
    // O(n + k) for the LinkedHashMap + result string
    // O(n) for toCharArray()
    public String frequencySort(String s) {
        String ans="";
        HashMap<Character,Integer> hs=new HashMap<>();

        for(char ch:s.toCharArray())
        {
            if(hs.containsKey(ch))
            {
                hs.put(ch,hs.get(ch)+1);
            }
            else
                hs.put(ch,1);
        }
        // use bucket sort for better time and space complexity 
        Map<Character, Integer> sM = hs.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // Merge function to handle potential key collisions (not an issuehere)
                        LinkedHashMap::new // Use LinkedHashMap to preserve the sorted order
                )); // Sort in descending order of values
        String k="";
        for (Map.Entry<Character,Integer> mapElement : sM.entrySet()) {
            char key = mapElement.getKey();
            int value =  mapElement.getValue();
            for(int i=1;i<=value;i++)
            {
                k= k+ key;
            }
            ans=ans+k;
            k="";
        }

        return ans;
    }
    public String frequencySortOptimized(String s) { // using bucket sort 
        int[] freq = new int[128];  // ASCII characters
        for(char ch : s.toCharArray()) 
        {
            freq[ch]++;
        }
        // Step 2: Bucket sort - index = frequency, value = list of chars
        // Max frequency can be s.length()
        List<List<Character>> buckets = new ArrayList<>();
        for(int i = 0; i <= s.length(); i++) 
        {
            buckets.add(new ArrayList<>());
        }
        for(int i = 0; i < 128; i++) {
            if(freq[i] > 0) {
                buckets.get(freq[i]).add((char) i);
            }
        }
        // Step 3: Build result from highest frequency bucket to lowest
        StringBuilder sb = new StringBuilder();
        for(int i = s.length(); i >= 1; i--) { // Loop 1: visits each bucket (max frequency down to 1)
            for(char ch : buckets.get(i)) { // Loop 2: visits each character inside that bucket
                for(int j = 0; j < i; j++) { // loop 3: appends that character as many times as its frequency
                    sb.append(ch);
                }
            }
        }
        return sb.toString();

        // **Example `s = "tree"`:**
        // ```
        // i=4 → bucket empty, skip
        // i=3 → bucket empty, skip
        // i=2 → bucket has [e] → append 'e' twice  → "ee"
        // i=1 → bucket has [r,t] → append 'r' once, 't' once → "eetr"
        // ```

        // **Why it's still O(n) total:**

        // Even though there are 3 loops, the total characters appended across ALL iterations equals exactly `n` (length of original string). You're never doing more work than the input size.
        // ```
        // Total appends = sum of (frequency × 1) for each character
        //              = total characters in string
        //              = n
        // ```
            //         Think of it like this — if `s = "aabbb"`, n=5:
            // ```
            // 'a' appended 2 times
            // 'b' appended 3 times
            // Total = 2 + 3 = 5 = n ✓
            // ```

            // ---
            // buckets[0] = []
            // buckets[1] = [r, t]      ← chars with frequency 1
            // buckets[2] = [e]          ← chars with frequency 2
            // buckets[3] = []
            // buckets[4] = []
    }
    //leetcode 3 Longest Substring Without Repeating Characters
    // REMEMBER THE BELOW PATTERN FOR SUBSTRING/SLIDING WINDOW PROBLEMS
    public int lengthOfLongestSubstring(String s) {
        int freaky[]=new int[128];
        int left=0,ans=0;
        for(int right=0;right<s.length();right++)
        {
            freaky[s.charAt(right)]++; // append frequesncy of the given chararacter
            // check invalid condition to move the window
            while(freaky[s.charAt(right)]>1) // because without repeating means frequency has to be one to be included in the window
            {
                freaky[s.charAt(left)]--; //reduce the occurence as this will get removed from the window
                left++;

            }
            ans= Math.max(ans, right-left+1); // where right-left+1 is the size of the sliding window
        }
        return ans;
    }
    // leetcode 1781 Sum of Beauty in the Array

    public int beautySum(String s) {
        int ans = 0;
        // Fix starting point i, expand j from i+1 to end
        for(int i = 0; i < s.length(); i++) {
            int[] freq = new int[26];
            freq[s.charAt(i) - 'a']++;                 // Count starting char
            for(int j = i + 1; j < s.length(); j++) {
                freq[s.charAt(j) - 'a']++;             // Expand window right
                // Beauty = max frequency - min frequency in window
                int max = 0, min = Integer.MAX_VALUE;
                for(int f : freq) {
                    if(f > 0) {                         // Only consider chars present
                        max = Math.max(max, f);
                        min = Math.min(min, f);
                    }
                }
                ans += (max - min);                     // Beauty of substring s[i..j]
            }
        }

        return ans;
    }
    // Count number of substrings with k distinct characters
    /*
    * Problem Statement: You are given a string s and a positive integer k. Return the number of substrings that contain exactly k distinct characters.
    Example 1:
    Input:
    s = "pqpqs", k = 2  
    Output:
    7  
    Explanation:
    All substrings with exactly 2 distinct characters:  
    "pq", "pqp", "pqpq", "qp", "qpq", "pqs", "qs"  
    Total = 7.

    Example 2:
    Input:
    s = "abcbaa", k = 3  
    Output:
    5  
    Explanation:
    All substrings with exactly 3 distinct characters:  
    "abc", "abcb", "abcba", "bcba", "cbaa"  
    Total = 5.
    */

    public static int atMostKDistinct(String s, int k) {
        int left = 0, res = 0;
        Map<Character, Integer> freq = new HashMap<>();

        // Iterate with right pointer
        for (int right = 0; right < s.length(); right++) {
            freq.put(s.charAt(right), freq.getOrDefault(s.charAt(right), 0) + 1);

            // Shrink window if distinct characters exceed k
            while (freq.size() > k) {
                char leftChar = s.charAt(left);
                freq.put(leftChar, freq.get(leftChar) - 1);
                if (freq.get(leftChar) == 0) freq.remove(leftChar);
                left++;
            }

            // Add count of substrings in current window
            res += (right - left + 1);
        }
        return res;
    }

    // Function to count substrings with exactly k distinct characters
    public static int countSubstrings(String s, int k) {
        return atMostKDistinct(s, k) - atMostKDistinct(s, k - 1);
    }
    // lc 14 Longest Common Prefix
    // Compare each string character by chararcter wise with the first string, o(n^2)
    public String longestCommonPrefix(String[] strs) {
        String ans="";
        for(int i=0;i<strs[0].length();i++)
        {
            for(String str: strs)
            {
                if(i==str.length() || str.charAt(i)!=strs[0].charAt(i))
                { // i==str.length() to make sure that we dont go out of bounds if a given string is leeser than the first string
                    return ans;
                }
            }
            ans= ans + strs[0].charAt(i);
        }
        return ans;
    }
    // leetcode 5 Longest Palindromic Substring
    public String longestPalindrome(String s) {
        if(s.length()==1)
            return s;
        int st=0,e=0;
        for(int i=0;i<s.length();i++)
        {
            int odd= checkPallindromeLen(i,i,s);
            int even= checkPallindromeLen(i,i+1,s);
            int ma=Math.max(odd,even);
            if(ma>(e-st))
            {
                st=i-(ma-1)/2;
                e=i+ma/2;
            }
        }

        return s.substring(st,e+1);
        

    }
    private int checkPallindromeLen(int left,int right,String s)
    {
        while((left>=0 && right<s.length()) && (s.charAt(left)==s.charAt(right)))
        {
            left--;right++;
        }
        return right-left-1; // length of the pallindrome 
    }
    // leetcode 647 Palindromic Substrings brite force 
    public int countSubstrings(String s) {
        // brute force 
        int ans=0;
        for(int i=0;i<s.length();i++)
        {
            for(int j=i+1;j<=s.length();j++)
            {
                String str=s.substring(i,j);
                ans+=isPallindrome(str);
            }
        }
        return ans;
    }
    private int isPallindrome(String str)
    {
        int n= str.length();
        for(int i=0;i<n/2;i++)
        {
            if(str.charAt(i)!=str.charAt(n-i-1) && n>0)
                return 0;
        }
        return 1;
    }
    // better approach for lc 647 using similar pattern in lc 5

    public int countSubstringsOptimized(String s) {
        if(s.length()==1)
            return 1;
        int ans=0;
        for(int i=0;i<s.length();i++)
        {
            ans += checkPallindrome(i,i,s);
            ans += checkPallindrome(i,i+1,s);
        }

        return ans;
    }
    private int checkPallindrome(int left,int right,String s)
    {
        int count = 0;
        while((left>=0 && right<s.length()) && (s.charAt(left)==s.charAt(right)))
        {
            left--;right++;
            count++;
        }
        return count; 
    }

    // leetcode 921 Minimum Add to Make Parentheses Valid
    public int minAddToMakeValid(String s) {
        int ch1=0,ch2=0;
        for(int i=0;i<s.length();i++)
        {
            char ch=s.charAt(i);
            if(ch=='(')
            {
                ch1++;
            }
            else if(ch==')' && ch1>0)
                ch1--;
            else
                ch2++;
        }
        return ch1+ch2;
    }
    public static void main(String[] args) {
        String path="WNEENESENNN";
       // System.out.println(ShortestPath(path));
       // System.out.println(compress2("aaabbcccdd"));

        String s = "abcbaa";
        int k = 3;

        // Output the result
        System.out.println("Count: " + countSubstrings(s, k)); // Output: 7
    }
}