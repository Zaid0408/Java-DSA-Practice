import java.util.*;
class array{
    Scanner sc = new Scanner(System.in);
    public static List<List<Integer>> generateSubarraysBruteForce(int[] arr) { // O(n^3) to find all subarrays of a given array
        List<List<Integer>> result = new ArrayList<>();
        
        int n = arr.length;
        for (int i = 0; i < n; i++) { // Start index
            for (int j = i; j < n; j++) { // End index
                List<Integer> subarray = new ArrayList<>();
                for (int k = i; k <= j; k++) { // Extract elements
                    subarray.add(arr[k]);
                }
                result.add(subarray);
            }
        }
        return result;
    }
    public static List<List<Integer>> generateSubarraysOptimized(int[] arr) { // O(n^2) to find all subarrays of a given array. Optimized approach
        List<List<Integer>> result = new ArrayList<>();
        
        int n = arr.length;
        for (int i = 0; i < n; i++) { // Start index
            List<Integer> subarray = new ArrayList<>();
            for (int j = i; j < n; j++) { // Extend the subarray
                subarray.add(arr[j]); // Append element instead of using a third loop
                result.add(new ArrayList<>(subarray)); // Store copy of subarray
            }
        }
        return result;
    }
    public long subArrayRanges(int[] nums) { // Leetcode 2104 , refer to above code for finding all sub arrays
        int n = nums.length;
        long answer=0;
        for (int i = 0; i < n; i++) { // Start index of the subarray
            // List<Integer> subarray=new ArrayList<>();
            int mini=Integer.MAX_VALUE;
            int maxi=Integer.MIN_VALUE;
            for (int j = i; j < n; j++) { // Extend the subarray
                //subarray.add(nums[j]); // Append element instead of using a third loop will form a sub array each time an element is added.
                mini=Math.min(mini,nums[j]);
                maxi=Math.max(maxi,nums[j]);
                answer+=(maxi-mini);
                
            }
        }
        return answer;
    }
    public static void maxSubArrSum(int nums[])//Calculate Maximum SubArray Sum in O(n^2) complexity.
    {
        int maxSum=Integer.MIN_VALUE;
        int currSum=0;
        int prefixArray[]= new int[nums.length];//use curr sum and prefix sum array to store the sum of all the elements up till that index initially
        prefixArray[0]=nums[0];
        for(int i=1;i<nums.length;i++)
        {
            prefixArray[i]=prefixArray[i-1]+nums[i];
        }
        for(int i=0;i<nums.length;i++)
        {
            int start=i;
            for(int j=0;j<nums.length;j++)
            {
                int end=j;
                currSum= start==0?prefixArray[end]:prefixArray[end]-prefixArray[start-1];
                if(maxSum<currSum)
                    maxSum=currSum;
                /*for remaining subarray's do prefixArray[end]-prefixArray[start-1] 
                which will help in determing the sum of that particular subarray startting from start position till end postion
                 */
            }
        }
        System.out.println("Max Subarray Sum is "+maxSum);
    }
    // letcode 560 Subarray Sum Equals K 
    public int subarraySum(int[] nums, int k) {
        HashMap<Integer,Integer> map=new HashMap<>();
            //(sum,occurence of the sum in the array which is same as total number of sub arrays with sum k.)
        map.put(0,1);
        int sum=0,ans=0;
        for(int j=0;j<nums.length;j++)
        {
            sum+=nums[j];
            if(map.containsKey(sum-k))
                ans+=map.get(sum-k);
            map.put(sum,map.getOrDefault(sum,0)+1);
        }

        /*
        Here's how the algorithm works:

            Initialize the cumulative sum sum to 0 and the answer ans to 0.
            Add a key-value pair to the map with a key of 0 and a value of 1. This represents the fact that there is one subarray with a sum of 0 (the empty subarray).
            Iterate through the array nums from index 0 to nums.length-1.
            For each element nums[i], update the cumulative sum sum by adding nums[i].
            Check if the map contains a key that is equal to sum - k. If it does, it means that there is a subarray with a sum equal to k ending at the current index i. The number of such subarrays is obtained from the map by retrieving the value associated with the key sum - k. 
            This value represents the number of times the cumulative sum sum - k has occurred before the current index i.
            Add the value obtained from the map to the answer ans.
            Update the map by adding a new key-value pair with the current cumulative sum sum and incrementing the value by 1.
            Repeat steps 4-7 for each element in the array nums.
            Return the final value of ans, which represents the number of subarrays with a sum equal to k.
        */

        // or we can do this : o(n^2) complexity O(N) space
        // int sum=0,ans=0;
        // int prefix[]=new int[nums.length+1];
        // prefix[0]=nums[0];
        // for(int i=1;i<=nums.length;i++)
        // {
        //     prefix[i]=prefix[i-1]+nums[i-1];
        // }
        // for(int i=0;i<nums.length;i++)
        // {
        //     for(int j=i+1;j<=nums.length;j++)
        //     {
        //         if(prefix[j]-prefix[i]==k)
        //             ans +=1;
        //     }
        // }

        return ans;
    }
    // leetocde 53
    public static void KadaneAlgo(int nums[])
    {//Maximum Sub Array Sum
        int currSum=0,maxSum=Integer.MIN_VALUE;
        for(int i=0;i<nums.length;i++){
            currSum+=nums[i];
            
            if(maxSum<currSum)
                maxSum=currSum;
            
            if(currSum<0)
                currSum=0;
            
        }
        System.out.println("Max Subarray Sum using Kadane Algo is  "+maxSum);
    }

