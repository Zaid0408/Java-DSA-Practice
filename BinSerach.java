import java.security.Key;
import java.util.*;


// For identifying binary searach : see this in question : SEARCH , SORT these keywords together then yes 
// If we have to search andthe question has a sorrting tendency or exactly sorted then yes
// aslo if we run out of time while solving for o(n) then yes
// more than sorting also if we see any particular range in the question then yes we can use binary search ex: square root nth root koko eating bananas etc 
public class BinSerach {
    // Pattern 1 : uperbound and lower bound
    // lowerbound problem to find the index of the smallest element greater than or equal to x. nums[i]>=x
    public int lowerBound(int[] nums, int x) {
        int ans=nums.length-1;
        int s=0,e=nums.length-1;
        while(s<=e)
        {
            int mid=(s+e)/2;
            if(nums[mid]>=x){
                ans=mid;
                e= mid-1;    
            }
            else if(nums[mid]<x)
            {
                s=mid+1;
            }
        }

        return ans;
    }

    // leetcode 35 Search Insert Position
    public int searchInsert(int[] nums, int target) { // same as lower bound finding 
        int ans=nums.length-1;
        if(target>nums[ans]) // one edge case where target is greater than the last element
            return ans+1;
        int s=0,e=nums.length-1;
        while(s<=e)
        {
            int mid=(s+e)/2;
            if(nums[mid]>=target){
                ans=mid;
                e= mid-1;    
            }
            else if(nums[mid]<target)
            {
                s=mid+1;
            }
        }

        return ans;
    }

    // upperbound problem The upper bound of x is defined as the smallest index i such that nums[i] > x.
    public int upperBound(int[] nums, int x) {
        int ans=nums.length-1;
        int s=0,e=nums.length-1;
        while(s<=e)
        {
            int mid=(s+e)/2;
            if(nums[mid]>x){
                ans=mid;
                e= mid-1;    
            }
            else if(nums[mid]<=x)
            {
                s=mid+1;
            }
        }

        return ans;
    }

    // floor and ceil : floor is the largest element less than or equal to x and ceil is the smallest element greater than or equal to x
    public int[] getFloorAndCeil(int[] nums, int x) {
        int ans=nums.length-1;
         int s=0,e=nums.length-1;
         boolean found= false;
         while(s<=e) // lower bound code
         {
             int mid=(s+e)/2;
             if(nums[mid]>=x){
                 ans=mid;
                 e= mid-1;    
                 if(nums[mid]==x) // if the element is found then floor and ceil are same
                     found=true;
             }
             else if(nums[mid]<x)
             {
                 s=mid+1;
             }
         }
         if(found)
             return new int[]{nums[ans],nums[ans]};
         return new int[]{nums[ans-1],nums[ans]};
     }

    //leetcode 34. Find First and Last Position of Element in Sorted Array
    public int[] searchRange(int[] nums, int target) {
        int s=0,e=nums.length-1;
        int mid=0;
        int ans[]=new int[]{-1,-1};
        while(s<=e)
        {
            mid=(s+e)/2;
            if(nums[mid]>target)
                e=mid-1;
            else if(nums[mid]<target)
                s=mid+1;
            else if(nums[mid]==target)
            {
                int x=mid,y=mid;
                while(x< nums.length-1 && nums[x+1]==target)
                    x++;
                while(y>0 && nums[y-1]==target)
                    y--;
                ans[0]=y;
                ans[1]=x;
                return ans; // important otherwise time limit exceded
            }
        }

        return ans;
    }
    // count Occurences : Count occurences of given element in sorted array
    public int countOccurrences(int[] arr, int target) {
        // Your code goes here
        int s=0,e=arr.length-1;
        int mid=0;
        int ans=-1;
        while(s<=e)
        {
            mid=(s+e)/2;
            if(arr[mid]>target)
                e=mid-1;
            else if(arr[mid]<target)
                s=mid+1;
            else if(arr[mid]==target)
            {
                int x=mid,y=mid;
                while(x< arr.length-1 && arr[x+1]==target)
                    x++;
                while(y>0 && arr[y-1]==target)
                    y--;
                // ans[0]=y;
                // ans[1]=x;
                ans=x-y+1;
                return ans;
            }
        }

        return ans;
    }
    // pattern 2 : Rotatede sorted array problems 
    // leetcode 33 : Search in rotated sorted array
    public int search(int[] nums, int target) { 
        // Figure out the sorted part of the array left or right? then check if target comes in left part or right part based on relation with mid element
        // Atleast one half of the array is sorted compulsorily
        int ans=-1;
        int s=0,e=nums.length-1;
        int mid=0;
        while(s<=e)
        {
            mid=(s+e)/2;
            if(nums[mid]==target)
                ans=mid;
            if (nums[s] <= nums[mid]) { // assuming if left part of the array us sorted (from start to mid), nothing to do with the target
                if (target > nums[mid] || target < nums[s]) // target is on right side 
                    s = mid + 1;
                else
                    e = mid - 1;
            }
            else { // assuming if right part of the array us sorted (from mid to end), nothing to do with the target
                if (target < nums[mid] || target > nums[e]) // target is on left side
                    e = mid - 1;
                else
                    s = mid + 1;
            }
        }

        return ans;
    }

