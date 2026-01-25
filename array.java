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
    // letcode 560 Subarray Sum Equals K 
    public int subarraySum(int[] nums, int k) {
        HashMap<Integer,Integer> map=new HashMap<>();
            //(sum,index)
        map.put(0,1);
        int sum=0,ans=0;
        for(int j=0;j<nums.length;j++)
        {
            sum+=nums[j];
            if(map.containsKey(sum-k))
                ans+=map.get(sum-k);
            map.put(sum,map.getOrDefault(sum,0)+1);
        }
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

        return Math.max(maxCount,count);
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
    public void reverse(int[] nums,int i,int j)
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