    /*
    *  Check if array is sorted and rotatted Leetcode 1752
        // find pivot elements (where the array must be rotated at the given position) must be at most one 
        // bcuz for array to be rotated and sorted there will only be one occurence of an elemnt x < y where x is smallest and y is largest post that it wont be rotatable
        //ex array 3,4,5,1,2 here there are are two occurences where arr[i]>arr[i+1] so arr[i] could be a possible pivot , the count of such conditions should be <=1 and is true for every rotated sorted array
    */
    public boolean check(int[] nums) {
        int ans =0;
        int n=nums.length;
        for (int i =0;i<n;i++)
        {
            if(nums[i] > nums[(i+1)%n])
                ans++;
        }

        return ans<=1;
    }
    // leetcode 485
    public int findMaxConsecutiveOnes(int[] nums) {
        int count=0;
        int maxCount=-1;
        for(int i=0;i<nums.length;i++)
        {
            if(nums[i]==0){
                maxCount=Math.max(maxCount,count);
                count =0;
            }   
            else
                count++;
        } 

        return Math.max(maxCount,count); // this is impo as what if the max consecutive ones are as=t the end of array
    }
    // leetcode 283 
    // instead of counting zeroes count non zeroes and move them to the front
    public void moveZeroes(int[] nums) {
        int count =0;
        int n=nums.length;
        for(int i=0;i<n;i++)
        {
            if(nums[i]!=0){
                nums[count]=nums[i];
                count++;
            }
        }
        while(count<n){
            nums[count]=0;
            count++;
        }
    }