    // leetcode 81 Search in Rotated Sorted Array II , this time elements are repeated , same as above but extra condition added 
    public static boolean searchOnly(int[] nums, int target) {
        int s=0,e=nums.length-1;
        while(s<=e)
        {
            int mid=(s+e)/2;
            if(nums[mid]==target)
                return true;
            if(nums[s]== nums[mid] && nums[mid]==nums[e])// necessary because what if the array repeats and goes to the other half ex: [1,0,1,1,1] where target is 0 
            {
                s++;
                e--;
                continue;
            }
            if (nums[s] <= nums[mid]) { 
                if (target > nums[mid] || target < nums[s]) // target is on right side 
                    s = mid + 1;
                else
                    e = mid - 1;
            }
            else { 
                if (target < nums[mid] || target > nums[e]) // target is on left side
                    e = mid - 1;
                else
                    s = mid + 1;
            }
            
        }
        return false;
    }

    // leetcode 153 Find Minimum in Rotated Sorted Array
    public static int findMin(int[] nums) {
        // Find minimum element in a rotated sorted array
        // input: nums = [4,5,6,7,0,1,2] Output: 0 input: nums= [3,4,5,1,2] Output: 1
        // Key Intuition (VERY IMPORTANT)
        // One half is always sorted
        //Minimum lies in the unsorted half
        // If fully sorted → leftmost is min
        int mini=Integer.MAX_VALUE;
        int start=0,end=nums.length-1;
        while(start<=end)
        {
            if(nums[start]<nums[end]) // when we reach a sorted subarray then the leftmost lement is the smallest element in the list
            {
                mini=Math.min(nums[start],mini);
                break;
            }
            int mid=(start+end)/2;
            mini=Math.min(nums[mid],mini);
            if(nums[start]<=nums[mid]) // mid > start means mid is part of left sorted array and is larger than start hence search right sorted array part
            {   // this is done as since the array is rotated so nums[start] is NOT the samllest element ex: [4,5,6,7,0,1,2] ; [3,4,5,1,2]
                start=mid+1;
                
            }
            else
            {
                end=mid-1;
            }
        }
        return mini;
    }

    // Find out how many times the array is rotated ,same as above but return the index  
    public int findKRotation(ArrayList<Integer> nums) { // find the smallest number in the arry , the index at which it is present is the number of times the array is rotated
        int mini=-1;
        int start=0,end=nums.size()-1;
                // Key Intuition (VERY IMPORTANT)
        // One half is always sorted
        //Minimum lies in the unsorted half
        // If fully sorted → leftmost is min
        while(start<=end)
        {
            if(nums.get(start)<nums.get(end)) // when we reach a sorted subarray then the leftmost lement is the smallest element in the list
            {
                mini=start;
                break;
            }
            int mid=(start+end)/2;
            mini=Math.min(mid,mini);
            if(nums.get(start)<=nums.get(mid))
            {   // mid > start means mid is part of left sorted array and is larger than start hence search right sorted array part
                start=mid+1;
                
            }
            else
            {
                end=mid-1;
            }
        }
        return mini;
    }

