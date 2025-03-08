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
        int prefixArray[]= new int[nums.length];//use curr sum and prefix sumarray to store the sum of all the elements uptil that index initially
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
    // Container with most water leetcode 11
    public static int maxArea(int[] height) {
        int maxWater=0; // brute force O(n^2)
        for(int i=0;i<height.length-1;i++)
        {
            for(int j=i+1;j<height.length-1;i++)
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
    public static int findMin(int[] nums) {
        // Find minimum element in a rotated sorted array
        // input: nums = [4,5,6,7,0,1,2] Output: 0 input: nums= [3,4,5,1,2] Output: 1
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
            if(nums[start]<=nums[mid])
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

//     Given an array nums of size n, return the majority element.
//     The majority element is the element that appears more than ⌊n / 2⌋ times. 
//     You may assume that the majority element always exists in the array.
    public static int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length/2];
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