    //leetcode 217

    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> hs=new HashSet<>();
        for(int num:nums)
        {
            if(hs.contains(num))
                return true;
            hs.add(num);
        }
        return false;
    }
    //leetcode 242
    public boolean isAnagram(String s, String t) {
        int chs[]=new int[26];

        for(int i=0;i<s.length();i++)
        {
            chs[(int)(s.charAt(i)-'a')] +=1;
        }
        for(int i=0;i<t.length();i++)
        {
            chs[(int)(t.charAt(i)-'a')] -=1;
        }
        for(int i=0;i<chs.length;i++)
        {
            if(chs[i]!=0)
                return false;
        }
        return true;
    }

    // leetcode 1
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer,Integer> ans= new HashMap<>();
         for(int i=0;i<nums.length;i++)
         {
             if(ans.containsKey(target-nums[i]))
             {
                 return new int[]{i,ans.get(target-nums[i])};
             }
             ans.put(nums[i],i);
         }
 
         return new int[]{0,0};
     }
    // leetcode 26
    public int removeDuplicates(int[] nums) {
        int c=0;
        for(int i=0;i<nums.length;i++)
        {
            if(nums[c]!=nums[i])
            {
                c++;
                nums[c]=nums[i];
            }
        }
        return c+1;

    }
    //leetcode 74
    public boolean searchMatrix(int[][] matrix, int target) {
        int j=matrix[0].length-1,i=0;
        while(j>=0 && i<matrix.length)
        {
            if(target==matrix[i][j])
                return true;
            else if(target>matrix[i][j])
            {
                i++;
            }
            else
                j--;
        }
        return false;
    }

    // leaders in an array
    /*
    Given an integer array nums, return a list of all the leaders in the array. A leader in an array is an element whose value is strictly greater than all elements to its right in the given array. The rightmost element is always a leader. The elements in the leader array must appear in the order they appear in the nums array.

Example 1

Input: nums = [1, 2, 5, 3, 1, 2]

Output: [5, 3, 2]

Explanation:

2 is the rightmost element, 3 is the largest element in the index range [3, 5], 5 is the largest element in the index range [2, 5]

Example 2

Input: nums = [-3, 4, 5, 1, -4, -5]

Output: [5, 1, -4, -5]

Explanation:

-5 is the rightmost element, -4 is the largest element in the index range [4, 5], 1 is the largest element in the index range [3, 5] and 5 is the largest element in the range [2, 5]
    */
    public List<Integer> leaders(int[] nums) {
        List<Integer> ans=new ArrayList<>();
        ans.add(nums[nums.length-1]);
        int rightMax=nums[nums.length-1];
        for(int i=nums.length-2;i>0;i--)
        {
            if(nums[i]>rightMax)
            {
                ans.add(nums[i]);
                rightMax=nums[i];
            }
        }

        Collections.reverse(ans);

        return ans;
    }

    // leetcode 136
    public int singleNumber(int[] nums) {
        // XOR operation n^n=0 n^0=n 
        // if all are appearing twice then n^n wil give zero for all , p[ost that there will be only one odd number left which will be doing x^0 hence we will get x
        int ans=0;

        for(int i=0;i<nums.length;i++)
        {
            ans=ans^nums[i];
        }
        return ans;
    }

    public static void AppearTwice(int nums[]){
        Arrays.sort(nums);
        for(int i=0;i<nums.length-1;i++)
        {
            if(nums[i]==nums[i+1]){
                System.out.println("Duplicate number exists at position "+ i);
                return;
            }  
        }
        System.out.println("Array is unique ");

    }
    // leetcode 118 

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ans=new ArrayList<>();
        for(int i=0;i<numRows;i++)
        {
            List<Integer> row=new ArrayList<>();
            for(int j=0;j<=i;j++)
            {
                if(j==0 || j==i){
                    row.add(1);
                }
                else{
                    List<Integer> prev=ans.get(i-1);
                    row.add(prev.get(j)+prev.get(j-1));
                }
            }
            ans.add(row);
        }

        return ans;
    }
    // Container with most water leetcode 11
    public static int maxArea(int[] height) {
        int maxWater=0; // brute force O(n^2)
        for(int i=0;i<height.length-1;i++)
        {
            for(int j=i+1;j<height.length-1;j++)
            {
                int ht=Math.min(height[i],height[j]);
                int wd=j-i;
                int currWater=ht*wd;
                maxWater=Math.max(currWater, maxWater);
            }
        }
        return  maxWater;
    }
    public int ContainerWithMostWater(int[] height) {
        int maxWater=0;// optimised soln using two pointers O(n)
        int lp=0,rp=height.length-1;
        while(lp<rp)
        {
            // calculate area
            int ht=Math.min(height[lp],height[rp]);
            int wd=rp-lp;
            int currWater=ht*wd;
            maxWater=Math.max(currWater, maxWater);

            
            // update pointer
            if(height[lp]<height[rp])
                lp++;
            else
                rp--;
        }
        return  maxWater;
    }
    public static int BestTimeToBuySellStock(int prices[])
    {
        int max_profit=Integer.MIN_VALUE;
        int bp=Integer.MAX_VALUE;
        for(int i=0;i<prices.length;i++)
        {
            if(i==1)
                bp=prices[0];
            else{
                bp=Math.min(prices[i], bp);
            }
            if(bp<prices[i]){
                int profit=prices[i]-bp;
                if(max_profit<profit)
                    max_profit=profit;
            }
            else
            {
                bp=prices[i];
            }
            
        }
        if(max_profit<=0)
            return 0;
        return max_profit;
    }
    public static void TrappedRainWater(int height[])
    {
        if(height.length<=2)
        {
            System.out.println("Invalid");
            return;
        }
        if(isAscending(height)|| isDescending(height))
        {
            System.out.println("Invalid");
            return;
        }
        int LeftMaxBoundary[]= new int[height.length];
        int RightMaxBoundary[]= new int[height.length];
        LeftMaxBoundary[0]=height[0];
        RightMaxBoundary[height.length-1]=height[height.length-1];
        for(int i=1;i<height.length;i++){
            LeftMaxBoundary[i]=Math.max(LeftMaxBoundary[i-1], height[i]);
        }
        for(int j=height.length-2;j>=0;j--)
        {
            RightMaxBoundary[j]=Math.max(RightMaxBoundary[j+1], height[j]);
        }
        int waterStored= 0;
        for(int i=0;i<height.length;i++){
            int waterHeight=Math.min(LeftMaxBoundary[i],RightMaxBoundary[i]);
            int waterOnBar=waterHeight-height[i];
            System.out.println(waterOnBar);
            waterStored = waterStored + waterOnBar;
        }
        System.out.println("total water area stored is "+waterStored);

    }
    static boolean isAscending(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1])
                return false;
        }
        return true;
    }
    static boolean isDescending(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] < array[i + 1])
                return false;
        }
        return true;
    }

    // leetcode 287 
    public int findDuplicate(int[] nums) {
        // implement hare tortoise algo
        // similar to checking from where cycle starts in leetcode 142
    // Phase 1: Finding the intersection point in the cycle
        int slow = nums[0];
        int fast = nums[0];
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        // Phase 2: Finding the entrance to the cycle (the duplicate)
        slow = nums[0];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
            
        return slow;
    }

    public int SerachInRotatedSortedArray(int[] nums, int target) {
        int start = 0, mid = 0, end = nums.length - 1;
    
        while (start <= end) {
            mid = (start + end) / 2;
        
            if (nums[mid] == target)
                return mid;
        
            // For left sorted array
            if (nums[start] <= nums[mid]) {
                if (target > nums[mid] || target < nums[start])
                    start = mid + 1;
                else
                    end = mid - 1;
            }
            // For right sorted array
            else {
                if (target < nums[mid] || target > nums[end])
                    end = mid - 1;
                else
                    start = mid + 1;
            }
        }
        return -1;
    }

    // leetcode 189
    public void rotate(int[] nums, int k) {
        // brute force : for every 1 to k we rotate once the array 
        // extra space : new array K+1 pos to end then 0 to k 
        int n=nums.length;
        k=k%n;
        int arr[]=new int[n];
        for (int i=0;i<n;i++) {
            arr[(i + k) % n] = nums[i];
        }
        for (int i=0;i<n;i++) {
            nums[i] = arr[i];
        }
    }