    public static void main(String[] args)
    {
        //         m
        //         s e   
        // int arr[]={1,0,1,1,1};
        // System.out.println(searchOnly(arr, 0));

        int mat[][]= {{1,2,3,1,8},{2,3,4,5,6}};
        System.out.println(findPeakGrid(mat));
    }

    // leetcode 540 Single Element in a Sorted Array
    public int singleNonDuplicate(int[] nums) {
        int n=nums.length;
        if(nums.length==1)
            return nums[0];
        if(nums[0]!=nums[1])
            return nums[0];
        if(nums[n-1]!=nums[n-2])
            return nums[n-1];
// From left side of target element for all pairs 1st occurrence is at even position 2nd at odd (even,odd)

        // From the target element all pairs to the right : 1st occurrence at odd pos but 2nd at even (odd,even)

        int mid=0,s=1,e=nums.length-2;
        while(s<=e)
        {
            mid=(s+e)/2;
            if(nums[mid-1]!= nums[mid] && nums[mid]!=nums[mid+1])
                return nums[mid];
            if((mid%2==1 && nums[mid]==nums[mid-1]) || (mid%2==0 && nums[mid]==nums[mid+1])) // if odd position and mid and mid-1 are same then no single element on the left hence go right , either of these conditions satisfy condition one
            {
                s=mid+1;
            } // or if even position and mid and mid+1 are same then no single element on the right hence go left , can be checked with above conditions to verify 
            else 
            {
                e=mid-1;
            } // everything else go left
        }
        return -1;

    }
    // leetcode 162 Find Peak Element
    public int findPeakElement(int[] nums) {
        int s=1,e=nums.length-2,mid=0;
        if(nums.length==1)
            return 0;
        if(nums[e+1]>nums[e])
            return e+1;
        if(nums[0]>nums[1])
            return 0;
        
        while(s<=e)
        {
            mid=(s+e)/2;
            if(nums[mid]>nums[mid-1] && nums[mid]>nums[mid+1])
                return mid;
            else if(nums[mid]>nums[mid-1])
                s=mid+1;
            else // not writing the other condition nums[mid]>nums[mid+1] as 1. if nums[mid] is less than both then it getsv stuck in an infinite loop
                e=mid-1;
        }
        // even if nums[mid] is less than both the elements, i know that the peak is there on either side as condition is to return any peak so go anyway from there
        // also satisfies the condition nums[mid]>nums[mid+1]

        return -1;
    }
    // Pattern 3 : Binary seqarch over search space instead of array , calculate s and e from the array or question and do bin search over search space
    // leetcode 69 Sqrt(x) 
    public int mySqrt(int x) {
        if(x<=1)
            return x;
        int left=0,right=x;
        int ans=0;
        while(left<=right)
        {
            int mid=(left + right)/2;
            if(mid<x/mid){
                left=mid+1;
                ans=mid; // could be possible max value whose sqaure <=x
            }
            else if(mid>x/mid) // better than mid*mid > x as the multiplication can cause overflow of integer and comparision will onot be possble 
                right=mid-1;
            else if(mid == x/mid)
                return mid;   
        }
        return ans;
    }

    //nth root of a number
    public int NthRoot(int N, int M) {
        int s=0,e=M,mid=0,midN=0;
        while(s<=e)
        {
            mid=(s+e)/2;
            midN=func(mid,N,M);
            if(midN== 1)
                return mid;
            else if(midN == 0)
                s=mid+1;
            else
                e=mid-1;
        }
        return -1;
    }
    public int func(int mid, int n, int m)
    { // 13 3 27
        long ans=1;
        for(int i=1;i<=n;i++) // multiply mid n times withitself so know which way to move 
        {
            ans=ans*mid;
            if(ans> m) // return 2 immidiately to stop any more iterations 
                return 2;
        }
        if(ans==m) // if multiplying n times with itself is equal to m then this is the answer
            return 1;
        return 0;
    }

// advanced binary search problems : they deal with search space more than search elements , finding the serach space is crucial for us to solve this problem

    // leetcode 875 Koko eating bananas

