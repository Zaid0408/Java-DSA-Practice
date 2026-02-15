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
                c=c+s.charAt(i);
            }
            else
            {
                if(c.length()>0){
                    c=rev(c);
                    System.out.println(c);
                    m= m+c+" ";
                    c="";
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
    public String rev(String k)
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
    // Leetcode 205 Isomorphic Strings
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
                ));
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
        for(int i = s.length(); i >= 1; i--) {
            for(char ch : buckets.get(i)) {
                for(int j = 0; j < i; j++) {
                    sb.append(ch);
                }
            }
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        String path="WNEENESENNN";
       // System.out.println(ShortestPath(path));
        System.out.println(compress2("aaabbcccdd"));
    }
}