Great question! This is one of the most important pattern recognition skills for interviews. Let me break it down systematically.

---

## How to DETECT These Problems

**Sliding Window keywords to spot:**
```
"longest substring..."
"shortest substring..."
"maximum/minimum length..."
"contiguous subarray/substring..."
"within k distinct..."
"at most/least k..."
"no repeating characters..."
"contains all characters of..."
```

**Key giveaway:** The problem involves a **contiguous** portion of the string and asks for optimal (min/max) result.

---

## Two Types of Sliding Window

```
Type 1: FIXED size window
→ Window size k is given
→ Slide one step at a time

Type 2: VARIABLE size window
→ Window expands and shrinks
→ Uses two pointers (left, right)
→ Most common in string problems
```

---

## The Universal Template

**Fixed Window:**
```java
int left = 0;
// Step 1: Build initial window of size k
for(int right = 0; right < k; right++) {
    // add s.charAt(right) to window
}
// Step 2: Slide window
for(int right = k; right < s.length(); right++) {
    // add s.charAt(right)       → expand right
    // remove s.charAt(left++)   → shrink left
    // update answer
}
```

**Variable Window:**
```java
int left = 0;
for(int right = 0; right < s.length(); right++) {
    // Step 1: Add right character to window
    
    // Step 2: Check if window is INVALID
    while(window is invalid) {
        // remove left character from window
        left++;
    }
    
    // Step 3: Window is valid here, update answer
    ans = Math.max(ans, right - left + 1);
}
```

---

## Pattern Recognition Guide

### Pattern 1: No Repeating Characters
**Recognise:** "longest substring without repeating"
```java
// LC 3 - Longest Substring Without Repeating Characters
public int lengthOfLongestSubstring(String s) {
    int[] freq = new int[128];
    int left = 0, ans = 0;
    
    for(int right = 0; right < s.length(); right++) {
        freq[s.charAt(right)]++;
        
        // Window invalid when any char appears more than once
        while(freq[s.charAt(right)] > 1) {
            freq[s.charAt(left)]--;
            left++;
        }
        
        ans = Math.max(ans, right - left + 1);
    }
    return ans;
}
```

### Pattern 2: At Most K Distinct Characters
**Recognise:** "at most k distinct", "k unique characters"
```java
// LC 340 - Longest Substring with At Most K Distinct Characters
public int lengthOfLongestSubstringKDistinct(String s, int k) {
    int[] freq = new int[128];
    int left = 0, ans = 0, distinct = 0;
    
    for(int right = 0; right < s.length(); right++) {
        if(freq[s.charAt(right)] == 0) distinct++;
        freq[s.charAt(right)]++;
        
        // Window invalid when distinct chars exceed k
        while(distinct > k) {
            freq[s.charAt(left)]--;
            if(freq[s.charAt(left)] == 0) distinct--;
            left++;
        }
        
        ans = Math.max(ans, right - left + 1);
    }
    return ans;
}
```

### Pattern 3: Contains All Characters (Minimum Window)
**Recognise:** "minimum window substring", "contains all characters of t"
```java
// LC 76 - Minimum Window Substring
public String minWindow(String s, String t) {
    int[] need = new int[128];
    int[] have = new int[128];
    
    for(char ch : t.toCharArray()) need[ch]++;
    
    int left = 0, matched = 0, minLen = Integer.MAX_VALUE;
    int start = 0;
    int required = t.length();
    
    for(int right = 0; right < s.length(); right++) {
        char ch = s.charAt(right);
        have[ch]++;
        
        // Count valid matches
        if(need[ch] > 0 && have[ch] <= need[ch]) matched++;
        
        // Shrink window when all chars matched
        while(matched == required) {
            if(right - left + 1 < minLen) {
                minLen = right - left + 1;
                start = left;
            }
            have[s.charAt(left)]--;
            if(need[s.charAt(left)] > 0 && 
               have[s.charAt(left)] < need[s.charAt(left)]) matched--;
            left++;
        }
    }
    
    return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
}
```

### Pattern 4: Fixed Size Window
**Recognise:** "every substring of size k", "window of size k"
```java
// LC 438 - Find All Anagrams in a String
public List<Integer> findAnagrams(String s, String p) {
    int[] need = new int[26];
    int[] have = new int[26];
    List<Integer> ans = new ArrayList<>();
    
    for(char ch : p.toCharArray()) need[ch - 'a']++;
    
    int left = 0;
    for(int right = 0; right < s.length(); right++) {
        have[s.charAt(right) - 'a']++;
        
        // Shrink window when it exceeds size p
        if(right - left + 1 > p.length()) {
            have[s.charAt(left) - 'a']--;
            left++;
        }
        
        // Check if window matches
        if(Arrays.equals(have, need)) ans.add(left);
    }
    return ans;
}
```

---

## How to Think When You See a New Problem

```
Step 1: Is it asking for contiguous portion? → Maybe sliding window
Step 2: Is there a constraint on the window? → Yes = sliding window
Step 3: Fixed or variable size?
        → Size given = fixed window
        → Find min/max = variable window
Step 4: What makes window INVALID?
        → This drives your while loop condition
Step 5: What are you tracking?
        → frequency? → int[128] or HashMap
        → distinct count? → separate counter
        → matched chars? → matched counter
```

---

## The Invalidity Condition is Everything

This is the **core skill** — identifying what makes a window invalid:

| Problem Type | Invalid When |
|---|---|
| No repeating chars | any `freq > 1` |
| At most K distinct | `distinct > k` |
| Minimum window | `matched < required` (shrink when valid) |
| Anagram/permutation | `window size > p.length()` |
| At most K replacements | `windowSize - maxFreq > k` |

---

## Practice Roadmap in Order

```
Level 1 - Fixed Window:
→ LC 643  Maximum Average Subarray I
→ LC 438  Find All Anagrams
→ LC 567  Permutation in String

Level 2 - Variable Window (easy):
→ LC 3    Longest Substring Without Repeating
→ LC 1004 Max Consecutive Ones III

Level 3 - Variable Window (medium):
→ LC 424  Longest Repeating Character Replacement
→ LC 340  Longest Substring with At Most K Distinct

Level 4 - Hard:
→ LC 76   Minimum Window Substring
→ LC 239  Sliding Window Maximum
```

The **single biggest skill** is identifying what makes your window invalid — once you nail that, the rest of the template stays the same across almost every problem.