    public int minEatingSpeed(int[] piles, int h) { // Time Complexity O(nlogn)
        // to find min integer k which is the banana eaten per hour to finish all bananas within time h in hours
        // ex: if we say avg eating of banana ie k=3 then piles[0]=3 -> 1 hr, 6->2 7->3 hrs(3+3+1) and 11->4(3+3+3+2) adding all times comes around 10 > h=8 hence not correct 
        // if we say avg eating of banana ie k=4 then piles[0]=3 -> 1 hr, 6->2 7->2 hrs(4+3) and 11->3(4+3+3) adding all times comes around 8 == h=8 hence  correct 
        // k=5,6,7... are all correct but since we need min integer hence only correct answer is k=4
        int e=-1,s=1,mid=0,ans=-1;
        for(int i=0;i<piles.length;i++)
        {
            e=Math.max(piles[i],e); // search space calculation between s and e 
        }
        while(s<=e)
        {
            mid=(s+e)/2;
            if(helper(piles,mid,h)==1) // helper function that compares with mid 
            {
                e=mid-1;
                ans=mid;
            }
            else
            {
                s=mid+1;
            }
        }

        return ans;

    }
    public int helper(int[] piles, int mid,int h)
    {
        long count=0;
        for(int i=0;i<piles.length;i++)
        {
            if(piles[i]%mid>0)
                count += 1 ;
            count += piles[i]/mid;
        }
        if(count>h)
            return 0;
        return 1;
    }

    // leetcode 1283 Find the Smallest Divisor Given a Threshold , same logic as koko eating bananas // same pattern as searching in search space 
    public int smallestDivisor(int[] nums, int threshold) { // Time Complexity O(nlogn)
        int s=1,e=-1,mid=0,ans=-1;
        long divisor=0;
        for(int i=0;i<nums.length;i++)
        {
            e=Math.max(e,nums[i]);
        }
        while(s<=e)
        {
            mid=(s+e)/2;
            divisor=helper(nums,mid);
            if(divisor <= threshold)
            {
                e=mid-1;
                ans=mid;
            }
            else
            {
                s=mid+1;
            }

        }

        return ans;
    }
    public long helper(int nums[],int mid)
    {
        long div=0;
        for(int i=0;i<nums.length;i++)
        {
            div += (int)Math.ceil((double)nums[i]/(double)mid);
        }
        return div;
    }

    // leetcode 1011 Capacity to ship Packages within D days // same logic as above 
    public int shipWithinDays(int[] weights, int days) {
        int s=1,e=0,mid=0,ans=-1,capacity=0;
        for(int i=0; i<weights.length;i++)
        {
            s=Math.max(s,weights[i]); // starting with maximum weight as it reduces calculation INSTEAD OF starting from 1:  time saved 
            e+=weights[i];
        }
        while(s<=e)
        {
            mid=(s+e)/2;
            capacity=numOfDays(weights,mid);
            if(capacity<=days)
            {
                e=mid-1;
                ans=mid;
            }
            else
            {
                s=mid+1;
            }
        }

        return ans;
    }

    public int numOfDays(int weights[],int mid)
    {
        int day = 1; 
        int currentLoad = 0;
        for(int i = 0; i < weights.length; i++) {
            // If adding current weight exceeds capacity, start a new day
            if(currentLoad + weights[i] > mid) {
                day++;
                currentLoad = 0;  // Reset load for new day
            }
            currentLoad += weights[i];
        }
        
        return day;
    
    }

    // leetcode 1482 Minimum Number of Days to Make m Bouquets // same logic as above
    public int minDays(int[] bloomDay, int m, int k) {
        if(m*k>bloomDay.length)
            return -1;
        
        int s=Integer.MAX_VALUE, e=-1,mid=0,ans=-1,countM=0;
        for(int i=0;i<bloomDay.length;i++) // find ideal range
        {
            s=Math.min(s,bloomDay[i]);
            e=Math.max(e,bloomDay[i]);
        }
        while(s<=e)
        {
            mid=(s+e)/2;
            countM=help(bloomDay,mid,k);
            if(countM>=m)
            {
                e=mid-1;
                ans=mid;
            }
            else
            {
                s=mid+1;
            }
        }
        return ans;
    }
    public int help(int[] bloomDay, int mid, int k)
    {
        int cnt=0,noOfB=0;
        for(int i=0;i<bloomDay.length;i++)
        {
            if(bloomDay[i]>mid)
            {
                noOfB += (cnt/k);
                cnt=0;
            }
            else
            {
                cnt++; 
            }
        }
        noOfB += (cnt/k);
        return noOfB;
    }