//     Given an array nums of size n, return the majority element.
//     The majority element is the element that appears more than ⌊n / 2⌋ times. 
//     You may assume that the majority element always exists in the array.
//      Leetcode 169
    public static int majorityElement(int[] nums) { // o(nlogn)
        Arrays.sort(nums);
        return nums[nums.length/2];
    }
    public static int majorityElementOptimzed(int[] nums) {
        // Moore Voting algo : find candidate to be a majority element, count will not turn to 0 if it is majority element
        int count = 0, candidate = -1;
        for(int i=0;i<nums.length;i++) // loop to find candidate with maximum occurences
        {
            if(count==0){ // select one candidate
                candidate=nums[i];
                count=1;
            } // logic is that if the count of a candididate becomes 0 then it cannot be majority element ans it occures atleast n/2+1 times hence cannot be zeero
            else if(candidate==nums[i])
                count++; // increment of given candidate is majority element
            else
                count--; // decrement if not majority
            
        }
        count=0;
        for(int i=0;i<nums.length;i++)
        {
            if(candidate==nums[i])
                count++;
        } 
        if(count>nums.length/2)
            return candidate;   
        return -1;
    }

    // leetcode 229 Majority Element 2
    // return all elemnts who occure more than n/3
    public List<Integer> majorityElement2(int[] nums) {
        HashMap<Integer,Integer> hm=new HashMap<>();
        List<Integer> ans=new ArrayList<>();
        for(int num: nums){
            hm.put(num,hm.getOrDefault(num,0)+1);
        }
        Set<Integer> st=hm.keySet(); 
        for(Integer e: st)
        {
            if(hm.get(e)>nums.length/3)
                ans.add(e);
        }
        return ans;
    }

    // there will be atmax 2 majority elements
    // example for n=16 n/3=5 , for a majority element it shoud occur 6 times or more
    // assuming a is majority ele and b is another majority element then a will occur 6 times and b will occur 6 times (minimum for both)
    // 6+6 is 12 , 16-12=4 , so even though there are only 4 elemnts left and even if these 4 are same they are not majority hence there will be atmax 2 majority elements
    public List<Integer> majorityElement2Optimized(int[] nums) {
        // there will be atmax only 2 occurences pf the majority elements 
        // same algo as majority element 1 but repeated twice as there are only 2 majority elements
        int c1=0,c2=0,e1=-1,e2=-1; 
        int n=nums.length;
        List<Integer> ans=new ArrayList<>();
        for(int i=0;i<n;i++)
        {
            if(c1==0 && nums[i]!=e2)
            {
                c1=1;e1=nums[i];
            }
            else if(c2==0 && nums[i]!=e1)
            {
                c2=1;e2=nums[i];
            }
            else if(e1==nums[i])
                c1++;
            else if(e2==nums[i])
                c2++;
            else {
                c1--;c2--;}
        }
        // manual verification fo count being grater than n/3 impo step
        c1=0;c2=0;
        for(int i=0;i<n;i++)
        {
            if(nums[i]==e1){
                c1++;
            }
            else if(nums[i]==e2){
                c2++;
            }
        }
        if(c1>n/3)
            ans.add(e1);
        if(c2>n/3)
            ans.add(e2);

        return ans;
    
        
    }

    // sort given arrays of numbers 0,1,2 without using built in function
    // use count sort to count frequency and then store in original array
    // can be optimised by using dutch national flag algorithm
    public void sortColors(int[] nums) {
        // count sort 
        int count0=0,count1=0,count2=0;
        for(int num: nums)
        {
            if(num==0)
                count0++;
            else if(num==1)
                count1++;
            else
                count2++;
        }
        int i=0,j=0;
        while(j<count0){
            nums[i++]=0;
            j++;
        }
        j=0;
        while(j<count1){
            nums[i++]=1;
            j++;
        }
        j=0;
        while(j<count2){
            nums[i++]=2;
            j++;
        }
    }

    // leetcode 1299 replace elements with greatest element on right side
    public int[] replaceElements(int[] arr) {
        int n=arr.length;
        int ans[]=new int[n];
        int max=-1;
        ans[n-1]=max;
        for(int i=n-2;i>=0;i--)
        {
            max=Math.max(max,arr[i+1]);
            ans[i]=max;
        }
        return ans;
    }

    // leetcode 2149. Rearrange Array Elements by Sign
    public int[] rearrangeArray(int[] nums) {
        // brute force : two arrs one for storing positive other for negative , loop once in nums add in order for both positive and negative, combine both the arrays by each index one by one Time O(2n) space O(n)
        // little optimal : Time O(n) space O(n), since arr to begin with positive number and follow pos,neg,pos,neg format , we can say that every pos number will be at even index and every ne number will be at an odd index
        int pos=0,neg=1,n=nums.length;
        int arr[]=new int[n];
        for(int i=0;i<n;i++)
        {
            if(nums[i]>0)
            {
                arr[pos]=nums[i];
                pos +=2;
            }
            else{
                arr[neg]=nums[i];
                neg +=2;
            }
            
        }

        return arr;
    }

    // leetcode 31. Next Permutation
    /**
     * 
     * 2,1,5,4,3,0,0 
    1. Longer prefix matching first , find a breaking point where a[I] < a[I+1] , break point here is between 1,5 where a(i)=1 
        1. If we did not find this breaking point that means this array is the greatest permutation, reverse to get the answer.
    2. Post that find an element on the right side of a[I] which is greater than  a[I] but the smallest greater element . In this case among 5,4,3 it is 3 
    3. a(i) will be come this greatest element ie 3 .
    4. Post that we have 5,4,1,0,0 remaining, sort these remaining numbers in ascending order to get the final answer as 2,3,0,0,1,4,5. 
        1. Or instead of sorting we already knew that since the breaking point is where we see the dip from the increasing order, som simply reverse all the remains elements
        2. 5,4,1,0,0 is what we get after swapping 3 and 1 , since this arrangement is already increasing we can just reverse the remaining to get the array. 
     */
    public void nextPermutation(int[] nums) {
        // step 1 : longest prefix matching and finding the break point.
        int n=nums.length;
        int index=-1;
        for(int i=n-2;i>=0;i--)
        {
            if(nums[i]<nums[i+1])
            {
                index=i;
                break; // do not forget 
            }
        }
        if (index==-1) // no breaking point found this means we are at highest possible permutation
        {
            reverse(nums,0,n-1);
            return;
        }

        // step 2 : swap element at index with the element which is the samllest greater number than with index
        for(int i=n-1;i>=index;i--)
        {
            if(nums[i]>nums[index])
            {
                int s=nums[i];
                nums[i]=nums[index];
                nums[index]=s;
                break; // do not forget.
            }
        }

        // step 3 : reverse all the numbers from index to n. all numbers from n-1 to index+1 are always in increasing order reverse to get the array
        reverse(nums,index+1,n-1);

    }
    public void reverse(int[] nums,int i,int j) // two pointers to reverse array
    {
        while (i < j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            i++;
            j--;
        }
    }

    // leetcode 56. Merge Intervals
    // [[1,1],[2,2],[0,0],[2,3],[1,3],[3,5],[2,3],[3,5]]
    // [[0,0],[1,1],[1,3],[2,2],[2,3],[2,3],[3,5],[3,5]]
    // [[0,0],[1,1],[1,3],[3,5],[3,5]] 
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        List<int[]> ans=new ArrayList<>();
        int p[]=intervals[0];
        for(int i=1;i<intervals.length;i++)
        {
            if(p[1]>= intervals[i][0]) // able to merge then merge the interval in p arr
            {
               p[1]= Math.max(intervals[i][1],p[1]);
            }
            else // if merge not possible just add to results
            {
                ans.add(p);
                p=intervals[i];
            }

        }
        ans.add(p);
        return ans.toArray(new int[ans.size()][]);
    }