    // leetcode 1539 Kth Missing Positive Number
    public int findKthPositive(int[] arr, int k) {
        int s = 0, e = arr.length - 1;
        int mid=0;
        while (s <= e) {
            mid=s+(e-s)/2;
            // number of missing numbers before arr[mid]
            int missing = arr[mid]-(mid+1); // works in theory , please apply all scenarios and validate
            if (missing < k) {
                s=mid+1;
            } else {
                e=mid-1;
            }
        }
        // s is the first index where missing >= k
        return s+k;
    }

    // Pattern 4 Agressive Cows remeber this keyword in the question : maximum of minimum value or minimum of maximumvalue.
    // Key takeaway (IMPORTANT for interviews)

    //Pattern:

    // Minimize max → move left when valid
    // Maximize min → move right when valid
    /*
    Aggressive Cows — Problem Statement

You are given an array of integers stalls[] representing the positions of stalls on a number line. You are also given an integer k, representing the number of cows.

Your task is to place the k cows in these stalls such that the minimum distance between any two cows is as large as possible.

Return the largest possible minimum distance between any two cows.

Constraints
Each stall can have only one cow
Cows can only be placed in the given stall positions
Stall positions may not be sorted
📥 Input
stalls[]: array of integers (positions of stalls)
k: number of cows
📤 Output
An integer representing the maximum possible minimum distance

Example
Input:
stalls = [1, 2, 4, 8, 9]
k = 3

Output:
3
Explanation (brief)

Place cows at positions 1, 4, 8 → minimum distance = 3, which is the best possible.
    */


    public int AgressiveCows(int stalls[],int k)
    {
        Arrays.sort(stalls);
        int s=1,e=stalls[stalls.length-1]-stalls[0],mid=0; // e has to be that value because we have to find the maximum possible minimum distance
        while(s<=e)
        {
            mid=(s+e)/2;
            if(isPossible(stalls,mid,k)) // possible to keep k cows in given mid stalls? if yes then try finding out the maximum value mid can be to be able to place all cows 
            {
                s=mid+1;// Maximize min → move right when valid
            }
            else
            {
                e=mid-1; // if we cannot place k cows among stalls for a given mid value then reduce the mid value
            }
        }
        return e; // e will be pointing to the maximum possible minimum distance as s>e after binary search.
    }
    public boolean isPossible(int stalls[],int mid,int k)
    {
        int cows=1;
        int lastPos=stalls[0];
        for(int i=1;i<stalls.length;i++)
        {
            if(stalls[i]-lastPos>=mid) // can place a cow by checking the difference between stall positions is >=mid(min length of distance cows should be kept at)
            {
                cows++; // if true then we can add another cow
                lastPos=stalls[i]; // last cow was placed at this distance
            }
        }
        return cows>=k; // if cows count > k return true because we can place k cows in the stalls for the given mid values 
    }

    //leetcode 1552. Magnetic Force Between Two Balls , exactly same as above pattern
    public int maxDistance(int[] position, int m) {
        Arrays.sort(position);
        int s=1,mid=0;
        if(m<=2)
            return position[position.length-1]-position[0];
        int e=position[position.length-1]-position[0];
        while(s<=e)
        {
            mid=(s+e)/2;
            if(isPossibleMageneticForce(position,mid,m))
            {
                s=mid+1; // Maximize min → move right when valid
            }
            else
                e=mid-1;
        }

        return e;
    }
    public boolean isPossibleMageneticForce(int position[],int mid,int m)
    {
        int ball=1;
        int lastPos=position[0];
        for(int i=1;i<position.length;i++)
        {
            if(position[i]-lastPos>=mid)
            {
                ball++;
                lastPos=position[i];
            }
        }

        return ball>=m;
    }

    // Book allocation problem in TUF sheet