// For rotation of matrix : 90 = transpose + reverse row
// 180 = reverse row + reverse column
// 270 = transpose + reverse col

    // leetcode 48
    public void rotate(int[][] matrix) {
        // For rotation of matrix : 90 = transpose + reverse row
        int m=matrix.length;
        for(int i=0;i<m;i++)
        {
            for(int j=i;j<m;j++)
            {
                int s=matrix[i][j];
                matrix[i][j]=matrix[j][i];
                matrix[j][i]=s;
            }
        }

        for(int i=0;i<m;i++)
        {
            for(int j=0;j<m/2;j++)
            {
                int s=matrix[i][j];
                matrix[i][j]=matrix[i][m-1-j];
                matrix[i][m-1-j]=s;
            }
        }
    }

    // leetcode 88 Merge Sorted Array
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int a=m-1,b=n-1,c=m+n-1;
        // merge conditions : b> a c-- b comes at pos c esle a>b c-- a comes in place of c
        while( b>=0)
        {
            if (a >= 0 && nums1[a] > nums2[b]) { // is done so that it does not compare when a beocmes -1.
                nums1[c--] = nums1[a--];
            } else {
                nums1[c--] = nums2[b--];
            }
        }
    }

    // leetcode 73

    public void setZeroes(int[][] matrix) {
        int n=matrix.length;
        int m=matrix[0].length;
        Set<Integer> r=new HashSet<>();
        Set<Integer> c=new HashSet<>(); // unique elements
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                if(matrix[i][j]==0)
                {
                    r.add(i);c.add(j);
                }
            }
        }
        if(r.isEmpty() && c.isEmpty())
            return;
            
        for(int i:r)
        {
            for(int j=0;j<matrix[0].length;j++)
            {
                matrix[i][j]=0;
            }
        }
        for(int j:c)
        {
            for(int i=0;i<matrix.length;i++)
            {
                matrix[i][j]=0;
            }
        }
    }

    public int longestConsecutive(int[] nums) {
        // Solution os to sort and count . keep track of the curent sequence and the longest sequence . n log n time complexity.
        // if(nums.length==1)
        //     return 1;
        // Arrays.sort(nums);
        // int cur=0,ans=0,prev=Integer.MIN_VALUE;

        // for(int i=0;i<nums.length;i++)
        // {
        //     if(nums[i]-1==prev)
        //     {
        //         cur++;
        //     }
        //     else if(nums[i]-1 >prev){
        //         cur=1;
        //     }
        //     prev=nums[i];
        //     ans=Math.max(ans,cur);
        // }
        // return ans;


        // Also correct soln: 
        HashSet<Integer> hs = new HashSet<>();
        for (int num : nums) {
            hs.add(num);
        }

        int ans = 0;

        for (int ele : hs) {
            // start only if ele is the beginning of a sequence
            if (!hs.contains(ele - 1)) {
                int cur = 1;
                int x = ele;

                while (hs.contains(x + 1)) {
                    x++;
                    cur++;
                }

                ans = Math.max(ans, cur);
            }
        }
        return ans;
    }
    
    public static void main(String[] args) {
        //int numbers[]={1,3,6,-1,3};
       // maxSubArrSum(numbers);
        //KadaneAlgo(numbers);
        //AppearTwice(numbers);
        // int prices[]={7,1,5,3,6,4};
        // System.out.println("Max profit is "+BestTimeToBuySellStock(prices));
        int height[]={1,8,6,2,5,4,8,3,7};
        //TrappedRainWater(height);
        int arr[]={1,2,3,4};
        System.out.println(generateSubarraysBruteForce(arr));
        System.out.println(generateSubarraysOptimized(arr));

    }
}