    public int findPages(int[] nums, int m) {
        if(m > nums.length) return -1;
        int s=1,e=0,mid=0;
        for (int num : nums) {
            s = Math.max(s, num); // max single book
            e += num;             // total pages
        }

        while(s<=e)
        {
            mid=(s+e)/2;
            if(AllocateBooks(nums,mid,m)) 
            {
                e=mid-1; // Minimize max → move left when valid
            }
            else
            {
                s=mid+1; 
            }
        }

        return s; //here minimizing the maximum hence return s
        
    }
    public boolean AllocateBooks(int nums[], int mid,int m)
    {
        int book=1;
        int sum=nums[0];
        for(int i=1;i<nums.length;i++)
        {
            if(sum+nums[i]>mid)
            {
                book++;
                sum=nums[i];
            }
            else{
                sum+=nums[i];
            }
        }
        return book<=m;
    }

    // leetcode 410. split array

    public int splitArray(int[] nums, int k) {
        int s = 0, e = 0;

        for (int num : nums) {
            s = Math.max(s, num); // largest element
            e += num;             // total sum
        }

        while (s <= e) {
            int mid = s + (e - s) / 2;

            if (canSplit(nums, mid, k)) {
                e = mid - 1; // try smaller max sum
            } else {
                s = mid + 1;
            }
        }

        return s; // first valid
    }

    public boolean canSplit(int[] nums, int maxSum, int k) {
        int count = 1;
        int sum = 0;

        for (int num : nums) {
            if (sum + num > maxSum) {
                count++;
                sum = num;
            } else {
                sum += num;
            }
        }

        return count <= k;
    }

    // Painters partition 

    public int paint(int A, int B, int[] C) {
        // Your code goes here
        long s=0,e=0;
        for(int num:C)
        {
            s=Math.max(num,s);
            e+=num;
        }
        long ans=0;
        while (s <= e) {
            long mid = s + (e - s) / 2;

            if (canPaint(C, mid, A)) {
                ans = mid;
                e = mid - 1; // minimize
            } else {
                s = mid + 1;
            }
        }

        return (int)((ans * B) % 10000003);
    }
    public boolean canPaint(int[] C, long maxLen, int A) {
        int painters = 1;
        long sum = 0;

        for (int len : C) {
            if (sum + len > maxLen) {
                painters++;
                sum = len;
            } else {
                sum += len;
            }
        }

        return painters <= A;
    }

    // leetcode 4 Median of 2 sorted array TODO: Apply the abovementioned pattern to this problem 

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        List<Integer> nums= new ArrayList<>();
        for(int num:nums1)
            nums.add(num);
        for(int num:nums2)
            nums.add(num);

        Collections.sort(nums);

        return median(nums);
            
    }
    public double median(List<Integer> nums)
    {
        int n=nums.size();
        if(n%2==0)
        {
            return ((double)nums.get(n/2) + nums.get(n/2-1))/2;
        }
        return (double)nums.get(n/2);
    }

    public static int[] findPeakGrid(int[][] mat) { // same logic as peak element 1
        if(mat.length==1 && mat[0].length==1)
            return new int[]{0,0};
        if(mat.length>1 && mat[0].length==1)
        {
            int pos=Max(mat,0);
            return new int[]{pos,0};
        }
        int s=0,e=mat[0].length-1,mid=0,pos=-1;
        while(s<=e)
        {
            mid=(s+e)/2;
            pos=Max(mat,mid);
            if(mid-1>=0 && mid+1<mat[0].length)
            {
                if(mat[pos][mid-1]>mat[pos][mid])
                    e=mid-1;
                else if(mat[pos][mid+1]>mat[pos][mid])
                    s=mid+1;
                else
                    return new int[]{pos,mid};
            }
            else if(mid-1>=0 && mid+1>=mat[0].length) // rightmost column
            {
                if(mat[pos][mid-1]>mat[pos][mid])
                    e=mid-1;
                else
                    return new int[]{pos,mid};
            }
            else if(mid+1<mat[0].length && mid-1<0) // leftmost column ??
            {
                if(mat[pos][mid+1]>mat[pos][mid])
                    s=mid+1;
                else
                    return new int[]{pos,mid};
            }
        }

        return new int[]{-1,-1};

    }
    public static int Max(int mat[][],int mid)
    {
        int p=-1;
        int max=Integer.MIN_VALUE;
        for(int i=0;i<mat.length;i++)
        {
            if(mat[i][mid]>max)
            {
                max=mat[i][mid];
                p=i;
            }
        }
        return p;
    }

}
